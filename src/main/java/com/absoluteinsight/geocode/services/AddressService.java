/**
 * @auther Ghulam Murtaza
 * @since Oct 8, 2019
 * gmurtaza@aliviaanlytics.com
 * com.absoluteinsight.geocode.services.AddressService.java
 * 
 */
package com.absoluteinsight.geocode.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.absoluteinsight.geocode.data.model.Address;
import com.absoluteinsight.geocode.data.repository.AddressRepository;
import com.absoluteinsight.geocode.data.repository.AddressrepositoryImp;

@Service
public class AddressService {
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private AddressrepositoryImp addressrepositoryImp;
	
	public void saveAddress(Address address)
	{
		addressRepository.save(address);
		addressrepositoryImp.initializeHibernateSearch();
	}
	
	
	public Address searchAddress(Address address)
	{
		try
		{
			List<Address> addresses = addressRepository.searchAddress(address.getAddress(),address.getCity(),address.getState(),address.getCountry(),address.getZipcode());
			if(addresses!=null && addresses.size()>0)
				return addresses.get(0);
		else 
			return null;
		}catch(Exception e) {return null;}
	}
	
	public List<Address> getSearchedAddress(String keyword)
	{
		
		return addressrepositoryImp.fuzzySearch(keyword);
		
	}
	
	public Address getFuzzyAddress(String keyword)
	{
		List<Address> fuzzyaddresses = addressrepositoryImp.fuzzySearch(keyword);
		if(fuzzyaddresses!=null&&fuzzyaddresses.size()>0)
			return fuzzyaddresses.get(0);
		
		return null;
	}
}

