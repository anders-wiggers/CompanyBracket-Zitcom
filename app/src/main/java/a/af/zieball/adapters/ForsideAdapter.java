package a.af.zieball.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import a.af.zieball.R;
import a.af.zieball.Scoreboard;
import a.af.zieball.classes.Afdeling;

public class ForsideAdapter extends BaseAdapter {

    private final ArrayList<Afdeling> hold;
    private final Context context;

    public ForsideAdapter(Context context, ArrayList<Afdeling> hold){
        this.context = context;
        this.hold = hold;
    }

    @Override
    public int getCount() {
        return (hold.size()-1);
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.teams,null);

        TextView textView = view.findViewById(R.id.textView);
        textView.setText(hold.get(i).getName());
        final int score = hold.get(i).getTotalScore();
        final String name = hold.get(i).getName();

        TextView textView1 = view.findViewById(R.id.textView2);
        textView1.setText(hold.get(i).getTotalScore()+"");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Scoreboard)context).openStats(score,name);
            }
        });

        return view;
    }
}
