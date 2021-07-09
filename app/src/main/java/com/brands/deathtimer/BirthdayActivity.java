package com.brands.deathtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.brands.deathtimer.extras.DateManager;
import com.brands.deathtimer.nav_btns_listeners.BackOnClick;
import com.brands.deathtimer.nav_btns_listeners.SettingsOnClick;

import java.time.LocalDate;
import java.util.Date;

public class BirthdayActivity extends AppCompatActivity implements DatePicker.OnDateChangedListener, View.OnClickListener {

    private final int INIT_DAY = 1;
    private final int INIT_MONTH = 0;
    private final int INIT_YEAR = 1990;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);

        DatePicker datePicker = findViewById(R.id.datePicker);
        EditText editText = findViewById(R.id.editText);

        datePicker.init(INIT_YEAR, INIT_MONTH, INIT_DAY, this);
        editText.setText(DateManager.getDate(INIT_DAY, INIT_MONTH, INIT_YEAR, this));

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new BackOnClick(this));
        ImageButton settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new SettingsOnClick());
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
        EditText editText = findViewById(R.id.editText);
        editText.setText(DateManager.getDate(day, month, year, this));
    }


    @Override
    public void onClick(View view) {
        DatePicker datePicker = findViewById(R.id.datePicker);

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Date bday = new Date(year, month, day);

        Intent intent = new Intent(this, BadHabitsActivity.class);
        intent.putExtra("bday", bday);
        startActivity(intent);
    }
}