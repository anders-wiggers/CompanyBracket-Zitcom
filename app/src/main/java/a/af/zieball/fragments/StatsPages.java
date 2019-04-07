package a.af.zieball.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import a.af.zieball.R;



public class StatsPages extends Fragment {
    private int score;
    private String name;
    private PieChart pieChart;


    public StatsPages() {
    }


    private void createAndInitializePieChartOfParties(View view, int[] nums){
        //Initialize pieChart
        pieChart = view.findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setHoleRadius(70f);
        pieChart.setTransparentCircleRadius(30f);

        //Add entries
        ArrayList numbs = new ArrayList();

        numbs.add(new PieEntry(nums[0]));
        numbs.add(new PieEntry(nums[1]));
        numbs.add(new PieEntry(nums[2]));
        numbs.add(new PieEntry(nums[3]));

        ArrayList names = new ArrayList();

        names.add("Fitness");
        names.add("Table football");
        names.add("Friday Bar");
        names.add("In-team");


        final int[] MY_COLORS = {
                Color.  rgb(142,92,203),
                Color. rgb(36,174,252),
                Color.  rgb(0,107,167),
                Color. rgb(67,224,170)
        };

        ArrayList<Integer> colors = new ArrayList<>();

        for(int c: MY_COLORS) colors.add(c);




        PieDataSet pieDataSet = new PieDataSet(numbs, "");
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setColors(colors);

        pieDataSet.setSelectionShift(5f);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.WHITE);





        pieChart.setData(pieData);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats_pages,container,false);

        TextView name = view.findViewById(R.id.textView4);
        name.setText(getArguments().getString("name"));
        TextView points = view.findViewById(R.id.textView6);
        points.setText(getArguments().getInt("score")+"");


        int[] nums = new int[4];

        int total = 100;
        Random rand = new Random();

        for(int i = 0; i<nums.length-1;i++){
            nums[i] = rand.nextInt(total);
            total -= nums[i];
        }
        nums[nums.length-1]=total;

        TextView fitness = view.findViewById(R.id.fit);
        fitness.setText(nums[0]+"%");

        TextView table = view.findViewById(R.id.tab);
        table.setText(""+nums[1]+"%");

        TextView friday = view.findViewById(R.id.textView13);
        friday.setText(""+nums[2]+"%");

        TextView in = view.findViewById(R.id.textView12);
        in.setText(""+nums[3]+"%");

        createAndInitializePieChartOfParties(view,nums);

        return view;
    }
}
