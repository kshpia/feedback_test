package com.kt.edu.common;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
//import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import jakarta.servlet.MultipartConfigElement;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class FileConfig {

	@Bean
	public StandardServletMultipartResolver multipartResolver(){
		StandardServletMultipartResolver standardMultipartResolver = new StandardServletMultipartResolver();
	    //CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		//standardMultipartResolver.setDefaultEncoding("UTF-8");
		//standardMultipartResolver.setMaxUploadSize(100 * 1024 * 1024);
		//standardMultipartResolver.setMaxUploadSizePerFile(20 * 1024 * 1024);
	    return standardMultipartResolver;
	}
	
	@Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxRequestSize(DataSize.ofBytes(100 * 1024 * 1024));
        factory.setMaxFileSize(DataSize.ofBytes(20 * 1024 * 1024));

        return factory.createMultipartConfig();
    }
}
