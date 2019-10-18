/**
 * @auther Ghulam Murtaza
 * @since Sep 26, 2019
 * gmurtaza@aliviaanlytics.com
 * com.absoluteinsight.geocode.data.repository.JdbcTempleteEngin.java
 * 
 */
package com.absoluteinsight.geocode.data.repository;

import org.springframework.jdbc.core.JdbcTemplate;

import com.absoluteinsight.geocode.dao.JdbcWithJDbcTemplet;
import com.absoluteinsight.geocode.data.model.JobForDataSource;

public interface JdbcTempleteEngin 
{
	public JdbcTemplate getJdbcTemplate(JobForDataSource datasource);
	
	public boolean isDataBaseConnected(JobForDataSource datasource);
	
}
