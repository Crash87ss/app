package com.crash.james.fitness.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by James on 12/1/2017.
 */

public class Workout implements Parcelable {

    private String wid, name, notes, mods, muscle, group;
    private int time, sets;
    private Boolean preset;  //true for a workout created by admin, false for user workout

    public Workout() {}

    //
    public Workout(String group,String muscle, String name, int sets, int time) {
        this.wid = wid;
        this.name = name;
        this.time = time;
        this.sets = sets;
        this.notes = notes;
        this.mods = mods;
        this.preset = preset;
        this.group = group;
        this.muscle = muscle;
    }

    //setters and getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getSets() {
        return sets;
    }

    public void setReps(int sets) {
        this.sets = sets;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getMods() {
        return mods;
    }

    public void setMods(String mods) {
        this.mods = mods;
    }

    public Boolean getPreset() {
        return preset;
    }

      public void setPreset(boolean preset) {
        this.preset = preset;}

    public String getMuscle(){return muscle;}

    public void setMuscle (String muscle){this.muscle = muscle;}

    public String getGroup(){return group;}

    public void setGroup(String group){this.group = group;}

//parcelable interface

public Workout(Parcel in){
        String[] data = new String[5];

        in.readStringArray(data);
        // Ensure the order is the same as writeToParcel() method
    //String setsString = String.valueOf(this.sets);
    //String timeString = String .valueOf(this.time);

    this.group = data[0];
    this.muscle = data[1];
    this.name = data[2];
    this.sets = Integer.parseInt(data[3]);
    this.time = Integer.parseInt(data[4]);

}

@Override
    public int describeContents(){
        return 0;
}

@Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeStringArray(new String[]{this.group, this.muscle, this.name,String.valueOf(this.sets),String.valueOf(this.time)});
}

public static final Parcelable.Creator<Workout> CREATOR = new Parcelable.Creator<Workout>(){

        @Override
        public Workout createFromParcel(Parcel in){
            return new Workout(in);
        }
        @Override
        public Workout[] newArray(int size){
            return new Workout[size];
        }
};

}//end class


