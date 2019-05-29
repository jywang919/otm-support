package com.ori.rest.demo;

import java.io.Serializable;
import java.util.Arrays;

public class ExcelResultsData implements Serializable {

    private static final long serialVersionUID = 7587886324282928278L;


    private Double[] teta1 = new Double[3];
    
    private String chartName ="";

    public String getChartName() {
		return chartName;
	}

	public void setChartName(String chartName) {
		this.chartName = chartName;
	}

	public Double[] getTeta1() {
        return teta1;
    }

    public void setTeta1(final Double[] teta1) {
        this.teta1 = teta1;
    }

    @Override
    public String toString() {
        return "ExcelResultsData: " +Arrays.toString(teta1)+" chart name: "+this.chartName;
    }

}
