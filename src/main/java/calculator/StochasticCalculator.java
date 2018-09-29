package calculator;

import binance.Candlestick;
import com.sun.xml.internal.bind.v2.TODO;

import java.util.List;

public class StochasticCalculator {
    enum Advice {BUY, SELL, STAY}

    int threshHold;

    public StochasticCalculator(int threshHold) {
        this.threshHold = threshHold;
    }

    Advice calc(List<Candlestick> candlesticks) {
        if (isLessThenThreshHold(candlesticks))
            return Advice.STAY;
        else {
            double result = getStochasticCalc(candlesticks);
            if (isCross(result))
                return Advice.STAY;
            else if (isRedAboveBlue(result))
                return Advice.SELL;
            else
                return Advice.BUY;
        }
    }

    private boolean isRedAboveBlue(double result) {
    return result < 0;
    }

    private boolean isCross(double result) {
    return result == 0;
    }

    private double getStochasticCalc(List<Candlestick> candlesticks) {
        return getBlueValueForLastCandle(candlesticks) - getRedValueForLastCandle(candlesticks);
    }

    private double getRedValueForLastCandle(List<Candlestick> candlesticks) {
        return average(
                getBlueValueForLastCandle(candlesticks),
                getBlueValueForLastCandle(candlesticks.subList(0, candlesticks.size() - 1)),
                getBlueValueForLastCandle(candlesticks.subList(0, candlesticks.size() - 2))
        );
    }

    private double average(double firstValue, double secondValue, double thiredValue) {
        return (firstValue + secondValue + thiredValue / 3);
    }

    private double getBlueValueForLastCandle(List<Candlestick> candlesticks) {
        return 100 *
                (
                        (getCurrentClose(candlesticks) - getLowestOfInterval(candlesticks)) /
                                (getHighestOfInterval(candlesticks) - getLowestOfInterval(candlesticks))
                );
    }

    private double getCurrentClose(List<Candlestick> candlesticks) {
        return getCurrentCandle(candlesticks).getClose();
    }

    private double getHighestOfInterval(List<Candlestick> candlesticks) {
        double result = getCurrentCandle(candlesticks).getLow();
        for (int i = 1; i < 14; i++) {
            double currLow = candlesticks.get(candlesticks.size() - i).getHigh();
            if (currLow > result)
                result = currLow;
        }
        return result;
    }

    private double getLowestOfInterval(List<Candlestick> candlesticks) {
        double result = getCurrentCandle(candlesticks).getLow();
        for (int i = 1; i < 14; i++) {
            double currLow = candlesticks.get(candlesticks.size() - i).getLow();
            if (currLow < result)
                result = currLow;
        }
        return result;
    }

    private Candlestick getCurrentCandle(List<Candlestick> candlesticks) {
        return candlesticks.get(candlesticks.size() - 1);
    }

    private boolean isLessThenThreshHold(List<Candlestick> candlesticks) {
        return candlesticks.size() < threshHold;
    }
}
