package com.crash.james.fitness;

import android.support.annotation.NonNull;
import android.util.Log;

import com.crash.james.fitness.Model.Workout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by James on 1/28/2018.
 */

public class WorkoutQuery {





    private boolean armsComp, legsComp, absComp, chestComp, backComp, qFailed;
    ArrayList<Workout> arms = new ArrayList<>();
    ArrayList<Workout> legs = new ArrayList<>();
    ArrayList<Workout> abs = new ArrayList<>();
    ArrayList<Workout> chest = new ArrayList<>();
    ArrayList<Workout> back = new ArrayList<>();
    Map<String,Boolean> include = new HashMap<>();



    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference dbworkouts = db.collection("workouts");

    //Listener Variable
    private WorkoutQueryListener listener;

    //constructor no args
    public WorkoutQuery(Map<String,Boolean> include){
        this.listener = null;
        this.include = include;
        QueryDatabase();
    }

    //listener interface
    public interface WorkoutQueryListener{
        //TODO: Pass the workout objects through the onWorkoutLoaded() method
        //TODO: Fire the listener by calling listener.onWorkoutLoaded(data) where data is the information to pass
        void onWorkoutLoaded(ArrayList<Workout> workoutArray, String name);
        void onWorkoutLoadFail(String failedQuery);

    }

    //Setter for listener
    public void setWorkoutQueryListener(WorkoutQueryListener listener){
        this.listener = listener;
    }

    public void QueryDatabase(){
    //TODO: future improvment - only query database for selected items
// Query methods

        if(include.get("arms")) {
            Query qArms = dbworkouts.whereEqualTo("group", "arms");
            qArms.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                arms.add(doc.toObject(Workout.class));
                            }
                            armsComp = true;
                            listener.onWorkoutLoaded(arms, "arms");

                        }
                    } else {
                        Log.i("Arms Query", "Database Error");
                        qFailed = true;
                        listener.onWorkoutLoadFail("Arms");
                    }
                }
            });
        }

        if (include.get("legs")) {
            Query qLegs = dbworkouts.whereEqualTo("group", "legs");
            qLegs.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                legs.add(doc.toObject(Workout.class));
                            }
                            legsComp = true;
                            listener.onWorkoutLoaded(legs, "legs");

                        }
                    } else {
                        Log.i("Legs Query", "Document not Found");
                        qFailed = true;
                        listener.onWorkoutLoadFail("Legs");
                    }
                }
            });
        }

        if (include.get("abs")) {
            Query qAbs = dbworkouts.whereEqualTo("group", "abs");
            qAbs.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                abs.add(doc.toObject(Workout.class));
                            }
                            absComp = true;
                            listener.onWorkoutLoaded(abs, "abs");
                        }
                    } else {
                        Log.i("Abs Query", "Document not Found");
                        qFailed = true;
                        listener.onWorkoutLoadFail("abs");
                    }
                }
            });
        }
        if (include.get("chest")) {
            Query qChest = dbworkouts.whereEqualTo("group", "chest");
            qChest.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                chest.add(doc.toObject(Workout.class));
                            }
                            chestComp = true;
                            listener.onWorkoutLoaded(chest, "chest");
                }
            } else {
                Log.i("Chest Query", "Document not Found");
                qFailed = true;
                listener.onWorkoutLoadFail("chest");
            }
        }
    });
}
        if (include.get("back")) {
            Query qBack = dbworkouts.whereEqualTo("group", "back");
            qBack.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                back.add(doc.toObject(Workout.class));
                            }
                            backComp = true;
                            listener.onWorkoutLoaded(back, "back");
                        }
                    } else {
                        Log.i("Back Query", "Document not Found");
                        qFailed = true;
                        listener.onWorkoutLoadFail("back");
                    }
                }
            });
        }

                        if (qFailed){
        //TODO: do somthign about a failed query
        Log.i("Query", "Something went wrong!");
    }

    // TODO: Async a progress bar.
    }
}
