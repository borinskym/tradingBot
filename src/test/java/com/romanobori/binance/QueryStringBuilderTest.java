package com.romanobori.binance;

import com.romanobori.security.SHA256Calculator;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class QueryStringBuilderTest {

    private final String PRIVATE_KEY = "NhqPtmdSJYdKjVHjA7PZj4Mge3R5YNiP1e3UZjInClVN65XAbvqqM6A7H5fATj0j";

    private final String BINANCE_HOST = "https://api.binance.com";
    private final String BINANCE_ORDER_PATH = "/api/v3/order";
    private QueryStringBuilder queryStringBuilder;
    @Before
    public void setUp() {
        queryStringBuilder = new QueryStringBuilder(params(), PRIVATE_KEY);
    }

    @Test
    public void shouldReturnBuyString() {
        assertEquals(expectedPath(), queryStringBuilder.execute());
    }

    private String expectedPath() {
        return String.join("&",
                        expectedArgs(),
                        String.format("signature=%s",
                                new SHA256Calculator(expectedArgs(), PRIVATE_KEY).calculate()));
    }


    @Test
    public void shouldReturnAccountParams() {

    }

    List<Pair<String, String>> params(){
        return Arrays.asList(
                Pair.of("symbol", "LTCBTC"),
                Pair.of("side", "BUY"),
                Pair.of("type", "MARKET"),
                Pair.of("timeInForce", "GTC"),
                Pair.of("quantity", "0.1"),
                Pair.of("recvWindow", "5000"),
                Pair.of("timestamp", "1499827319559"));
    }

    private String expectedArgs() {
        return String.join("&",
                "symbol=LTCBTC",
                "side=BUY",
                "type=MARKET",
                "timeInForce=GTC",
                "quantity=0.1",
                "recvWindow=5000",
                "timestamp=1499827319559");
    }

}