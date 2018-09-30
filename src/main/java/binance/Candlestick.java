package binance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class Candlestick {
    double open;
    double high;
    double low;
    double close;
    long closeTime;
}
