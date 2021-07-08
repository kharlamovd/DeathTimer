package com.brands.deathtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.widget.ImageButton;
import android.widget.TextView;

import com.brands.deathtimer.nav_btns_listeners.BackOnClick;
import com.brands.deathtimer.nav_btns_listeners.SettingsOnClick;

public class MainActivity extends AppCompatActivity {

    private static int TIME_OUT = 3400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView t2 = (TextView) findViewById(R.id.privPolTextView);
        t2.setMovementMethod(LinkMovementMethod.getInstance());

        setNavButtonsOnClickListeners();

        setTimeOut();
    }

    private void setTimeOut() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, ButtonActivity.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);
    }

    private void setNavButtonsOnClickListeners() {
        ImageButton backButton = findViewById(R.id.backButton);
        //  TODO change as for global
        backButton.setOnClickListener(new BackOnClick(this));
        ImageButton settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new SettingsOnClick());
    }
}