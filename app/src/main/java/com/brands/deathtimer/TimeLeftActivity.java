package com.brands.deathtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageButton;
import android.widget.TextView;

import com.brands.deathtimer.nav_btns_listeners.BackOnClick;
import com.brands.deathtimer.nav_btns_listeners.SettingsOnClick;

import java.util.Date;

public class TimeLeftActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_left);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new BackOnClick(this));
        ImageButton settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new SettingsOnClick());
    }

    /*private long calculateDeathDate() {
        Intent intent = getIntent();

        if (SettingsActivity.isDeathDateAuto(this)) {

        } else {
            int minusYears = intent.getIntExtra("minusYears", 0);
            Date bday = (Date) intent.getSerializableExtra("bday");
            Date dday = (Date) intent.getSerializableExtra("dday");


        }
    }*/

    private void setCountDownTimer(TextView daysLeftTextView, TextView timeLeftTextView) {
        String daysStr = getString(R.string.days);

        new CountDownTimer(30000, 1000) {
            private long days, hours, min, sec;

            public void onTick(long millisUntilFinished) {
                long now = System.currentTimeMillis();

                /*days = (now - millisUntilFinished) / 1000 / 3600 / 24;
                hours = (now - millisUntilFinished) % ;
                min = ;
                sec = ;*/

                daysLeftTextView.setText(days + daysStr);
                timeLeftTextView.setText(hours + ":" + min + ":" + sec);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                daysLeftTextView.setText(getString(R.string.your_dead));
                timeLeftTextView.setText("");

                TextView info = findViewById(R.id.textView6);
                info.setText("");
            }

        }.start();
    }
}