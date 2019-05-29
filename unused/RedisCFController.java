package com.ori.rest.demo;

import org.springframework.web.bind.annotation.RestController;

//import com.fasterxml.jackson.databind.JsonNode;

import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonRootNode;
import argo.saj.InvalidSyntaxException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;

//redis on cf -  https://docs.run.pivotal.io/marketplace/services/rediscloud.html#java
import static argo.jdom.JsonNodeFactories.*;

@RestController
public class RedisController {
	@Autowired
//	private StringRedisTemplate redisTemplate;    
	@SuppressWarnings("unchecked")
	@RequestMapping("/r/redis")
    public String redis() {		
		int counter = 0;
		String key = "redis.counter";
		JedisPool pool = null;
		String value ="";
		try {
		    String vcap_services = System.getenv("VCAP_SERVICES");
//log from cf 
//	    	2018-06-08T16:11:07.721-04:00 [RTR/2] [OUT] wangmobile.cfapps.io - [2018-06-08T20:11:07.623+0000]
//	    	"GET /r/redis HTTP/1.1" 404 0 306 "-" "Mozilla/5.0 (Windows NT 6.1; 
//	    	Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Safari/537.36" 
//	    	"10.10.66.212:23767" "10.10.149.20:61018" x_forwarded_for:"108.171.130.166, 10.10.66.212" 
//	    	x_forwarded_proto:"https" vcap_request_id:"14177adb-0852-4d26-5e75-aeffda8db6d2" 
//	    	response_time:0.097394426 app_id:"2486feda-646d-4963-8d9e-a771ab06a52b" 
//	    	app_index:"0" x_b3_traceid:"cc02fb64af2bd8c1" x_b3_spanid:"cc02fb64af2bd8c1" x_b3_parentspanid:"-"
	    	
		    if (vcap_services != null && vcap_services.length() > 0) {
		    	System.out.println("vcap_services: "+vcap_services);
		    		
		        // parsing rediscloud credentials
		        JsonRootNode root = new JdomParser().parse(vcap_services);
		        argo.jdom.JsonNode rediscloudNode = root.getNode("rediscloud");
		        JsonNode credentials = rediscloudNode.getNode(0).getNode("credentials");
		        if (null == pool) {
			        pool = new JedisPool(new JedisPoolConfig(),
	//		                credentials.getStringValue("hostname"),
			                credentials.getStringValue("redis-12422.c8.us-east-1-4.ec2.cloud.redislabs.com"),		                
	//		               end point from cf redis-12422.c8.us-east-1-4.ec2.cloud.redislabs.com:12422
	//		                Integer.parseInt(credentials.getNumberValue("port")),
			                Integer.parseInt(credentials.getNumberValue("12422")),
			                Protocol.DEFAULT_TIMEOUT,
			                credentials.getStringValue("password"));
		        }
		        Jedis jedis = pool.getResource();
		        jedis.set("foo", "bar");
		        value = jedis.get("foo");
		        // return the instance to the pool when you're done
//		        pool.returnResource(jedis);
		        
		    }
		} catch (InvalidSyntaxException ex) {
		     System.out.println("vcap_services could not be parsed");
		}
		
//		ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
//		
//		if(!this.redisTemplate.hasKey(key)) {
//			ops.set(key, Integer.toString(counter));
//			System.out.println("key not found");
//		}else {
//			counter = Integer.parseInt(ops.get(key));
//			counter++;
//			ops.set(key, Integer.toString(counter));
//		}
//        return ops.get(key);
		return value;
    }
    
}
