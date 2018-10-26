package com.romanobori.time;

import org.springframework.stereotype.Service;

@Service
public class TimeCalculatorImpl implements TimeCalculator {
    @Override
    public long calcTime() {
        return System.currentTimeMillis();
    }
}
