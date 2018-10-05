package com.romanobori.binance;

import java.util.List;

public interface binanceClient {

    List<Candlestick> getCandlesticks();
    void sell();
    void buy();
    boolean isInMarket();

    public class BinanceException extends RuntimeException{

        public BinanceException(String message) {
            super(message);
        }

        public BinanceException(Throwable cause) {
            super(cause);
        }
    }
}
