package com.crash.james.fitness;



import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import android.widget.Button;
import android.widget.ListView;

import com.crash.james.fitness.Model.Workout;



import java.util.ArrayList;

/**
 * Created by James on 1/6/2018.
 */

public class Fragment_WorkoutDialog extends DialogFragment implements OnClickListener {

    public Fragment_WorkoutDialog(){}

   private ListView mylist;
   private Button ok_Button, cancel_Button;
   private ArrayList<Workout> exercises = new ArrayList<>();


    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments()!= null){
            exercises = getArguments().getParcelableArrayList("exercises");
            /*TODO: Get the workout object sent through the bundle.
            the object model has methods to create a parcelable object*/
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState){

        View view = inflator.inflate(R.layout.fragment_workoutdialog,null,false);
        mylist = view.findViewById(R.id.list);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        ok_Button = view.findViewById(R.id.go);
        cancel_Button = view.findViewById(R.id.cancel);
        ok_Button.setOnClickListener(this);
        cancel_Button.setOnClickListener(this);


            return view;
    }

    @Override
    public void  onActivityCreated(Bundle savedInstanceState){

        super.onActivityCreated(savedInstanceState);

        //ArrayAdapter<String> adapter4558 = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,listitems);

        //TODO: Finish the adapter by placing the proper arguments from teh workout object.
        // Since this might be an array of objects I need to make sure the adapter can handle it.
        Workout_Adapter adapter = new Workout_Adapter(getActivity(),exercises);
        mylist.setAdapter(adapter);


    }

    @Override
    public void onClick(View view){

        switch (view.getId()){
            case R.id.go:
                Intent intent = new Intent(getContext(), WorkoutActivity.class);
                startActivity(intent);

                //TODO: Package data and Start "Workout Activity"
                break;
            case R.id.cancel:
                dismiss();
                break;
        }


    }
}//end class
