package com.kt.edu.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ErrorCodeException extends RuntimeException {
	
	Logger logger = LoggerFactory.getLogger(ErrorCodeException.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String code;
	String msg;
	
	public ErrorCodeException() {
		logger.error("ErrorCodeException contructor");
	}
	
	public ErrorCodeException(String code) {
		this.code = code;
		logger.error("ErrorCodeException construnctor code");
	}
	
	public ErrorCodeException(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getMessage() {
		return msg;
	}
	
}
