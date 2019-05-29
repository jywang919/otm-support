package com.ori.rest;

import java.io.Serializable;
import java.util.Arrays;

public class SineWaveResultsData implements Serializable {

    private static final long serialVersionUID = 7587886324282928278L;


    private Double[] teta1 = new Double[3];
    
    private String chartName ="";
    private String image ="";
    
    public String getChartName() {
		return chartName;
	}

	public void setChartName(String chartName) {
		this.chartName = chartName;
	}	

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Double[] getTeta1() {
        return teta1;
    }

    public void setTeta1(final Double[] teta1) {
        this.teta1 = teta1;
    }

    @Override
    public String toString() {
        return "SineWaveResults teta1: " +Arrays.toString(teta1)+" chart name: "+this.chartName;
    }

}
