package com.absoluteinsight.geocode.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.absoluteinsight.geocode.data.repository.AddressrepositoryImp;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//@EnableAutoConfiguration
//@Configuration
public class HibernateSearchConfiguration {

 //   @Autowired
    @PersistenceContext
    private EntityManager em;

//    @Bean
//    AddressrepositoryImp hibernateSearchService() {
//    	AddressrepositoryImp hibernateSearchService = new AddressrepositoryImp(em);
//        hibernateSearchService.initializeHibernateSearch();
//        return hibernateSearchService;
//    }
    
    
}