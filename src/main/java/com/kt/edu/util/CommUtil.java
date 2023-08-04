package com.kt.edu.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommUtil {
	
	/**
	 * 날짜문자를 날짜형식으로 변환
	 * @param sdate
	 * @param itype
	 * @return
	 */
	public static String getStringTodate(String sdate, int itype) {
		String result = "";
		Date nowDate = new Date();
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		SimpleDateFormat simpleDateFormat = null;
		if (itype == 0) {
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		} else if (itype == 1) {
			simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		} else if (itype == 2) {
			simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
		} else {
			simpleDateFormat = new SimpleDateFormat("yyyy년MM월dd일", Locale.KOREA);
		}
		
		try {
			Date formatDate = dtFormat.parse(sdate);
			result = simpleDateFormat.format(formatDate);
		} catch(Exception e) {
			result = simpleDateFormat.format(nowDate);
			log.info("getStringTodate()=>" + "Exception:: [" + e + "]");
		}
		
		return result;
	}
	
	/**
	 * 시간문자를 시간형식을 변환
	 * @param stime
	 * @return
	 */
	public static String getStringTotime(String stime) {
		DateTimeFormatter inFormat = DateTimeFormatter.ofPattern("HHmm");
	    LocalTime time = LocalTime.parse(stime, inFormat);
        DateTimeFormatter outFormat = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(outFormat);
	}
	
	/**
	 * 날짜형식 문자를 요일 변환
	 * @param sday
	 * @param stype : 1-토, 2-토, 3-토요일
	 * @return
	 */
	public static String getStringToWeek(String sday, int itype) {
		String result = "";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.US);
    	LocalDate date = LocalDate.parse(sday, formatter);
    	DayOfWeek dayOfWeek = date.getDayOfWeek();
    	if (itype == 1) {
    		result = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN);
    	} else if (itype == 1) {
    		result = dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREAN);
    	} else {
    		result = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
    	}
    	return result;
	}
	
	/**
	 * 한글로만 이루어진 문자열인지 검사
	 * @param mberNm
	 * @return
	 */
	public static boolean isOnlyHangul(String mberNm) {
		if (mberNm == null) {
			return false;
		}
		return mberNm.matches("^[ㄱ-하-ㅣ가-힣]+$");
	}
	
	/**
	 * 랜덤하게 0~z까지 7자리
	 * @return
	 */
	public static String getRandomize() {
		int leftLimit = 48;   // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 7;
		Random random = new Random();

		String generatedString = random.ints(leftLimit,rightLimit + 1)
		  .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
		  .limit(targetStringLength)
		  .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		  .toString();
		return generatedString;
	}
	
	/**
	 * String To JSON
	 * @param str
	 * @return
	 */
	public static JSONObject getStrToJson(String str) {
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(str);
	        return jsonObject;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Base64
	 * @param jsonStr
	 * @return
	 */
	public static String getBase64Enc(String jsonStr) {
		return Base64.encodeBase64String(jsonStr.getBytes());
	}
	
	/**
	 * 날짜포맷
	 * @param itag
	 * @return
	 */
	public static String getTimeStamp(int itag) {
		String result = "";
		if (itag == 0) {
			SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
			result = date.format(new Date());
		} else if (itag == 1) {
			SimpleDateFormat dYyyymmdd = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
			result = dYyyymmdd.format(new Date());
		} else if (itag == 2) {
			SimpleDateFormat dYyyymmdd = new SimpleDateFormat("yyyyMMddHHmm", Locale.KOREA);
			result = dYyyymmdd.format(new Date());
		} else if (itag == 3) {
			SimpleDateFormat dYyyymmdd = new SimpleDateFormat("yyyyMM", Locale.KOREA);
			result = dYyyymmdd.format(new Date());
		} else if (itag == 4) {
			result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.KOREA).format(System.currentTimeMillis());
		}
		
		return result;
	}
	
	/**
	 * Message
	 * @param code
	 * @param message
	 * @return
	 */
	public static JSONObject messageJson(String code, String message) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("CODE", code);
		jsonObj.put("MESSAGE", message);
		return jsonObj;
	}
	
	/**
	 * Message
	 * @param code
	 * @param message
	 * @return
	 */
	public static JSONObject messageJsonSe(String code, String message, String data) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("CODE", code);
		jsonObj.put("MESSAGE", message);
		jsonObj.put("DATA", data);
		return jsonObj;
	}
	
	/**
	 * String to JSONObject
	 * @param jsonstr
	 * @return
	 */
	public static JSONObject stringToJSonObj(String jsonstr) {
		JSONObject jsonObj = new JSONObject();
		try {
			JSONParser jsonParser = new JSONParser();
	        Object obj = jsonParser.parse(jsonstr);
	        jsonObj = (JSONObject)obj;
			return jsonObj;
		} catch (Exception e) {
			return jsonObj;
		}
	}
	
