/**
 * @auther Ghulam Murtaza
 * @since Sep 30, 2019
 * gmurtaza@aliviaanlytics.com
 * com.absoluteinsight.geocode.jobs.DataSourceGroupJob.java
 * DataSourceGroupJob is used to start thread for each address group.
 * Each group have its own formated address, latitude and lonigtude
 * columns this class find the columns and also fill there values
 */
package com.absoluteinsight.geocode.jobs;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.absoluteinsight.geocode.data.model.Address;
import com.absoluteinsight.geocode.data.model.DasourceColumn;
import com.absoluteinsight.geocode.data.model.GeoCodeJob;
import com.absoluteinsight.geocode.data.model.JobForDataSource;
import com.absoluteinsight.geocode.data.repository.OutsiderDataSourceRepository;
import com.absoluteinsight.geocode.services.AddressService;
import com.absoluteinsight.geocode.services.DataSourceService;
import com.absoluteinsight.geocode.services.GeoCodeJobeService;
import com.absoluteinsight.geocode.services.GeocoderService;
import com.absoluteinsight.geocode.services.GeocoderServiceImpl;
import com.absoluteinsight.geocode.utils.GeoStates;
import com.absoluteinsight.geocode.utils.JobType;

public class DataSourceGroupJob extends Thread {
	private static final Logger logger = LoggerFactory.getLogger(DataSourceGroupJob.class);
	
	private List<DasourceColumn> dasourceColumns;
	private JobForDataSource datasource; 
	private JdbcTemplate jdbcTemplate;
	private String groupName;
	private OutsiderDataSourceRepository odtr;
	
	private boolean interpted=false;
	
	private List<DasourceColumn> inputdasourceColumns=null;
	private List<DasourceColumn> outputdasourceColumns=null;
	
	
	private Map<String,DataSourceGroupJob> currantrunningJobs;
	private AddressService addressservice;
	private GeocoderService geocoderService;
	private DataSourceService dataSourceService;
    private GeoCodeJobeService geoCodeJobeService;
    private GeoCodeJob geoCodeJob;
	
	private long totalRecordsUpdated = 0;
	private long totalRecord=0;
	private long totalgeocodedRecords=0;
	private long totallocalprocsssedRecords=0;
	
	
	public DataSourceGroupJob(List<DasourceColumn> dasourceColumns, OutsiderDataSourceRepository odtr, GeoCodeJobeService  geoCodeJobeService,JobType jobtype)
	{
		this.datasource = odtr.getDatasource();
		this.dasourceColumns = dasourceColumns;
		this.groupName = odtr.getGroupName();
		
		this.odtr = odtr;
		this.geoCodeJobeService = geoCodeJobeService;
		
		this.geoCodeJob = geoCodeJobeService.createAndsaveNewJob(datasource, groupName,jobtype);
	
	}
		
	public void run() 
	{
		process();
	}
	
	private void process()
	{
		if(!interpted)
		{
			logger.info("Start processing " + groupName+ ' ' + datasource.getDatasourceName());
			this.inputdasourceColumns =  dasourceColumns.stream().filter(col->!col.getColumnName().equalsIgnoreCase(groupName+"_address")&&!col.getColumnName().equalsIgnoreCase(groupName+"_longitude") && !col.getColumnName().equalsIgnoreCase(groupName+"_latitude")).collect(Collectors.toList());
			this.outputdasourceColumns =  dasourceColumns.stream().filter(col->col.getColumnName().equalsIgnoreCase(groupName+"_address")||col.getColumnName().equalsIgnoreCase(groupName+"_longitude") || col.getColumnName().equalsIgnoreCase(groupName+"_latitude")).collect(Collectors.toList());
			logger.info("inputcolumn size is " + groupName+ ' ' + inputdasourceColumns.size());
			logger.info("output colun size is " + groupName+ ' ' + outputdasourceColumns.size());
			if(this.inputdasourceColumns.size()>0 && this.outputdasourceColumns.size()>0)
			{
				List<Map<String, Object>> data = odtr.getDataFromDataSource(inputdasourceColumns, outputdasourceColumns);
				updateGeocodeStatus(GeoStates.InProgress);
				totalRecord = data.size();
				logger.info("totlal row counts of the datasource table "+datasource.getTableName()+ " " + data.size());
				if(data!=null && !interpted)
				{
					for(Map<String, Object> mapobject: data)
					{
						if(!interpted)
						{   
							try
							{
							Address address  = new Address();
							for(DasourceColumn inputcolumn: inputdasourceColumns)
							{
								if(inputcolumn.getGeographicType().equalsIgnoreCase("address") || inputcolumn.getGeographicType().equalsIgnoreCase("street"))
								{
									address.setAddress(mapobject.get(inputcolumn.getColumnName()).toString());
								}
								else
								if(inputcolumn.getGeographicType().equalsIgnoreCase("city"))
								{
									address.setCity(mapobject.get(inputcolumn.getColumnName()).toString());
								}
								else
								if(inputcolumn.getGeographicType().equalsIgnoreCase("state"))
								{
									address.setState(mapobject.get(inputcolumn.getColumnName()).toString());
								}
								else
								if(inputcolumn.getGeographicType().equalsIgnoreCase("zip"))
								{
									try
									{
										address.setZipcode(mapobject.get(inputcolumn.getColumnName()).toString());
									}catch(Exception e) {}
								}
								else
								if(inputcolumn.getGeographicType().equalsIgnoreCase("country"))
								{
									address.setCountry(mapobject.get(inputcolumn.getColumnName()).toString());
								}
								else
								{
									address.setAddress(mapobject.get(inputcolumn.getColumnName()).toString());
								}
							
							}
							
							if(address.getAddressToGeoCode()!=null && !address.getAddressToGeoCode().isEmpty())
							{
								Address validaddress = addressservice.getFuzzyAddress(address.getAddressToGeoCode());
								boolean isAddresFoundinside = false;
								
								if(validaddress!=null)
								{
									updateLatAndLon(validaddress,mapobject);
									totalRecordsUpdated++;
									totalgeocodedRecords=0;
									totallocalprocsssedRecords++;
									isAddresFoundinside = true;
								}
								
								if(!isAddresFoundinside)
								{
									boolean isaddres = geocoderService.getAddressInformation(address);
									if(isaddres)
									{
										totalRecordsUpdated++;
										totalgeocodedRecords++;
										logger.info("addressfound"+address.getFormatedAddress());
										updateLatAndLon(address,mapobject);
										addressservice.saveAddress(address);
									}
									else
									{
										logger.info("address not found in google check");
									}
								}
							}
						 }catch(Exception e) 
							{e.printStackTrace();
		
							}
						
						}else
						{
							logger.info("thread interepted");
							interepted();
							break;
							
						}
						
						
					}
					
					postprocessing();
					logger.info("complete process");
					
				}
				else
				{
					logger.info("data null or thread interepted");
					interepted();
				}
				
			}
			else
			{
				logger.info("input columns or output columns are not found");
				interepted();
			}
				
		
		}
		else 
		{
			interepted();
			logger.info("interpted");
		}
	}
	
