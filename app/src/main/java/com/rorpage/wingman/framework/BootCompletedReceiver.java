package com.rorpage.wingman.framework;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.rorpage.wingman.services.WingmanService;

public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent wingmanServiceIntent = new Intent(context, WingmanService.class);
        context.startService(wingmanServiceIntent);
    }
}
