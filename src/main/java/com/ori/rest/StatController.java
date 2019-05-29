package com.ori.rest;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatController {

	private static final Logger LOG = LoggerFactory.getLogger(StatController.class);
	private static final boolean isLogDebugEnabled = LOG.isDebugEnabled();
	
	@Autowired  //mark this DI
	private StatService statService;
	
	@RequestMapping("/stats")
	public List<Stat> gpses() {
		return statService.getAllStats();						
	}

	@RequestMapping("/stats-")
	public List<Stat> statsMinus() {
		return statService.getAllStatsExcludeMe();						
	}
	
	@RequestMapping("/statsnames")
	public List<String> names() {
		LOG.info(this.getClass().getSimpleName()+" GET using /statsnames");
		return statService.getAllNames();						
	}

	@RequestMapping("/statsnamestoday")
	public List<String> namestoday() {
		LOG.info(this.getClass().getSimpleName()+" GET using /namestoday");
		return statService.getAllNamesToday();						
	}
	
	@RequestMapping("/stats/{any}")
	public List<Stat>  getGps(@PathVariable("any") Integer hr) {
		return statService.getForHours(hr);
	}
	
	@RequestMapping("/statsOfId/{any}")
	public Stat getGps(@PathVariable("any") String id) {
		return statService.getForName(id);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/stats")
	public void addStat(@RequestBody Object obj) {
		Object ob2 = obj;
		System.out.println("addStat:");
//		System.out.print(((Map)(obj[0])).toString());
		statService.addStat(mapToEntity((Map)obj, null));
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="/stats/{name}")
	public void addGpsForName(@RequestBody Object obj,@PathVariable String name) {
		Object ob2 = obj;
//		System.out.println("great for name:");
//		System.out.print(((Map)(obj[0])).toString());
		statService.addStat(mapToEntity((Map)(obj),name));
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/stats/{id}")
	public void updateGps(@RequestBody Map gps, @PathVariable String id) {
//		System.out.print(mapToEntity(gps, null).toString());
		statService.updateStat(mapToEntity(gps, null));
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/stats/{id}")
	public void deleteGps(@PathVariable String id) {		
		statService.deleteStat(id);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/stats/since/{time}")
	public void deleteGpsSinceTime(@PathVariable String time) {		
		statService.deleteStatSinceTime(time);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/stats/name/{name}")
	public void deleteGpsOfName(@PathVariable String name) {		
		statService.deleteStatOfName(name);
	}
	
//	private Stat mapToEntity(Map obj) {		
//		Long id = (Long)(obj.get("id"));		
//		String name = (String)(obj.get("name"));
//		String instime = (String)(obj.get("instime"));				
//		String data = (String)(obj.get("data"));
//		if(null == id)
//			return new Stat(name,instime,data);	
//		else
//			return new Stat(id,name,instime,data);	
//	}

	Date date;
	Instant instant;
	Timestamp ts;
	String timeZone = Calendar.getInstance().getTimeZone().getID();
	long utcTs;
	private Stat mapToEntity(Map obj,String inName) {		
		Long id = (Long)(obj.get("id"));
		String name = inName;
		if(null == inName)
		 name = (String)(obj.get("name"));		
		String instime = (String)(obj.get("instime"));
		
		instant = Instant.now(); //this time is local time
        date = Date.from(instant);	        
		utcTs = date.getTime() - TimeZone.getTimeZone(timeZone).getOffset(date.getTime());
		//Date local = new Date(date.getTime() + TimeZone.getTimeZone(timeZone).getOffset(date.getTime()));			
        date.setTime(utcTs); //now it is UTC time
        ts=new Timestamp(date.getTime());
        if(isLogDebugEnabled)
        	LOG.debug(""+ts);
        instime=ts.toString();
        
		String data = (String)entityToString(obj);
		if(null == name) {
//			,"uuid":"1CC08919-DF1C-473C-9978-C355DBE60A5C" //for iOS devices
		}
		if(null == id)
			return new Stat(name,instime,data);	
		else
			return new Stat(id,name,instime,data);	
		
	}
	//obj is LinkedHashMap with 
	// [0] key = "rcaa_func" value="BGLocationsCtrl85:::startTracking" when click green star in Android
	// [2] key ="v" value ="7.7.3
	//inName= rcaa_stats_1_GCef364e3fab0e6143
	
	Map.Entry<String,Object> map;
	final char bs='\\';
	final String dq="\"";
	final String bsdq = bs+dq;
	final char col = ':';
	Object val;
	private String entityToString(Map ent) {
		String res ="";
		for (Object entry : ent.entrySet()) {
			map =  (Map.Entry<String, Object>) entry;
			if(res.length() > 1) res += ',';			
			val=map.getValue();
			if(val instanceof String) {
				res += bsdq+map.getKey()+bsdq+col+bsdq+(String)val+bsdq;
			}else if(val instanceof Long || val instanceof Double ||val instanceof Integer||val instanceof Boolean ){
				res +=  bsdq+map.getKey()+bsdq+col+val;
			}else {
				res +=  bsdq+map.getKey()+bsdq+col+'{'+entityToString((Map)map.getValue())+'}';
			}
			if(isLogDebugEnabled)
				LOG.debug("Key : " + map.getKey()+"\nvalue: "+res);
		}
		return res;
	}
	
    //client post  http://localhost:8080/stats with 
    // Header Content-Type: application/json
    //body as 
//    {
//    	"name": "Jeff Wang"
//    	,"instime":"2018"
//    	,"data": "JeffWang is good time!"
//    }
    
//    {
//    	"location":
//		{
//		    	"name": "Jeff Wang"
//		    	,"instime":"2018"
//		    	,"data": "JeffWang is good time!"
//		    	,"timestamp":"2018-05-09 16:10:32.333T"
//		}
//    }
    
	//client DELETE
//	http://localhost:8080/stats/4
//	http://localhost:8080/stats/since/2018
//	http://localhost:8080/stats/name/Jeff Wang
			
    //client get
//	http://localhost:8080/stats
//	http://localhost:8080/stats/search/findByNameAndTimeSince?name=Jeff Wang&time=2018
//    http://localhost:8080/stats/search/findByName?name=Jeff Wang
//    http://localhost:8080/stats/search/findByNameAndTimeBefore?name=Jeff Wang&instime=2019
	
}
