package com.ori.rest;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
 
@Entity
public class Stat {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
 
	public long getId() {
        return id;
    }
 
    public void setId(long id) {
        this.id = id;
    }

    public Stat(long id, String name, String instime, String data) {
    	this(name, instime, data);
    	this.id=id;
    }    
    
    private String name;
    private String data;
    
    private String instime;
    
    public Stat() {
    	super();
    }
    public Stat(String name, String instime, String data) {
    	this();
    	this.name=name;
    	this.instime=instime;
    	this.data=data;
    }
 
    public void setName(String name) {
        this.name = name;
    }
     
    public String getName() {
        return name;
    }

	public String getData() {
		return data;
	}

	public String getInstime() {
		return instime;
	}

	public void setInstime(String instime) {
		this.instime = instime;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	
	
	
}