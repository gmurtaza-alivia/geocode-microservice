package com.absoluteinsight.geocode.services;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.absoluteinsight.geocode.data.model.Address;
/**
 * 
 * Simple Remote google geocoder Service 
 */


public interface  GeocoderService {
	/**
	 * @param address
	 * @return Map 
	 */
	
	public Map<String, String> getAddressInformation(String address);
	public boolean getAddressInformation(Address address);

}
