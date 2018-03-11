package com.crash.james.fitness;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.crash.james.fitness.Model.Workout;

import java.util.ArrayList;

/**
 * Created by James on 1/7/2018.
 */

public class Workout_Adapter extends ArrayAdapter<Workout> {


    private static class ViewHolder {
        TextView tvName;
        TextView tvSets;
        TextView tvTime;
    }


    Workout_Adapter(Context context, ArrayList<Workout> workout){
        super(context, R.layout.workout_adapter, workout);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Get the data item for this position
        Workout workouts = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;

        final View result;

        if(convertView == null){
            viewHolder = new ViewHolder();
            //LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.workout_adapter, parent,false);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.firstLine);
            viewHolder.tvSets = convertView.findViewById(R.id.secondLine);
            viewHolder.tvTime = convertView.findViewById(R.id.thirdLine);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.tvName.setText("Activity: " + workouts.getName());
        viewHolder.tvTime.setText("Time per Set: " + String.valueOf(workouts.getTime()));
        viewHolder.tvSets.setText("Number of Sets: " + String.valueOf(workouts.getSets()));

        return convertView;
    }
}
