package com.ori.rest.demo;

public interface IExcelService {
    public ExcelResultsData calculate();
    // public SineWaveResultsData findCaseDetailsByCaseKey(CaseNumber caseKey);
    public ExcelResultsData calculate(
            final ExcelInputData data);
}
