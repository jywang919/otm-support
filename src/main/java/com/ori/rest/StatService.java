package com.ori.rest;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatService {
	private static final Logger LOG = LoggerFactory.getLogger(StatService.class);
	private static final boolean isLogDebugEnabled = LOG.isDebugEnabled();
	 
	@Autowired
	private StatRepository statRepository; 
	
	List<Stat> Local = new ArrayList<Stat>();
	List<Stat> StatsExMe = new ArrayList<Stat>();
	
	public List<Stat> getAllStats(){
		LOG.info("getAllStats");
		Local = new ArrayList<Stat>();
		statRepository.findAll().forEach(Local::add);
		
		return Local;
	}
	
	@SuppressWarnings("unchecked")
	public List<Stat> getAllStatsExcludeMe(){
		LOG.info("getAllStatsExcludeMe");
//		return topics;	
		List<Stat> tmp = new ArrayList<Stat>();	
		StatsExMe = new ArrayList<Stat>();
		statRepository.findAll().forEach(tmp::add);
		
		boolean exclude =false;
		String name;
		for(Stat st: tmp) {
			name=st.getName();
			 exclude =false;
			for(String id : IdsExcludes.MY_IDS) {
				if(name.indexOf(id) > -1) { //should be if id contains any of My_IDs
					exclude=true;
					break;
				}
			}
			if(!exclude)
				StatsExMe.add(st);
		}
		
		ArrayList<Stat> res = (ArrayList<Stat>)StatsExMe;
		Object cl = res.clone();
		ArrayList<Stat> st = (ArrayList<Stat>)cl;
		Collections.reverse(st);
		return st;
		
//		return StatsExMe;
	}
	
	  
	public List<String> getAllNames(){
		List<String> names = new ArrayList<String>();
//		statRepository.findAllNames().forEach(names::add);	
//		return names;
		List<Stat> stats = getAllStatsExcludeMe();		
		String ver="";
		int idx;
		for(Stat st: stats) {
			idx=st.getData().lastIndexOf(",")+1;
			ver = st.getData().substring(idx);//.replaceAll("\\", "");			
			names.add(st.getName()+ver+st.getInstime());
		}
		return names;
	}
	
	Date date;
	Instant instant;
	Timestamp ts;
	String instime;
	
	public List<String> getAllNamesToday(){		
		List<Stat> tmp = new ArrayList<Stat>();	
		StatsExMe = new ArrayList<Stat>();
		statRepository.findAll().forEach(tmp::add);
		
		instant = Instant.now(); //this time is local time
        date = Date.from(instant);        
        ts=new Timestamp(date.getTime());
        ts.setHours(0);
        ts.setMinutes(0);
        instime=ts.toString(); 
        
		boolean exclude =false;
		String name;
		for(Stat st: tmp) {
			name=st.getName();
			 exclude =false;
			for(String id : IdsExcludes.MY_IDS) {
				if(name.indexOf(id) > -1) { //should be if id contains any of My_IDs
					exclude=true;
					break;
				}
				if(st.getInstime().compareTo(instime) < 0) {
					exclude=true;
					break;
				}
			}
			if(!exclude)
				StatsExMe.add(st);
		}
		
		tmp = StatsExMe;
		List<String> names = new ArrayList<String>();
		List<String> namesOnly = new ArrayList<String>();
		String ver="";
		int idx;
		for(Stat st: tmp) {
			idx=st.getData().lastIndexOf(",")+1;
			ver = st.getData().substring(idx);//.replaceAll("\\", "");
			if(namesOnly.indexOf(st.getName()) < 0) {
				names.add("-------"+st.getName() + " "+ ver); //+st.getInstime());				
			}else if(namesOnly.indexOf(st.getName()) > -1) {
				names.add(st.getName() + " "+ ver); //st.getInstime());
			}
			namesOnly.add(st.getName());
		}
		
		return names;
	}
	
	
	public void addStat(Stat gps) {
//		topics.add(topic);		
		statRepository.save(gps);
		Local.add(gps);
	}
	
	public Stat getForName(String id) {
		return Local.stream().filter(t -> new String(""+t.getId()).equalsIgnoreCase(id)).findFirst().get();
	}


	public List<Stat> getForHours(int hr) {
		instant = Instant.now(); //this time is local time
        date = Date.from(instant);        
        ts=new Timestamp(date.getTime());
        int h = ts.getHours();
        ts.setHours(h-hr);
        instime=ts.toString(); 
		return Local.stream().filter(t -> t.getInstime().compareTo(instime) > 0 ).collect(Collectors.toList())
//				.findFirst().get()
				;
	}

	
	public void updateStat(Stat gps) {
		for(int i=0; i< Local.size(); i++) {
			Stat t = Local.get(i);
			if(t.getId() == gps.getId()) {
				Local.set(i, gps);
				statRepository.save(gps);
			}
		}		
	}

	public void deleteStat(String id) {
		System.out.print("Deleting id "+id);
		Local.removeIf(t -> (""+t.getId()).equalsIgnoreCase(id));	
		statRepository.delete(Long.valueOf(id));
	}

	private void deleteBunch(Iterable<Stat> gpses) {
		for(Stat gps : gpses) {
			Local.removeIf(t -> (t.getId() == gps.getId()));	
			statRepository.delete(Long.valueOf(gps.getId()));
		}
	}
	
	public void deleteStatSinceTime(String time) {
		System.out.print("deleteGpsSinceTime:  "+time);
		Iterable<Stat> gpses = statRepository.findByTimeSince(time);
		deleteBunch(gpses);
	}

	public void deleteStatOfName(String name) {
		System.out.print("deleteGpsOfName:  "+name);
		Iterable<Stat> gpses = statRepository.findByName(name);
		deleteBunch(gpses);
	}
	
	
}
