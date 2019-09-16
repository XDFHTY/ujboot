package com.cj.sfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.cj")
public class SFileApplication {

	public static void main(String[] args) {
		SpringApplication.run(SFileApplication.class, args);
	}


}

