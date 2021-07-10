package com.brands.deathtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.brands.deathtimer.nav_btns_listeners.BackOnClick;
import com.brands.deathtimer.nav_btns_listeners.SettingsOnClick;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

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

        int minusYears = intent.getIntExtra("minusYears", 0);
        Date bday = (Date) intent.getSerializableExtra("bday");
        Date dday;

        if (intent.hasExtra("dday"))
            dday = (Date) intent.getSerializableExtra("dday");
        else {
            Calendar c = Calendar.getInstance();
            c.setTime(bday);
            c.add(Calendar.YEAR, 10);
            Date d1 = c.getTime();

            c.setTime(bday);
            c.add(Calendar.YEAR, AVG_LIFE_DURATION_YRS);
            c.add(Calendar.YEAR, -minusYears);
            Date d2 = c.getTime();

            dday = new Date(ThreadLocalRandom.current()
                    .nextLong(d1.getTime(), d2.getTime()));
        }

        return dday.getTime() - System.currentTimeMillis();
    }

    private void setCountDownTimer(long deathDateMillis) {
        String daysStr = getString(R.string.days);
        Context context = this;

        TextView daysLeftTextView = findViewById(R.id.daysLeftTextView),
                timeLeftTextView = findViewById(R.id.timeLeftTextView);

        timer = new CountDownTimer(deathDateMillis, 1000) {

            private long days, hours, minutes, seconds, now, millisLeft;

            public void onTick(long millisUntilFinished) {
                now = System.currentTimeMillis();
                millisLeft = now - millisUntilFinished;

                SettingsActivity.setDeathDateMillis(millisLeft, context);

                days = millisLeft / 1000 / 3600 / 24;
                seconds = (int) (millisLeft / 1000) % 60 ;
                minutes = (int) ((millisLeft / (1000*60)) % 60);
                hours   = (int) ((millisLeft / (1000*60*60)) % 24);

                daysLeftTextView.setText(days + " " + daysStr);
                if (seconds < 10)
                    timeLeftTextView.setText(hours + ":" + minutes + ":0" + seconds);
                else
                    timeLeftTextView.setText(hours + ":" + minutes + ":" + seconds);
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

        AlertDialog dialog = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK)
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