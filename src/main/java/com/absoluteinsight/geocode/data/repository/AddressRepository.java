/**
 * @auther Ghulam Murtaza
 * @since Oct 1, 2019
 * gmurtaza@aliviaanlytics.com
 * com.absoluteinsight.geocode.data.repository.AddressRepository.java
 * 
 */
package com.absoluteinsight.geocode.data.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.absoluteinsight.geocode.data.model.Address;

public interface  AddressRepository extends JpaRepository<Address, Long> 
{
	//@Query(nativeQuery = true, value = " FROM Address")// WHERE CONTAINS(formatedAddresss, :address, FUZZY(0.7, 'similarCalculationMode=searchCompare, textSearch=compare'))")
	//@Query(value = "select * FROM geocoded_address a WHERE CONTAINS(a.address, :address, FUZZY(0.7, 'similarCalculationMode=searchCompare, textSearch=compare'))", nativeQuery = true)
	//public List<Address> findByFormatedAddress(@Param("address")String address);
	
//	@Query(value = "select a FROM Address a", nativeQuery = false) //WHERE CONTAINS(formatedAddresss, :address, FUZZY(0.7, 'similarCalculationMode=searchCompare, textSearch=compare'))")
//	public List<Address> checkAddress();
	
	//@Query(value = "select a FROM Address a where (a.address like %:address% and a.city = %:city% and a.state = %:state% and a.country like %:country%  and a.zipcode = :zipcode) or (a.phonenumber = :phonenumber) or (a.formatedAddress like %:formatedAddress%)"  , nativeQuery = false)
//	@Query(value = "select a FROM Address a where a.address like ''"  , nativeQuery = false)
//	public  List <Address> searchAddress(@Param("address") String address,String city,String state, String country,long zipcode, String formatedAddress, String phonenumber);
	
	//@Query(value = "select a FROM Address a where (a.address like %:address% and a.city like %:city% and a.state like %:state% and a.country like %:country% and a.zipcode = :zipcode) or (a.phonenumber = :phonenumber) or (a.formatedAddress like %:formatedAddress%)"  , nativeQuery = false)
	//public  List <Address> searchAddress(@Param("address") String address,@Param("city") String city,@Param("state") String state,@Param("country") String country,@Param("zipcode") long zipcode,@Param("phonenumber") String phonenumber,@Param("formatedAddress") String formatedAddress);
	
	@Query(value = "select a FROM Address a where (a.address like %:address% and a.city like %:city% and a.state like %:state% and a.country like %:country% and a.zipcode = :zipcode)"  , nativeQuery = false)
	public  List <Address> searchAddress(@Param("address") String address,@Param("city") String city,@Param("state") String state,@Param("country") String country,@Param("zipcode") String zipcode);
		
}
