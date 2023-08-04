package com.kt.edu.common;

public class Paging {

	private int totalCount;
	private int pageNum = 20;
	private int startPage;
	private int endPage;
	
	private Criteria criteria;
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		pagingData();
	}
	
	private void pagingData() {
		endPage = (int) (Math.ceil(criteria.getPageNo() / (double) pageNum) * pageNum);
		startPage = (endPage - pageNum) + 1;
		
		int tmpEndPage = (int)(Math.ceil(totalCount/(double) criteria.getPerPageNum()));
		if(endPage > tmpEndPage) {
			endPage = tmpEndPage;
		}
		
	}
	
	public int getPageNum() {
		return pageNum;
	}
	
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	public int getEndPage() {
		return endPage;
	}
	
	public void setEndPage (int endPage) {
		this.endPage = endPage;
	}
	
	public int getStartPage() {
		return startPage;
	}
	
	public void setStartPage (int startPage) {
		this.startPage = startPage;
	}
	
	public Criteria getCriteria() {
		return criteria;
	}
	
	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

}
