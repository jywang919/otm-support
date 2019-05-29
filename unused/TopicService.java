package com.ori.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {
	
	 List<Topic> topics  = new ArrayList<Topic>( Arrays.asList(  //Arrays.asList() is not mutable, the new ArrayList is mutable
			new Topic("Spring","Rest","boot Rest"),
			new Topic("Uber","Car cat","uber desc"),
			new Topic("Jeff", "Jeff Wang","Myself")			
			)
			 )
			;

	@Autowired
	private TopicRepository topicRepository; 
	
	public List<Topic> getAllTopics(){
//		return topics;
		List<Topic> Local = new ArrayList<Topic>();
		topicRepository.findAll()
		.forEach(Local::add);
		return Local;
	}
	
	public void addTopic(Topic topic) {
//		topics.add(topic);		
		topicRepository.save(topic);
	}
	
	public Topic getTopic(String id) {
		return topics.stream().filter(t -> t.getId().equalsIgnoreCase(id)).findFirst().get();
	}


	public void updateTopic(Topic topic) {
		for(int i=0; i< topics.size(); i++) {
			Topic t = topics.get(i);
			if(t.getId().equalsIgnoreCase(topic.id)) {
				topics.set(i, topic);
			}
		}
		
	}

	public void deleteTopic(String id) {
		topics.removeIf(t -> t.getId().equalsIgnoreCase(id));		
	}

}
