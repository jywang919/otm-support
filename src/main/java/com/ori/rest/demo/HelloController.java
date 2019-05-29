package com.ori.rest.demo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {
    
    @RequestMapping("/r/hello")
    public String index() {
        return "Greetings from Spring Boot - gaeGradle2_DeployTomcat HelloController!";
    }
    
}
