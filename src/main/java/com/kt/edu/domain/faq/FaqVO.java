package com.kt.edu.domain.faq;

import com.kt.edu.domain.BaseVO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FaqVO extends BaseVO{
	
	private int id;
	private String title;
	private String category;
	private String action;
	private String service;
	private String userType;

}
