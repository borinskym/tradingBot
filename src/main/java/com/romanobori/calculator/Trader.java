package com.romanobori.calculator;

import com.romanobori.binance.BinanceClient;
import com.romanobori.binance.RestBinanceClient;

public class Trader {
    boolean isTrading = false;
    BinanceClient client;

    public Trader(BinanceClient client) {
        this.client = client;
    }

    public void trade() {

        new AdviceToAction(
                new StochasticCalculator(16).calc(client.getCandlesticks()),
                client).trade();
    }
}
