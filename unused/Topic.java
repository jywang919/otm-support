package com.ori.rest;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Topic {
	
	@Id
	String id;
	String name;
	String description;
	
	public Topic() {
		super();
	}
	
	public Topic(String id, String name,String desc) {
		super();
		this.id=id;
		this.name=name;
		this.description=desc;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
	
	public String toString() {
		return "Topic id:"+id+", name:"+name+", description: "+description;
	}
}
