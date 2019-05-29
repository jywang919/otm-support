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
 
    private String name;
    private String data;
    
    private String instime;
    
    
    public long getId() {
        return id;
    }
 
    public void setId(long id) {
        this.id = id;
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

	public void setData(String data) {
		this.data = data;
	}
    
    
}