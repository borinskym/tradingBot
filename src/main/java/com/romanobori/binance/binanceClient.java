package com.romanobori.binance;

import java.util.List;

public interface binanceClient {

    List<Candlestick> getCandlesticks();

    public class BinanceException extends RuntimeException{

        public BinanceException(String message) {
            super(message);
        }

        public BinanceException(Throwable cause) {
            super(cause);
        }
    }
}
