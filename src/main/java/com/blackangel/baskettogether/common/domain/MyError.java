package com.blackangel.baskettogether.common.domain;

import java.util.Map;

public class MyError {
	private String url;
	private String errMessage;
	private Map<String, Object> errParams;
	
	public MyError(String url, String errMessage, Map<String, Object> errParams) {
		super();
		this.url = url;
		this.errMessage = errMessage;
		this.errParams = errParams;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getErrMessage() {
		return errMessage;
	}
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	public Map<String, Object> getErrParams() {
		return errParams;
	}
	public void setErrParams(Map<String, Object> errParams) {
		this.errParams = errParams;
	}
	
	
	
}
