package com.ori.rest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TopicController {
	
	@Autowired  //mark this DI
	private TopicService topicService;
	
	@RequestMapping("/topics")
	public List<Topic> topic() {
		return topicService.getAllTopics();						
	}

	@RequestMapping("/topics/{any}")
	public Topic getTopic(@PathVariable("any") String id) {
		return topicService.getTopic(id);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/topics")
	public void addTopic(@RequestBody Object obj) {
		Object ob2 = obj;
		System.out.println("great:");
//		System.out.print(((Map)(obj[0])).toString());
		topicService.addTopic(mapToTopic((Map)(obj)));
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/topics/{id}")
	public void updateTopic(@RequestBody Map topic, @PathVariable String id) {
		System.out.print(mapToTopic(topic).toString());
		topicService.updateTopic(mapToTopic(topic));
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/topics/{id}")
	public void deleteTopic(@PathVariable String id) {
		topicService.deleteTopic(id);
	}
	
	@RequestMapping(value="/fileUpload", method=RequestMethod.POST)
	public @ResponseBody  Map<String, Object>  fileUpload(@RequestParam("file") MultipartFile file){
	    String fileName = null;
	    Map<String,Object> result = new HashMap<String, Object>();
	    if (!file.isEmpty()) {
	        try {
	            fileName = file.getOriginalFilename();
//	            byte[] bytes = file.getBytes();
//	            BufferedOutputStream buffStream = 
//	                    new BufferedOutputStream(new FileOutputStream(new File("C:/jw_tmp/" + fileName)));
//	            buffStream.write(bytes);
//	            buffStream.close();
                FileCopyUtils.copy(file.getBytes(), new FileOutputStream(
                		"C:/jw_tmp"
//                		servletContext.getRealPath("/resources/assets/images/hotelImages")
                		+ "/" + fileName));
//	            return "You have successfully uploaded " + fileName;
	            result.put("post update", "successful");
	            return result;
	        } catch (Exception e) {
	        	result.put("You failed to upload", e.getMessage());
	            return result;
	        }
	    } else {
	    	result.put("You failed to upload", "File is empty");
            return result;
	    }
	}
	
	private Topic mapToTopic(Map obj) {
		String id = (String)(obj.get("id"));
		String name = (String)(obj.get("name"));
		String description = (String)(obj.get("description"));				
		return new Topic(id,name,description);		
	}
}
