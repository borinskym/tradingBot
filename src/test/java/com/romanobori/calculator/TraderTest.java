package com.romanobori.calculator;

import com.romanobori.binance.RestBinanceClient;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class TraderTest {

    @Test
    public void shouldStopTradingAfterThreeSeconds() throws InterruptedException {
        RestTemplate restTemplate = mock(RestTemplate.class);
        String binanceHost = "http://com.romanobori.binance";
        String getcandelstickPath = "path";

        RestBinanceClient restBinanceClient = new RestBinanceClient(restTemplate, binanceHost, getcandelstickPath);

        Trader trader = new Trader(restBinanceClient);
        trader.startTrading(3, 1);
        assertEquals(false, trader.isTrading);
    }
}