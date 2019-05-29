package com.ori.rest;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

//This class combined Application.java class with adding following anni
@SpringBootApplication
public class SpringTomcatAndJettyServletInitializer extends SpringBootServletInitializer {
	
//	SpringBootServletInitializer subclass and override its configure() method. This makes use of Spring Framework’s Servlet 3.0 support and allows you to configure your application when it’s launched by the servlet container.
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringTomcatAndJettyServletInitializer.class);		
	}

	public static int counter = 0;
	
    public static void main(String[] args) {
        SpringApplication.run(SpringTomcatAndJettyServletInitializer.class, args);
    }
    
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

    
}
