package a.af.zieball;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import a.af.zieball.adapters.ForsideAdapter;
import a.af.zieball.classes.Afdeling;
import a.af.zieball.classes.User;
import a.af.zieball.fragments.StatsPages;

public class Scoreboard extends AppCompatActivity {

    private String TAG = "ZB DEBUG";
    private ArrayList<User> myUsers = new ArrayList<>();
    private ArrayList<Afdeling> myAf = new ArrayList<>();

    private Boolean[] truthArray = new Boolean[6];
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_scoreboard);

        for(int i = 0; i<6;i++){
            truthArray[i]=false;
        }
        database = FirebaseDatabase.getInstance();

        final DatabaseReference afdelinger = database.getReference("Afdelinger");

        DatabaseReference myAUser = database.getReference("a");
        myAUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                truthArray[0] = (boolean)dataSnapshot.getValue();
                checkPlayers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference myBUser = database.getReference("b");
        myBUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                truthArray[1] = (boolean)dataSnapshot.getValue();
                checkPlayers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference myCUser = database.getReference("c");
        myCUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                truthArray[2] = (boolean)dataSnapshot.getValue();
                checkPlayers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference myDUser = database.getReference("d");
        myDUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                truthArray[3] = (boolean)dataSnapshot.getValue();
                checkPlayers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference myeUser = database.getReference("e");
        myeUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                truthArray[4] = (boolean)dataSnapshot.getValue();
                checkPlayers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference myfUser = database.getReference("f");
        myfUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                truthArray[5] = (boolean)dataSnapshot.getValue();
                checkPlayers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        //AFDELINGER ###
        afdelinger.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated;
                myAf = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Afdeling afdeling = new Afdeling();
                    afdeling.setName(snapshot.getValue(String.class));
                    myAf.add(afdeling);
                    Log.e(TAG,afdeling.toString());
                }

                bolAfdelinger = true;
                createGrid();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        //USERS ###
        DatabaseReference users = database.getReference("users");

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myUsers = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    Log.e(TAG,user.toString()+"");
                    myUsers.add(user);
                }

                bolUsers = true;
                createGrid();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });
    }

    private void createGrid(){
        if(bolAfdelinger && bolUsers) {
            for(Afdeling a : myAf){
                a.setTotalScore(0);
            }
            for(User u : myUsers){
                for(Afdeling a : myAf){
                    if(u.getAfdeling().equals(a.getName())){
                        a.setTotalScore(a.getTotalScore()+u.getScore());
                    }
                }
            }


            GridView gridView = (GridView) findViewById(R.id.gridview);
            ForsideAdapter forsideAdapter = new ForsideAdapter(this, myAf);
            gridView.setAdapter(forsideAdapter);
        }

    }


    private void checkPlayers(){
        int counter = 0;
        for(int i = 0; i<6; i++){
            if(truthArray[i]) counter++;
        }
        Log.e(TAG,"in Truth array: "+counter);
        if(counter>1){
            for(int i = 0; i<6; i++){
                if(truthArray[i]) myUsers.get(i).incScore();
            }
            DatabaseReference theUsers = database.getReference("users");
            int i=0;
            for(User u : myUsers){
                theUsers.child(i+"").setValue(u);
                i++;
            }
            database.getReference("a").setValue(false);
            database.getReference("b").setValue(false);
            database.getReference("c").setValue(false);
            database.getReference("d").setValue(false);
            database.getReference("e").setValue(false);
            database.getReference("f").setValue(false);
            for(int j = 0; j<6;j++){
                truthArray[j]=false;
            }
        }
    }

    private boolean bolAfdelinger = false;
    private boolean bolUsers = false;

    public void openStats(int totalScore, String name){
        Boolean isThereAFragment = getSupportFragmentManager().findFragmentByTag("stats") == null;
        if(isThereAFragment) {
            Fragment fragment = new StatsPages();
            Bundle args = new Bundle();
            args.putInt("score",totalScore);
            args.putString("name",name);

            fragment.setArguments(args);
            replaceFragment(fragment,"stats");
        }
    }

    public void replaceFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer, fragment,tag);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
}
