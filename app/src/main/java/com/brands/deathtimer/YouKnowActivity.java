package com.brands.deathtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.brands.deathtimer.nav_btns_listeners.BackOnClick;
import com.brands.deathtimer.nav_btns_listeners.SettingsOnClick;

import java.util.Date;

public class YouKnowActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_know);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new BackOnClick(this));
        ImageButton settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new SettingsOnClick());
    }

    @Override
    public void onClick(View view) {
        Intent intent = getIntent();

        int minusYears = intent.getIntExtra("minusYears", 0);
        Date bday = (Date) intent.getSerializableExtra("bday");

        switch (view.getId()) {
            case R.id.yesButton:
                intent = new Intent(this, EnterDeathDayActivity.class);
                break;
            case R.id.skipButton:
                intent = new Intent(this, TimeLeftActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                break;
        }

        intent.putExtra("bday", bday);
        intent.putExtra("minusYears", minusYears);

        startActivity(intent);
    }

    private void onButtonClick(Class nextActivity) {



    }

}