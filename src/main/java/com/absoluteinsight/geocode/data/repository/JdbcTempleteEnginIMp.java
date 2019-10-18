/**
 * @auther Ghulam Murtaza
 * @since Sep 26, 2019
 * gmurtaza@aliviaanlytics.com
 * com.absoluteinsight.geocode.data.repository.JdbcTempleteEnginIMp.java
 */

package com.absoluteinsight.geocode.data.repository;

import java.sql.Connection;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.absoluteinsight.geocode.dao.JdbcWithJDbcTemplet;
import com.absoluteinsight.geocode.data.model.JobForDataSource;

@Repository
public class JdbcTempleteEnginIMp implements JdbcTempleteEngin{
	/**
	 * @auther Ghulam Murtaza
	 * @since Sep 26, 2019
	 * gmurtaza@aliviaanlytics.com
	 * @param datasource
	 * @return JdbcTemplate
	 * getJdbcTemplate function is used to get the JdbcTemplete 
	 */
	@Override
	public JdbcTemplate getJdbcTemplate(JobForDataSource datasource) {
		
		return new JdbcWithJDbcTemplet(datasource);
	}
	
	/**
	 * @auther Ghulam Murtaza
	 * @since Sep 26, 2019
	 * gmurtaza@aliviaanlytics.com
	 * @param datasource
	 * @return boolean
	 * isDataBaseConnected function is used to test the datasource  
	 */
	
	@Override
	public boolean isDataBaseConnected(JobForDataSource datasource) {
		JdbcTemplate jdbctemplete =  getJdbcTemplate(datasource);
		Connection connection = null;
		boolean isreadonly = true;
		boolean isConnectionWorking =false;
		try
		{
			connection=jdbctemplete.getDataSource().getConnection();
			isreadonly = connection.isReadOnly();
			
			if(!isreadonly)
				isConnectionWorking= true; 
			
			if(isConnectionWorking)
			{
				long count = getTotalRecordsFromDataSource(datasource, jdbctemplete);
				if(count>0)
					isConnectionWorking= true; 
			}
		}catch(Exception e) {
			
		}
		finally
		{
			try
			{
				connection.close();
			}catch(Exception e) {}
		}
		
		return isConnectionWorking;
	}

	private String getQuesryForCountRecordDataSource(JobForDataSource datasource)
	{
		String query ="Select count(*) from " + datasource.getTableName()+"";
		
		return query;
	}

	/**
	 * @auther Ghulam Murtaza
	 * @since Sep 26, 2019
	 * gmurtaza@aliviaanlytics.com
	 * @param datasource
	 * jdbctemplete
	 * @param  @return boolean
	 * getTotalRecordsFromDataSource function is used to get the total records. 
	 */
	
	private long getTotalRecordsFromDataSource(JobForDataSource datasource, JdbcTemplate jdbctemplete) {
		return  jdbctemplete.queryForObject(getQuesryForCountRecordDataSource(datasource),Long.class);
	}
	
	
}