	private void postprocessing()
	{
		logger.info(datasource.getDatasourceName()+" "+groupName+"completed: total record geocoded "+totalRecordsUpdated +"/"+totalRecord );		
		removefromMap();
		updateGeocodeStatus(GeoStates.Completed);
		
		
	}

	private void interepted()
	{
		updateGeocodeStatus(GeoStates.Intrupted);
		logger.info(datasource.getDatasourceName()+" "+groupName+" Are intrupted and geocoded these records"+totalRecordsUpdated +"/"+totalRecord );		
		removefromMap();
	}
	
	public JobForDataSource getDatasource() {
		return datasource;
	}
	private void removefromMap()
	{
		try
		{
		currantrunningJobs.remove(datasource.getDatasourceid()+groupName);
		}catch(Exception e) {}
	}
	
	public String getGroupName() {
		return groupName;
	}

	@Override
	public void interrupt()
	{
		interpted = true;
		removefromMap();
		super.interrupt();
		//interepted();
		
	}
	
	@Override
	public void finalize()
	{
				
	}

	public Map<String, DataSourceGroupJob> getCurrantrunningJobs() {
		return currantrunningJobs;
	}

	public AddressService getAddressservice() {
		return addressservice;
	}

	public GeocoderService getGeocoderService() {
		return geocoderService;
	}

	public void setCurrantrunningJobs(Map<String, DataSourceGroupJob> curratrunninJobs) {
		this.currantrunningJobs = currantrunningJobs;
	}

	public void setAddressservice(AddressService addressservice) {
		this.addressservice = addressservice;
	}

	public void setGeocoderService(GeocoderService geocoderService) {
		this.geocoderService = geocoderService;
	}
	public DataSourceService getDataSourceService() {
		return dataSourceService;
	}

	public void setDataSourceService(DataSourceService dataSourceService) {
		this.dataSourceService = dataSourceService;
	}
	
	private void  updateGeocodeStatus(GeoStates jobStatus)
	{
		geoCodeJob.setJobStatus(jobStatus);
		geoCodeJob.setTotalGeocoded(totalRecord);
		geoCodeJob.setTotalUpdatedRecords(totalRecordsUpdated);
		geoCodeJob.setTotalGeocoded(totalgeocodedRecords);
		geoCodeJob.setTotalLocalProcessedRecords(totallocalprocsssedRecords);
		geoCodeJob.setUnprocessed((totalRecord-totalRecordsUpdated));
		geoCodeJobeService.save(geoCodeJob);
		datasource.setJobStatus(jobStatus);
		dataSourceService.save(datasource);
		dataSourceService.updateDataSourcestatus(datasource, GeoStates.Completed,totalRecord, totalRecordsUpdated,groupName);
	}
	
	
	private void updateLatAndLon(Address address,Map<String, Object> mapobject)
	{
		odtr.savAndUpdateLatitudeAndLonitude(inputdasourceColumns,outputdasourceColumns,address,mapobject);
	}
	
	
}
