package com.ori.rest.demo;


public class ExcelInputData {

    static Float DEFAULT_FREQUENCY = new Float(0.01);

    static Double DEFAULT_OFFSET = new Double(0.1);
    private static final long serialVersionUID = -6492053913066834188L;
    
    //set default values for amp, frequency, offset, n
    float amp = 1; 
    Float frequency = ExcelInputData.DEFAULT_FREQUENCY;
    Double offset = ExcelInputData.DEFAULT_OFFSET;
    int n = 301;

    Integer[] x;

    public ExcelInputData() {
        x = new Integer[n];
        for (int i = 0; i < n; i++ ) {
            x[i] = i;
        }
    }

    public ExcelInputData(Float amp,Float frequency,Double offset, Integer n) {
    	if(null != amp ) this.amp = amp;
    	if(null != frequency ) this.frequency = frequency;
    	if(null != offset ) this.offset = offset;
    	if(null != n ) this.n = n.intValue();
        x = new Integer[n];
        for (int i = 0; i < n; i++ ) {
            x[i] = i;
        }
    }
    

    public float getAmp() {
		return amp;
	}

	public void setAmp(float amp) {
		this.amp = amp;
	}

	public Float getFrequency() {
        return frequency;
    }

    public int getN() {
        return n;
    }

    public Double getOffset() {
        return offset;
    }

    public Integer[] getX() {
        return x;
    }

    public boolean isDefault(){
        if(amp == 1 && n == 301 && frequency == ExcelInputData.DEFAULT_FREQUENCY && offset == ExcelInputData.DEFAULT_OFFSET) {
            return true;
        }
        return false;
    }

    public void setFrequency(final Float frequency) {
        this.frequency = frequency;
    }

    public void setN(final int n) {
        this.n = n;
    }

    public void setOffset(final Double offset) {
        this.offset = offset;
    }

    public void setX(final Integer[] x) {
        this.x = x;
    }

    public String toString() {
        return "SineWaveData [" + "'Frequency':" + frequency + "'Offset':"
                + offset + "'Amp':" + amp + "'Intervals':" + n + "]";
    }

}
