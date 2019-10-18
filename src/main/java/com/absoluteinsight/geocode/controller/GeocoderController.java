package com.absoluteinsight.geocode.controller;


import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.absoluteinsight.geocode.data.model.Address;
import com.absoluteinsight.geocode.data.model.DataSourceAndColumn;
import com.absoluteinsight.geocode.data.model.GeoCodeJob;
import com.absoluteinsight.geocode.data.model.JobForDataSource;
import com.absoluteinsight.geocode.services.AddressService;
import com.absoluteinsight.geocode.services.DataSourceService;
import com.absoluteinsight.geocode.services.GeoCodeJobeService;
import com.absoluteinsight.geocode.services.GeocoderService;
import com.absoluteinsight.geocode.services.JobService;
import com.absoluteinsight.geocode.utils.GeoStatus;
import com.absoluteinsight.geocode.utils.ResponseStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class GeocoderController {
	
	private static final Logger logger = LoggerFactory.getLogger(GeocoderController.class);
	 
	@Autowired
	private GeocoderService geocoderService;
	
	@Autowired
	private AddressService addressservice;
	
	@Autowired
	private DataSourceService dataSourceService;
	
	@Autowired
	private GeoCodeJobeService geoCodeJobeService;
	
	@Autowired
	private JobService jobService;
	
	@Value("${goecode.google.apikey}")
	private String apikey;
	
	@RequestMapping(value="/geocodeadr", method = RequestMethod.GET, produces = "application/json")
	public  List<Address>  geocoderadr(@RequestParam(name="address", required=true) String  address)
	{
		return  addressservice.getSearchedAddress(address);
	}
	
	@RequestMapping(value="/geocodefuzzyaddress", method = RequestMethod.GET, produces = "application/json")
	public  Address  geocodefuzzyaddress(@RequestParam(name="address", required=true) String  address)
	{
		return  addressservice.getFuzzyAddress(address);
	}
	
	@RequestMapping(value="/geocode", method = RequestMethod.GET, produces = "application/json")
	public Map<String, String> geocoder(@RequestParam(name="address", required=true) String  address){

		return  geocoderService.getAddressInformation(address);
	}
	
	@RequestMapping(value="/test", method = RequestMethod.GET)
	public String test(HttpServletRequest request){
		//userRepository.findAll();
		String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
        .replacePath(null)
        .build()
        .toUriString();
		 System.out.println(request.getRequestURI()+"addr"+request.getRemoteAddr()+"host"+request.getRemoteHost()+"port"+request.getRemotePort()+"user"+request.getRemoteUser());
		return  apikey+baseUrl+"test here fine "+request.getLocalName()+request.getContextPath()+"-------"+request.getContentType()+request.getServletContext().getContextPath()+request.getRequestURI()+"addr"+request.getRemoteAddr()+"host"+request.getRemoteHost()+"port"+request.getRemotePort()+"user"+request.getRemoteUser();
	}
	
	@RequestMapping(value="/geocodeDatasource", method = RequestMethod.POST)
	public GeoStatus geocodeDatasource(@RequestBody DataSourceAndColumn dataSourceAndColumn){
		
		logger.info(dataSourceAndColumn.getJobForDataSource().getDatasourceName()+ " is going to geo coding");
		GeoStatus geoStatus =  dataSourceService.saveDatasourceForGeoCodeJob(dataSourceAndColumn);
		logger.info(dataSourceAndColumn.getJobForDataSource().getDatasourceName()+ " "+geoStatus.getMessage());
		
		return geoStatus;
	}
	
	@RequestMapping(value="/getJobs", method = RequestMethod.GET)
	public List<GeoCodeJob> geocodeDatasource(){
		
		return  geoCodeJobeService.getALlJobs() ;
	}
	
	@RequestMapping(value="/getDataSources", method = RequestMethod.POST)
	public Page<JobForDataSource> getDatasources(int pageno, int noOfPageElements){
		
		return  dataSourceService.getAllDataSourceJobs(pageno,noOfPageElements) ;
	}
	
	@RequestMapping(value="/intereptjob", method = RequestMethod.POST)
	public ResponseStatus interept(@RequestParam("jobname") String jobname ){
		
		  boolean status = jobService.intruptJob(jobname);
		  String message ="Job interpted";
		  if(!status) {
			  message="this Job dose not exist";
			  logger.info(jobname +" have intereped");
		  }else
			  logger.info(jobname +" not intereped");
		  
		  return new ResponseStatus(status, message);
		  
	}
	
	@RequestMapping(value="/restartjobs", method = RequestMethod.POST)
	public ResponseStatus restartJobs(@RequestParam("datasourceid") String datasourceid ){
		
		  logger.info(datasourceid +" request came to restart the job");
		  return dataSourceService.restartJobsAgainstDatasource(datasourceid,"");
		  
		
		  
	}
	
	@RequestMapping(value="/restartjob", method = RequestMethod.POST)
	public ResponseStatus restartJob(@RequestParam("datasourceid") String datasourceid,@RequestParam("jobname") String jobname ){
		  logger.info("Request to restart the job "+datasourceid +" "+jobname);
		  return dataSourceService.restartJobsAgainstDatasource(datasourceid,jobname);
		  
	}
	
}
