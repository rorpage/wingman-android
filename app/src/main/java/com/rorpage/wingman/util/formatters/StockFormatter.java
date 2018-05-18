package com.rorpage.wingman.util.formatters;

import com.rorpage.wingman.models.stocks.Stock;

import java.util.Locale;

public class StockFormatter {
    public static String getFormattedStock(Stock stock) {
        double priceChangeAsDouble = getPriceChangeAsDouble(stock);

        String formatString = "%.2f";
        if (stock.Result.Quote.PriceChange.contains("-")) {
            formatString = "(%.2f)";
        }

        String priceChange = String.format(Locale.US, formatString, priceChangeAsDouble);
        return String.format(Locale.US, "%s $%.2f\n$%s (%s%%)",
                stock.Result.Quote.Symbol,
                stock.Result.Quote.Price,
                priceChange,
                stock.Result.Quote.PercentChange);
    }

    private static double getPriceChangeAsDouble(Stock stock) {
        String cleanPriceChange = stock.Result.Quote.PriceChange
                .replace("$", "")
                .replace("-", "");
        return Double.parseDouble(cleanPriceChange);
    }
}
