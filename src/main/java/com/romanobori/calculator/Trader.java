package com.romanobori.calculator;

import com.romanobori.binance.RestBinanceClient;

public class Trader {
    boolean isTrading = false;
    RestBinanceClient client;

    public Trader(RestBinanceClient client) {
        this.client = client;
    }

    public void startTrading(long tradingTimeInSec, long intervalTimeInSec) throws InterruptedException {
        isTrading = true;
        long endTime = System.currentTimeMillis() + tradingTimeInSec * 1000;
        while (System.currentTimeMillis() < endTime) {
            tradeIteration();
            Thread.sleep(intervalTimeInSec * 1000);
        }
        isTrading = false;
    }

    private void tradeIteration() {
       /**
        AdviceToAction.Action action = new AdviceToAction(new StochasticCalculator(16)
                .calc(client.getCandlesticks()), client.isInMarket()).getAction();

        if (action.equals(AdviceToAction.Action.BUY)) {
            client.buy();
        } else if (action.equals(AdviceToAction.Action.SELL)) {
            client.sell();
        }
      */
        //I think this is need to be extracted to class ^^
    }
}
