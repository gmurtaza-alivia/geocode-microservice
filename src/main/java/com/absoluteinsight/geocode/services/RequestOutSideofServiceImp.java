/**
 * @auther Ghulam Murtaza
 * @since Oct 4, 2019
 * gmurtaza@aliviaanlytics.com
 * com.absoluteinsight.geocode.services.RequestOutSideService.java
 * this service is used to send request to the ai server to update the status of the datasource.
 */
package com.absoluteinsight.geocode.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.absoluteinsight.geocode.data.model.JobForDataSource;
import com.absoluteinsight.geocode.utils.GeoStates;

@Service
public class RequestOutSideofServiceImp implements RequestOutSideofService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	public boolean sendRequestToUpdateStatusatDatasource(JobForDataSource jobfordatasource, GeoStates geostate )
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		String url = jobfordatasource.getRequestedUrl()+"/geocoderesponse";
		MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<String, String>();
		bodyMap.add("datasourceid", jobfordatasource.getDatasourceid());
		bodyMap.add("jobstatus", geostate.toString());
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(bodyMap, headers);
		
		if(!url.contains("http://"))
			url = "http://"+ url;
	
		ResponseEntity<String> model = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
		String response = model.getBody();
		if(response.equals("done"))
			return true;
		else
			return true;
		
	}
}
