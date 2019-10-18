package com.absoluteinsight.geocode.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.absoluteinsight.geocode.data.model.JobForDataSource;

/**
 * @author Ghulam Murtaza
 * @dated Sep 26, 2019
 * gmurtaza@aliviaanlytics.com
 * com.absoluteinsight.geocode.dao.DirectJdbcWithJDbcTemplets.java
 * DirectJdbcWithJDbcTemplets is used to connect and manipulate the data
 * source in different datasources.
 *  
 */

public class JdbcWithJDbcTemplet extends JdbcTemplate{

	@Autowired
	Environment environment;
	
	private String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	
	/**
	 * @author Ghulam Murtaza
	 * @dated Sep 26, 2019
	 * gmurtaza@aliviaanlytics.com
	 * DirectJdbcWithJDbcTemplets constructor is used to create the JdbcTemplate with datasource
	 * @param datasource 
	 */
	public JdbcWithJDbcTemplet(JobForDataSource datasource)
	{
		setDataSource(getDataSourceBysource(datasource));
	}
	
	/**
	 * @author Ghulam Murtaza
	 * @since Sep 26, 2019
	 * gmurtaza@aliviaanlytics.com
	 * DirectJdbcWithJDbcTemplets constructor is used to create the JdbcTemplate with datasource
	 * @param datasource
	 * @return DriverManagerDataSource  
	 */
	
	public DriverManagerDataSource getDataSourceBysource(JobForDataSource datasource)
	{
		//driverName = environment.getProperty("spring.datasource.driver-class-name");
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(driverName);
		ds.setUrl(datasource.getConnectionUrl());
		ds.setUsername("");
		ds.setPassword("");
		
		return ds;
	}
}
