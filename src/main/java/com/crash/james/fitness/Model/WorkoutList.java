package com.crash.james.fitness.Model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 2/11/2018.
 */

public class WorkoutList  {
    private static WorkoutList sWorkoutlist;

    private List<Workout> mWorkouts;

    public static WorkoutList getInstance()
    {
        if (sWorkoutlist == null) {
            sWorkoutlist = new WorkoutList();
        }
        return sWorkoutlist;
    }

    private WorkoutList() {
        mWorkouts = new ArrayList<>();
    }

    public void addWorkout(Workout workout) {
        mWorkouts.add(workout);
    }

    public void addList(ArrayList<Workout> workouts){
        if (mWorkouts.isEmpty()){
            mWorkouts.addAll(workouts);
        } else {
            mWorkouts.clear();
            mWorkouts.addAll(workouts);
        }
    }  //May want to expand this to check if object exist and add them if not.

    public void removeWorkout(Workout workout) {
        mWorkouts.remove(workout);
    }

    public List<Workout> getWorkouts(){
        return mWorkouts;
    }
}
