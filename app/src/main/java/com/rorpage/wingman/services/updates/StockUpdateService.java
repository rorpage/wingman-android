package com.rorpage.wingman.services.updates;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.rorpage.wingman.models.stocks.Stock;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import timber.log.Timber;

import static com.rorpage.wingman.modules.updatables.StockModule.PREFERENCE_KEY_MODULEDATA_STOCKMODULE;

public class StockUpdateService extends BaseUpdateService {
    private Set<String> stocks;

    @Override
    public void onCreate() {
        super.onCreate();

        stocks = new HashSet<>();

        String[] symbols = new String[] {
//          "DBX", "DIS", "GOOGL", "MSFT", "NFLX"
            "DBX"
        };

        for (String symbol : symbols) {
            final String uri = String.format(Locale.US,
                    "https://api.stockpile.com/app/api/giftitems/pub/withquote/giftitemcode/%s::::%s::",
                    symbol, symbol);
            Ion.with(StockUpdateService.this)
                    .load(uri)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            Timber.d("onCompleted()");
                            if (e != null) {
                                Timber.e(e);
                            } else {
                                Stock stock = (new Gson()).fromJson(result, Stock.class);
                                stocks.add(stock.toString());

                                mSharedPreferences.edit()
                                        .putStringSet(PREFERENCE_KEY_MODULEDATA_STOCKMODULE, stocks)
                                        .apply();

                                stopSelf();
                            }
                        }
                    });
        }
    }

//    @Override
//    public boolean onStartJob(final JobParameters jobParameters) {
//        Timber.d("onStartJob()");
//
//        stocks = new HashSet<>();
//
//        final SharedPreferences sharedPreferences =
//                PreferenceManager.getDefaultSharedPreferences(this);
//
//        ArrayList<String> symbols = new ArrayList<>();
//        symbols.add("DBX");
//        symbols.add("DIS");
//        symbols.add("GOOGL");
//        symbols.add("MSFT");
//        symbols.add("NFLX");
//
//        for (String symbol : symbols) {
//            final String uri = String.format(Locale.US,
//                    "https://api.darksky.net/forecast/%s/%.4f,%.4f",
//                    symbol,
//                    0,
//                    0);
//
//            Ion.with(StockUpdateService.this)
//                    .load(uri)
//                    .asJsonObject()
//                    .withResponse()
//                    .setCallback(this);
//        }
//
//        sharedPreferences.edit()
//                .putStringSet(PREFERENCE_KEY_MODULEDATA_STOCKMODULE, stocks)
//                .apply();
//
//        jobFinished(jobParameters, false);
//        return true;
//    }
//
//    @Override
//    public boolean onStopJob(JobParameters jobParameters) {
//        return true;
//    }
//
//    @Override
//    public void onCompleted(Exception e, Response<JsonObject> result) {
//        Timber.d("onCompleted()");
//        if (e != null) {
//            Timber.e(e);
//        } else {
//            Stock stock = (new Gson()).fromJson(result.getResult(), Stock.class);
//            stocks.add(stock.toString());
//        }
//    }
}
