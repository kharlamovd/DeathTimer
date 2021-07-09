package com.brands.deathtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.brands.deathtimer.nav_btns_listeners.BackOnClick;
import com.brands.deathtimer.nav_btns_listeners.SettingsOnClick;

import java.util.Date;

public class BadHabitsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bad_habits);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new BackOnClick(this));
        ImageButton settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new SettingsOnClick());
    }

    @Override
    public void onClick(View view) {
        Intent intent = getIntent();

        CheckBox smokeCheckBox = findViewById(R.id.smokeCheckBox);
        Spinner spinner = findViewById(R.id.drinkSpinner);

        boolean doesSmoking = smokeCheckBox.isChecked();
        int drinkFrequency = spinner.getSelectedItemPosition();

        Date bday = (Date) intent.getSerializableExtra("bday");
        int minusYears = calculateBadHabitsImpactInYears(drinkFrequency, doesSmoking);

        if (SettingsActivity.isDeathDateAuto(this))
            intent = new Intent(this, TimeLeftActivity.class);
        else
            intent = new Intent(this, YouKnowActivity.class);

        intent.putExtra("bday", bday);
        intent.putExtra("minusYears", minusYears);
        startActivity(intent);
    }

    private int calculateBadHabitsImpactInYears(int drinkFrequency, boolean doesSmoking) {
        int minusYears = 0;

        switch (drinkFrequency) {
            case 4:
                minusYears += 10;
            case 2:
                minusYears += 3;
            case 1:
                minusYears += 2;
            case 0:
                minusYears += 1;
                break;
        }
        if (doesSmoking)
            minusYears += 10;

        return minusYears;
    }

}