package com.ori.rest;

import java.io.IOException;

public interface ISineWaveService {
    public SineWaveResultsData calculate() throws IOException;
    // public SineWaveResultsData findCaseDetailsByCaseKey(CaseNumber caseKey);
    public SineWaveResultsData calculate(
            final SineWaveData data) throws IOException ;
}
