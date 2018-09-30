package com.romanobori.calculator;


import com.romanobori.binance.Candlestick;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class StochasticCalculator {

    private int threshHold;

    public StochasticCalculator(int threshHold) {
        this.threshHold = threshHold;
    }

    Advice calc(List<Candlestick> candlesticks) {
        return isLessThenThreshHold(candlesticks)?  Advice.STAY :
                adviceForSufficientInput(candlesticks);
    }

    private Advice adviceForSufficientInput(List<Candlestick> candlesticks) {
        double result = getStochasticCalc(candlesticks);
        return isCross(result) ? Advice.STAY : adviceForNotCrossResult(result);

    }

    private Advice adviceForNotCrossResult(double result) {
        return isRedAboveBlue(result) ? Advice.SELL : Advice.BUY;
    }

    private boolean isLessThenThreshHold(List<Candlestick> candlesticks) {
        return candlesticks.size() < threshHold;
    }

    private double getStochasticCalc(List<Candlestick> candlesticks) {
        return getBlueValueForLastCandle(candlesticks) - getRedValueForLastCandle(candlesticks);
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
        return getExtremePointOfInterval(candlesticks, Candlestick::getHigh,(result, current)-> result < current);

    }

    private double getLowestOfInterval(List<Candlestick> candlesticks) {
        return getExtremePointOfInterval(candlesticks, Candlestick::getLow,(result, current)->result > current);
    }

    private double getExtremePointOfInterval(List<Candlestick> candlesticks, Function<Candlestick, Double> getExtremePoint, BiFunction<Double, Double, Boolean> condition) {
        double result = getExtremePoint.apply(getCurrentCandle(candlesticks));
        for (int i = 1; i < 14; i++) {
            double currExtremeValue = getExtremePoint.apply(candlesticks.get(candlesticks.size() - 1 - i));
            if (condition.apply(result, currExtremeValue))
                result = currExtremeValue;
        }
        return result;

    }

    private Candlestick getCurrentCandle(List<Candlestick> candlesticks) {
        return candlesticks.get(candlesticks.size() - 1);
    }


    private double getRedValueForLastCandle(List<Candlestick> candlesticks) {
        return average(
                getBlueValueForLastCandle(candlesticks),
                getBlueValueForLastCandle(candlesticks.subList(0, candlesticks.size() - 1)),
                getBlueValueForLastCandle(candlesticks.subList(0, candlesticks.size() - 2))
        );
    }

    private double average(double firstValue, double secondValue, double thirdValue) {
        return (firstValue + secondValue + thirdValue / 3);
    }

    private boolean isRedAboveBlue(double result) {
        return result < 0;
    }

    private boolean isCross(double result) {
        return result == 0;
    }
}
