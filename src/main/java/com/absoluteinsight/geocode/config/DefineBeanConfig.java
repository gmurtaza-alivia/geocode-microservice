package com.absoluteinsight.geocode.config;
/**
 * @author Ghulam Murtaza 
 * 
 * dated 23/09/2019
 * 
 * RestTemplateConfig for configring the RestTemplate
 * 
 */
 

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import com.absoluteinsight.geocode.jobs.DataSourceGroupJob;
import com.absoluteinsight.geocode.utils.GeoConstants;

@Configuration
public class DefineBeanConfig {
	
	@Bean
	@Scope("prototype")
	public RestTemplate restTemplate() {
	    SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

	    Proxy proxy= new Proxy(Type.HTTP, new InetSocketAddress("vip-px.main.aviva.eu.corp", 8080));
	    requestFactory.setProxy(proxy);
	    //return new RestTemplate(requestFactory);
	   return new RestTemplate();
	}
    
	@Bean
	@Scope("singleton")
    public TaskExecutor threadPoolTaskExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(GeoConstants.NOOFTHREADEXICUTEINTHREADPOOL);

        executor.setMaxPoolSize(GeoConstants.NOOFTHREADEXICUTEINTHREADPOOL);

        executor.setThreadNamePrefix(GeoConstants.THREAD_POOL_NAME);

        executor.initialize();
       
        return executor;

    }
	
	@Bean("currantrunningJobs")
	@Scope("singleton")
    public Map<String,DataSourceGroupJob> getMap() {
    	
        return new HashMap<String, DataSourceGroupJob>();

    }
	
}
