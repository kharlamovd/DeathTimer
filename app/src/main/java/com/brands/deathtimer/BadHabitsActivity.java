package com.brands.deathtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class BadHabitsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bad_habits);
    }

    /*private Intent getBadHabits() {
        Intent intent = new Intent(BadHabitsActivity.this, TimeLeftActivity.class);
        intent.putExtra("message", message);
        startActivity(intent);
    }*/

}