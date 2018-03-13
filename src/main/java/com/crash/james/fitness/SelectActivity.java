package com.crash.james.fitness;



import android.content.Context;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.crash.james.fitness.Model.Workout;
import com.crash.james.fitness.Model.WorkoutList;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class SelectActivity extends FragmentActivity {


  //  Button bGetWorkout = findViewById(R.id.bCreateWorkout);
    private int npTensValue;
    private int npMinsValue;
    private TextView tvSelection;
    private static final String TAG = "Muscle ";
    private ArrayList<String> groupDocs = new ArrayList<>();
    //ArrayList<String> include= new ArrayList<>();
    Map<String,Boolean> include = new HashMap<>();

    int time, warmuptime, cooldowntime, workouttime = 0; //time is in seconds
   // Map<String, Boolean> include;
    boolean warmup = false, cooldown = false;

    ArrayList<Workout> exercises = new ArrayList<>();
    ArrayList<Workout> exerciseSelection = new ArrayList<>();
    ArrayList<String>  querysCompleted = new ArrayList<>(5);
    ArrayList<String>  querysWaiting = new ArrayList<>(5);
    Random rand = new Random();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        //set initial key/value pairs for hashmap
        include.put("arms",false);
        include.put("legs",false);
        include.put("abs",false);
        include.put("chest",false);
        include.put("back",false);

        final NumberPicker npTens = findViewById(R.id.npTenMinutes);
        final NumberPicker npMins = findViewById(R.id.npMinutes);
        //Intent intent = getIntent();


        npTens.setMinValue(0);
        npTens.setMaxValue(9);
        npTens.setWrapSelectorWheel(true);

        final String[] mins = {"0", "5"};
        npMins.setMinValue(0);
        npMins.setMaxValue(mins.length - 1);
        npMins.setDisplayedValues(mins);
        npMins.setWrapSelectorWheel(true);


        npTens.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                npTensValue = newVal;
            }
        });

        npMins.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                npMinsValue = Integer.parseInt(mins[newVal]);
            }
        });


    }

    // Handle click events fot the checkboxes.
    public void onCheckboxClicked(View view){

            boolean checked = ((CheckBox) view).isChecked();
            CheckBox cbArms = findViewById(R.id.checkArms);
            CheckBox cbAbs = findViewById(R.id.checkAbs);
            CheckBox cbLegs = findViewById(R.id.checkLegs);
            CheckBox cbBack = findViewById(R.id.checkBack);
            CheckBox cbChest = findViewById(R.id.checkChest);
            CheckBox cbInclude = findViewById(R.id.checkIncludeAll);

                switch (view.getId()){
                    case R.id.checkArms:
                        if(checked){
                            include.put("arms",true);
                        } else{
                            include.put("arms",false);
                        }
                        break;

                    case R.id.checkAbs:
                        if(checked){
                            include.put("abs",true);
                        } else{
                            include.put("abs",false);
                        }
                        break;

                    case R.id.checkBack:
                        if(checked){
                            include.put("back",true);
                        } else{
                            include.put("back",false);
                        }
                        break;

                    case R.id.checkChest:
                        if(checked){
                            include.put("chest",true);
                        } else{
                            include.put("chest",false);
                        }
                        break;

                    case R.id.checkLegs:
                        if(checked){
                            include.put("legs",true);
                        } else{
                            include.put("legs",false);
                        }
                        break;

                    case R.id.checkIncludeAll:
                        if(checked){
                            include.put("arms",true);
                            include.put("abs",true);
                            include.put("back",true);
                            include.put("chest",true);
                            include.put("legs",true);

                            cbArms.setChecked(true);
                            cbAbs.setChecked(true);
                            cbBack.setChecked(true);
                            cbChest.setChecked(true);
                            cbLegs.setChecked(true);

                        } else{

                        }
                        break;


                } //end switch-case


        } //End onCheckboxClicked

    @Override
    public void onPause(){
            super.onPause();
            Log.d("SelectActivity", "onPause() called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d("SelectActivity", "onStop called");
    }

    @Override
    public void onDestroy(){
            super.onDestroy();
            Log.d("SelectActivity","OnStop() called");
    }


    // GO Button press event
    public void bGetWorkout (View view) {

         time = (npTensValue*10+npMinsValue)*60;
         warmup = false;
         cooldown = false;


        buildWorkout();
         Log.d("bGetWorkout", "Completed");
        // tvSelection.setText(Integer.toString(npTensValue*10 + npMinsValue));

            // }
         } //End bGetWorkout

// Shows a dialog with a list of the workouts that were selected.
    public void showDialog(Bundle exercises){

        FragmentManager manager = getSupportFragmentManager();
        DialogFragment workoutDialog = new Fragment_WorkoutDialog();
        Log.d("showDialog",this.getClass().getSimpleName());
        workoutDialog.setArguments(exercises);
       try {
           workoutDialog.show(manager, "exercises");
       }  catch (Exception e){
           e.printStackTrace();
           Log.d("showDialog",this.getClass().getSimpleName());
       }
    }

       void buildWorkout () {

   //Workouts Selected
       for(Map.Entry<String, Boolean> e : include.entrySet()){
           if (e.getValue()){
               querysWaiting.add(e.getKey());
           }
       }

    WorkoutQuery workoutQuery = new WorkoutQuery(include);

    workoutQuery.setWorkoutQueryListener(new WorkoutQuery.WorkoutQueryListener() {
    @Override
    public void onWorkoutLoaded(ArrayList<Workout> workoutArray, String name) {

        querysCompleted.add(name);
        exercises.addAll(workoutArray);


        if (querysCompleted.size() == querysWaiting.size()) {

          //Set time for warmup and cooldown exercised.
            if (warmup) {
                if (time / 4 > 300) {
                    warmuptime = 300;
                } else {
                    warmuptime = time / 4;
                }
            }

            if (cooldown) {
                if (time / 4 > 300) {
                    cooldowntime = 300;
                } else {
                    cooldowntime = time / 4;
                }
            }

            workouttime = time - warmuptime - cooldowntime;
            Log.i("Workout time is: ", Integer.toString(workouttime));
                /* Algorithm to build list of workouts. Attempts to add random exercises
                     to exerciseSelection until time is met   */
            int totalTime = 0;
            int i = 0;
            while (totalTime < workouttime) {

                exerciseSelection.add(exercises.get(rand.nextInt(exercises.size())));
                totalTime += exerciseSelection.get(i).getTime() * exerciseSelection.get(i).getSets();
                i++;
                if (i > 50) {
                    break;
                }
            }

            querysWaiting.clear();
            querysCompleted.clear();
            //Once complete open dialog fragment. Data is sent via a bundle
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("exercises", exerciseSelection);

            //The workout is added to singleton list. this should allow it to maintain state through activity changes.
            WorkoutList workoutList = WorkoutList.getInstance();
            workoutList.addList(exerciseSelection);

                Log.d("onWorkoutLoaded","Completed");
            //SelectActivity selectActivity = new SelectActivity();
            showDialog(bundle);


        }


    }

    @Override
    public void onWorkoutLoadFail(String failedQuery) {

    }
});


        } //end buildworkout method


} //End Class

