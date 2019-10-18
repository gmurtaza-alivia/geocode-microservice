package com.absoluteinsight.geocode.data.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.absoluteinsight.geocode.utils.GeoStates;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Ghulam Murtaza
 * 
 * July 24th 2019
 * gmurtaza@aliviaanalytics.com
 * 
 * DataSource class is used to save and update datasource for updateing the lat lon in the table. 
 * 
 */
@Entity
@Table(name="geo_jobForDataSource")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobForDataSource implements Serializable
{

	private static final long serialVersionUID = -8632463680872096454L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "job_id", unique = true, nullable = false)
	//@JsonIgnore
	private Long jodid;
	
	@Column(name = "datasourceid", unique = true, nullable = false, length = 36)
	private String datasourceid;
	
	
	private String datasourceName;
	//@JsonIgnore
	private String connectionUrl;
	//@JsonIgnore
	private String userName;
	//@JsonIgnore
	private String password;
	//@JsonIgnore
	private Boolean connectionStatus;
		
	private String tableName;
	
	@CreationTimestamp
	private Date jobcreationDate;
	
	@UpdateTimestamp
	private Date jobStartedDate;
	
	private GeoStates jobStatus;
	//@JsonIgnore
	private String requestedUrl;
	
	

	 @OneToMany(
		        mappedBy = "jobForDataSource",
		        cascade = CascadeType.ALL,
		       // orphanRemoval = true,
		        fetch = FetchType.LAZY
		    )
	private List<GeoCodeJob> geoCodeJobs;
	
	
	public String getRequestedUrl() {
		return requestedUrl;
	}



	public void setRequestedUrl(String requestedUrl) {
		this.requestedUrl = requestedUrl;
	}



	public Long getJodid() {
		return jodid;
	}



	public String getDatasourceName() {
		return datasourceName;
	}

	public String getConnectionUrl() {
		return connectionUrl;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public Boolean getConnectionStatus() {
		return connectionStatus;
	}

	public String getTableName() {
		return tableName;
	}

	public Date getJobcreationDate() {
		return jobcreationDate;
	}

	public Date getJobStartedDate() {
		return jobStartedDate;
	}

	public GeoStates getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(GeoStates jobStatus) {
		this.jobStatus = jobStatus;
	}

	public void setJodid(Long jodid) {
		this.jodid = jodid;
	}


	public String getDatasourceid() {
		return datasourceid;
	}



	public void setDatasourceid(String datasourceid) {
		this.datasourceid = datasourceid;
	}



	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}

	public void setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setConnectionStatus(Boolean connectionStatus) {
		this.connectionStatus = connectionStatus;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setJobcreationDate(Date jobcreationDate) {
		this.jobcreationDate = jobcreationDate;
	}

	public void setJobStartedDate(Date jobStartedDate) {
		this.jobStartedDate = jobStartedDate;
	}



	public List<GeoCodeJob> getGeoCodeJobs() {
		return geoCodeJobs;
	}



	public void setGeoCodeJobs(List<GeoCodeJob> geoCodeJobs) {
		this.geoCodeJobs = geoCodeJobs;
	}

}
