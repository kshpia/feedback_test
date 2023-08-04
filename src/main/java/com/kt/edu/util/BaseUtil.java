package com.kt.edu.util;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseUtil {
	
	protected Logger logger = LogManager.getLogger(this.getClass());
	
	private static String COMPARE_HTML_REGEX;
	public static List<String> paramList = null;
//	private static char[] charArr;

	static {
		paramList = new ArrayList();
		paramList.add("pageNo");
		paramList.add("searchType");
		paramList.add("searchText");
		paramList.add("startDate");
		paramList.add("endDate");
		paramList.add("searchCategorySrl");
//		charArr = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
//				's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	}

	public static String getParameter(HttpServletRequest request, String paramName) {
		return getRplcAll(request.getParameter(paramName) == null ? "" : request.getParameter(paramName));
	}

	public static String getParameter(HttpServletRequest request, String paramName, String setValue) {
		return getRplcAll(request.getParameter(paramName) == null ? setValue : request.getParameter(paramName));
	}

	public static String getParams(HttpServletRequest request) {
		return getParams(request, (String) null, (String) "");
	}

	public static String getParams(HttpServletRequest request, String ampChar) {
		return getParams(request, ampChar, "");
	}

	public static String getParams(HttpServletRequest request, String ampChar, String exclusiveParam) {
		Enumeration paramNames = request.getParameterNames();
		String sAmpChar = ampChar == null ? "&amp;" : ampChar;
		String sExclusiveParam = exclusiveParam == null ? "" : exclusiveParam;
		StringBuffer params = new StringBuffer();

		while (paramNames.hasMoreElements()) {
			try {
				String k = (String) paramNames.nextElement();
				String v = URLEncoder.encode(request.getParameter(k), "UTF-8");
				if (!k.equals(sExclusiveParam) && v != null && !v.trim().equals("")) {
					if (params.length() > 0) {
						params.append(sAmpChar).append(k).append("=").append(v);
					} else {
						params.append(k).append("=").append(v);
					}
				}
			} catch (Exception var7) {
				continue;
			}
		}

		return params.toString();
	}

	public static String getParams(HttpServletRequest request, String ampChar, String... exclusiveParams) {
		Enumeration paramNames = request.getParameterNames();
		String sAmpChar = ampChar == null ? "&amp;" : ampChar;
		StringBuffer params = new StringBuffer();

		while (paramNames.hasMoreElements()) {
			try {
				String k = (String) paramNames.nextElement();
				String v = URLEncoder.encode(request.getParameter(k), "UTF-8");
				if (isNotExistinExclusiveParams(k, exclusiveParams) && v != null && !v.trim().equals("")) {
					if (params.length() > 0) {
						params.append(sAmpChar).append(k).append("=").append(v);
					} else {
						params.append(k).append("=").append(v);
					}
				}
			} catch (Exception var7) {
				continue;
			}
		}

		return params.toString();
	}

	public static String getSearchParams(HttpServletRequest request, String ampChar) {
		return getSearchParams(request, ampChar, "");
	}

	public static String getSearchParams(HttpServletRequest request, String ampChar, String... exclusiveParams) {
		Enumeration paramNames = request.getParameterNames();
		String sAmpChar = ampChar == null ? "&amp;" : ampChar;
		StringBuffer params = new StringBuffer();

		while (paramNames.hasMoreElements()) {
			try {
				String k = (String) paramNames.nextElement();
				String v = URLEncoder.encode(request.getParameter(k), "UTF-8");
				if ((k.equals("cpage") || k.startsWith("s_")) && v != null && !v.trim().equals("")
						&& isNotExistinExclusiveParams(k, exclusiveParams)) {
					if (params.length() > 0) {
						params.append(sAmpChar).append(k).append("=").append(v);
					} else {
						params.append(k).append("=").append(v);
					}
				}
			} catch (Exception var7) {
				continue;
			}
		}

		return params.toString();
	}

	public static boolean isNotExistinExclusiveParams(String value, String[] exclusiveParams) {
		boolean result = true;
		if (exclusiveParams == null) {
			return result;
		} else {
			String[] var6 = exclusiveParams;
			int var5 = exclusiveParams.length;

			for (int var4 = 0; var4 < var5; ++var4) {
				String compareValue = var6[var4];
				if (value.equals(compareValue) || value.equals("auth")) {
					return false;
				}
			}

			return result;
		}
	}

	public static String convertHTML(String value, String escapeSpace) {
		if (value != null && !value.isEmpty()) {
			char[] arr = value.toCharArray();
			StringBuffer sb = new StringBuffer();
			char[] var7 = arr;
			int var6 = arr.length;

			for (int var5 = 0; var5 < var6; ++var5) {
				char c = var7[var5];
				switch (c) {
					case '\t' :
						if (escapeSpace.equalsIgnoreCase("y")) {
							sb.append(c);
						} else {
							sb.append("&nbsp;&nbsp;&nbsp;");
						}
						break;
					case '\n' :
						sb.append("<br />");
						break;
					default :
						sb.append(c);
				}
			}

			return sb.toString();
		} else {
			return "";
		}
	}

	public static String replaceNullToString(String str) {
		return str == null ? "" : str;
	}

	public static String editorsTrim(String str) {
		return str == null ? "" : str.trim().replace("\n", "").replace("&nbsp;", "").replace("<P></P>", "");
	}

	public static boolean isUsableUserId(String userId) throws Exception {
		if (userId != null && !userId.trim().equals("") && userId.length() >= 6 && userId.length() <= 20) {
			int strCount = 0;
			int intCount = 0;
			int etcCount = 0;
			char[] charArr = userId.toCharArray();

			for (int i = 0; i < charArr.length; ++i) {
				char c = charArr[i];
				if (i == 0 && (c < 'a' || c > 'z')) {
					return false;
				}

				if (c >= 'a' && c <= 'z') {
					++strCount;
				} else if (c >= '0' && c <= '9') {
					++intCount;
				} else {
					++etcCount;
				}
			}

			if (strCount > 0 && intCount > 0 && etcCount == 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}


	public static String rplc(String str, String n1, String n2) {
		if (str == null) {
			return "";
		} else {
			String tmp = str;
			StringBuffer sb = new StringBuffer();
			sb.append("");

			while (tmp.indexOf(n1) > -1) {
				int itmp = tmp.indexOf(n1);
				sb.append(tmp.substring(0, itmp));
				sb.append(n2);
				tmp = tmp.substring(itmp + n1.length());
			}

			sb.append(tmp);
			return sb.toString();
		}
	}

	public static String clobToHtml(String msg) {
		String result = "";
		result = rplc(msg, "\n", "<br>");
		result = rplc(result, " ", "&nbsp;");
		result = rplc(result, "\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		return result;
	}

	public static String byteTranslater(long size) {
		NumberFormat nf = NumberFormat.getIntegerInstance();
		DecimalFormat df = new DecimalFormat("#,##0.00");
		int kbyteSize = 1024;
		double doubleSize = 0.0D;
		String returnSize = null;
		int intSize;
		if (size >= 1048576000L) {
			intSize = (Long.valueOf(size / 1048576000L)).intValue();
			return nf.format((long) intSize) + "GB";
		} else if (size > (long) (kbyteSize * 1024)) {
			intSize = (int) ((double) size / (double) (kbyteSize * 1024) * 100.0D);
			doubleSize = (double) intSize / 100.0D;
			returnSize = df.format(doubleSize);
			if (returnSize.lastIndexOf(".") != -1
					&& "00".equals(returnSize.substring(returnSize.length() - 2, returnSize.length()))) {
				returnSize = returnSize.substring(0, returnSize.lastIndexOf("."));
			}

			return returnSize + "MB";
		} else if (size > (long) kbyteSize) {
			intSize = (Long.valueOf(size / (long) kbyteSize)).intValue();
			return nf.format((long) intSize) + "KB";
		} else {
			return "1KB";
		}
	}

	public static String getRplcAll(String param) {
		String result = "";
		if (param != null && param.length() > 0) {
			result = param.replaceAll("&", "&amp;");
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll(">", "&gt;");
			result = result.replaceAll("\\(", "&#40;");
			result = result.replaceAll("\\)", "&#41;");
			result = result.replaceAll("\"", "&quot;");
			result = result.replaceAll("'", "&apos;");
		}

		return result;
	}

	public static String getDeRplcAll(String param) {
		String result = "";
		if (param != null && param.length() > 0) {
			result = param.replaceAll("&amp;", "&");
			result = result.replaceAll("&lt;", "<");
			result = result.replaceAll("&gt;", ">");
			result = result.replaceAll("&#40;", "\\(");
			result = result.replaceAll("&#41;", "\\)");
			result = result.replaceAll("&quot;", "\"");
			result = result.replaceAll("&apos;", "'");
		}

		return result;
	}

	public static String getFileNameRplcAll(String filename) {
		String result = "";
		if (filename != null && filename.length() > 0) {
			result = filename.replaceAll("/", "");
			result = result.replaceAll("\\", "");
			result = result.replaceAll(".", " ");
			result = result.replaceAll("&", " ");
		}

		return result;
	}

	public static String nchk(String str) {
		return str == null ? "" : str;
	}

	public static String nchk(String str, String str1) {
		return str == null ? str1 : str;
	}

	public static int getIntParameter(HttpServletRequest request, String parameter) {
		return getIntParameter(request, parameter, 0);
	}

	public static int getIntParameter(HttpServletRequest request, String parameter, int defaultValue) {
		int value = defaultValue;
		if (request.getParameter(parameter) != null) {
			try {
				value = Integer.parseInt(request.getParameter(parameter));
			} catch (NumberFormatException var5) {
				value = defaultValue;
			}
		}

		return value;
	}

	public static String getAllParams(HttpServletRequest request, String exclusiveParam, String ampChar)
			throws UnsupportedEncodingException {
		Enumeration paramNames = request.getParameterNames();
		String[] exclusiveParams = new String[0];
		if (exclusiveParam != null) {
			exclusiveParams = exclusiveParam.split(",");
		}

		StringBuffer param = new StringBuffer();

		while (paramNames.hasMoreElements()) {
			String k = (String) paramNames.nextElement();
			String v = URLEncoder.encode(request.getParameter(k), "UTF-8");
			if (!isExists(exclusiveParams, k) && !k.equals("resultCode")) {
				param.append(ampChar);
				param.append(k);
				param.append("=");
				param.append(v);
			}
		}

		return param.toString();
	}

	public static String getAllParams(HttpServletRequest request, String exclusiveParam)
			throws UnsupportedEncodingException {
		return getAllParams(request, exclusiveParam, "&amp;");
	}

	public static String getAllParams(HttpServletRequest request) throws UnsupportedEncodingException {
		return getAllParams(request, (String) null);
	}

	private static boolean isExists(String[] array, String key) {
		String[] var5 = array;
		int var4 = array.length;

		for (int var3 = 0; var3 < var4; ++var3) {
			String a = var5[var3];
			if (a.equals(key)) {
				return true;
			}
		}

		return false;
	}

	private static void setCompareHtmlRegex() {
		COMPARE_HTML_REGEX = "(?is).*<(br|table|img|p|div|span|frame|iframe|http)[^<>]*[/]?>.*";
	}

	private static boolean hasHTMLTag(String value) {
		if (COMPARE_HTML_REGEX == null) {
			setCompareHtmlRegex();
		}

		return value != null && value.contains("<") ? value.matches(COMPARE_HTML_REGEX) : false;
	}

	public static String convertStringToHTMLTag(String str) {
		String result = "";
		if (str != null && !str.trim().equals("")) {
			if (hasHTMLTag(str)) {
				return str;
			}

			char[] arr = str.toCharArray();
			StringBuffer sb = new StringBuffer();
			char[] var6 = arr;
			int var5 = arr.length;

			for (int var4 = 0; var4 < var5; ++var4) {
				char c = var6[var4];
				switch (c) {
					case '\t' :
						sb.append("&nbsp;&nbsp;&nbsp;");
						break;
					case '\n' :
						sb.append("<br />");
					case '\r' :
						break;
					case ' ' :
						sb.append("&nbsp;");
						break;
					case '<' :
						sb.append("&lt;");
						break;
					case '>' :
						sb.append("&gt;");
						break;
					default :
						sb.append(c);
				}
			}

			result = sb.toString();
		}

		return result;
	}

	public static boolean regExpCheck(String param, String regExp) {
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(param);
		return matcher.matches();
	}

	public static String getBoardHeaderSubString(String boardHeard) {
		int cnt = 0;
		String retBoardHeard = "";

		try {
			if (boardHeard.indexOf("\n") == -1) {
				retBoardHeard = boardHeard;
			} else {
				while (boardHeard != null) {
					++cnt;
					String subStrHeard = boardHeard.substring(0, boardHeard.indexOf("\n"));
					String subBoardHeard = boardHeard.substring(boardHeard.indexOf("\n") + 1);
					int subStrHeardLength = subStrHeard.length();
					int ni = subStrHeardLength / 38;
					int nii = subStrHeardLength % 38;
					if (nii == 0) {
						cnt += ni - 1;
					} else if (nii > 0) {
						cnt += ni;
					}

					if (cnt >= 7 && subStrHeard.length() > 38) {
						subStrHeard = subStrHeard.substring(0, 38);
					}
					
					StringBuffer sb = new StringBuffer();
					sb.append(retBoardHeard);
					sb.append(subStrHeard);
					sb.append(retBoardHeard);
					retBoardHeard = sb.toString();
					if (cnt >= 7 || subBoardHeard.indexOf("\n") == -1) {
						break;
					}
				}
			}
		} catch (Exception var7) {
			retBoardHeard = "";
		}

		return retBoardHeard;
	}

	public static String getBoardMainHeaderSubString(String boardHeard) {
		String retBoardHeard = "";

		try {
			if (boardHeard != null && !boardHeard.equals("")) {
				retBoardHeard = boardHeard.substring(0, boardHeard.indexOf("\n"));
			}
		} catch (Exception var3) {
			retBoardHeard = "";
		}

		return retBoardHeard;
	}

	public static String requestReplace(String paramValue, String gubun) {
		String result = "";
		if (paramValue != null) {
			result = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			result = result.replaceAll("\\*", "");
			result = result.replaceAll("\\?", "");
			result = result.replaceAll("\\[", "");
			result = result.replaceAll("\\{", "");
			result = result.replaceAll("\\(", "");
			result = result.replaceAll("\\)", "");
			result = result.replaceAll("\\^", "");
			result = result.replaceAll("\\$", "");
			result = result.replaceAll("'", "");
			result = result.replaceAll("@", "");
			result = result.replaceAll("%", "");
			result = result.replaceAll(";", "");
			result = result.replaceAll(":", "");
			result = result.replaceAll("-", "");
			result = result.replaceAll("#", "");
			result = result.replaceAll("--", "");
			result = result.replaceAll("-", "");
			result = result.replaceAll(",", "");
			if (!"encodeData".equals(gubun)) {
				result = result.replaceAll("\\+", "");
				result = result.replaceAll("/", "");
				result = result.replaceAll("=", "");
			}
		}

		return result;
	}

	public static String removeTag(String str) {
		Pattern script = Pattern.compile("<(no)?script[^>]*>.*?</(no)?script>", 32);
		Matcher mat = script.matcher(str);
		String result = "";
		result = mat.replaceAll("");
		Pattern style = Pattern.compile("<style[^>]*>.*</style>", 32);
		mat = style.matcher(result);
		result = mat.replaceAll("");
		Pattern image = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");
		mat = image.matcher(result);
		result = mat.replaceAll("");
		Pattern tag = Pattern.compile("<(\"[^\"]*\"|'[^']*'|[^'\">])*>");
		mat = tag.matcher(result);
		result = mat.replaceAll("");
		Pattern ntag = Pattern.compile("<\\w+\\s+[^<]*\\s*>");
		mat = ntag.matcher(result);
		result = mat.replaceAll("");
		Pattern Eentity = Pattern.compile("&[^;]+;");
		mat = Eentity.matcher(result);
		result = mat.replaceAll("");
//		Pattern wspace = Pattern.compile("\\s\\s+");
//		mat = wspace.matcher(result);
//		result = mat.replaceAll("");
		return result;
	}

	public static String cutString(String str, int maxLength) {
		if (str == null) {
			return "";
		} else {
			return str.length() > maxLength ? str.substring(0, maxLength) + "..." : str;
		}
	}

	public static String splitLast(String paramValue, String gubun) {
		String[] splitData = paramValue.split("\\" + gubun);
		String splitLastValue = splitData[splitData.length - 1];
		return splitLastValue.toUpperCase();
	}

	public static TimeZone getTimeZone() {
		return TimeZone.getDefault();
	}

	public static String getCurrentTime(String formatter) {
		SimpleDateFormat fmt = new SimpleDateFormat(formatter, Locale.KOREA);
		fmt.setTimeZone(getTimeZone());
		return fmt.format(new Date(System.currentTimeMillis()));
	}

	public static String getBaseURI(HttpServletRequest request, String... params) {
		String baseURI = (String) request.getAttribute("baseURI");
		String append = "";
		if (params.length > 0) {
			for (int i = 0; i < params.length; ++i) {
				if (i == 0) {
					baseURI = baseURI.replaceAll(params[0], "");
				}

				if (i == 1) {
					append = params[i];
				}
			}
		}

		return baseURI + append;
	}

	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");

	    if (ip == null) {
	    	ip = request.getHeader("Proxy-Client-IP");
	    }
	    if (ip == null) {
	    	ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (ip == null) {
	    	ip = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if (ip == null) {
	    	ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if (ip == null) {
	    	ip = request.getRemoteAddr();
	    }

	    return ip;
	}

	public static String getProp(String prop) {
		ResourceBundle cfg = null;
		String configPath = "com.properties.config";
		String value = null;

		try {
			cfg = ResourceBundle.getBundle(configPath);
			value = cfg.getString(prop).trim();
		} catch (MissingResourceException var5) {
			value = null;
		}

		return value;
	}

	public static boolean isReferer(HttpServletRequest request) {
		String referer = request.getHeader("referer");
		return referer == null;
	}

	public static String getReferer(HttpServletRequest request) {
		String referer = request.getHeader("referer");
		if (referer != null) {
			referer = referer.replace(request.getScheme() + "://" + request.getHeader("Host"), "");
			if (referer.contains("?")) {
				referer = referer.substring(0, referer.indexOf("?"));
			}

			return referer;
		} else {
			return "";
		}
	}

	public static String RPad(String str, Integer length, char car) {
		String result = str;
		if (str == null) {
			result = "";
		}

		while (result.length() < length) {
			StringBuffer sb = new StringBuffer();
			sb.append(result);
			sb.append(String.valueOf(car));
			result = sb.toString();
		}

		return result;
	}

	public static String LPad(String str, Integer length, char car) {
		String result = str;
		if (str == null) {
			result = "";
		}

		return String.format("%" + length + "s", result).replace(" ", String.valueOf(car));
	}

	public static String splitDate(String date) {
		String regex = "";
		if (date.length() == 8) {
			regex = "(\\d{4})(\\d{2})(\\d{2})";
		} else if (date.length() == 6) {
			regex = "(\\d{4})(\\d{2})";
		}

		Matcher matcher = Pattern.compile(regex).matcher(date);
		if (matcher.find()) {
			if (date.length() == 8) {
				return matcher.group(1) + "-" + matcher.group(2) + "-" + matcher.group(3);
			}

			if (date.length() == 6) {
				return matcher.group(1) + "-" + matcher.group(2);
			}
		}

		return date;
	}

	public static String splitTelNo(String telNo) {
		String sTelNo = BaseUtil.isEmpty(telNo) ? "" : telNo.trim();
		if (sTelNo.length() <= 4 || sTelNo.length() > 8
				|| !sTelNo.startsWith("13") && !sTelNo.startsWith("14") && !sTelNo.startsWith("15")
						&& !sTelNo.startsWith("16") && !sTelNo.startsWith("17") && !sTelNo.startsWith("18")
						&& !sTelNo.startsWith("19")) {
			sTelNo = sTelNo.replaceAll("\\D", "")
					.replaceAll("^(02|050.|030.|0[0-1][1-9]|0[0-9]0|0[0-6][1-9])(\\d{3,4})(\\d{4})*$", "$1-$2-$3")
					.replaceAll("--", "-").replaceAll("-$", "");
		} else {
			sTelNo = sTelNo.replaceAll("\\D", "").replaceAll("^(\\d{4})(\\d{1,4})*$", "$1-$2");
		}

		return sTelNo;
	}

	public static String splitBizNo(String bizNo) {
		String regex = "";
		regex = "(\\d{3})(\\d{2})(\\d{5})";
		Matcher matcher = Pattern.compile(regex).matcher(bizNo);
		return matcher.find() ? matcher.group(1) + "-" + matcher.group(2) + "-" + matcher.group(3) : bizNo;
	}

	public static String[] splitTelNoAry(String telNo) {
		String regex = "";
		String[] rsltVal = new String[]{"", "", ""};
		if (telNo != null) {
			String telNum = telNo.trim();
			if (telNum.startsWith("01")) {
				regex = "(01[016789])(\\d{3,4})(\\d{4}$)";
			} else {
				regex = "(\\d{2,3})(\\d{3,4})(\\d{4})";
			}

			Matcher matcher = Pattern.compile(regex).matcher(telNum.trim());
			if (matcher.find()) {
				rsltVal[0] = matcher.group(1);
				rsltVal[1] = matcher.group(2);
				rsltVal[2] = matcher.group(3);
			} else {
				String[] splitVal = telNum.trim().split("-");
				if (splitVal.length > 0) {
					for (int i = 0; i < splitVal.length && i < rsltVal.length; ++i) {
						rsltVal[i] = splitVal[i].trim();
					}
				}
			}
		}

		return rsltVal;
	}

	public static String splitRegNo(String regNo) {
		String result = "";
		if (!BaseUtil.isEmpty(regNo) && regNo.length() > 5) {
			result = regNo.substring(0, 6);
		}

		return result;
	}

	public static String replaceRegNoToAster(String regNo) {
		String result = "";
		if (!BaseUtil.isEmpty(regNo)) {
			result = regNo.replaceAll("^(\\d{6}).*", "$1*******");
		}

		return result;
	}

	public static String splitInfoSecurity(String strInfo, String strType) {
		String rsltVal = "";
		String[] splitVal = strInfo.trim().split("-");
		if (splitVal.length > 1) {
			for (int i = 0; i < splitVal.length; ++i) {
				if (i == 0) {
					rsltVal = splitVal[i].trim();
				} else if (i == 1 && "ssn".equals(strType)) {
					StringBuffer sb = new StringBuffer();
					sb.append(rsltVal);
					sb.append("-");
					sb.append(RPad(splitVal[i].trim().substring(0, 1), splitVal[i].trim().length(), '*'));
					rsltVal = sb.toString();
				} else {
					StringBuffer sb = new StringBuffer();
					sb.append(rsltVal);
					sb.append("-");
					sb.append(RPad("", splitVal[i].trim().length(), '*'));
					rsltVal = sb.toString();
				}
			}
		} else if (splitVal.length == 1) {
			String tmpSSN = splitVal[0].trim();
			if ("ssn".equals(strType) && tmpSSN.length() >= 6) {
				splitVal = new String[]{tmpSSN.substring(0, 6), null};
				if (tmpSSN.length() >= 6) {
					splitVal[1] = tmpSSN.substring(6, tmpSSN.length());
				} else {
					splitVal[1] = "*******";
				}
			} else {
				splitVal = splitTelNoAry(splitVal[0].trim());
			}

			for (int i = 0; i < splitVal.length; ++i) {
				if (i == 0) {
					rsltVal = splitVal[i].trim();
				} else if (i == 1 && "ssn".equals(strType)) {
					StringBuffer sb = new StringBuffer();
					sb.append(rsltVal);
					sb.append("-");
					sb.append(RPad(splitVal[i].trim().substring(0, 1), 7, '*'));
					rsltVal = sb.toString();
				} else {
					StringBuffer sb = new StringBuffer();
					sb.append(rsltVal);
					sb.append("-");
					sb.append(RPad("", splitVal[i].trim().length(), '*'));
					rsltVal = sb.toString();
				}
			}
		}

		return rsltVal;
	}

	public static String getMonthFstDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM01", Locale.KOREA);
		String today = sdf.format(new Date());
		return today;
	}

	public static String getToday(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.KOREA);
		String today = sdf.format(new Date());
		return today;
	}

	public static String getMonthMinus(String format, int cnt) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(5, -(cnt * 30));
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.KOREA);
		String today = sdf.format(calendar.getTime());
		return today;
	}
	
	public static boolean isEmpty(Object o) { // true: empty or null | false : not null and not empty
		if(o == null) {
			return true;
		}
		else {
			return (String) o == null || ((String) o).trim().isEmpty();
		}
	}
	
	public static String[] phoneNumberSplit(String phoneNumber) throws Exception{

        Pattern tellPattern = Pattern.compile( "^(01\\d{1}|02|0505|0502|0506|0\\d{1,2})-?(\\d{3,4})-?(\\d{4})");

        Matcher matcher = tellPattern.matcher(phoneNumber);
        if(matcher.matches()) {
            //정규식에 적합하면 matcher.group으로 리턴
            return new String[]{ matcher.group(1), matcher.group(2), matcher.group(3)};
        }else{
            //정규식에 적합하지 않으면 substring으로 휴대폰 번호 나누기
            
            String str1 = phoneNumber.substring(0, 3);
            String str2 = phoneNumber.substring(3, 7);
            String str3 = phoneNumber.substring(7, 11);
            return new String[]{str1, str2, str3};
        }
    }
	
	public static boolean isInt(String str) {
		
	  	try {
	      	Integer.parseInt(str);
	      	return true; //String is an Integer
		} catch (NumberFormatException e) {
	    	return false; //String is not an Integer
		}
	  	
	}
	
	public static boolean isString(Object o) {
		if(o == null) {
			return false;
		}
		
		if( o instanceof String ) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static String parseString(Object o) {
		if(BaseUtil.isEmpty(o)) {
			return "";
		}
		if( o instanceof Integer ) {
			return Integer.toString((int) o);
		}
		if( o instanceof Long ) {
			return Long.toString((Long) o);
		}
		return (String) o;
	}
	
	public static int parseInt(Object o) {
		if(o == null) {
			return 0;
		}
		if(isString(o)) {
			return Integer.parseInt( (String) o );
		}
		return (int) o;
	}
	
	public static int parseIntDft1(Object o) {
		if(o == null) {
			return 1;
		}
		if(isString(o)) {
			if(isEmpty(o)) {
				return 1;
			}
			return Integer.parseInt( (String) o );
		}
		return (int) o;
	}
	
	public static int parseIntDft(Object o, int dft) {
		if(o == null) {
			return dft;
		}
		if(isString(o)) {
			if(isEmpty(o)) {
				return dft;
			}
			return Integer.parseInt( (String) o );
		}
		return (int) o;
	}
	
	public static String numberGen(int len, int dupCd ) {
        
        Random rand = new Random();
        String numStr = ""; //난수가 저장될 변수
		
        for(int i=0;i<len;i++) {

    		StringBuffer sb = new StringBuffer();
    		sb.append(numStr);
            
            //0~9 까지 난수 생성
            String ran = Integer.toString(rand.nextInt(10));
            
            if(dupCd==1) {
                //중복 허용시 numStr에 append
                sb.append(ran);
            }else if(dupCd==2) {
                //중복을 허용하지 않을시 중복된 값이 있는지 검사한다
                if(!numStr.contains(ran)) {
                    //중복된 값이 없으면 numStr에 append
                    sb.append(ran);
                }else {
                    //생성된 난수가 중복되면 루틴을 다시 실행한다
                    i-=1;
                }
            }
            
            numStr = sb.toString();
        }
        return numStr;
    }
	
	public static String getServerIp() {
		
		InetAddress local = null;
		try {
			local = InetAddress.getLocalHost();
		}
		catch ( UnknownHostException e ) {
			return "";
		}
			
		if( local == null ) {
			return "";
		}
		else {
			String ip = local.getHostAddress();
			return ip;
		}
			
	}
	
	public static boolean chkFileType(String[] fileExt, String chkFileType) {
		boolean result = false;
		
		List<String> fileExtList = new ArrayList<>(Arrays.asList(fileExt));
		if(chkFileType != null && fileExtList.contains(chkFileType.toLowerCase())) {
			result = true;
		}
		
		return result;
	}
	
	
}