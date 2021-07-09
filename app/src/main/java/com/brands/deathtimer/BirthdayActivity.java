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
import java.util.Calendar;
import java.util.Date;

import static com.brands.deathtimer.extras.DateManager.INIT_DAY;
import static com.brands.deathtimer.extras.DateManager.INIT_MONTH;
import static com.brands.deathtimer.extras.DateManager.INIT_YEAR;

public class BirthdayActivity extends AppCompatActivity implements DatePicker.OnDateChangedListener, View.OnClickListener {

    private boolean isUSDevice = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);

        DatePicker datePicker = findViewById(R.id.datePicker);

        isUSDevice = DateManager.isUSDevice(this);

        datePicker.init(INIT_YEAR, INIT_MONTH, INIT_DAY, this);
        onDateChanged(datePicker, INIT_YEAR, INIT_MONTH, INIT_DAY);

        datePicker.setMaxDate(DateManager.getTodayInMillis());

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new BackOnClick(this));
        ImageButton settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new SettingsOnClick());
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
        EditText editText = findViewById(R.id.editText);
        if (isUSDevice)
            editText.setText(DateManager.getUSDate(day, month, year, this));
        else
            editText.setText(DateManager.getRegularDate(day, month, year, this));
    }


    @Override
    public void onClick(View view) {
        DatePicker datePicker = findViewById(R.id.datePicker);

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear() - 1900;

        Date bday = new Date(year, month, day);

        Intent intent = new Intent(this, BadHabitsActivity.class);
        intent.putExtra("bday", bday);
        startActivity(intent);
    }

}