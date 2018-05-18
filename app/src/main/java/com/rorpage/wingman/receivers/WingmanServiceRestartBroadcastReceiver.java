package com.rorpage.wingman.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.rorpage.wingman.services.WingmanService;

import timber.log.Timber;

public class WingmanServiceRestartBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.d("Restarting service");
        context.startService(new Intent(context, WingmanService.class));
    }
}
