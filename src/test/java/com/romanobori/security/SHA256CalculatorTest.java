package com.romanobori.security;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SHA256CalculatorTest {

    private String params = "symbol=LTCBTC&side=BUY&type=LIMIT&timeInForce=GTC&quantity=1&price=0.1&recvWindow=5000&timestamp=1499827319559";
    private String privateKey = "NhqPtmdSJYdKjVHjA7PZj4Mge3R5YNiP1e3UZjInClVN65XAbvqqM6A7H5fATj0j";

    @Test
    public void shouldCalculateSHA256() {
        assertThat(new SHA256Calculator(params, privateKey).calculate(),
                is(expectedResult()));
    }

    private String expectedResult() {
        return "c8db56825ae71d6d79447849e617115f4a920fa2acdcab2b053c4b2838bd6b71";
    }

}