//	/**
//	 * 
//	 * @param bufferedWriter
//	 * @param bufferedReader
//	 * @param request
//	 * @return
//	 * @throws IOException
//	 */
//	public static String toString(ReusedVO reusedIO, String request) throws Exception {
//		StringBuffer stringBuffer = new StringBuffer();
//
//		char ch;
//
//		reusedIO.getBufferedWriter().write(request);
//		reusedIO.getBufferedWriter().flush();
//
//		while ((ch = (char) reusedIO.getBufferedReader().read()) > 0) {
//			stringBuffer.append(ch);
//		}
//
//		return stringBuffer.toString();
//	}

	/**
	 * 
	 * @param bufferedWriter
	 * @param bufferedReader
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String toString(BufferedWriter bufferedWriter, BufferedReader bufferedReader, String request) throws Exception {
		StringBuffer stringBuffer = new StringBuffer();

		char ch;

		bufferedWriter.write(request);
		bufferedWriter.flush();

		while ((ch = (char) bufferedReader.read()) > 0) {
			stringBuffer.append(ch);
		}

		return stringBuffer.toString();
	}
	
	/**
	 * 
	 * @param string
	 * @param tagname
	 * @param replacement
	 * @return
	 */
	public static String replaceTag(String string, String tagname, String replacement) throws Exception {
		String result = string;

		try {
			String startTag = "<" + tagname + ">";
			String endTag = "</" + tagname + ">";

			int startPosition = result.indexOf(startTag);
			int endPosition = result.indexOf(endTag);

			String target = result.substring(startPosition, endPosition + endTag.length());

			result = result.replace(target, replacement);
		} catch (Exception e) {
			log.error("replaceTag::" + e.getMessage());
		}

		return result;
	}
	
	/**
	 * Outbound Port 매핑
	 * @param xroshotServerIP
	 * @return
	 */
	public static Map<String, String> getOutboundPort(String xroshotServerIP){
		String[] array = xroshotServerIP.split(",");
		Map<String, String> map = new HashMap<>();
		
		for(int i=0;i<array.length;i++) {
			String[] subArray = array[i].split(":");
			map.put(subArray[0], subArray[1]);
		}
		return map;
	}
	
	/**
     * 8자리 난수 메시지ID생성
     * @param ilen : 난수의 길이
     */
	public static String genRanmMsgId(int ilen) {
        Random randm = new Random();
        String result = "";

		StringBuffer sb = new StringBuffer();
		sb.append(result);
		
        for(int i=0; i<ilen; i++) {
            String randomi = Integer.toString(randm.nextInt(10)); //0~9 난수발생
    		sb.append(randomi);
        }
        
        result= sb.toString();
        return result;
    }
	
	/**
	 * 연락처 마스킹 처리
	 * @param phoneNum
	 * @return
	 */
	public static String maskingPhoneNum(String phoneNum) {
		
		if(BaseUtil.isEmpty(phoneNum)) {
			return "FAIL";
		}
		
		if( !(phoneNum.length() == 10 || phoneNum.length() == 11)) {
			return "FAIL";
		}
		
		if(phoneNum.length() == 10) {
			return phoneNum.substring(0,3) + "-" + phoneNum.substring(3,5) + "*-*" + phoneNum.substring(8);   
		}else {
			return phoneNum.substring(0,3) + "-" + phoneNum.substring(3,5) + "**-*" + phoneNum.substring(8);
		}
	}

	/**
	 * 연락처 마스킹 해제
	 * @param phoneNum
	 * @return
	 */
	public static String nonMaskingPhoneNum(String phoneNum) {
		
		if(BaseUtil.isEmpty(phoneNum)) {
			return "FAIL";
		}
		
		if( !(phoneNum.length() == 10 || phoneNum.length() == 11)) {
			return "FAIL";
		}
		
		if(phoneNum.length() == 10) {
			return phoneNum.substring(0,3) + "-" + phoneNum.substring(3,7) + "-" + phoneNum.substring(7);   
		}else {
			return phoneNum.substring(0,3) + "-" + phoneNum.substring(3,7) + "-" + phoneNum.substring(7);
		}
	}

	/**
	 * 이름 마스킹 처리
	 * @param phoneNum
	 * @return
	 */
	public static String maskingMemName(String memName) {
		
		if(BaseUtil.isEmpty(memName)) {
			return "FAIL";
		}
		
		if(memName.length() < 2) {
			return "FAIL";
		}
		
		return memName.substring(0, memName.length() - 1) + "*";
	}

}
