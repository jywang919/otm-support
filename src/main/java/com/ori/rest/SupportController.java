package com.ori.rest;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//https://howtodoinjava.com/spring-boot/spring-boot-jsp-view-example/
import org.springframework.web.bind.annotation.RestController;

@Controller
public class SupportController {

	// inject via application.properties
	@Value("${welcome.message:test}")
	private String message = "Hello World";

	@GetMapping ("/support")
	public String welcome(Map<String, Object> model) {
		SpringTomcatAndJettyServletInitializer.counter ++;
		System.out.println("SupportController - support");
		model.put("message", this.message);
		return "support";
	}
	
	//needed in tomcat 
	@RequestMapping("/")		
	public String home(Map<String, Object> model) {
		System.out.println("SupportController - index.jsp");
		model.put("message", this.message);
		return "index.html";
	}
}