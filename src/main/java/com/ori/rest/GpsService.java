package com.ori.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GpsService {
	private static final Logger LOG = LoggerFactory.getLogger(GpsService.class);
	@Autowired
	private GpsRepository gpsRepository; 
	
	List<Gps> Local = new ArrayList<Gps>();
	List<Gps> GpsesExMe = new ArrayList<Gps>();
	public List<Gps> getAllGps(){
		LOG.info(this.getClass().getSimpleName() +" - getAllGps");
		Local = new ArrayList<Gps>();
		gpsRepository.findAll().forEach(Local::add);
		
		return Local;
	}

	public List<Gps> getAllGpsExcludeMine(){
		LOG.info("getAllGpsExcludeMine");
//		return topics;	
		List<Gps> tmp = new ArrayList<Gps>();
		GpsesExMe = new ArrayList<Gps>();			
		gpsRepository.findAll().forEach(tmp::add);
				
		boolean exclude =false;
		String name;
		for(Gps st: tmp) {
			name=st.getName();
			exclude =false;
			for(String id : IdsExcludes.MY_IDS) {
				if(name.indexOf(id) > -1) { //should be if id contains any of My_IDs
					exclude=true;
					break;
				}
			}
			if(!exclude)
				GpsesExMe.add(st);
			
		}
		return GpsesExMe;
	}
	
	
	public List<String> getAllNames(){
		List<String> names = new ArrayList<String>();
		gpsRepository.findAllNames().forEach(names::add);	
		return names;
	}
	

	public Map<String, NameStat>  getAllNameGpsStats(){
		List<Gps> gpses = new ArrayList<Gps>();
		gpsRepository.findAll().forEach(gpses::add);
		Map<String, NameStat> nameStats = new HashMap<String,NameStat>();
		NameStat nameStat;
		for(Gps gps : gpses) {
			if(!nameStats.containsKey(gps.getName())) {
				nameStat =  new NameStat(gps.getName());
				nameStats.put(gps.getName(),nameStat);
			}else {
				nameStat=(NameStat)(nameStats.get(gps.getName()));
				nameStat.cnt++;
			}
		}
		return nameStats;
	}
	
	public void addGps(Gps gps) {
//		topics.add(topic);		
		gpsRepository.save(gps);
		Local.add(gps);
	}
	
//	public Gps getGps(String id) {
//		return Local.stream().filter(t -> new String(""+t.getId()).equalsIgnoreCase(id)).findFirst().get();
//	}

//	public Iterable<Gps> getGps(String id) {
	public List<Gps> getGps(String id) {	
		Iterable<Gps> gpses = gpsRepository.findByName(id);
		LOG.info(this.getClass().getSimpleName() +" - getGps - name: "+id);
		Collection<Gps> g = (Collection<Gps>)gpses;
		LOG.info(this.getClass().getSimpleName() +" - getGps - name: "+id+ " returned: "+g.size());
		return (ArrayList)g;
	}
	
	public Gps getGpsforUuidLatest(String name) {
		LOG.info(this.getClass().getSimpleName() +" - getGpsforUuidLatest - name: "+name);
		List<Gps> gpses = getGps(name);
		LOG.info(this.getClass().getSimpleName() +" - getGpsforUuidLatest - name: "+name+ " returned: "+gpses.size());
		if(gpses.size()>0)
			return gpses.get(gpses.size()-1);
		else
			return null;
	}
	
	public Iterable<Gps> getGpsforUuidSinceTime(String name, String time) {
		Iterable<Gps> gpses = gpsRepository.findByNameAndTimeSince(name, time);
		return gpses;
	}
	
	public Iterable<Gps> getGpsforUuidOfDate(String name, String date, String nextDay) {
		LOG.info(this.getClass().getSimpleName() +" - getGpsforUuidOfDate - name: "+name+" data begin: "+date+" date end: "+nextDay);
		Iterable<Gps> gpses = gpsRepository.findByNameOfDate(name, date, nextDay);
		Iterator ite = gpses.iterator();
		int cnt=0;
		while(ite.hasNext()) {
			cnt++;
			Gps gps=(Gps)ite.next();
			LOG.debug(this.getClass().getSimpleName() +" - gps: instime: "+gps.getInstime() + " data:\n "+gps.getData());
		}
		LOG.info(this.getClass().getSimpleName() +" - getGpsforUuidOfDate - name: "+name+ "/date/"+date + " returned: "+cnt);
		return gpses;
	}
	
	public void updateGps(Gps gps) {
		for(int i=0; i< Local.size(); i++) {
			Gps t = Local.get(i);
			if(t.getId() == gps.getId()) {
				Local.set(i, gps);
				gpsRepository.save(gps);
			}
		}		
	}

	public void deleteGps(String id) {
		System.out.print("Deleting id "+id);
		Local.removeIf(t -> (""+t.getId()).equalsIgnoreCase(id));	
		gpsRepository.delete(Long.valueOf(id));
	}

	private void deleteBunch(Iterable<Gps> gpses) {
		for(Gps gps : gpses) {
			Local.removeIf(t -> (t.getId() == gps.getId()));	
			gpsRepository.delete(Long.valueOf(gps.getId()));
		}
	}
	
	public void deleteGpsSinceTime(String time) {
		System.out.print("deleteGpsSinceTime:  "+time);
		Iterable<Gps> gpses = gpsRepository.findByTimeSince(time);
		deleteBunch(gpses);
	}

	public void deleteGpsOfName(String name) {
		System.out.print("deleteGpsOfName:  "+name);
		Iterable<Gps> gpses = gpsRepository.findByName(name);
		deleteBunch(gpses);
	}
	
	
}
