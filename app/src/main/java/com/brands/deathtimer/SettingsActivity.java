package com.brands.deathtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.brands.deathtimer.nav_btns_listeners.BackOnClick;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String IS_AUTO_DEATH_DATE_PREF = "autoDeathDate";
    private static final String DEATH_DATE_PREF = "deathDate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView t2 = (TextView) findViewById(R.id.privPolTextView);
        t2.setMovementMethod(LinkMovementMethod.getInstance());

        CheckBox checkBox = findViewById(R.id.checkBox);
        checkBox.setChecked(isDeathDateAuto(this));

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new BackOnClick(this));
    }

    @Override
    public void onClick(View view) {
        CheckBox checkBox = findViewById(R.id.checkBox);
        boolean auto = checkBox.isChecked();

        setDeathDateAuto(auto);

        Activity activity = getActivity(view);
        if (isYouKnowActivity(activity) && auto) {
            Intent myIntent = new Intent(activity, TimeLeftActivity.class);
            activity.startActivity(myIntent);
        } else {
            super.onBackPressed();
        }
    }

    public static boolean isDeathDateAuto(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(IS_AUTO_DEATH_DATE_PREF, false);
    }

    private void setDeathDateAuto(boolean auto) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(IS_AUTO_DEATH_DATE_PREF, auto);
        editor.commit();
    }

    public static long getDeathDateMillis(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getLong(DEATH_DATE_PREF, 0);
    }

    private void setDeathDateMillis(long deathDateMillis) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putLong(DEATH_DATE_PREF, deathDateMillis);
        editor.commit();
    }

    private Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    private boolean isYouKnowActivity(Activity activity) {
        return (activity instanceof YouKnowActivity);
    }

}