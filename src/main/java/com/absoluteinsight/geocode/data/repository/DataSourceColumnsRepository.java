package com.absoluteinsight.geocode.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.absoluteinsight.geocode.data.model.DasourceColumn;


public interface DataSourceColumnsRepository  extends JpaRepository<DasourceColumn, Long>  {
	
	public List<DasourceColumn> getColumnsByDatasourceId(String datasourceId);

}
