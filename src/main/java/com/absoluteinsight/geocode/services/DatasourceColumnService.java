package com.absoluteinsight.geocode.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.absoluteinsight.geocode.data.model.DasourceColumn;
import com.absoluteinsight.geocode.data.repository.DataSourceColumnsRepository;

@Transactional
@Service
public class DatasourceColumnService {
	
	@Autowired
	private DataSourceColumnsRepository  dataSourceColumnsRepository;
	
	
	public boolean saveAllDatasourceColumn(List<DasourceColumn> dasourceColumns)
	{
		
		dataSourceColumnsRepository.saveAll(dasourceColumns);
		
		return true;
	}
	
	
	public List<DasourceColumn> getColumnsByDatasourceId(String datasourceId)
	{
		return dataSourceColumnsRepository.getColumnsByDatasourceId(datasourceId);
	}
}
