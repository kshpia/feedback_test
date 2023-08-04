package com.kt.edu.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.edu.common.Criteria;
import com.kt.edu.domain.faq.FaqVO;
import com.kt.edu.service.FaqService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/faq")
public class FaqController {

	@Autowired
	FaqService faqService;
	
	
	@RequestMapping("/faqList")
	public JSONObject faqList(@RequestBody FaqVO faqVO, Criteria criteria )  {
		JSONObject resultObj = new JSONObject();
		
		try {
			JSONObject data = new JSONObject();
			
			if(ObjectUtils.isEmpty(faqVO.getUserType())) {
				resultObj.put("errorMessage", "userType 값은 필수입니다.");
				resultObj.put("status", "01");
			}else {
				data.put("faqs", faqService.faqList(faqVO));
				resultObj.put("code", "200");
				data.put("result", "Success");
				resultObj.put("data", data);
				
			}
			
		}catch (Exception e) {
			log.error("ERROR :: faqList ::" , e.toString());
			resultObj.put("errorMessage", "오류가 발생했습니다.");
			resultObj.put("status", "01");
		}
		
		return resultObj;

	}
	
	@RequestMapping("/detailFaq")
	public JSONObject detailFaq(@RequestBody FaqVO faqVO)  {
		JSONObject resultObj = new JSONObject();
		
		try {
			JSONObject data = new JSONObject();
			log.info("faqVO.getId() : " + faqVO.getId());
			
			if(ObjectUtils.isEmpty(faqVO.getId())) {
				resultObj.put("errorMessage", "id 값은 필수입니다.");
				resultObj.put("status", "01");
			}else {
				resultObj.put("code", "200");
				data.put("faq", faqService.detailFaq(faqVO));
				data.put("result", "Success");
				resultObj.put("data", data);
				
			}
			
		}catch (Exception e) {
			log.error("ERROR :: detailFaq ::" , e.toString());
			resultObj.put("errorMessage", "오류가 발생했습니다.");
			resultObj.put("status", "01");
		}
		
		return resultObj;

	}
	
	
	//등록
	@RequestMapping("/insertFaq")
	public JSONObject insertFaq(@RequestBody FaqVO faqVO)  {
		JSONObject resultObj = new JSONObject();
		
		try {
			
			resultObj = faqService.insertFaq(faqVO);
			
		}catch (Exception e) {
			log.error("ERROR :: insertFaq ::" , e.toString());
			resultObj.put("errorMessage", "오류가 발생했습니다.");
			resultObj.put("status", "01");
		}
		
		return resultObj;

	}
	
	
	//수정
	@RequestMapping("/updateFaq")
	public JSONObject updateFaq(@RequestBody FaqVO faqVO)  {
		JSONObject resultObj = new JSONObject();
		
		try {
			
		
			if(ObjectUtils.isEmpty(faqVO.getId())) {
				
				resultObj.put("errorMessage", "id 값은 필수입니다.");
				resultObj.put("status", "01");
				
			}else {
				
				resultObj = faqService.updateFaq(faqVO);
			}
			
		}catch (Exception e) {
			log.error("ERROR :: updateFaq ::" , e.toString());
			resultObj.put("errorMessage", "오류가 발생했습니다.");
			resultObj.put("status", "01");
		}
		
		return resultObj;	

	}
	
	
	//삭제
	@RequestMapping("/deleteFaq")
	public JSONObject faqList(@RequestBody FaqVO faqVO)  {
		JSONObject resultObj = new JSONObject();
		
		try {
			
			if(ObjectUtils.isEmpty(faqVO.getId())) {
				
				resultObj.put("errorMessage", "id 값은 필수입니다.");
				resultObj.put("status", "01");
				
			}else {
				
				resultObj = faqService.deleteFaq(faqVO);
			}
			
		}catch (Exception e) {
			log.error("ERROR :: deleteFaq ::" , e.toString());
			resultObj.put("errorMessage", "오류가 발생했습니다.");
			resultObj.put("status", "01");
		}
		
		return resultObj;	

	}
	

	
}