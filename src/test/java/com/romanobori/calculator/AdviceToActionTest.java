package com.romanobori.calculator;

import org.junit.Test;

import static org.junit.Assert.*;

public class AdviceToActionTest {
    @Test
    public void shouldBuyIfOutOfMarketAndAdviceIsBuy(){
        assertEquals(AdviceToAction.Action.BUY,
                new AdviceToAction(Advice.BUY,false).getAction());
    }
    @Test
    public void shouldStayIfOutOfMarketAndAdviceIsSell(){
        assertEquals(AdviceToAction.Action.STAY,
                new AdviceToAction(Advice.SELL,false).getAction());
    }
    @Test
    public void shouldStayIfOutOfMarketAndAdviceIsStay(){
        assertEquals(AdviceToAction.Action.STAY,
                new AdviceToAction(Advice.STAY,false).getAction());
    }
    @Test
    public void shouldStayIfInMarketAndAdviceIsBuy(){
        assertEquals(AdviceToAction.Action.STAY,
                new AdviceToAction(Advice.BUY,true).getAction());
    }
    @Test
    public void shouldSellIfInMarketAndAdviceIsSell(){
        assertEquals(AdviceToAction.Action.SELL,
                new AdviceToAction(Advice.SELL,true).getAction());
    }
    @Test
    public void shouldStayIfInMarketAndAdviceIsStay(){
        assertEquals(AdviceToAction.Action.STAY,
                new AdviceToAction(Advice.STAY,true).getAction());
    }
}