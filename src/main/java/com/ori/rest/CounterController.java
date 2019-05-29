package com.ori.rest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CounterController {
	@RequestMapping("/counter")
	public String welcome() {
//		Application.counter ++;		
		return "Number of hits: " +SpringTomcatAndJettyServletInitializer.counter;
	}
}