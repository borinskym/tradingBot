package binance;

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
public class RestBinanceClient implements binanceClient {

    private RestTemplate restTemplate;
    private String binanceHost;
    private String getCandlestickPath;

    @Autowired
    public RestBinanceClient(RestTemplate restTemplate,
                             @Value("${binance.host}") String binanceHost,
                             @Value("${binance.getCandlestickPath}") String getCandlestickPath) {
        this.restTemplate = restTemplate;
        this.binanceHost = binanceHost;
        this.getCandlestickPath = getCandlestickPath;
    }

    @Override
    public List<Candlestick> getCandlesticks() {
        try {
            ResponseEntity<JsonArray> response = restTemplate.getForEntity(binanceHost + getCandlestickPath, JsonArray.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new BinanceException("didnt success get candelsticks from binance");
            }
            return retrieveCandlesticksFromBody(response.getBody());
        } catch (RestClientException e) {
            throw new BinanceException(e);
        }
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
