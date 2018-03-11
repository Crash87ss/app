package com.crash.james.fitness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void mSelectWorkout(View view){
        Intent i = new Intent(this, SelectActivity.class);
        startActivity(i);
            }
/*
        Button bSelectWorkout = findViewById(R.id.selectWorkout);
        Button bLogin = findViewById(R.id.login);

        bSelectWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SelectActivity.class);
                startActivity(i);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), TestACtivity.class);
                startActivity(i);
            }
        });*/

    }

