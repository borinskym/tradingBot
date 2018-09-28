package calculator;

import binance.Candlestick;

import java.util.List;

public class StochasticCalculator {
    int threshHold;

    public StochasticCalculator(int threshHold) {
        this.threshHold = threshHold;
    }


    boolean calc(List<Candlestick> candlesticks) {
        if (isLessThenThreshHold(candlesticks))
            return false;
        else
            return true;
    }

    private boolean isLessThenThreshHold(List<Candlestick> candlesticks) {
        return candlesticks.size() < threshHold;
    }
}
