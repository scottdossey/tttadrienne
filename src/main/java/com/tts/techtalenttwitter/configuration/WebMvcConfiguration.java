package com.tts.techtalenttwitter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//If you mark a class with a configuration annotation...
//one of the things it means is that you can add instructions
//on how to make injectable (autowired) objects to Spring Boot.
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	//The way we tell Spring Boot how to make an object that is to be Autowired
	//is we use the @Bean annotation...
	
	//A Bean in this context is an object whose lifecycle (creation and destruction) will
	//not been done by the user calling constructors, but will be managed by springboot.
	
	//THe @Bean annotation tells springboot how to create objects when they are needed.
	//Allowing springboot to manage your object via beans rather than instantiating
	//them yourself every time you need one is part of a Spring Boot concept called
	//Inversion of Control. 
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder =
			new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;		
	}	
}
