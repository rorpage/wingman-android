package com.rorpage.wingman.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.rorpage.wingman.WingmanApplication;
import com.rorpage.wingman.models.messages.WingmanMessage;
import com.rorpage.wingman.modules.BaseBroadcastReceiverModule;
import com.rorpage.wingman.modules.BaseWingmanModule;
import com.rorpage.wingman.modules.WingmanModuleFactory;
import com.rorpage.wingman.util.Constants;
import com.rorpage.wingman.util.formatters.WingmanMessageFormatter;

import java.util.ArrayList;

import me.aflak.bluetooth.Bluetooth;
import timber.log.Timber;

public class MessageDisplayService extends Service {
    private int mCurrentModule = 0;
    private int mCurrentMessage = 0;
    private int mDelayBetweenScreens = 10000;

    private Handler mSendMessageHandler;
    private Bluetooth mBluetooth;
    private ArrayList<BaseWingmanModule> mWingmanModules;

//    private ArrayList<WingmanMessage> mHighPriorityMessages;
//    private ArrayList<WingmanMessage> mMediumPriorityMessages;
//    private ArrayList<WingmanMessage> mLowPriorityMessages;
    private WingmanMessage mInjectableWingmanMessage;

    private NewMessageReceiver mNewMessageReceiver;

    @Override
    public void onCreate() {
        super.onCreate();

        final WingmanApplication wingmanApplication = (WingmanApplication) super.getApplication();
        mBluetooth = wingmanApplication.getBluetooth();

        mSendMessageHandler = new Handler();

        mWingmanModules = WingmanModuleFactory.getWingmanModules(this);
//        mHighPriorityMessages = new ArrayList<>();
//        mMediumPriorityMessages = new ArrayList<>();
//        mLowPriorityMessages = new ArrayList<>();

        IntentFilter mMessageIntentFilter = new IntentFilter();
        mMessageIntentFilter.addAction(Constants.NEW_NOTIFICATION_INTENT);
        mMessageIntentFilter.addAction(Constants.NEW_SMS_INTENT);
        mNewMessageReceiver = new NewMessageReceiver();

        registerReceiver(mNewMessageReceiver, mMessageIntentFilter);

        sendMessage();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy()");

        for (BaseWingmanModule module : mWingmanModules) {
            if (module instanceof BaseBroadcastReceiverModule) {
                ((BaseBroadcastReceiverModule) module).unregisterReceiver();
            }
        }

        mSendMessageHandler.removeCallbacks(mSendMessageRunnable);
        unregisterReceiver(mNewMessageReceiver);
    }

    private void updateSendMessageHandler() {
        mSendMessageHandler.removeCallbacks(mSendMessageRunnable);
        mSendMessageHandler.postDelayed(mSendMessageRunnable, mDelayBetweenScreens);
    }

    private Runnable mSendMessageRunnable = new Runnable() {
        public void run() {
            sendMessage();
        }
    };

    private void updateInfoAndSendMessage(int screenDelay, WingmanMessage wingmanMessageToSend) {
        mDelayBetweenScreens = screenDelay;

        final String formattedMessage = WingmanMessageFormatter.formatMessage(wingmanMessageToSend);
        Timber.d("Sending: %s", formattedMessage);
        mBluetooth.send(formattedMessage);
    }

    private void sendMessage() {
        Timber.d("sendMessage()");

        if (mInjectableWingmanMessage != null) {
            updateInfoAndSendMessage(20000, mInjectableWingmanMessage);
            mInjectableWingmanMessage = null;
            updateSendMessageHandler();
        } else {
            ArrayList<WingmanMessage> wingmanMessages = mWingmanModules.get(mCurrentModule).getCurrentData();
            final WingmanMessage currentWingmanMessage = wingmanMessages.get(mCurrentMessage);
            updateInfoAndSendMessage(10000, currentWingmanMessage);

            updateSendMessageHandler();

            if (mCurrentMessage < wingmanMessages.size()) {
                mCurrentMessage++;
            }

            if (mCurrentMessage >= wingmanMessages.size()) {
                mCurrentMessage = 0;
                mCurrentModule++;
            }

            if (mCurrentModule >= mWingmanModules.size()) {
                mCurrentModule = 0;
            }
        }
    }

    public class NewMessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final WingmanMessage wingmanMessage = BroadcastIntentService.getMessageFromIntent(intent);
            mInjectableWingmanMessage = wingmanMessage;
            Timber.d("onReceive(): %s", WingmanMessageFormatter.formatMessage(wingmanMessage));
        }
    }
}
