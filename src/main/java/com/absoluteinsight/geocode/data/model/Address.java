/**
 * @auther Ghulam Murtaza
 * @since Oct 1, 2019
 * gmurtaza@aliviaanlytics.com
 * com.absoluteinsight.geocode.data.model.Address.java
 * Address class is used to save and get geocoded address and it will also use to contain the datasource columns data.
 */
package com.absoluteinsight.geocode.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenizerDef;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Indexed
@Entity
@Table(name="geocoded_address")
//@AnalyzerDef(name = "customanalyzer",
//tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
//filters = {
//  @TokenFilterDef(factory = LowerCaseFilterFactory.class),
//  @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
//    @Parameter(name = "language", value = "English")
//  })
//})

public class Address implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5461708504957922743L;
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	
	private String address = "";
	
	@Field
	private String formatedAddress="";
	
	@Field
	private String city="";
	
	@Field
	private String state="";
	
	private String phonenumber="";
	
	
	private String zipcode;
	

	
	private double latitude;
	
	private double longitude;
	
	@Field
	private String country = "";

	public Long getId() {
		return id;
	}

	public String getAddress() {
		return address;
	}

	public String getFormatedAddress() {
		return formatedAddress;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public String getZipcode() {
		return zipcode;
	}

	public String getCountry() {
		return country;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAddress(String address) {
		this.address += address;
	}

	public void setFormatedAddress(String formatedAddress) {
		this.formatedAddress = formatedAddress;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Transient
	
	public String getAddressToGeoCode()
	{
		String addresstogeocode = address +" "+city+" "+state+" "+ zipcode +" "+country;
		
		return addresstogeocode.trim();
	}
	
	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
