/**
 * @auther Ghulam Murtaza
 * @since Oct 2, 2019
 * gmurtaza@aliviaanlytics.com
 * com.absoluteinsight.geocode.data.repository.AddressrepositoryImp.java
 * 
 */
package com.absoluteinsight.geocode.data.repository;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import javax.transaction.Transactional;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.absoluteinsight.geocode.data.model.Address;
import com.absoluteinsight.geocode.jobs.DataSourceGroupJob;

@Service
public class AddressrepositoryImp {
   
	
	private static final Logger logger = LoggerFactory.getLogger(DataSourceGroupJob.class);
	
	
    private EntityManager em;
	
    @Autowired
	public AddressrepositoryImp(final EntityManagerFactory entityManagerFactory)
	{
		this.em = entityManagerFactory.createEntityManager();
	}
	
	@Transactional
    public List<Address> findByAddressContaining(String address) {
		  Query query = this.em
		      .createNativeQuery(
		            "select * from geocoded_address"
		          
		          //+ "where a.address like  :address  "
		          ,Address.class );

		 // query.setParameter( "address", address );

		  List<Address> addresses = query.getResultList();

		  return addresses;
    	
		}
    
   
    @Transactional
    public List<Address> fuzzySearch(String searchTerm) {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Address.class).get();
       
        org.apache.lucene.search.Query luceneQuery = qb.keyword()
        		.fuzzy()
        		.withEditDistanceUpTo(1)
        		.withPrefixLength(1)
        		.boostedTo(10)
        		.onField("formatedAddress")
                .matching(searchTerm)
                .createQuery();
        
//        		.fuzzy()
//        		.withEditDistanceUpTo(1)
//        		.withPrefixLength(10)
//        		.boostedTo(15)
//        		.onField("formatedAddress")
//        		//.andField("city")
//        		//.andField("state")
//        		//.andField("country")
//                .matching(searchTerm)
//                .createQuery();

        
        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Address.class);
        jpaQuery.setMaxResults(5);
       
        
        List<Address> addressList = null;
        try {
        	addressList = jpaQuery.getResultList();
        	 logger.info("Search results"+addressList.size());
        } catch (NoResultException nre) {
            logger.info(nre.getMessage());

        }

        return addressList;


    }
   
    @Transactional
    public Address fuzzyAddressSearch(String searchkeyword) {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Address.class).get();
       
        org.apache.lucene.search.Query luceneQuery = qb.keyword()
        		.fuzzy()
        		.withEditDistanceUpTo(1)
        		.withPrefixLength(5)
        		.boostedTo(150)
        		.onField("formatedAddress")
                .matching(searchkeyword)
                .createQuery();

        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Address.class);
        Address address = null;
        try {
        	
        	address =  (Address) jpaQuery.getSingleResult();
        	 logger.info("Search results"+address);
        } catch (NoResultException nre) {
            logger.info(nre.getMessage());

        }

        return address;


    }
    
    @PostConstruct
    public void initializeHibernateSearch()
    {
    	try
    	{
		    //FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
		    //fullTextEntityManager.createIndexer().startAndWait();
		    logger.info("Indexing completed");
    	}
    	catch(Exception e) {
    		logger.info(e.getMessage());
    		e.printStackTrace();
    		
    	}
    }

}
