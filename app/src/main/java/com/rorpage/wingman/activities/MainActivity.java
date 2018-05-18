package com.rorpage.wingman.activities;

import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.rorpage.wingman.BuildConfig;
import com.rorpage.wingman.R;
import com.rorpage.wingman.fragments.ModulesFragment;
import com.rorpage.wingman.fragments.NotificationsFragment;
import com.rorpage.wingman.fragments.SettingsFragment;
import com.rorpage.wingman.services.WingmanService;
import com.rorpage.wingman.util.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private Fragment mFragment;
    private FragmentManager mFragmentManager;

    private BluetoothDeviceStatusReceiver mBluetoothStatusReceiver;
    private IntentFilter mBluetoothStatusIntentFilter;

    private Intent mWingmanServiceIntent;

    @BindView(R.id.bottom_navigation) BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ButterKnife.bind(this);

        setupBottomNavigation();

        if (!isWingmanServiceRunning(WingmanService.class)) {
            mWingmanServiceIntent = new Intent(this, WingmanService.class);
            startService(mWingmanServiceIntent);
        }

        mBluetoothStatusIntentFilter = new IntentFilter();
        mBluetoothStatusIntentFilter.addAction(Constants.BLUETOOTH_CONNECTED_INTENT);
        mBluetoothStatusIntentFilter.addAction(Constants.BLUETOOTH_DISCONNECTED_INTENT);
        mBluetoothStatusReceiver = new BluetoothDeviceStatusReceiver();

        if (BuildConfig.DEBUG) {
            PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply();
        }

        checkPermissions();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mBluetoothStatusReceiver, mBluetoothStatusIntentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mBluetoothStatusReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(mWingmanServiceIntent);
    }

    private void setupBottomNavigation() {
        mFragmentManager = getSupportFragmentManager();
        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int id = item.getItemId();
                        return startFragmentFromNavigation(id);
                    }
                });

        startFragmentFromNavigation(R.id.bottomnavigation_action_modules);
    }

    private boolean startFragmentFromNavigation(int id) {
        switch (id) {
            case R.id.bottomnavigation_action_modules:
                mFragment = new ModulesFragment();
                break;
            case R.id.bottomnavigation_action_notifications:
                mFragment = new NotificationsFragment();
                break;
            case R.id.bottomnavigation_action_settings:
                mFragment = new SettingsFragment();
                break;
        }

        final FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, mFragment).commit();
        return true;
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            ArrayList<String> permissionsNeeded = new ArrayList<>();
            permissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            permissionsNeeded.add(Manifest.permission.RECEIVE_SMS);
            permissionsNeeded.add(Manifest.permission.READ_CONTACTS);

            ArrayList<String> permissionsToRequest = new ArrayList<>();

            for (String permission : permissionsNeeded) {
                if (checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                    permissionsToRequest.add(permission);
                }
            }

            if (permissionsToRequest.size() > 0) {
                String[] permissionsArray = permissionsToRequest.toArray(new String[permissionsToRequest.size()]);
                ActivityCompat.requestPermissions(this, permissionsArray, 1);
            }
        }
    }

    private boolean isWingmanServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }

        return false;
    }

    private void makeSnackbar(String message) {
        Snackbar.make(findViewById(R.id.coordinator_layout), message, Snackbar.LENGTH_LONG).show();
    }

    private void handleOnConnected() {
        makeSnackbar("Connected!");
    }

    private void handleOnDisconnected() {
        makeSnackbar("Disconnected!");
    }

    public class BluetoothDeviceStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String status = intent.getAction();
            if (status.equals(Constants.BLUETOOTH_CONNECTED_INTENT)) {
                handleOnConnected();
            } else if (status.equals(Constants.BLUETOOTH_DISCONNECTED_INTENT)) {
                handleOnDisconnected();
            }
        }
    }
}
