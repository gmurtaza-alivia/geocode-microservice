/**
 * @auther Ghulam Murtaza
 * @since Oct 16, 2019
 * gmurtaza@aliviaanlytics.com
 * com.absoluteinsight.geocode.data.model.GeoCodeJob.java
 * GeoCodeJob is used to save the datasource request running at the geocode service.
 */
package com.absoluteinsight.geocode.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.absoluteinsight.geocode.utils.GeoStates;
import com.absoluteinsight.geocode.utils.JobType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="geocode_jobs")
@JsonIgnoreProperties(ignoreUnknown = true)

public class GeoCodeJob implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4123748262085649434L;
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private JobForDataSource jobForDataSource;
	
	private String jobname;
	
	private String groupname;
	
	

	@CreationTimestamp
	private Date jobcreationDate;
	
	@UpdateTimestamp
	private Date jobStartedDate;
	
	private long totalrecords;
	private long totalUpdatedRecords;
	private long totalGeocoded;
	private long totalLocalProcessedRecords;
	private long unprocessed;
	
	private GeoStates jobStatus;
	
	private JobType jobType;

	public long getId() {
		return id;
	}

	public JobForDataSource getJobForDataSource() {
		return jobForDataSource;
	}

	public String getJobname() {
		return jobname;
	}

	public Date getJobcreationDate() {
		return jobcreationDate;
	}

	public Date getJobStartedDate() {
		return jobStartedDate;
	}

	public long getTotalrecords() {
		return totalrecords;
	}

	public long getTotalUpdatedRecords() {
		return totalUpdatedRecords;
	}

	public long getTotalGeocoded() {
		return totalGeocoded;
	}

	public long getTotalLocalProcessedRecords() {
		return totalLocalProcessedRecords;
	}

	public long getUnprocessed() {
		return unprocessed;
	}

	public GeoStates getJobStatus() {
		return jobStatus;
	}

	public JobType getJobType() {
		return jobType;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setJobForDataSource(JobForDataSource jobForDataSource) {
		this.jobForDataSource = jobForDataSource;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public void setJobcreationDate(Date jobcreationDate) {
		this.jobcreationDate = jobcreationDate;
	}

	public void setJobStartedDate(Date jobStartedDate) {
		this.jobStartedDate = jobStartedDate;
	}

	public void setTotalrecords(long totalrecords) {
		this.totalrecords = totalrecords;
	}

	public void setTotalUpdatedRecords(long totalUpdatedRecords) {
		this.totalUpdatedRecords = totalUpdatedRecords;
	}

	public void setTotalGeocoded(long totalGeocoded) {
		this.totalGeocoded = totalGeocoded;
	}

	public void setTotalLocalProcessedRecords(long totalLocalProcessedRecords) {
		this.totalLocalProcessedRecords = totalLocalProcessedRecords;
	}

	public void setUnprocessed(long unprocessed) {
		this.unprocessed = unprocessed;
	}

	public void setJobStatus(GeoStates jobStatus) {
		this.jobStatus = jobStatus;
	}

	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}
	
	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}


}
