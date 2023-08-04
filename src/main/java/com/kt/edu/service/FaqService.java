package com.kt.edu.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.kt.edu.domain.faq.FaqVO;


public interface FaqService {

	List<JSONObject> faqList(FaqVO faqVO) throws Exception;
	
	JSONObject detailFaq(FaqVO faqVO) throws Exception;
	
	JSONObject insertFaq(FaqVO faqVO) throws Exception;
	
	JSONObject updateFaq(FaqVO faqVO) throws Exception;
	
	JSONObject deleteFaq(FaqVO faqVO) throws Exception;

//	JSONObject faqListCnt(FaqVO faqVO) throws Exception;
	
}
