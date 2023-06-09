package com.brands.deathtimer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.brands.deathtimer.extras.notifications.NotificationWorker;
import com.brands.deathtimer.nav_btns_listeners.BackOnClick;
import com.brands.deathtimer.nav_btns_listeners.SettingsOnClick;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import static com.brands.deathtimer.extras.DateManager.AVG_LIFE_DURATION_YRS;
import static com.brands.deathtimer.extras.DateManager.NOTIFICATION_FIRE_PERIOD_HOURS;

public class TimeLeftActivity extends AppCompatActivity implements View.OnClickListener {

    private CountDownTimer timer;
    public static UUID notificationWorkRequestId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_left);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new BackOnClick(this));
        ImageButton settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new SettingsOnClick());

        long deathTimeMillis = SettingsActivity.getDeathDateMillis(this);

        if (deathTimeMillis == 0) {
            PeriodicWorkRequest periodicWork = new PeriodicWorkRequest.Builder(NotificationWorker.class, NOTIFICATION_FIRE_PERIOD_HOURS, TimeUnit.HOURS)
                    .build();
            WorkManager.getInstance(this).enqueue(periodicWork);

            notificationWorkRequestId = periodicWork.getId();

            deathTimeMillis = calculateTimeLeft();
        }

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

            c.setTime(bday);
            c.add(Calendar.YEAR, AVG_LIFE_DURATION_YRS);
            c.add(Calendar.YEAR, -minusYears);
            dday = c.getTime();

        }

        return dday.getTime();
    }

    public static String getTimeLeft(Context context) {
        long deathDateMillis = SettingsActivity.getDeathDateMillis(context);

        long days, hours, minutes, weeks, years, seconds, now, millisLeft;

        now = System.currentTimeMillis();
        millisLeft = Math.abs(now - deathDateMillis);

        String daysStr = context.getString(R.string.days);
        String weeksStr = context.getString(R.string.weeks);
        String yearsStr = context.getString(R.string.years);

        days = millisLeft / 1000 / 3600 / 24;
        seconds = (int) ((millisLeft) / 1000) % 60;
        minutes = (int) ((millisLeft / (1000 * 60)) % 60);
        hours = (int) ((millisLeft / (1000 * 60 * 60)) % 24);
        weeks = (int) (millisLeft / (1000 * 60 * 60 * 24 * 7));
        years = (int) (millisLeft / 1000 / 60 / 60 / 24 / 365);

        String timeLeftStr = days + " " + daysStr + '\n' +
                weeks + " " + weeksStr + '\n' +
                years + " " + yearsStr + '\n';
        timeLeftStr += String.format("%02d:%02d:%02d", hours, minutes, seconds);

        return timeLeftStr;
    }

    private void setCountDownTimer(long deathDateMillis) {
        String daysStr = getString(R.string.days);
        String weeksStr = getString(R.string.weeks);
        String yearsStr = getString(R.string.years);

        TextView daysLeftTextView = findViewById(R.id.daysLeftTextView),
                timeLeftTextView = findViewById(R.id.timeLeftTextView);

        timer = new CountDownTimer(deathDateMillis, 1000) {

            private long days, hours, minutes, seconds, now, millisLeft, weeks, years;

            public void onTick(long millisUntilFinished) {

                now = System.currentTimeMillis();
                millisLeft = Math.abs(now - deathDateMillis);

                days = millisLeft / 1000 / 3600 / 24;
                seconds = (int) ((millisLeft) / 1000) % 60;
                minutes = (int) ((millisLeft / (1000*60)) % 60);
                hours   = (int) ((millisLeft / (1000*60*60)) % 24);
                weeks = (int) (millisLeft / (1000 * 60 * 60 * 24 * 7));
                years = (int) (millisLeft / 1000 / 60 / 60 / 24 / 365);

                daysLeftTextView.setText(
                        days + " " + daysStr + '\n' +
                        weeks + " " + weeksStr + '\n' +
                        years + " " + yearsStr + '\n'
                );
                timeLeftTextView.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));

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

        new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK)
                .setTitle(getString(R.string.reset_date))
                .setMessage(getString(R.string.are_you_sure))

                .setPositiveButton(android.R.string.yes, (dialog1, which) -> {

                    WorkManager.getInstance(this).cancelWorkById(notificationWorkRequestId);

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