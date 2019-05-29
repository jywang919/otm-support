package com.ori.rest;

public class NameStat{
    
	
	public long cnt = 0;
    private String name;
    
    public NameStat() {
    	super();
    }
    public NameStat(String name) {
    	this();
    	this.name=name;
    	this.cnt = 1;
    }
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
}
