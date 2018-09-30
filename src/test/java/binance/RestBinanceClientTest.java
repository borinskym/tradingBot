package binance;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RestBinanceClientTest {

    RestTemplate restTemplate = mock(RestTemplate.class);
    String binanceHost = "http://binance";
    String getcandelstickPath = "path";

    RestBinanceClient restBinanceClient = new RestBinanceClient(restTemplate, binanceHost, getcandelstickPath);

    String candlesticksJson = "[\n" +
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

    @Test
    public void shouldGetCandlesticks() {
        when(restTemplate.getForEntity(binanceHost + getcandelstickPath, JsonArray.class))
                .thenReturn(ResponseEntity.ok().body(new Gson().fromJson(candlesticksJson, JsonArray.class)));

        List<Candlestick> candlesticks = restBinanceClient.getCandlesticks();

        assertEquals(1, candlesticks.size());
        assertThat(candlesticks.get(0), is(expectedCandlestick()));

    }


    @Test(expected = binanceClient.BinanceException.class)
    public void shouldFailWhenStatusReturnedNotOK() {
        when(restTemplate.getForEntity(binanceHost + getcandelstickPath, JsonArray.class))
                .thenReturn(ResponseEntity.badRequest().build());

        restBinanceClient.getCandlesticks();

    }

    @Test(expected = binanceClient.BinanceException.class)
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