package com.rorpage.wingman.listeners.notifications;

import android.app.Notification;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.rorpage.wingman.models.messages.WingmanMessage;
import com.rorpage.wingman.models.messages.MessagePriority;
import com.rorpage.wingman.models.messages.MessageType;
import com.rorpage.wingman.services.BroadcastIntentService;

import timber.log.Timber;

public class NotificationListener extends NotificationListenerService {
    private BroadcastIntentService mBroadcastIntentService;

    public NotificationListener() {
        mBroadcastIntentService = new BroadcastIntentService(this);
    }

    public void onCreate() {
        Timber.d("onCreate()");
    }

    public void onNotificationPosted(final StatusBarNotification statusBarNotification) {
        if (statusBarNotification == null) {
            Timber.d("onNotificationPosted: statusBarNotification is null");
        } else {
            String notificationPackageName = statusBarNotification.getPackageName();

            if (shouldSendBroadcast(notificationPackageName)) {
                Notification notification = statusBarNotification.getNotification();
                CharSequence notificationText = notification.extras
                        .getCharSequence(Notification.EXTRA_TEXT);
                if (notificationText != null) {
                    Timber.d("onNotificationPosted: " + notificationText.toString());

                    WingmanMessage wingmanMessage = new WingmanMessage(MessageType.MESSAGE,
                            "New notification",
                            MessagePriority.HIGH);

                    mBroadcastIntentService.broadcastNewNotification(wingmanMessage);
                }
            }
        }
    }

    public void onListenerConnected() {
        Timber.d("onListenerConnected()");
    }

    public void onListenerDisconnected() {
        Timber.d("onListenerDisconnected()");
    }

    private boolean shouldSendBroadcast(String notificationPackageName) {
        if (notificationPackageName.equals("com.google.android.apps.messaging")) {
            return false;
        }

        return true;
    }
}
