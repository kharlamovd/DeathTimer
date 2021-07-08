package com.brands.deathtimer.nav_btns_listeners;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.view.View;

import com.brands.deathtimer.SettingsActivity;

public class SettingsOnClick implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        Activity activity = getActivity(view);
        if (activity != null) {
            Intent myIntent = new Intent(activity, SettingsActivity.class);
            activity.startActivity(myIntent);
        }
    }

    private Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

}
