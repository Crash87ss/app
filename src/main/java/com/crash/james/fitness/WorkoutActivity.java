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
import java.util.Locale;


public class WorkoutActivity extends AppCompatActivity {
    //TODO: Add workout timer
    //Receive new workout list

    List<Workout> mExercise;
    Workout currentExercise;
    WorkoutList workoutList = WorkoutList.getInstance();
    long runTime,curTime, restTime, restTimeMultiplier, secondsUntilFinished, minutesUntilFinished;
    long interval = 250;
    TextView txtClock, txtExercise, txtNextExercise, txtTimeRemaining;
    boolean resting = true;
    int intCurrentExercise = 0;
    int intCurSet;
    short sSwitch = 1;  //1: !playing !paused, 2 Playing, 3 paused
    MyCountdown timer;
    TotalCountDown totalCountDown;
    FloatingActionButton fabPlayPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        txtClock = findViewById(R.id.txtClock);
        txtTimeRemaining = findViewById(R.id.txtTimeRemaining);
        txtExercise = findViewById(R.id.txtCurrentExercise);
        txtNextExercise = findViewById(R.id.txtNext);
        fabPlayPause = findViewById(R.id.fabStart);

        mExercise = workoutList.getWorkouts(); //get workout list
        currentExercise = mExercise.get(intCurrentExercise);
        intCurSet = 1;

        //restTime = 30000; // ToDo: make this a setting option variable

        txtClock.setText(String.format(Locale.US,"%1$02d%n : %2$02d%n : %3$02d%n", currentExercise.getTime()/60000, (currentExercise.getTime()/1000)%60 , 0));  //initial clock setting
        //txtExercise.setText("Current Exercise: " + currentExercise.getName() + "Set: " + intCurSet + "of " + currentExercise.getSets());
        txtExercise.setText(getString(R.string.current_exercise,currentExercise.getName(),intCurSet,currentExercise.getSets()));
        //txtNextExercise.setText("Next Exercise: " + mExercise.get(intCurrentExercise+1).getName());
        txtNextExercise.setText(getString(R.string.next_exercise,mExercise.get(intCurrentExercise+1).getName()));

        //Sum total time in the workout
        for (int i = 0; i < mExercise.size(); i++){
            secondsUntilFinished += mExercise.get(i).getTime() * mExercise.get(i).getSets() + restTime / 1000 * mExercise.get(i).getSets();
        }

        minutesUntilFinished = secondsUntilFinished / 60;
        secondsUntilFinished = secondsUntilFinished % 60;

        txtTimeRemaining.setText(getString(R.string.time_Remaining, minutesUntilFinished,secondsUntilFinished));
    }

   public class MyCountdown extends CountDownTimer {

         MyCountdown (long millisInFuture, long countDownInterval){
                super(millisInFuture, countDownInterval);
            }

           @Override
           public void onTick(long millisUntilFinished) {

               curTime = millisUntilFinished;
               long clockSec = (millisUntilFinished / 1000)%60;
               long clockMin = millisUntilFinished / 60000;
               long clockMSec = millisUntilFinished % 1000;
               Log.d("Timer:", "onTick: " + millisUntilFinished);
               txtClock.setText(String.format(Locale.US,"%1$02d%n : %2$02d%n : %3$02d%n", clockMin, clockSec, clockMSec));
           }

           @Override
           public void onFinish() {
               //sSwitch = 1;
               //fabPlayPause.setImageResource(R.drawable.mr_media_play_dark);
               if(intCurrentExercise == mExercise.size() - 1){
                   //ToDo: workout is complete
               } else if (intCurSet == currentExercise.getSets() && !resting) {
                   intCurrentExercise++;
                   currentExercise = mExercise.get(intCurrentExercise);
                   txtExercise.setText(getString(R.string.current_exercise,currentExercise.getName(),intCurSet,currentExercise.getSets()));
                   txtNextExercise.setText(getString(R.string.next_exercise,mExercise.get(intCurrentExercise+1).getName()));
                   resting = true;
                   runTime = 1000 * currentExercise.getTime();
                   timer = new MyCountdown(runTime, interval);
                   timer.start();
                } else if(intCurSet < currentExercise.getSets() && !resting){
                   curTime = currentExercise.getTime();
                   txtExercise.setText(getString(R.string.current_exercise,currentExercise.getName(),intCurSet,currentExercise.getSets()));
                   txtNextExercise.setText(getString(R.string.next_exercise,mExercise.get(intCurrentExercise+1).getName()));
                   resting = true;
                   runTime = 1000 * currentExercise.getTime();
                   timer = new MyCountdown(runTime, interval);
                   timer.start();
               } else if(intCurSet <= currentExercise.getSets() && resting) {
                   //curTime = currentExercise.getTime() / 5; //time in ms
                   resting = false;
                   restTime = currentExercise.getTime() * 200;
                   if(intCurSet < currentExercise.getSets()) {
                       intCurSet++;
                   }
                   txtExercise.setText("Rest");
                   txtNextExercise.setText(getString(R.string.next_exercise,mExercise.get(intCurrentExercise+1).getName()));
                   runTime = restTime;
                   timer = new MyCountdown(runTime, interval);
                   timer.start();
               }else{
                   //ToDo: Error
               }


           }

   } //end MyCountdownClass

    public class TotalCountDown extends CountDownTimer{

        TotalCountDown (long millisInFuture, long countDownInterval){
            super(millisInFuture,countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished){

            minutesUntilFinished = millisUntilFinished / 60000;
            secondsUntilFinished = (millisUntilFinished / 1000) % 60;

            txtTimeRemaining.setText(getString(R.string.time_Remaining, minutesUntilFinished,secondsUntilFinished));
        }

        @Override
        public  void onFinish(){

        }
    }

    public void startStop (View view) {

        switch (sSwitch) {
            case 1:
                runTime = 1000 * currentExercise.getTime();
                timer = new MyCountdown(runTime, interval);
                totalCountDown = new TotalCountDown((secondsUntilFinished + (minutesUntilFinished *60)) * 1000, 1000);
                fabPlayPause.setImageResource(R.drawable.mr_media_pause_dark);
                timer.start();
                totalCountDown.start();
                sSwitch = 2;
                Log.d("WorkoutActivity", "startStop: Playing");
                break;
            case 2:
                runTime = curTime;
                timer.cancel();
                totalCountDown.cancel();
                fabPlayPause.setImageResource(R.drawable.mr_media_play_dark);
                sSwitch = 3;
                Log.d("WorkoutActivity", "startStop: Paused");
                break;
            case 3:
                timer = new MyCountdown(runTime, interval);
                timer.start();
                totalCountDown.start();
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

        intCurSet = 1;
        txtExercise.setText(getString(R.string.current_exercise,currentExercise.getName(),intCurSet,currentExercise.getSets()));

        if (intCurrentExercise == mExercise.size() - 1 ) {
            txtNextExercise.setText(mExercise.get(0).getName());
        } else {
            txtNextExercise.setText(getString(R.string.next_exercise,mExercise.get(intCurrentExercise+1).getName()));
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

        intCurSet = 1;
        txtExercise.setText(getString(R.string.current_exercise,currentExercise.getName(),intCurSet,currentExercise.getSets()));

        txtNextExercise.setText(getString(R.string.next_exercise,mExercise.get(intCurrentExercise+1).getName()));

    }


}//end class
