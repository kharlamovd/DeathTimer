package com.brands.deathtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.brands.deathtimer.extras.DateManager;
import com.brands.deathtimer.nav_btns_listeners.BackOnClick;
import com.brands.deathtimer.nav_btns_listeners.SettingsOnClick;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.brands.deathtimer.extras.DateManager.INIT_DAY;
import static com.brands.deathtimer.extras.DateManager.INIT_MONTH;
import static com.brands.deathtimer.extras.DateManager.INIT_YEAR;
import static com.brands.deathtimer.extras.DateManager.ONE_DAY_IN_MILLIS;

public class EnterDeathDayActivity extends AppCompatActivity implements DatePicker.OnDateChangedListener, View.OnClickListener {

    private boolean isUSDevice = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_death_day);

        DatePicker datePicker = findViewById(R.id.datePicker);

        isUSDevice = DateManager.isUSDevice(this);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);

        int year  = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int date  = cal.get(Calendar.DATE);

        datePicker.init(year, month, date, this);
        datePicker.setMinDate(System.currentTimeMillis() + ONE_DAY_IN_MILLIS);

        onDateChanged(datePicker, year, month, date);

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
        int year = datePicker.getYear();

        Intent intent = getIntent();

        Date dday = new Date(year - 1900, month, day);
        int minusYears = intent.getIntExtra("minusYears", 0);
        Date bday = (Date) intent.getSerializableExtra("bday");

        intent = new Intent(this, TimeLeftActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        intent.putExtra("bday", bday);
        intent.putExtra("dday", dday);
        intent.putExtra("minusYears", minusYears);

        startActivity(intent);
    }
}