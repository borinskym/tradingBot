package com.romanobori.calculator;

import com.romanobori.binance.BinanceClient;

public class AdviceToAction {
    enum Action {BUY, SELL, STAY}
    private Advice advice;
    BinanceClient client;
    public AdviceToAction(Advice advice, BinanceClient client) {
        this.advice = advice;
        this.client = client;

    }

    public void trade(){

        if(client.isInMarket())
             inMarketAction();
        else
             outOfMarketAction();
    }

    private void outOfMarketAction() {
        if(isBuyAdvice()) client.buy();
    }

    private void inMarketAction() {
        if(isSellAdvice()) client.sell();
    }

    private boolean isBuyAdvice() {
        return advice == Advice.BUY;
    }

    private boolean isSellAdvice() {
        return advice == Advice.SELL;
    }
}
