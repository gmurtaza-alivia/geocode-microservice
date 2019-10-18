package com.absoluteinsight.geocode.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @auther Ghulam Murtaza
 * @date july 24th 2019
 * gmurtaza@aliviaanlytics.com
 *
 */

@Entity
@Table(name="geo_dasourceColumns")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DasourceColumn implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6272925311381478168L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "datasourceid", nullable = false, length = 36)
	private String datasourceId;
		
	@Column(name = "columnId", unique = true, nullable = false, length = 36)
	private String columnId;
	
	private String columnName;
	
	private String columnAlias;
	
	private String columnDescription;
	
	private String columnTypeGeneral;
	
	private String columnTypeJava;
	
	private String columnTypeSql;
	
	@Column(name = "is_address", nullable = false, columnDefinition = "bit default 0")
	private boolean address;
	
	private String addressGroupName;
	
	private String geographicType;

	public String getDatasourceId() {
		return datasourceId;
	}

	public void setDatasourceId(String datasourceId) {
		this.datasourceId = datasourceId;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnAlias() {
		return columnAlias;
	}

	public void setColumnAlias(String columnAlias) {
		this.columnAlias = columnAlias;
	}

	public String getColumnDescription() {
		return columnDescription;
	}

	public void setColumnDescription(String columnDescription) {
		this.columnDescription = columnDescription;
	}

	public String getColumnTypeGeneral() {
		return columnTypeGeneral;
	}

	public void setColumnTypeGeneral(String columnTypeGeneral) {
		this.columnTypeGeneral = columnTypeGeneral;
	}

	public String getColumnTypeJava() {
		return columnTypeJava;
	}

	public void setColumnTypeJava(String columnTypeJava) {
		this.columnTypeJava = columnTypeJava;
	}

	public String getColumnTypeSql() {
		return columnTypeSql;
	}

	public void setColumnTypeSql(String columnTypeSql) {
		this.columnTypeSql = columnTypeSql;
	}

	public boolean isAddress() {
		return address;
	}

	public void setAddress(boolean address) {
		this.address = address;
	}

	public String getAddressGroupName() {
		return addressGroupName;
	}

	public void setAddressGroupName(String addressGroupName) {
		this.addressGroupName = addressGroupName;
	}

	public String getGeographicType() {
		return geographicType;
	}

	public void setGeographicType(String geographicType) {
		this.geographicType = geographicType;
	}

}
