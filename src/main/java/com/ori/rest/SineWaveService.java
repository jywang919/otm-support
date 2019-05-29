package com.ori.rest;

import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class SineWaveService implements ISineWaveService {

    SineWaveHelper helper = null;

    public SineWaveResultsData calculate() throws IOException {
        helper = new SineWaveHelper();
        return helper.calc();
    }

    public SineWaveResultsData calculate(final SineWaveData data
    ) throws IOException {
        helper = new SineWaveHelper(data);
        return helper.calc();
    }

}
