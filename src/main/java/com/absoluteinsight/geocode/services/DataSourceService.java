package com.absoluteinsight.geocode.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.annotations.Synchronize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.absoluteinsight.geocode.data.model.DasourceColumn;
import com.absoluteinsight.geocode.data.model.DataSourceAndColumn;
import com.absoluteinsight.geocode.data.model.JobForDataSource;
import com.absoluteinsight.geocode.data.repository.DataSourceRepository;
import com.absoluteinsight.geocode.utils.GeoStates;
import com.absoluteinsight.geocode.utils.GeoStatus;
import com.absoluteinsight.geocode.utils.JobType;
import com.absoluteinsight.geocode.utils.ResponseStatus;
import com.absoluteinsight.geocode.data.repository.JdbcTempleteEngin;
import com.absoluteinsight.geocode.jobs.DataSourceGroupJob;;


@Service
public class DataSourceService  {
	
	@Autowired
	private DataSourceRepository dataSourceRepository;
	@Autowired
	private JobService jobService;
	
	@Autowired
	private JdbcTempleteEngin jdbcTempleteEngin;
	
	@Autowired
	private DatasourceColumnService datasourceColumnService;
	
	@Autowired
	private RequestOutSideofService requestOutSideofService;
	
	@Autowired
	private Map<String,DataSourceGroupJob> currantrunningJobs;
	
	@Transactional
	public boolean saveDatasource(JobForDataSource datasource)
	{
		if(dataSourceRepository.getByDatasourceid(datasource.getDatasourceid())==null)
		{
			dataSourceRepository.save(datasource);
			return true;
		}
		else 
			return false;
	}
	
	@Transactional
	public void save(JobForDataSource datasource)
	{
		dataSourceRepository.save(datasource);
	}
	
	public Page<JobForDataSource> getAllDataSourceJobs(int pageno, int noPageElements)
	{
		Sort sort = new Sort(Sort.Direction.DESC, "jodid");
		Pageable pageable = PageRequest.of(pageno, noPageElements,sort);
		
		return dataSourceRepository.findAll(pageable);
	}
	
	
	@Transactional
	public JobForDataSource getJobForDatasourceById(String datasourceid)
	{
		return dataSourceRepository.getByDatasourceid(datasourceid);
	}
	
	public GeoStatus saveDatasourceForGeoCodeJob(DataSourceAndColumn dataSourceAndColumn)
	{
		GeoStatus geoStatus = new GeoStatus();
		JobForDataSource jobfordatasource = dataSourceAndColumn.getJobForDataSource();
		if(jobfordatasource!=null && jobfordatasource.getDatasourceid()!=null && !jobfordatasource.getDatasourceid().equals(""))
		{
			boolean connectionstatus = jdbcTempleteEngin.isDataBaseConnected(jobfordatasource); 
			if(connectionstatus)
			{
				jobfordatasource.setJobStatus(GeoStates.Waiting);
				jobfordatasource.setRequestedUrl(dataSourceAndColumn.getRequestedUrl());
				boolean status = saveDatasource(jobfordatasource);
				
				if(status)
				{
					List<DasourceColumn> dasourceColumns = dataSourceAndColumn.getDasourceColumns();
					datasourceColumnService.saveAllDatasourceColumn(dasourceColumns);
					geoStatus.setGeostatus(true);
					geoStatus.setStatusCode(GeoStates.Waiting);
					geoStatus.setMessage("Update job Sucessfully its in Progress Now");
					startProcessToUpdateLatLong(jobfordatasource,dasourceColumns,JobType.Auto);
										
				}
				else
				{
					geoStatus.setGeostatus(false);
					geoStatus.setStatusCode(GeoStates.Waiting);
					geoStatus.setMessage("Datasource are not acessible");
				}
			}else
			{
				geoStatus.setGeostatus(false);
				geoStatus.setStatusCode(GeoStates.Waiting);
				geoStatus.setMessage("Datasource are not acessible");
	
			}	
		}
				
		return geoStatus;
	}
	
	/**
	 * 
	 * @auther Ghulam Murtaza
	 * @since Oct 18, 2019
	 * @param dataSourceId
	 * @param groupName
	 * @return
	`* @parim
	 * @return 
	 * 
	 * this function is used to restart the jobs of datasource or restart the job with group
	 */
	
