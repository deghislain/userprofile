package com.userprofile.utility;

import java.net.URI;

import org.springframework.http.ProblemDetail;

import com.userprofile.model.ErrorDetails;

public class ProblemDetailBuilder {
	private ProblemDetail pd;
	/*private HttpStatus status;
	private String  url;
	private String title;
	private String errDetails;
	private String resourceId;
	private String hostname;*/
	
	public ProblemDetailBuilder(ErrorDetails err){
		this.pd = ProblemDetail.forStatusAndDetail(err.getStatus(), err.getErrDescription());
		pd.setType(URI.create(err.getUrl()));
		pd.setTitle(err.getTitle());
		pd.setProperty("resourceId", err.getResourceId());
		pd.setProperty("hostname", err.getHostname());
	}
	
	public ProblemDetail getProbemDetail() {
		return this.pd;
	}

}
