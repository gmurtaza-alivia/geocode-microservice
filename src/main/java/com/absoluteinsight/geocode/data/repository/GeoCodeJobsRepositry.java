/**
 * @auther Ghulam Murtaza
 * @since Oct 16, 2019
 * gmurtaza@aliviaanlytics.com
 * com.absoluteinsight.geocode.data.repository.GeoCodeJobsRepositry.java
 * 
 */
package com.absoluteinsight.geocode.data.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.absoluteinsight.geocode.data.model.GeoCodeJob;

public interface GeoCodeJobsRepositry extends JpaRepository<GeoCodeJob, Long> {

}
