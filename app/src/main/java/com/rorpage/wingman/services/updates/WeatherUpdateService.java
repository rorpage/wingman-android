package com.rorpage.wingman.services.updates;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.rorpage.wingman.R;
import com.rorpage.wingman.models.weather.darksky.DarkSkyWeather;

import java.util.Locale;

import timber.log.Timber;

import static com.rorpage.wingman.modules.updatables.WeatherModule.PREFERENCE_KEY_MODULEDATA_WEATHERMODULE;

public class WeatherUpdateService extends BaseUpdateService {
    @Override
    public void onCreate() {
        super.onCreate();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_DENIED) {
            FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            final String uri = String.format(Locale.US,
                                    "https://api.darksky.net/forecast/%s/%.4f,%.4f?exclude=minutely,hourly,daily,alerts,flags",
                                    getString(R.string.DarkSkyApiKey),
                                    location.getLatitude(),
                                    location.getLongitude());

                            Ion.with(WeatherUpdateService.this)
                                    .load(uri)
                                    .asJsonObject()
                                    .setCallback(new FutureCallback<JsonObject>() {
                                        @Override
                                        public void onCompleted(Exception e, JsonObject result) {
                                            Timber.d("onCompleted()");
                                            if (e != null) {
                                                Timber.e(e);
                                            } else {
                                                DarkSkyWeather currentWeather =
                                                        mGson.fromJson(result, DarkSkyWeather.class);

                                                mSharedPreferences.edit()
                                                        .putString(PREFERENCE_KEY_MODULEDATA_WEATHERMODULE, currentWeather.toString())
                                                        .apply();

                                                stopSelf();
                                            }
                                        }
                                    });
                        }
                    });
        }
    }
}
