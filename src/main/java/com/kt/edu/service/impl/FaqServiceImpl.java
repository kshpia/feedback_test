package com.kt.edu.service.impl;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.edu.dao.FaqDao;
import com.kt.edu.domain.faq.FaqVO;
import com.kt.edu.service.FaqService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class FaqServiceImpl implements FaqService {

	@Autowired
	FaqDao faqDao;

	/**
	 * 공지사항 전체 목록
	 */
	@Override
	public List<JSONObject> faqList(FaqVO faqVO) throws Exception{
		List<JSONObject> result = faqDao.faqList(faqVO);

		return result;
	}
	
	@Override
	public JSONObject detailFaq(FaqVO faqVO) throws Exception{
		
		return faqDao.detailFaq(faqVO);
	}

	@Override
	public JSONObject insertFaq(FaqVO faqVO) throws Exception {
		JSONObject result = new JSONObject();
		
		faqDao.insertFaq(faqVO);
		result.put("status", "00");
		
		return result;
	}

	@Override
	public JSONObject updateFaq(FaqVO faqVO) throws Exception {
		JSONObject result = new JSONObject();
		
		faqDao.updateFaq(faqVO);
		result.put("status", "00");
		
		return result;
	}

	@Override
	public JSONObject deleteFaq(FaqVO faqVO) throws Exception {
		JSONObject result = new JSONObject();
		
		faqDao.deleteFaq(faqVO);
		result.put("status", "00");
		
		return result;
	}

//	@Override
//	public JSONObject faqListCnt(FaqVO faqVO) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}

}


