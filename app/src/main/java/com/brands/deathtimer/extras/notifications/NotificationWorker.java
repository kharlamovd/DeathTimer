package com.brands.deathtimer.extras.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.brands.deathtimer.MainActivity;
import com.brands.deathtimer.R;
import com.brands.deathtimer.TimeLeftActivity;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NotificationWorker extends Worker {

    public static String CHANNEL_ID = "notification channel";

    private Context context;

    public NotificationWorker(@NonNull @org.jetbrains.annotations.NotNull Context context, @NonNull @org.jetbrains.annotations.NotNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @Override
    public Result doWork() {
        reminderNotification();

        return Result.success();
    }

    public void reminderNotification()
    {
        /*NotificationUtils _notificationUtils = new NotificationUtils(context);
        long _currentTime = System.currentTimeMillis();
        _notificationUtils.setReminder(_currentTime);

        NotificationCompat.Builder _builder = _notificationUtils.setNotification(context.getString(R.string.left),
                TimeLeftActivity.getTimeLeft(context));
        _notificationUtils.getManager().notify(101, _builder.build());*/
        int mNotificationId = 001;

        // Build Notification , setOngoing keeps the notification always in status bar
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.notif_ico)
                        .setContentTitle(context.getString(R.string.left))
                        .setContentText(TimeLeftActivity.getTimeLeft(context))
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Create pending intent, mention the Activity which needs to be
        //triggered when user clicks on notification(StopScript.class in this case)

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);

        // Gets an instance of the NotificationManager service
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Builds the notification and issues it.
        mNotificationManager.notify(mNotificationId, mBuilder.build());

    }
}

