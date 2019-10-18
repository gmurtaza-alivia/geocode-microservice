/**
 * @auther Ghulam Murtaza
 * @since Oct 16, 2019
 * gmurtaza@aliviaanlytics.com
 * com.absoluteinsight.geocode.services.GeoCodJobeServiceImp.java
 * 
 */
package com.absoluteinsight.geocode.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.absoluteinsight.geocode.data.model.GeoCodeJob;
import com.absoluteinsight.geocode.data.model.JobForDataSource;
import com.absoluteinsight.geocode.data.repository.GeoCodeJobsRepositry;
import com.absoluteinsight.geocode.utils.GeoStates;
import com.absoluteinsight.geocode.utils.JobType;

@Service
public class GeoCodJobeServiceImp implements GeoCodeJobeService
{
	@Autowired
	private GeoCodeJobsRepositry geoCodeJobsRepositry;
	
	@Autowired
	DataSourceService dataSourceService;
	
	@Override
	public void save(GeoCodeJob job) {
		geoCodeJobsRepositry.save(job);
		
	}
	
	public  List<GeoCodeJob> getALlJobs()
	{
		return geoCodeJobsRepositry.findAll();
	}
	
	
	public GeoCodeJob createAndsaveNewJob(JobForDataSource jobForDataSource, String jobName,JobType jobtype)
	{
		GeoCodeJob geoCodeJob = new GeoCodeJob();
		try
		{
			
			geoCodeJob.setJobForDataSource(jobForDataSource);
			geoCodeJob.setJobname(jobForDataSource.getDatasourceid()+"_"+jobName);
			geoCodeJob.setGroupname(jobName);
			geoCodeJob.setJobStatus(GeoStates.Waiting);
			geoCodeJob.setJobType(jobtype);
			geoCodeJobsRepositry.save(geoCodeJob);
			
		}catch(Exception e) {e.printStackTrace();};
		
		
		return geoCodeJob;
	}

}
