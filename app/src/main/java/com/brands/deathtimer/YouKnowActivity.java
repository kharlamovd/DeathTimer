package com.brands.deathtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Date;

public class YouKnowActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_know);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.yesButton:
                onButtonClick(EnterDeathDayActivity.class);
                break;
            case R.id.skipButton:
                onButtonClick(TimeLeftActivity.class);
                break;
            default:
                break;
        }
    }

    private void onButtonClick(Class nextActivity) {
        Intent intent = getIntent();

        int minusYears = intent.getIntExtra("minusYears", 0);
        Date bday = (Date) intent.getSerializableExtra("bday");

        intent = new Intent(this, nextActivity);
        intent.putExtra("bday", bday);
        intent.putExtra("minusYears", minusYears);

        startActivity(intent);
    }

}