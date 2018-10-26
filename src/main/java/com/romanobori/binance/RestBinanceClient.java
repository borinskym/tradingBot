package com.romanobori.binance;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.romanobori.time.TimeCalculator;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RestBinanceClient implements BinanceClient {

    private RestTemplate restTemplate;
    private TimeCalculator timeCalculator;
    private String orderPath;
    private String privateKey;
    private String publicKey;
    private String binanceHost;
    private String getCandlestickPath;

    public void sell(){};
    public void buy(){};
    public boolean isInMarket(){
        return true;
    };
    @Autowired
    public RestBinanceClient(RestTemplate restTemplate,
                             TimeCalculator timeCalculator,
                             @Value("${com.romanobori.binance.host}") String binanceHost,
                             @Value("${com.romanobori.binance.getCandlestickPath}") String getCandlestickPath,
                             @Value("${com.romanobori.binance.orderPath}") String orderPath,
                             @Value("${com.romanobori.binance.privateKey}") String privateKey,
                             @Value("${com.romanobori.binance.publicKey}") String publicKey) {
        this.restTemplate = restTemplate;
        this.binanceHost = binanceHost;
        this.getCandlestickPath = getCandlestickPath;
        this.timeCalculator = timeCalculator;
        this.orderPath = orderPath;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    @Override
    public List<Candlestick> getCandlesticks() {
        try {
            ResponseEntity<JsonArray> response = restTemplate.getForEntity(binanceHost + getCandlestickPath, JsonArray.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new BinanceException("didnt success get candelsticks from com.romanobori.binance");
            }
            return retrieveCandlesticksFromBody(response.getBody());
        } catch (RestClientException e) {
            throw new BinanceException(e);
        }
    }

    @Override
    public BinanceWallet getWallet() {
        return null;
    }

    @Override
    public void sell(double quantity) {
        restTemplate.exchange(binanceHost + orderPath + "?" + createPathForTrade(quantity, "SELL"), HttpMethod.POST, new HttpEntity<>(createHeaders()), String.class);
    }

    @Override
    public void buy(double quantity) {
        restTemplate.exchange(binanceHost + orderPath + "?" + createPathForTrade(quantity, "BUY"), HttpMethod.POST, new HttpEntity<>(createHeaders()), String.class);
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-MBX-APIKEY", publicKey);
        return headers;
    }

    private String createPathForTrade(double quantity, String side) {
        return new QueryStringBuilder(
                Arrays.asList(
                        Pair.of("symbol", "BTCUSD"),
                        Pair.of("side", side),
                        Pair.of("type", "MARKET"),
                        Pair.of("timeInForce", "GTC"),
                        Pair.of("quantity", Double.toString(quantity)),
                        Pair.of("recvWindow", Integer.toString(5000)),
                        Pair.of("timestamp", Long.toString(timeCalculator.calcTime()))),
                privateKey
        ).execute();
    }

    private List<Candlestick> retrieveCandlesticksFromBody(JsonArray candlesticksJson) {
        List<Candlestick> candlesticks = new ArrayList<>();

        for (JsonElement candlestickJsonElement : candlesticksJson) {
            JsonArray candlestickJson = candlestickJsonElement.getAsJsonArray();
            candlesticks.add(
                    Candlestick.builder()
                            .close(candlestickJson.get(4).getAsDouble())
                            .open(candlestickJson.get(1).getAsDouble())
                            .closeTime(candlestickJson.get(6).getAsLong())
                            .high(candlestickJson.get(2).getAsDouble())
                            .low(candlestickJson.get(3).getAsDouble())
                            .build()
            );
        }
        return candlesticks;
    }
}
