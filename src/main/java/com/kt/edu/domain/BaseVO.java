package com.kt.edu.domain;

import lombok.Data;


@Data
public class BaseVO {
	
	// 페이징
	int totalCnt; // 전체 갯수
	int pageStart; // 시작 페이지
	int perPageNum; // 페이지 당 갯수

	int pageNo; // 페이지

	
}
