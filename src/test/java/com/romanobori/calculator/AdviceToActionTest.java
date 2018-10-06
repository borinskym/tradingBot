package com.romanobori.calculator;

import com.romanobori.binance.BinanceClient;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AdviceToActionTest {
    private BinanceClient client = mock(BinanceClient.class);

    @Test
    public void shouldBuyIfOutOfMarketAndAdviceIsBuy() {
        when(client.isInMarket()).thenReturn(false);
        new AdviceToAction(Advice.BUY, client).trade();

        verify(client, times(1)).buy();
        verify(client, never()).sell();
    }

    @Test
    public void shouldStayIfOutOfMarketAndAdviceIsSell() {
        when(client.isInMarket()).thenReturn(false);
        new AdviceToAction(Advice.SELL, client).trade();

        verify(client, never()).buy();
        verify(client, never()).sell();
    }

    @Test
    public void shouldStayIfOutOfMarketAndAdviceIsStay() {
        when(client.isInMarket()).thenReturn(false);
        new AdviceToAction(Advice.STAY, client).trade();

        verify(client, never()).buy();
        verify(client, never()).sell();
    }

    @Test
    public void shouldStayIfInMarketAndAdviceIsBuy() {
        when(client.isInMarket()).thenReturn(true);
        new AdviceToAction(Advice.BUY, client).trade();

        verify(client, never()).buy();
        verify(client, never()).sell();
    }

    @Test
    public void shouldSellIfInMarketAndAdviceIsSell() {
        when(client.isInMarket()).thenReturn(true);
        new AdviceToAction(Advice.SELL, client).trade();

        verify(client, never()).buy();
        verify(client, times(1)).sell();
    }

    @Test
    public void shouldStayIfInMarketAndAdviceIsStay() {
        when(client.isInMarket()).thenReturn(true);
        new AdviceToAction(Advice.STAY, client).trade();

        verify(client, never()).buy();
        verify(client, never()).sell();
    }
}