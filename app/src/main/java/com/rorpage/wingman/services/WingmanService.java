package com.rorpage.wingman.services;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.rorpage.wingman.WingmanApplication;
import com.rorpage.wingman.managers.NotificationManager;

import me.aflak.bluetooth.Bluetooth;
import me.aflak.bluetooth.DeviceCallback;
import timber.log.Timber;

public class WingmanService extends BaseWingmanService {
    private Bluetooth mBluetooth;
    private BroadcastIntentService mBroadcastIntentService;
    private NotificationManager mNotificationManager;
    private Handler mCheckBluetoothDeviceStatusHandler;
    private Intent mMessageDisplayServiceIntent;

    @Override
    public void onCreate() {
        super.onCreate();

        mBluetooth = new Bluetooth(this);
        WingmanApplication mWingmanApplication = (WingmanApplication) super.getApplication();
        mWingmanApplication.setBluetooth(mBluetooth);

        mNotificationManager = new NotificationManager(this);
        mCheckBluetoothDeviceStatusHandler = new Handler();
        mBroadcastIntentService = new BroadcastIntentService(this);
        mMessageDisplayServiceIntent = new Intent(this, MessageDisplayService.class);

        mNotificationManager.sendNotification("Your Wingman is disconnected.");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        mBluetooth.setDeviceCallback(new DeviceCallback() {
            @Override
            public void onDeviceConnected(BluetoothDevice device) {
                Timber.d("onDeviceConnected()");

                mBroadcastIntentService.broadcastBluetoothConnected();
                Timber.d("Broadcast sent");

                mNotificationManager.sendNotification("Your Wingman is connected.");
                mCheckBluetoothDeviceStatusHandler.removeCallbacks(mCheckBluetoothDeviceStatusRunnable);

                getApplicationContext().startService(mMessageDisplayServiceIntent);
            }

            @Override
            public void onDeviceDisconnected(BluetoothDevice device, String message) {
                Timber.d("onDeviceDisconnected()");

                mBroadcastIntentService.broadcastBluetoothDisconnected();
                Timber.d("Broadcast sent");

                mNotificationManager.sendNotification("Your Wingman is disconnected.");
                updateCheckBluetoothDeviceStatusHandler();
                getApplicationContext().stopService(mMessageDisplayServiceIntent);
            }

            @Override
            public void onMessage(String message) {
                Timber.d("onMessage: %s", message);
            }

            @Override
            public void onError(String message) {
                Timber.e("onError %s", message);
            }

            @Override
            public void onConnectError(BluetoothDevice device, String message) {
                Timber.e("onConnectError %s", message);
                updateCheckBluetoothDeviceStatusHandler();
            }
        });

        mBluetooth.onStart();
        updateCheckBluetoothDeviceStatusHandler();

//        IntentFilter mMessageIntentFilter = new IntentFilter();
//        mMessageIntentFilter.addAction(Constants.NEW_NOTIFICATION_INTENT);
//        mMessageIntentFilter.addAction(Constants.NEW_SMS_INTENT);
//        mNewMessageReceiver = new NewMessageReceiver();
//
//        registerReceiver(mNewMessageReceiver, mMessageIntentFilter);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy()");

        mBluetooth.onStop();

        mCheckBluetoothDeviceStatusHandler.removeCallbacks(mCheckBluetoothDeviceStatusRunnable);

        stopService(mMessageDisplayServiceIntent);

        Intent broadcastIntent = new Intent("com.rorpage.wingman.Broadcast.RestartWingmanService");
        sendBroadcast(broadcastIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void updateCheckBluetoothDeviceStatusHandler() {
        mCheckBluetoothDeviceStatusHandler.removeCallbacks(mCheckBluetoothDeviceStatusRunnable);
        mCheckBluetoothDeviceStatusHandler.postDelayed(mCheckBluetoothDeviceStatusRunnable, 5000);
    }

    private Runnable mCheckBluetoothDeviceStatusRunnable = new Runnable() {
        public void run() {
            checkBluetoothDeviceStatus();
        }
    };

    private void checkBluetoothDeviceStatus() {
        Timber.d("checkBluetoothDeviceStatus()");
        if (!mBluetooth.isConnected()) {
            mBluetooth.connectToName("Wingman 27D2");
        } else {
            updateCheckBluetoothDeviceStatusHandler();
        }
    }
}
