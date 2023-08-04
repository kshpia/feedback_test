package com.kt.edu.dao;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.edu.domain.faq.FaqVO;
import com.kt.edu.mapper.FaqMapper;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class FaqDao {

	@Autowired
	FaqMapper faqMapper;

	public List<JSONObject> faqList(FaqVO faqVO) throws Exception{
		return faqMapper.faqList(faqVO);
	}
	
	public JSONObject detailFaq(FaqVO faqVO) throws Exception{
		return faqMapper.detailFaq(faqVO);
	}
	
	public int insertFaq(FaqVO faqVO) throws Exception{
		int rstCnt = faqMapper.insertFaq(faqVO);
		return rstCnt;
	}
	
	public int updateFaq(FaqVO faqVO) throws Exception{
		int rstCnt = faqMapper.updateFaq(faqVO);
		return rstCnt;
	}
	
	public int deleteFaq(FaqVO faqVO) throws Exception{
		int rstCnt = faqMapper.deleteFaq(faqVO);
		return rstCnt;
	}
	
	
	

//	public JSONObject faqListCnt(FaqVO faqVO) throws Exception{
//		return faqMapper.faqListCnt(faqVO);
//	}


}



