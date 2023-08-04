package com.kt.edu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import com.kt.edu.domain.faq.FaqVO;



@Mapper
@Repository
public interface FaqMapper {

	public List<JSONObject> faqList(FaqVO faqVO) throws Exception;
	
	public JSONObject detailFaq(FaqVO faqVO) throws Exception;
	
	public int insertFaq(FaqVO faqVO) throws Exception;
	
	public int updateFaq(FaqVO faqVO) throws Exception;
	
	public int deleteFaq(FaqVO faqVO) throws Exception;

//	public JSONObject faqListCnt(FaqVO faqVO) throws Exception;
}
