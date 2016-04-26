package com.example.yggdralisk.kundomondo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yggdralisk.kundomondo.MapActivity;
import com.example.yggdralisk.kundomondo.R;
import com.example.yggdralisk.kundomondo.backEnd.DataGetter;
import com.example.yggdralisk.kundomondo.entities.Run;

import java.util.List;

/**
 * Created by yggdralisk on 17.04.16.
 */
public class RunsAdapter extends RecyclerView.Adapter<RunsAdapter.RunViewHolder> {
    List<Run> runsList;
    MapActivity upActivity;
    DataGetter dataGetter;

    public RunsAdapter(List<Run> runsList, MapActivity upActivity, DataGetter dataGetter) {
        this.runsList = runsList;
        this.upActivity = upActivity;
        this.dataGetter = dataGetter;
    }

    @Override
    public RunsAdapter.RunViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.run_element, parent, false);
        RunViewHolder r = new RunViewHolder(v);
        return r;
    }

    @Override
    public void onBindViewHolder(RunsAdapter.RunViewHolder holder, int position) {
        holder.setData(runsList.get(position));
    }

    @Override
    public int getItemCount() {
        return runsList.size();
    }

    public class RunViewHolder extends RecyclerView.ViewHolder {
        TextView runTime;
        TextView runCalories;
        TextView runDistance;

        public RunViewHolder(View itemView) {
            super(itemView);
            runTime = (TextView) itemView.findViewById(R.id.run_time);
            runCalories = (TextView) itemView.findViewById(R.id.run_calories);
            runDistance = (TextView) itemView.findViewById(R.id.run_distance);
        }

        public void setData(Run run) {
            runTime.setText(run.getTime());
            runDistance.setText(Double.toString(run.getDistance()));
            runCalories.setText(Double.toString(run.getCaloriesBurned()));
        }
    }
}
