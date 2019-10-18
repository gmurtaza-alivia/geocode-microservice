package com.absoluteinsight.geocode.data.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataSourceAndColumn implements Serializable{

	private JobForDataSource jobForDataSource;
	private List<DasourceColumn> dasourceColumns;
	private String requestedUrl;
	
	public JobForDataSource getJobForDataSource() {
		return jobForDataSource;
	}
	public void setJobForDataSource(JobForDataSource jobForDataSource) {
		this.jobForDataSource = jobForDataSource;
	}
	public List<DasourceColumn> getDasourceColumns() {
		return dasourceColumns;
	}
	public void setDasourceColumns(List<DasourceColumn> dasourceColumns) {
		this.dasourceColumns = dasourceColumns;
	}
	
	public String getRequestedUrl() {
		return requestedUrl;
	}
	
	public void setRequestedUrl(String requestedUrl) {
		this.requestedUrl = requestedUrl;
	}
	

}
