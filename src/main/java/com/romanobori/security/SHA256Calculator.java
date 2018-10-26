package com.romanobori.security;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SHA256Calculator {
    private String message;
    private String secret;

    public SHA256Calculator(String message, String secret) {
        this.message = message;
        this.secret = secret;
    }

    public String calculate(){
        try {
            return encode();
        } catch (Exception e) {
            throw new RuntimeException("Unable to sign message.", e);
        }
    }

    private String encode() throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secretKeySpec);
        return new String(Hex.encodeHex(sha256_HMAC.doFinal(message.getBytes())));
    }
}
