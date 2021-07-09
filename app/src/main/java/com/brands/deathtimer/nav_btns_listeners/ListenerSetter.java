package com.brands.deathtimer.nav_btns_listeners;

import android.app.Activity;
import android.widget.ImageButton;

import com.brands.deathtimer.R;

public class ListenerSetter {

    private void setNavButtonsOnClickListeners(Activity activity) {
        ImageButton backButton = activity.findViewById(R.id.backButton);
        //  TODO change as for global
        backButton.setOnClickListener(new BackOnClick(activity));
        ImageButton settingsButton = activity.findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new SettingsOnClick());
    }

}
