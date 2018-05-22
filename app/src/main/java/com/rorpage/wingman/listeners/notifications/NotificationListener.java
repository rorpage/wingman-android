package com.rorpage.wingman.listeners.notifications;

import android.app.Notification;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.rorpage.wingman.models.messages.WingmanMessage;
import com.rorpage.wingman.models.messages.MessagePriority;
import com.rorpage.wingman.models.messages.MessageType;
import com.rorpage.wingman.services.BroadcastIntentService;

import java.util.ArrayList;
import java.util.Locale;

import timber.log.Timber;

public class NotificationListener extends NotificationListenerService {
    private BroadcastIntentService mBroadcastIntentService;

    private ArrayList<String> mIgnoredPackages;

    public NotificationListener() {
        mBroadcastIntentService = new BroadcastIntentService(this);
    }

    public void onCreate() {
        Timber.d("onCreate()");

        setupIgnoredPackages();
    }

    private void setupIgnoredPackages() {
        mIgnoredPackages = new ArrayList<>();
        // SMS
        mIgnoredPackages.add("com.p1.chompsms");
        mIgnoredPackages.add("com.klinker.android.evolve_sms");
        mIgnoredPackages.add("com.jb.gosms");
        mIgnoredPackages.add("com.google.android.apps.messaging");
        mIgnoredPackages.add("com.hellotext.hello");
        mIgnoredPackages.add("com.textra");
        mIgnoredPackages.add("org.thoughtcrime.securesms");
        mIgnoredPackages.add("com.sonyericsson.conversations");
        mIgnoredPackages.add("com.disa");
        // Pebble
        mIgnoredPackages.add("com.getpebble.android.basalt");
        // Weather
        mIgnoredPackages.add("com.samruston.weather");
        // Wingman
        mIgnoredPackages.add("com.rorpage.wingman");
    }

    public void onNotificationPosted(final StatusBarNotification statusBarNotification) {
        if (statusBarNotification == null) {
            Timber.d("onNotificationPosted: statusBarNotification is null");
        } else {
            String notificationPackageName = statusBarNotification.getPackageName();

            if (shouldSendBroadcast(notificationPackageName)) {
                Notification notification = statusBarNotification.getNotification();

                final CharSequence notificationText = notification.extras
                        .getCharSequence(Notification.EXTRA_TEXT);
                final CharSequence notificationTitle =
                        notification.extras.getCharSequence(Notification.EXTRA_TITLE);

                if (notificationTitle != null && notificationText != null) {
                    Timber.d("onNotificationPosted: %s", notificationText.toString());

                    final String message = String.format(Locale.US,
                            "%s\n%s", notificationTitle.toString(), notificationText.toString());

                    WingmanMessage wingmanMessage = new WingmanMessage(MessageType.MESSAGE,
                            message,
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
        for (String ignoredPackage : mIgnoredPackages) {
            if (notificationPackageName.equals(ignoredPackage)) {
                return false;
            }
        }

        return true;
    }
}
