package com.crash.james.fitness;

import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.crash.james.fitness.Model.Workout;
import com.crash.james.fitness.Model.WorkoutList;

import java.util.List;



public class WorkoutActivity extends AppCompatActivity {
    //TODO: Add workout timer
    //Receive new workout list

    List<Workout> mExercise;
    Workout currentExercise;
    WorkoutList workoutList = WorkoutList.getInstance();
    long runTime,curTime, restTime;
    long interval = 250;
    TextView txtClock, txtExercise, txtNextExercise;
    boolean resting = false;
    int intCurrentExercise = 0;
    short sSwitch = 1;  //1: !playing !paused, 2 Playing, 3 paused
    MyCountdown timer;
    FloatingActionButton fabPlayPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        txtClock = findViewById(R.id.txtClock);
        txtExercise = findViewById(R.id.txtCurrentExercise);
        txtNextExercise = findViewById(R.id.txtNext);
        fabPlayPause = findViewById(R.id.fabStart);

        mExercise = workoutList.getWorkouts(); //get workout list
        currentExercise = mExercise.get(intCurrentExercise);

        restTime = 30000; // ToDo: make this a setting option variable

        txtClock.setText(String.format("%1$02d%n : %2$02d%n : %3$02d%n", currentExercise.getTime()/60000, (currentExercise.getTime()/1000)%60 , 0));  //initial clock setting
        txtExercise.setText("Current Exercise: " + currentExercise.getName());
        txtNextExercise.setText("Next Exercise: " + mExercise.get(intCurrentExercise+1).getName());
    }

   public class MyCountdown extends CountDownTimer {

        public MyCountdown (long millisInFuture, long countDownInterval){
                super(millisInFuture, countDownInterval);
            }

           @Override
           public void onTick(long millisUntilFinished) {

               curTime = millisUntilFinished;
               long clockSec = (millisUntilFinished / 1000)%60;
               long clockMin = millisUntilFinished / 60000;
               long clockMSec = millisUntilFinished % 1000;
               Log.d("Timer:", "onTick: " + millisUntilFinished);
               txtClock.setText(String.format("%1$02d%n : %2$02d%n : %3$02d%n", clockMin, clockSec, clockMSec));
           }

           @Override
           public void onFinish() {
               //isPlaying = false;
               sSwitch = 1;
               fabPlayPause.setImageResource(R.drawable.mr_media_play_dark);
               if (intCurrentExercise < mExercise.size() - 1) {
                   intCurrentExercise++;
                   currentExercise = mExercise.get(intCurrentExercise);
               } else {
                   //ToDo: workout complete
               }

           }

   }

    public void startStop (View view) {

        switch (sSwitch) {
            case 1:
                runTime = 1000 * currentExercise.getTime();
                timer = new MyCountdown(runTime, interval);
                fabPlayPause.setImageResource(R.drawable.mr_media_pause_dark);
                timer.start();
                sSwitch = 2;
                Log.d("WorkoutActivity", "startStop: Playing");
                break;
            case 2:
                runTime = curTime;
                timer.cancel();
                fabPlayPause.setImageResource(R.drawable.mr_media_play_dark);
                sSwitch = 3;
                Log.d("WorkoutActivity", "startStop: Paused");
                break;
            case 3:
                timer = new MyCountdown(runTime, interval);
                timer.start();
                fabPlayPause.setImageResource(R.drawable.mr_media_pause_dark);
                sSwitch = 2;
                Log.d("WorkoutActivity", "startStop: Playing from pause");
                break;
        }
    }


    public void nextExercise(View view){
        timer.cancel();
       sSwitch = 1;

        if(intCurrentExercise < mExercise.size() - 1) {
            intCurrentExercise++;
            currentExercise = mExercise.get(intCurrentExercise);

        } else if (intCurrentExercise == mExercise.size() - 1){
            currentExercise = mExercise.get(0);
        }

        txtExercise.setText(getString(R.string.Exercise) + currentExercise.getName());

        if (intCurrentExercise == mExercise.size() - 1 ) {
            txtNextExercise.setText(mExercise.get(0).getName());
        } else {
            txtNextExercise.setText("Next Exercise: " + mExercise.get(intCurrentExercise + 1).getName());
        }
    }

    public void prevExercise(View view){
        timer.cancel();
        sSwitch = 1;
        //isPlaying = false;
        //isPaused = false;
        if(intCurrentExercise > 0) {
            intCurrentExercise--;
            currentExercise = mExercise.get(intCurrentExercise);
        } else if (intCurrentExercise == 0) {
            currentExercise = mExercise.get(mExercise.size()-1);
        }
        txtExercise.setText("Current Exercise: " + currentExercise.getName());

        txtNextExercise.setText("Next Exercise: " + mExercise.get(intCurrentExercise + 1).getName());

    }


}//end class
