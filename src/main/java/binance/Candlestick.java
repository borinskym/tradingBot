package binance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class Candlestick {
    long openTime;
    double open;
    double high;
    double low;
    double close;
    double volume;
    long closeTime;
    double quoteAssetVolume;
    long numberOfTrades;
    double takerBuyBaseAssetVolume;
    double takerBuyQuoteAssetVolume;
    double ignore;
}
