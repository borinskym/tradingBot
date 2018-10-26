package com.romanobori.binance;

import com.romanobori.security.SHA256Calculator;
import lombok.Builder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class QueryStringBuilder {

    List<Pair<String, String>> params;
    private String privateKey;

    public QueryStringBuilder(List<Pair<String, String>> params, String privateKey) {
        this.params = params;

        this.privateKey = privateKey;
    }

    public String execute() {
        String paramsString = params();

        return String.join("&",
                paramsString,
                String.format("signature=%s",
                        new SHA256Calculator(paramsString, privateKey).calculate()));
    }

    private String params() {
        return params.stream().map(pair -> pair.getLeft() + "=" + pair.getRight()
        ).collect(Collectors.joining("&"));
    }

}
