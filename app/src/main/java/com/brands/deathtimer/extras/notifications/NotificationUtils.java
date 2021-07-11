package com.brands.deathtimer.extras.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import com.brands.deathtimer.MainActivity;
import com.brands.deathtimer.R;
import com.brands.deathtimer.TimeLeftActivity;

import androidx.core.app.NotificationCompat;

public class NotificationUtils extends ContextWrapper
{
    public static String TIMELINE_CHANNEL_NAME = "Timeline notification";

    private NotificationManager _notificationManager;
    private Context _context;

    public NotificationUtils(Context base)
    {
        super(base);
        _context = base;
        createChannel();
    }

    public NotificationCompat.Builder setNotification(String title, String body)
    {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notif_ico)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    private void createChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, TIMELINE_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getManager().createNotificationChannel(channel);
        }
    }

    public NotificationManager getManager()
    {
        if(_notificationManager == null)
        {
            _notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return _notificationManager;
    }

    public void setReminder(long timeInMillis)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_context);
        int requestCode = preferences.getInt("PREF_REMINDER_REQUEST_CODE", -1);

        //Intent _intent = new Intent(_context, ReminderBroadcast.class);
        Intent _intent = new Intent(_context, MainActivity.class);
        PendingIntent _pendingIntent = TaskStackBuilder.create(_context)
                .addNextIntent(_intent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        /*_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent _pendingIntent = PendingIntent.getBroadcast(_context, requestCode++, _intent, 0);*/

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("PREF_REMINDER_REQUEST_CODE", ++requestCode);
        editor.commit();

        AlarmManager _alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        _alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, _pendingIntent);
    }

}
