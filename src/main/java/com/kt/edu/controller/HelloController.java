package com.kt.edu.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HelloController {
	
	@RequestMapping("/")
	String home() {
		log.info("LOG INFO2");
		log.error("ERROR INFO2");
		return "Hello World!";
	}
	//public static void main(String[] args) {
	//	SpringApplication.run(HelloController.class, args);
	//}
}
