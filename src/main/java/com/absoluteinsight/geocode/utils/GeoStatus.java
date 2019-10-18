/**
 * @auther Ghulam Murtaza
 * @date Sep 25, 2019
 * gmurtaza@aliviaanlytics.com
 *
 */
package com.absoluteinsight.geocode.utils;

/**
 * @author Ghulam Murtaza
 * @dated Sep 25, 2019
 * com.absoluteinsight.geocode.utils.GeoStatus.java
 * gmurtaza@aliviaanalytics.com
 * GeoStatus use to receive and send the status of the request and response.
 */


public class GeoStatus {
	
	private boolean geostatus;
	private GeoStates statusCode;
	private String message;
	
	
	public boolean isGeostatus() {
		return geostatus;
	}
	
	public GeoStates getStatusCode() {
		return statusCode;
	}
	
	public String getMessage() {
		return message;
	}
	public void setGeostatus(boolean geostatus) {
		this.geostatus = geostatus;
	}
	
	public void setStatusCode(GeoStates statusCode) {
		this.statusCode = statusCode;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
