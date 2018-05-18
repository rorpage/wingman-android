package com.rorpage.wingman.managers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.rorpage.wingman.R;
import com.rorpage.wingman.activities.MainActivity;

public class NotificationManager {
    private static final int NOTIFICATION_ID = 1;
    private static final String PRIMARY_CHANNEL = "default";

    private Context mContext;
    private android.app.NotificationManager mNotificationManager;

    public NotificationManager(Context context) {
        mContext = context;
        mNotificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void sendNotification(String message) {
        buildNotificationChannelIfNeeded();

        PendingIntent pendingIntent = getPendingIntent();

        final String notificationTitle = mContext.getString(R.string.app_name);
        NotificationCompat.Builder notificationBuilder = getNotificationBuilder(notificationTitle,
                message, pendingIntent);
        Notification mNotification = notificationBuilder.build();

        mNotificationManager.notify(NOTIFICATION_ID, mNotification);
    }

    private NotificationCompat.Builder getNotificationBuilder(String title, String message,
                                                              PendingIntent pendingIntent) {
        return new NotificationCompat.Builder(mContext, PRIMARY_CHANNEL)
                .setSmallIcon(R.drawable.ic_stat_wingman)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher))
                .setContentTitle(title)
                .setContentText(message)
                .setOngoing(true)
                .setVibrate(null)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);
    }

    private void buildNotificationChannelIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(PRIMARY_CHANNEL,
                    mContext.getString(R.string.notification_channel_default),
                    android.app.NotificationManager.IMPORTANCE_LOW);

            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            mNotificationManager.createNotificationChannel(channel);
        }
    }
}
