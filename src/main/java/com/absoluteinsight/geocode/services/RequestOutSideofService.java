/**
 * @auther Ghulam Murtaza
 * @since Oct 4, 2019
 * gmurtaza@aliviaanlytics.com
 * com.absoluteinsight.geocode.services.RequestOutSideofService.java
 * 
 */
package com.absoluteinsight.geocode.services;

import com.absoluteinsight.geocode.data.model.JobForDataSource;
import com.absoluteinsight.geocode.utils.GeoStates;

public interface RequestOutSideofService 
{
	public boolean sendRequestToUpdateStatusatDatasource(JobForDataSource jobfordatasource, GeoStates geostate );
}
