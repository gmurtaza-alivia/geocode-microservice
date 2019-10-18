package com.absoluteinsight.geocode.data.repository;

import java.util.List;

/**
 * @auther Ghulam Murtaza
 * july 25th 2019
 * gmurtaza@aliviaanalytics.com
 * 
 * DataSourceRepository is used to manipulate the data source jobs
 * 
 */

import org.springframework.data.jpa.repository.JpaRepository;

import com.absoluteinsight.geocode.data.model.JobForDataSource;

public interface DataSourceRepository extends JpaRepository<JobForDataSource, Long>
{
	/**
	 * @auther Ghulam Murtaza
	 * july 25th 2019
	 * @param datasourceid
	 * @return JobForDataSource
	 * getJobForDataSourceId is used to get the datasource by datasourceid
	 * 
	 */
	
	public JobForDataSource getByDatasourceid(String datasourceId);
	
	/**
	 * @auther Ghulam Murtaza
	 * july 25th 2019
	 * @param String jobStatus
	 * @return List<JobForDataSource>
	 * getJobForStatus is used to get the list of datasource by jobStatus
	 * 
	 */
	
	public List<JobForDataSource> getJobByJobStatus(String jobStatus);
}
