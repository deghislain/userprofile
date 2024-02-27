package com.userprofile.model;

import org.springframework.http.HttpStatus;

public class ErrorDetails {
	private String errorId;
	private HttpStatus status;
	private String  url;
	private String title;
	private String errDescription;
	private String resourceId;
	private String hostname;
	
	public ErrorDetails(HttpStatus status, String  url, String title, String errDescription, String resourceId, String hostname) {
		this.status = status;
		this.url = url;
		this.title = title;
		this.errDescription = errDescription;
		this.resourceId = resourceId;
		this.hostname = hostname;
	}
	
	public String getErrorId() {
		return errorId;
	}
	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getErrDescription() {
		return errDescription;
	}
	public void setErrDescription(String errDescription) {
		this.errDescription = errDescription;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

}