	public ResponseStatus  restartJobsAgainstDatasource(String dataSourceId,String groupName)
	{	
		boolean status = false;
		String message = dataSourceId + " " +groupName +" " ;
		try
		{
			JobForDataSource jobfordatasource = dataSourceRepository.getByDatasourceid(dataSourceId);
			List<DasourceColumn> dasourceColumns = datasourceColumnService.getColumnsByDatasourceId(dataSourceId);
			if(jobfordatasource!=null && dasourceColumns!=null && dasourceColumns.size()>0)
			{
					if(groupName!=null && !groupName.equals(""))
					{
						String jobid = jobfordatasource.getDatasourceid()+"_"+groupName;
						DataSourceGroupJob job =  currantrunningJobs.get(jobid);
						
						if(job==null)
							dasourceColumns = dasourceColumns.stream().filter(col->col.getAddressGroupName().equals(groupName)).collect(Collectors.toList());
						else
						{
							status = false;
							message +="Job is already running againast this datasource group";
						}
					}
					
					if(dasourceColumns!=null)
					{
						startProcessToUpdateLatLong(jobfordatasource, dasourceColumns,JobType.Adhoc);
						status =  true;
						message +=" Datasource columns are not exist";
					}
			}
			else
			{
				status = false;
				message +="are not found please send correct datasource id";
			}
		}catch(Exception e) {
			e.printStackTrace();
			status = false;
			message +=" Exception came Please test the passed vaiable and than send request again";
		}
		
		
		return new ResponseStatus(status, message);
	}
	
	/**
	 * 
	 * @auther Ghulam Murtaza
	 * @since Oct 18, 2019
	 * @param jobfordatasource
	 * @param dasourceColumns
	 * @param jobtype
	`* @parim
	 * @return void
	 * 
	 * this function is used to send request to job service to start the process to update the latitude and longitude of the datasource.
	 */
	
	//@Async
	public void startProcessToUpdateLatLong(JobForDataSource jobfordatasource, List<DasourceColumn> dasourceColumns,JobType jobtype)
	{
		Set<String> groupSet = getGroupAddressFromColumns(dasourceColumns);
		for(String groupname: groupSet)
		{
			List<DasourceColumn> relatedGroupColumn = dasourceColumns.stream().filter(col->col.getAddressGroupName().equals(groupname)).collect(Collectors.toList());
			jobService.createNewJobforGroup(jobfordatasource,relatedGroupColumn,groupname,jobtype);
			
		}
		
	}
	
	/**
	 * 
	 * @auther Ghulam Murtaza
	 * @since Oct 18, 2019
	 * @param columns
	 * @return
	`* @parim
	 * @return Set<String> of groups of DasourceColumns 
	 * 
	 * 
	 */
	
	public Set<String> getGroupAddressFromColumns(List<DasourceColumn> columns)
	{
		Set<String> set = columns.stream().map(col->col.getAddressGroupName()).collect(Collectors.toSet());
		return set;
	}
	
	/**
	 * 
	 * @auther Ghulam Murtaza
	 * @since Oct 18, 2019
	 * @param jobfordatasource
	 * @param geostate
	`* @parim
	 * @return
	 * 
	 * this function is used to update the status of the ai server datasource
	 */
	
	@Async
	public void sendRequestToUpdateStatus(JobForDataSource jobfordatasource, GeoStates geostate )
	{
		jobfordatasource = getJobForDatasourceById(jobfordatasource.getDatasourceid());
		jobfordatasource.setJobStatus(geostate);
		boolean status = requestOutSideofService.sendRequestToUpdateStatusatDatasource(jobfordatasource, geostate);
		
	}
	
	/**
	 * 
	 * @auther Ghulam Murtaza
	 * @since Oct 18, 2019
	 * @param jobfordatasource
	 * @param geostate
	 * @param totalRecords
	 * @param UpdatedRecords
	 * @param GroupName
	`* @parim
	 * @return
	 * used to update requests
	 */
	
	public void updateDataSourcestatus(JobForDataSource jobfordatasource, GeoStates geostate,long totalRecords, long UpdatedRecords,String GroupName)
	{
		try
		{
			sendRequestToUpdateStatus(jobfordatasource, geostate);
		}catch(Exception e) {e.printStackTrace();}
	}
}
