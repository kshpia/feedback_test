package com.kt.edu.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private frontApiInterceptor interceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(interceptor).addPathPatterns("/**frontApi/**");
	}
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") //모든 요청에 대해서
//                .allowedOrigins("http://localhost:3000"); //허용할 오리진들
        registry.addMapping("/**")
	        .allowedOrigins("*")
	        .allowedMethods("*")
	        .allowCredentials(false)
	        .maxAge(3600);
    }

}