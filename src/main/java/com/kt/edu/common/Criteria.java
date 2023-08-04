package com.kt.edu.common;


public class Criteria {
	private int pageNo;
	private int perPageNum;
	
	public int getPageStart() {
		return (this.pageNo - 1) * perPageNum;
	}
	
	public Criteria() {
		this.pageNo = 1;
		this.perPageNum = 20;
	}
	
	public int getPageNo() {
		return pageNo;
	}
	
	public void setPageNo(int pageNo) {
		if(pageNo <= 0) {
			this.pageNo = 1;
		}
		else {
			this.pageNo = pageNo;
		}
	}
	
	public int getPerPageNum() {
		return perPageNum;
	}
	
	public void setPerPageNum(int perPageNum) {
		int cnt = this.perPageNum;
		
		if(perPageNum != cnt) {
			this.perPageNum = cnt;
		}
		else {
			this.perPageNum = perPageNum;
		}
	}
}
