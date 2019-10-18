/**
 * @auther Ghulam Murtaza
 * @since Oct 16, 2019
 * gmurtaza@aliviaanlytics.com
 * com.absoluteinsight.geocode.services.GeoCodeJobeService.java
 * 
 */
package com.absoluteinsight.geocode.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.absoluteinsight.geocode.data.model.GeoCodeJob;
import com.absoluteinsight.geocode.data.model.JobForDataSource;
import com.absoluteinsight.geocode.utils.JobType;


public interface GeoCodeJobeService {
	
	public void save(GeoCodeJob job);
	
	public GeoCodeJob createAndsaveNewJob(JobForDataSource jobForDataSource, String groupName,JobType jobtype);
	
	public  List<GeoCodeJob> getALlJobs();

}
