package com.rorpage.wingman.services.updates;

import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;
import com.rorpage.wingman.models.stocks.Stock;
import com.rorpage.wingman.util.formatters.StockFormatter;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import timber.log.Timber;

import static com.rorpage.wingman.modules.updatables.StockModule.PREFERENCE_KEY_MODULEDATA_STOCKMODULE;

public class StockUpdateService extends BaseUpdateService {
    @Override
    public void onCreate() {
        super.onCreate();

        String[] symbols = new String[] {
                "DBX", "DIS", "GOOGL", "MSFT", "NFLX"
        };

        Set<String> stocks = new HashSet<>();

        for (String symbol : symbols) {
            final String uri = String.format(
                    "https://api.stockpile.com/app/api/giftitems/pub/withquote/giftitemcode/%s::::%s::",
                    symbol, symbol);

            JsonObject json = null;
            try {
                json = Ion.with(this)
                        .load(uri)
                        .asJsonObject()
                        .get();

                Stock stock = mGson.fromJson(json, Stock.class);
                stocks.add(StockFormatter.getFormattedStock(stock));
            } catch (InterruptedException | ExecutionException e) {
                Timber.e(e);
            }
        }

        mSharedPreferences.edit()
                .putStringSet(PREFERENCE_KEY_MODULEDATA_STOCKMODULE, stocks)
                .apply();

        stopSelf();
    }
}
