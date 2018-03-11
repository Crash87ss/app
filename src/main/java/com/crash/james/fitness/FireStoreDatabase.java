package com.crash.james.fitness;

/**
 * Created by James on 11/29/2017.
 */


import android.support.annotation.NonNull;
import android.util.Log;

import com.crash.james.fitness.Model.Workout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;


public class FireStoreDatabase {


    //private String workoutSelected;
    //public Workout oWorkoutSelected;
    //public String workoutname;
    ArrayList<String> groupDocs = new ArrayList<>();
    Object a;

    //constructors
    public FireStoreDatabase() {
    }

    //Get Database instance.
     FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    //Get list of available groups
    CollectionReference workoutList = mFirestore.collection("workouts");


    // TODO: Add method to set workouts

    // TODO: Add method to get a workout

/*private void getGroups (){
        workoutList.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()){
                for(DocumentSnapshot document : task.getResult()) {
                    groupDocs.add(document.getId());
                }

            }
        }
    });
}

private void



    boolean isComplete;
     public ArrayList buildWorkout(ArrayList<String> group)  {

        final ArrayList<String> muscleArray = new ArrayList<>();
        CollectionReference collectionReference = mFirestore.collection("workouts");

        for (String l : group){
            collectionReference.document(l).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot d = task.getResult();
                    muscleArray.add(d.getString("muscles"));
                    isComplete = task.isComplete();
                    Log.i("doneit", muscleArray.toString());
                    }

            });

        }
        Log.i("Build Workout","Muscle Returning Now");
        return muscleArray;

    }*/



    //Add a routine
    // TODO: Add method to set routines

    //Get a routine
    // TODO: Add method to get a routine

    //Add a user
    // TODO: Add method to set a user

    //Get a user
    // TODO: Add a method to get a user

}
