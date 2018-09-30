package com.romanobori.calculator;

import com.romanobori.calculator.Advice;

import java.util.Arrays;

public class AdviceToAction {
    enum Action {BUY, SELL, STAY}
    private Advice advice;
    private boolean isInMarket;
    public AdviceToAction(Advice advice,boolean isInMarket) {
        this.advice = advice;
        this.isInMarket = isInMarket;
    }


    public Action getAction(){
        return isInMarket ? inMarketAction() : outOfMarketAction();
    }

    private Action outOfMarketAction() {
        return isSellOrStay() ? Action.STAY : Action.BUY;
    }

    private Action inMarketAction() {
        return isBuyOrStay() ? Action.STAY : Action.SELL;
    }

    private boolean isBuyOrStay() {
        return Arrays.asList(Advice.BUY, Advice.STAY).contains(advice);
    }

    private boolean isSellOrStay() {
        return Arrays.asList(Advice.SELL, Advice.STAY).contains(advice);
    }
}
