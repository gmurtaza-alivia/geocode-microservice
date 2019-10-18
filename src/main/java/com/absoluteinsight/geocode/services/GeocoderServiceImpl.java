package com.absoluteinsight.geocode.services;
import java.net.URLDecoder;
/**
 * @author Ghulam Murtaza 
 * 
 * dated 23/09/2019
 * 
 * main class the run the geocode microservice.
 * 
 */
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import java.net.URLEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import com.absoluteinsight.geocode.data.model.Address;
import com.absoluteinsight.geocode.data.repository.OutsiderDataSourceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;

@Service
public class GeocoderServiceImpl implements GeocoderService {

	private static final Logger logger = LoggerFactory.getLogger(GeocoderServiceImpl.class);
	public static final String GOOGLE_REST_SERVICE = "https://maps.googleapis.com/maps/api/geocode/json?address=";

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${goecode.google.apikey}")
	private String apikey;

	public Map<String, String> getAddressInformation(String address) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			
			Object result = restTemplate.getForObject(GOOGLE_REST_SERVICE.concat(address+"&key="+apikey), Object.class);
			logger.info(apikey);
			String json = (new Gson()).toJson(result, LinkedHashMap.class);
			System.out.println(json);
			ObjectNode node = new ObjectMapper().readValue(json, ObjectNode.class);
			map.put("lng", node.get("results").get(0).get("geometry").get("location").get("lng").asText());
			map.put("lat", node.get("results").get(0).get("geometry").get("location").get("lat").asText());
			map.put("formatedaddress", node.get("results").get(0).get("formatted_address").asText());
			
		} catch (Exception e) {
			System.out.println("----------------------------"+e.getMessage());
		}
		return map;
	}

	@Override
	public boolean getAddressInformation(Address address) {
		try {
			String faddress = URLEncoder.encode(address.getAddressToGeoCode(), "UTF-8");
			String requestUrl = GOOGLE_REST_SERVICE.concat(faddress+"&key="+apikey);
			Object result = restTemplate.getForObject(requestUrl, Object.class);
			String json = (new Gson()).toJson(result, LinkedHashMap.class);
			logger.info(json);
			ObjectNode node = new ObjectMapper().readValue(json, ObjectNode.class);
			address.setLatitude(node.get("results").get(0).get("geometry").get("location").get("lat").asDouble());
			address.setLongitude(node.get("results").get(0).get("geometry").get("location").get("lng").asDouble());
			address.setFormatedAddress(node.get("results").get(0).get("formatted_address").asText());
			
		} catch (Exception e) {
			logger.info("error to geting the geocode from google"+e.getMessage());
			return false;
		}
		return true;
	}

}
