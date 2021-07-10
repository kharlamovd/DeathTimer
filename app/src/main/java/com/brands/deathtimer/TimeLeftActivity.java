package com.brands.deathtimer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.brands.deathtimer.nav_btns_listeners.BackOnClick;
import com.brands.deathtimer.nav_btns_listeners.SettingsOnClick;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import androidx.appcompat.app.AppCompatActivity;

import static com.brands.deathtimer.extras.DateManager.AVG_LIFE_DURATION_YRS;

public class TimeLeftActivity extends AppCompatActivity implements View.OnClickListener {

    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_left);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new BackOnClick(this));
        ImageButton settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new SettingsOnClick());

        long deathTimeMillis = SettingsActivity.getDeathDateMillis(this);

        if (deathTimeMillis == 0)
            deathTimeMillis = calculateTimeLeft();

        SettingsActivity.setDeathDateMillis(deathTimeMillis, this);
        setCountDownTimer(deathTimeMillis);
    }

    private long calculateTimeLeft() {
        Intent intent = getIntent();

        Date dday;

        if (intent.hasExtra("dday")) {
            dday = (Date) intent.getSerializableExtra("dday");
        }
        else {
            Date bday = (Date) intent.getSerializableExtra("bday");
            int minusYears = intent.getIntExtra("minusYears", 0);

            Calendar c = Calendar.getInstance();
            c.setTime(bday);
            Date d1 = c.getTime();

            c.setTime(bday);
            c.add(Calendar.YEAR, AVG_LIFE_DURATION_YRS);
            c.add(Calendar.YEAR, -minusYears);
            dday = c.getTime();

        }

        return dday.getTime();
    }

    private void setCountDownTimer(long deathDateMillis) {
        String daysStr = getString(R.string.days);
        //Context context = this;

        TextView daysLeftTextView = findViewById(R.id.daysLeftTextView),
                timeLeftTextView = findViewById(R.id.timeLeftTextView);

        timer = new CountDownTimer(deathDateMillis, 1000) {

            private long days, hours, minutes, seconds, now, millisLeft;

            int secondsLeft = 0;

            public void onTick(long millisUntilFinished) {

                now = System.currentTimeMillis();
                millisLeft = Math.abs(now - deathDateMillis);

                /*if (Math.round((float)millisLeft / 1000.0f) != secondsLeft)
                {
                    secondsLeft = Math.round((float)millisLeft / 1000.0f);
                }*/

                days = millisLeft / 1000 / 3600 / 24;
                seconds = (int) ((millisLeft) / 1000) % 60;
                minutes = (int) ((millisLeft / (1000*60)) % 60);
                hours   = (int) ((millisLeft / (1000*60*60)) % 24);

                /*days *= (days < 0) ? -1 : 1;
                seconds *= (seconds < 0) ? -1 : 1;
                minutes *= (minutes < 0) ? -1 : 1;
                hours *= (hours < 0) ? -1 : 1;*/

                /*if (Math.round((float)millisUntilFinished / 1000.0f % 60.0f) != secondsLeft)
                {
                    secondsLeft = Math.round((float)millisUntilFinished / 1000.0f % 60.0f);
                }*/

                daysLeftTextView.setText(days + " " + daysStr);
                timeLeftTextView.setText(String.format("0%2d:%02d:%02d", hours, minutes, seconds));

            }

            public void onFinish() {
                daysLeftTextView.setText(getString(R.string.your_dead));
                timeLeftTextView.setText("");

                TextView info = findViewById(R.id.textView6);
                info.setText("");
            }

        }.start();
    }

    @Override
    public void onClick(View view) {
        Context context = this;

        /*AlertDialog dialog = */new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK)
                .setTitle(getString(R.string.reset_date))
                .setMessage(getString(R.string.are_you_sure))

                .setPositiveButton(android.R.string.yes, (dialog1, which) -> {

                    timer.cancel();
                    SettingsActivity.removeDeathDateMillis(context);

                    Intent myIntent = new Intent(context, ButtonActivity.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(myIntent);
                })

                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}