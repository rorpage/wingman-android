package com.rorpage.wingman.services.updates;

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
                                Stock stock = mGson.fromJson(result, Stock.class);
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
}
