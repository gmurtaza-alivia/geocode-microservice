/**
 * @auther Ghulam Murtaza
 * @since Oct 17, 2019
 * gmurtaza@aliviaanlytics.com
 * com.absoluteinsight.geocode.utils.ResponseStatus.java
 * 
 */
package com.absoluteinsight.geocode.utils;

import java.io.Serializable;

public class ResponseStatus implements Serializable{
	
	private boolean resstatus;
	private String message;
	
	public ResponseStatus( boolean resstatus,String message )
	{
		this.resstatus =resstatus;
		this.message=message;
	}

	public boolean isResstatus() {
		return resstatus;
	}
	public void setResstatus(boolean resstatus) {
		this.resstatus = resstatus;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
