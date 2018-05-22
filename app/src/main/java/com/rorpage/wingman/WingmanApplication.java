package com.rorpage.wingman;

import android.app.Application;

import me.aflak.bluetooth.Bluetooth;
import timber.log.Timber;

public class WingmanApplication extends Application {
    private Bluetooth mBluetooth;
    private static WingmanApplication mApplication;
//    private ObjectGraph mObjectGraph;

    public WingmanApplication() {
        mApplication = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public Bluetooth getBluetooth() {
        return this.mBluetooth;
    }

    public void setBluetooth(Bluetooth bluetooth) {
        this.mBluetooth = bluetooth;
    }

    public void cleanupBluetooth() {
        if (mBluetooth != null && mBluetooth.isConnected()) {
            mBluetooth.removeBluetoothCallback();
            mBluetooth.removeCommunicationCallback();
            mBluetooth.removeDiscoveryCallback();
            mBluetooth.disconnect();
        }
    }
}
