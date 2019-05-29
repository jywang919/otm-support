package com.ori.rest.demo;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;

//@RestController  //disable this controller as redis is not working 
public class RedisController {
	@Autowired
	private StringRedisTemplate redisTemplate;
    
	//**** logs on CF
	
	//logs using spring-boot-starter-data-redis
//	2018-06-08T16:28:49.997-04:00 [RTR/4] [OUT] wangmobile.cfapps.io - [2018-06-08T20:28:49.750+0000] "GET /r/redis HTTP/1.1"
//	404 0 306 "-" "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Safari/537.36" 
//	"10.10.2.27:54303" "10.10.149.19:61030" x_forwarded_for:"108.171.130.166, 10.10.2.27"
//	x_forwarded_proto:"https" vcap_request_id:"216102e3-a27a-490b-5516-0b63994750ab" 
//	response_time:0.247156357 app_id:"2486feda-646d-4963-8d9e-a771ab06a52b" 
//	app_index:"0" x_b3_traceid:"9bd121c5da9d23e5" x_b3_spanid:"9bd121c5da9d23e5" x_b3_parentspanid:"-"
	
	//compare log using redis.clients:jedis
//	2018-06-08T16:11:07.721-04:00 [RTR/2] [OUT] wangmobile.cfapps.io - [2018-06-08T20:11:07.623+0000] "GET /r/redis HTTP/1.1" 
//	404 0 306 "-" "Mozilla/5.0 (Windows NT 6.1;	Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Safari/537.36" 
//	"10.10.66.212:23767" "10.10.149.20:61018" x_forwarded_for:"108.171.130.166, 10.10.66.212" 
//	x_forwarded_proto:"https" vcap_request_id:"14177adb-0852-4d26-5e75-aeffda8db6d2" 
//	response_time:0.097394426 app_id:"2486feda-646d-4963-8d9e-a771ab06a52b" 
//	app_index:"0" x_b3_traceid:"cc02fb64af2bd8c1" x_b3_spanid:"cc02fb64af2bd8c1" x_b3_parentspanid:"-"
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/r/redis")
    public String redis() {
		int counter = 0;
		String key = "redis.counter";
		ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
		
		if(!this.redisTemplate.hasKey(key)) {
			ops.set(key, Integer.toString(counter));
			System.out.println("key not found");
		}else {
			counter = Integer.parseInt(ops.get(key));
			counter++;
			ops.set(key, Integer.toString(counter));
		}
        return ops.get(key);
    }
    
}
