package com.romanobori.binance;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestBinanceClient implements BinanceClient {

    private RestTemplate restTemplate;
    private String binanceHost;
    private String getCandlestickPath;

    public void sell(){};
    public void buy(){};
    public boolean isInMarket(){
        return true;
    };
    @Autowired
    public RestBinanceClient(RestTemplate restTemplate,
                             @Value("${com.romanobori.binance.host}") String binanceHost,
                             @Value("${com.romanobori.binance.getCandlestickPath}") String getCandlestickPath) {
        this.restTemplate = restTemplate;
        this.binanceHost = binanceHost;
        this.getCandlestickPath = getCandlestickPath;
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

    }

    @Override
    public void buy(double quantity) {

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
