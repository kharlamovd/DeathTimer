package com.brands.deathtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Browser;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.brands.deathtimer.nav_btns_listeners.BackOnClick;
import com.brands.deathtimer.nav_btns_listeners.SettingsOnClick;

import java.util.jar.JarEntry;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static int TIME_OUT = 3400;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView t2 = (TextView) findViewById(R.id.privPolTextView);
        t2.setMovementMethod(LinkMovementMethod.getInstance());

        t2.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTimeOut();
    }

    private void setTimeOut() {
        Activity activity = this;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                long deathTimeMillis = SettingsActivity.getDeathDateMillis(activity);

                Intent i;
                if (deathTimeMillis > 0)
                    i = new Intent(MainActivity.this, TimeLeftActivity.class);
                else
                    i = new Intent(MainActivity.this, ButtonActivity.class);

                startActivity(i);
                finish();
            }
        }, TIME_OUT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onClick(View view) {
        long deathTimeMillis = SettingsActivity.getDeathDateMillis(this);

        Intent i;
        if (deathTimeMillis > 0)
            i = new Intent(MainActivity.this, TimeLeftActivity.class);
        else
            i = new Intent(MainActivity.this, ButtonActivity.class);

        startActivity(i);

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.priv_policy_link)));
        startActivity(browserIntent);
    }
}