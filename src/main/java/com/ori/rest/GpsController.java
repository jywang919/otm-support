package com.ori.rest;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class GpsController {
	private static final Logger LOG = LoggerFactory.getLogger(GpsController.class);
	private static final boolean isLogDebugEnabled = LOG.isDebugEnabled();
	@Autowired  //mark this DI
	private GpsService gpsService;
	
	@RequestMapping("/gpses")
	public List<Gps> gpses() {
		return gpsService.getAllGps();						
	}
	
	@RequestMapping("/gpses-")
	public List<Gps> gpsesExMine() {
		return gpsService.getAllGpsExcludeMine();						
	}
	
	@RequestMapping("/gpsesnames")
	public List<String> names() {
		return gpsService.getAllNames();						
	}

	@RequestMapping("/gpsnamestats")
	public Map<String, NameStat>  namestats() {
		return gpsService.getAllNameGpsStats();						
	}
	
	@RequestMapping("/gpses/{any}") //get gpses by uuid
	public Iterable<Gps> getGps(@PathVariable("any") String id) {
		return gpsService.getGps(id);
	}
	
	@RequestMapping("/gpses/{any}/timesince/{timesince}") //get gpses by uuid
	public Iterable<Gps> getGpsforUuidSinceTime(@PathVariable("any") String id,@PathVariable("timesince") String time) {
		LOG.info(this.getClass().getSimpleName()+" GET using /gpses/"+id+"'/timesince/"+time);
		return gpsService.getGpsforUuidSinceTime(id, time);
	}
	
	@RequestMapping("/gpses/{any}/latest") //get gpses by uuid
	public Gps getGpsforUuidOfDate(@PathVariable("any") String id) {		
		LOG.info(this.getClass().getSimpleName()+" GET using /gpses/"+id+"/latest");
		return gpsService.getGpsforUuidLatest(id);
	}
	
	Calendar cal = Calendar.getInstance();
	@RequestMapping("/gpses/{any}/date/{date}") //get gpses by uuid
	public Iterable<Gps> getGpsforUuidOfDate(@PathVariable("any") String id,@PathVariable("date") String date) {
		//date in 2018-02-20 format
		LOG.info(this.getClass().getSimpleName()+" GET using /gpses/"+id+"/date/"+date);
		String origDate=date;		
		String nextDay = getNextDayStr(date);
		return gpsService.getGpsforUuidOfDate(id, origDate,nextDay);
	}
	
	private String caltoString(Calendar cal) {
		StringBuffer res= new StringBuffer("");
		res.append(cal.get(Calendar.YEAR));
		int tmp = cal.get(Calendar.MONTH);
		String str = ""+tmp;
		if(tmp< 10) str= "0"+str;
		res.append('-').append(str);
		tmp = cal.get(Calendar.DAY_OF_MONTH);
		str = ""+tmp;
		if(tmp< 10) str= "0"+str;
		res.append('-').append(str);
		
		return res.toString();
	}
	private String getNextDayStr(String inStr) {
		String inDate = inStr;
		String firstEight=inDate.substring(0,7);
//		LOG.debug(this.getClass().getSimpleName()+"-getNextDayStr - 1st 8:" +firstEight);
		String yr =inDate.substring(0,4);
		cal.set(Calendar.YEAR, Integer.parseInt(yr));
//		LOG.debug(this.getClass().getSimpleName()+"-getNextDayStr - year:" +yr);
		String month =inDate.substring(5,7);
//		LOG.debug(this.getClass().getSimpleName()+"-getNextDayStr - month:" +month);
//		cal.set(Calendar.MONTH, Integer.parseInt(inDate.substring(6,7)));
		cal.set(Calendar.MONTH, Integer.parseInt(month));
		String lastTwo=inDate.substring(8);
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(lastTwo));
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
//		System.out.println(this.getClass().getSimpleName()+" cal this day:"+cal.toString());
		
		LOG.debug(this.getClass().getSimpleName()+"-getNextDayStr - input day:" +caltoString(cal));
		
		long time = cal.getTimeInMillis();
		time += 86400000;
		cal.setTimeInMillis(time);
//		System.out.println(this.getClass().getSimpleName()+" cal next day:"+cal.toString());
//		System.out.println(this.getClass().getSimpleName()+" next day:" +cal.get(Calendar.YEAR)+'-'+cal.get(Calendar.MONTH)+'-'+cal.get(Calendar.DAY_OF_MONTH));
		LOG.debug(this.getClass().getSimpleName()+"-getNextDayStr - next day:" +caltoString(cal));
		
		return caltoString(cal);
	} 
	
	@RequestMapping(method=RequestMethod.POST, value="/gpses")
	public void addGps(@RequestBody Object obj) {
		Object ob2 = obj;
		LOG.info(this.getClass().getSimpleName()+" POST using /gpses");
//		System.out.print(((Map)(obj[0])).toString());
		gpsService.addGps(mapToEntity((Map)(obj), null));
	}
	
	///////////////////////////////////////////////////////////////////
	//////////////////////////  POST /gpses/rcaa_xxx  ////////////////////
	/////////////////////////////////////////////////////////////////
	Long tsLong;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
    Date parsedTimeStamp;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method=RequestMethod.POST, value="/gpses/{name}")
	public void addGpsForName(@RequestBody Object obj,@PathVariable String name) throws ParseException {
		LOG.info(this.getClass().getSimpleName()+" POSTed using /gpses/"+name);
		Object loc = ((Map<String, Object>)obj).get("location");		
		Object ts0 = ((Map<String, Object>)loc).get("timestamp");
		String tsStr = ""+ts0;
		Date d = new Date();
		try {
			if(tsStr.indexOf('T') > -1) {
				tsStr= tsStr.replace('T', ' ');
				tsStr= tsStr.replace("Z", "");
			     parsedTimeStamp = dateFormat.parse(tsStr);
			     d=parsedTimeStamp;
			}	else {
				tsLong=Long.parseLong(tsStr);
				d.setTime(tsLong);
				LOG.debug(this.getClass().getSimpleName()+" timestamp: "+tsLong);
			}
		}catch (Exception e) {
			LOG.error(this.getClass().getSimpleName()+" errar parsing: "+tsStr);
		}
		LOG.info(this.getClass().getSimpleName()+" timestamp in date format - d: "+d);
		
		//for android data of , the incoming data has obj(0) key = location value = ...
		// and obj(1) key = device, and 
//		System.out.print(((Map)(obj[0])).toString());
		//for iOS
//		 location:  {"coords":{"speed":-1,"longitude":-78.79355169840042,"latitude":35.80847241453434,"accuracy":65,"heading":-1,"altitude":137.64227294921875,"altitudeAccuracy":10},"is_heartbeat":false,"sample":true,"is_moving":false,"odometer":0,"uuid":"AFC21906-430D-4A50-A73D-F16410B1DA9A","activity":{"type":"unknown","confidence":100},"battery":{"level":1,"is_charging":true}
//		 ,"timestamp":"2018-02-21T00:18:42.127Z"}
		gpsService.addGps(mapToEntity((Map)(obj),name));
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/gpses/{id}")
	public void updateGps(@RequestBody Map gps, @PathVariable String id) {
		LOG.info(this.getClass().getSimpleName()+" PUT using /gpses/"+id);
		gpsService.updateGps(mapToEntity(gps, null));
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/gpses/{id}")	
	public void deleteGps(@PathVariable String id) {
		LOG.info(this.getClass().getSimpleName()+" DELETE using /gpses/"+id);
		gpsService.deleteGps(id);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/gpses/since/{time}")
	public void deleteGpsSinceTime(@PathVariable String time) {		
		LOG.info(this.getClass().getSimpleName()+" DELETE using /gpses/since"+time);
		gpsService.deleteGpsSinceTime(time);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/gpses/name/{name}")
	public void deleteGpsOfName(@PathVariable String name) {	
		LOG.info(this.getClass().getSimpleName()+" DELETE using /gpses/name/"+name);
		gpsService.deleteGpsOfName(name);
	}
	
	Date date;
	Instant instant;
	Map.Entry<String,Object> map;
	final char bs='\\';
	final String dq="\"";
	final String bsdq = bs+dq;
	final char col = ':';
	Timestamp ts;
	String valueStr="";
	String timeZone = Calendar.getInstance().getTimeZone().getID();
	long utcTs;
	private Gps mapToEntity(Map obj,String inName) {
		Long id = (Long)(obj.get("id"));
		String name = inName;
		if(null == inName)
		 name = (String)(obj.get("name"));
		String instime = (String)(obj.get("instime"));	
		if(null == instime) {
			instant = Instant.now(); //this time is local time
	        date = Date.from(instant);	        
			utcTs = date.getTime() - TimeZone.getTimeZone(timeZone).getOffset(date.getTime());
			//Date local = new Date(date.getTime() + TimeZone.getTimeZone(timeZone).getOffset(date.getTime()));			
	        date.setTime(utcTs); //now it is UTC time
	        ts=new Timestamp(date.getTime());
	        if(isLogDebugEnabled)
	        	LOG.debug(""+ts);
	        instime=ts.toString();
		}
		String data = (String)(obj.get("data"));

		if(null == data) {
			data ='{'+entityToString(obj) + '}';
		}
		LOG.info("data : " +data);
		if(null == id)
			return new Gps(name,instime,data);	
		else
			return new Gps(id,name,instime,data);	
	}
	

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
	
	public void checkTS(long ts) {
		instant = Instant.now(); //this time is local time
        date = Date.from(instant);	
        date.setTime(ts);
        LOG.info("data : " +date); //how did android generated a 	Wed Feb 21 02:11:40 EST 2018 at 2/20/2018 16:11:40?	a 10 hour difference?
	}
//	public static void main(String[] args) {
//		GpsController ins = new GpsController();
////		ins.getNextDayStr("2018-02-20");
//		ins.checkTS(Long.parseLong("1519161100682")); //javascrpt  new Date().getTime(),
//		ins.checkTS(Long.parseLong("1519197100000")); //android  location.time,
//		
//	}
	
	
    //client post  http://localhost:8080/gpses with 
    // Header Content-Type: application/json
	
    //body (raw) as 
//    {
//    	"location":
//		{
//		    	"name": "Jeff Wang"
//		    	,"instime":"2018"
//		    	,"data": "JeffWang is good time!"
//		    	,"timestamp":"2018-05-09 16:10:32.333T"
//		}
//    }
    
	//or
	
//	http://localhost:8080/gpses/Ling Zhou with body as
//	{
//		"instime":"2021"
//		,"data": "JeffWang 2021 is good stat!"
//	}
	
	//client DELETE
//	http://localhost:8080/gpses/4
//	http://localhost:8080/gpses/since/2018
//	http://localhost:8080/gpses/name/Jeff Wang
			
    //client get
//	http://localhost:8080/gpses
//	http://localhost:8080/gpses/search/findByNameAndTimeSince?name=Jeff Wang&time=2018
//    http://localhost:8080/gpses/search/findByName?name=Jeff Wang
//    http://localhost:8080/gpses/search/findByNameAndTimeBefore?name=Jeff Wang&instime=2019
	
}
