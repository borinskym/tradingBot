package com.romanobori.binance;

import java.util.List;

public interface binanceClient {

    List<Candlestick> getCandlesticks();
    BinanceWallet getWallet();
    public void sell(double quantity);
    public void buy(double quantity);

    public class BinanceException extends RuntimeException{

        public BinanceException(String message) {
            super(message);
        }

        public BinanceException(Throwable cause) {
            super(cause);
        }
    }
}
