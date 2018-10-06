package com.romanobori.binance;

import java.util.List;

public interface BinanceClient {

    List<Candlestick> getCandlesticks();
    BinanceWallet getWallet();
    public void sell(double quantity);
    public void buy(double quantity);
    public void sell();
    public void buy();

    public boolean isInMarket();

    public class BinanceException extends RuntimeException{

        public BinanceException(String message) {
            super(message);
        }

        public BinanceException(Throwable cause) {
            super(cause);
        }
    }
}
