package com.brands.deathtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String AUTO_DEATH_DATE_PREF = "autoDeathDate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
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
        return preferences.getBoolean(AUTO_DEATH_DATE_PREF, false);
    }

    private void setDeathDateAuto(boolean auto) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(AUTO_DEATH_DATE_PREF, auto);
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