/**
 * @auther Ghulam Murtaza
 * @since Sep 30, 2019
 * gmurtaza@aliviaanlytics.com
 * com.absoluteinsight.geocode.data.repository.DataSourceManager.java
 * OutsiderDataSourceRepository deals with the datasource to get records 
 * from table AI database to upldate the formated address, latitude and logintude.
 */
package com.absoluteinsight.geocode.data.repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.jdbc.core.JdbcTemplate;


import com.absoluteinsight.geocode.data.model.Address;
import com.absoluteinsight.geocode.data.model.DasourceColumn;
import com.absoluteinsight.geocode.data.model.JobForDataSource;

public class OutsiderDataSourceRepository 
{
	private static final Logger logger = LoggerFactory.getLogger(OutsiderDataSourceRepository.class);
	private JobForDataSource datasource; 
	private JdbcTemplate jdbcTemplate;
	private String groupName;
	
	public OutsiderDataSourceRepository(JobForDataSource datasource,JdbcTemplate jdbcTemplate,String groupName)
	{
		this.jdbcTemplate = jdbcTemplate;
		this.datasource = datasource;
		this.groupName = groupName;
	}
	
	public List<Map<String,Object>> getDataFromDataSource(List<DasourceColumn> inputdasourceColumns, List<DasourceColumn> outputdasourceColumns)
	{
		String query = getQueryTOGetData(inputdasourceColumns, outputdasourceColumns);
		logger.info(query);
		try
		{
			return jdbcTemplate.queryForList(query);
		}catch(Exception e)
		{
			logger.info(e.getMessage());
		}
		return null;
	}
	
	public boolean savAndUpdateLatitudeAndLonitude(List<DasourceColumn> inputdasourceColumns, List<DasourceColumn> outputdasourceColumns,Address address, Map<String,Object> mapobject)
	{
		boolean status = true;
		
		String query = getUpdateQuery(inputdasourceColumns,address,mapobject);
		logger.info(query);
		try
		{
			jdbcTemplate.update(query);
		}catch(Exception e) {
			logger.info(e.getMessage());
			status = false;
		}
		
		return status;
	}
	

	
	
	
	public JobForDataSource getDatasource() {
		return datasource;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public String getGroupName() {
		return groupName;
	}

	private String getQueryTOGetData(List<DasourceColumn> inputdasourceColumns, List<DasourceColumn> outputdasourceColumns)
	{
		String columns = inputdasourceColumns.stream().map(col->col.getColumnName()).collect(Collectors.joining(", "));
		String query = "Select "+ columns + " from "+ datasource.getTableName()+" where "+groupName+"_latitude is null or "+groupName+"_longitude is null group by " + columns ;
		
		return query;
	}
	
	
	private String getUpdateQuery(List<DasourceColumn> inputdasourceColumns,Address address,Map<String,Object> mapobject)
	{
		String query = "Update "+ datasource.getTableName() + " set " + groupName + "_latitude="+address.getLatitude()+" , "+ groupName +"_longitude=" + address.getLongitude() + " , "+groupName+"_Address='"+address.getFormatedAddress()+"' where 1=1";
		for(DasourceColumn col :inputdasourceColumns)
		{
			query = getWhereColuses(col,  mapobject, query);
		}
		
		return query;
	}
	

	public String getWhereColuses(DasourceColumn column, Map<String,Object> mapobject, String query)
	{
		
		String type = column.getColumnTypeJava();
	
		switch (type) {
		case "Boolean":
			try
			{
				query +=" and "+column.getColumnName() +" = "+ Boolean.parseBoolean(mapobject.get(column.getColumnName()).toString());
			}catch (Exception e) {}
			break;
		case "int":
			try
			{
				query +="  and  "+column.getColumnName() +" = "+ Integer.parseInt(mapobject.get(column.getColumnName()).toString());
			}catch (Exception e) {}
			break;
		case "Double":
			try
			{
				query +="  and  "+column.getColumnName() +" = "+ Double.parseDouble(mapobject.get(column.getColumnName()).toString());
			}catch (Exception e) {}
			break;
		case "String":
			try
			{
				query +="  and  "+column.getColumnName() +" = '"+ mapobject.get(column.getColumnName()).toString()+"'";
			}catch (Exception e) {}
			break;
		case "UnKnown":
			break;
		default:
			break;
		}

		return query;
	}
	
	
}
