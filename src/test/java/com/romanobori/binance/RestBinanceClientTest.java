package com.romanobori.binance;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.romanobori.time.TimeCalculator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class RestBinanceClientTest {

    private String binanceHost = "https://api.binance.com";
    private String getcandelstickPath = "path";
    private String orderPath = "/api/v3/order";
    private String privateKey = "NhqPtmdSJYdKjVHjA7PZj4Mge3R5YNiP1e3UZjInClVN65XAbvqqM6A7H5fATj0j";
    private String publicKey = "vmPUZE6mv9SD5VNHk4HlWFsOr6aKE2zvsw0MuIgwCIPy6utIco14y7Ju91duEh8A";
    private RestTemplate restTemplate;
    private TimeCalculator timeCalculator;
    private RestBinanceClient restBinanceClient;

    @Before
    public void setUp(){
        restTemplate = mock(RestTemplate.class);
        timeCalculator = mock(TimeCalculator.class);

        when(timeCalculator.calcTime()).thenReturn(1499827319559L);
        restBinanceClient = new RestBinanceClient(restTemplate, timeCalculator, binanceHost, getcandelstickPath, orderPath, privateKey, publicKey);
    }

    @Test
    public void shouldGetCandlesticks() {
        when(restTemplate.getForEntity(binanceHost + getcandelstickPath, JsonArray.class))
                .thenReturn(ResponseEntity.ok().body(new Gson().fromJson(candlesticksJson(), JsonArray.class)));

        List<Candlestick> candlesticks = restBinanceClient.getCandlesticks();

        assertEquals(1, candlesticks.size());
        assertThat(candlesticks.get(0), is(expectedCandlestick()));
    }

    @Test
    public void shouldBuyWithRightParams() {
        restBinanceClient.buy(0.1);

        verify(restTemplate).exchange(eq(buyUrl()), eq(HttpMethod.POST), entityWithSecurityHeader(), eq(String.class));
    }


    @Test
    public void shouldSell() {
        restBinanceClient.sell(0.1);

        verify(restTemplate).exchange(eq(sellUrl()), eq(HttpMethod.POST), entityWithSecurityHeader(), eq(String.class));
    }

    private HttpEntity entityWithSecurityHeader() {
        return argThat(argument -> {
            HttpHeaders headers = argument.getHeaders();
            return headers.entrySet().stream().anyMatch((e) -> e.getKey().equals("X-MBX-APIKEY") && e.getValue().contains(publicKey));
        });
    }

    private String buyParams() {
        return "symbol=BTCUSD&side=BUY&type=MARKET&timeInForce=GTC&quantity=0.1&recvWindow=5000&timestamp=1499827319559&signature=cd2a6c85ea66ab5218ed85b67adc2a9de9714668f3197e8ba24f6a0394b83fb1";
    }

    private String buyUrl() {
        return orderUrl() + buyParams();
    }

    private String sellUrl(){
        return orderUrl() + sellParams();
    }

    private String sellParams() {
        return "symbol=BTCUSD&side=SELL&type=MARKET&timeInForce=GTC&quantity=0.1&recvWindow=5000&timestamp=1499827319559&signature=ef3ce0d808d019b6a50aa1e9acd1605b4330cab429783d44996afdccf52e3a03";
    }

    private String orderUrl() {
        return "https://api.binance.com/api/v3/order?";
    }

    private String candlesticksJson() {
        return "[\n" +
                "      [\n" +
                "        1531108800000," +
                "        \"6717.28000000\"," +
                "        \"6748.27000000\"," +
                "        \"6682.00000000\"," +
                "        \"6725.00000000\"," +
                "        \"3414.46336800\"," +
                "        1531123199999," +
                "        \"22902235.18345779\"," +
                "        18537,\n" +
                "        \"1954.14633800\",\n" +
                "        \"13107200.18797596\"," +
                "        \"0\"" +
                "    ]" +
                "      ]";
    }


    @Test(expected = BinanceClient.BinanceException.class)
    public void shouldFailWhenStatusReturnedNotOK() {
        when(restTemplate.getForEntity(binanceHost + getcandelstickPath, JsonArray.class))
                .thenReturn(ResponseEntity.badRequest().build());

        restBinanceClient.getCandlesticks();

    }

    @Test(expected = BinanceClient.BinanceException.class)
    public void shouldRaiseError_WhenFailFetchRestTemplate() {
        when(restTemplate.getForEntity(binanceHost + getcandelstickPath, JsonArray.class))
                .thenThrow(new RestClientException("rest client exception"));

        restBinanceClient.getCandlesticks();
    }

    private Candlestick expectedCandlestick() {
        return Candlestick.builder()
                .close(6725.0)
                .closeTime(1531123199999L)
                .high(6748.27)
                .low(6682.0)
                .open(6717.28)
                .build();
    }

}