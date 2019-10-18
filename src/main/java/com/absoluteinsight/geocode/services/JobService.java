/**
 * @auther Ghulam Murtaza
 * @since Sep 30, 2019
 * gmurtaza@aliviaanlytics.com
 * com.absoluteinsight.geocode.services.JobService.java
 * 
 */
package com.absoluteinsight.geocode.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.absoluteinsight.geocode.data.model.DasourceColumn;
import com.absoluteinsight.geocode.data.model.JobForDataSource;
import com.absoluteinsight.geocode.data.repository.AddressrepositoryImp;
import com.absoluteinsight.geocode.data.repository.JdbcTempleteEngin;
import com.absoluteinsight.geocode.data.repository.OutsiderDataSourceRepository;
import com.absoluteinsight.geocode.jobs.DataSourceGroupJob;
import com.absoluteinsight.geocode.utils.JobType;

@Service
public class JobService {
	
	@Autowired
	private TaskExecutor taskExecutor;
	
	@Autowired
	private Map<String,DataSourceGroupJob> currantrunningJobs;
	
	@Autowired
	private JdbcTempleteEngin jdbcTempleteEngin;
	
	@Autowired
	private AddressService addressservice;
	
	@Autowired
	private GeocoderService geocoderService;
	
	@Autowired
	private DataSourceService dataSourceService;
	
	@Autowired
	private GeoCodeJobeService  GeoCodeJobeService;
	
	@Autowired
	AddressrepositoryImp addressrepositoryImp;
	
	
	public void createNewJobforGroup(JobForDataSource datasource, List<DasourceColumn> dasourceColumns,String groupName,JobType jobtype)
	{
		
		DataSourceGroupJob job = new DataSourceGroupJob(dasourceColumns,new OutsiderDataSourceRepository(datasource,jdbcTempleteEngin.getJdbcTemplate(datasource),groupName),GeoCodeJobeService,jobtype);
		job.setAddressservice(addressservice);
		job.setGeocoderService(geocoderService);
		job.setCurrantrunningJobs(currantrunningJobs);
		job.setDataSourceService(dataSourceService);
		addressrepositoryImp.initializeHibernateSearch();
		currantrunningJobs.put(datasource.getDatasourceid()+"_"+groupName,job);
		taskExecutor.execute(job);
	}
	
	
	public boolean intruptJob(String jobname)
	{
		DataSourceGroupJob job =  currantrunningJobs.get(jobname);
		if(job!=null)
		{
			job.interrupt();
			return true;
		}else
			return false;
	}
	
	
	

}
