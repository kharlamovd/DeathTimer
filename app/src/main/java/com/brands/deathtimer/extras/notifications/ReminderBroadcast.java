package com.brands.deathtimer.extras.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.brands.deathtimer.R;
import com.brands.deathtimer.TimeLeftActivity;

import androidx.core.app.NotificationCompat;

public class ReminderBroadcast extends BroadcastReceiver {

    //public static final long NOTIFICATION_TIME_PERIOD = 10000;

    @Override
    public void onReceive(Context context, Intent intent) {
        /*NotificationUtils _notificationUtils = new NotificationUtils(context);
        NotificationCompat.Builder _builder = _notificationUtils.setNotification(context.getString(R.string.left),
                TimeLeftActivity.getTimeLeft(context));
        _notificationUtils.getManager().notify(101, _builder.build());*/
    }

}
