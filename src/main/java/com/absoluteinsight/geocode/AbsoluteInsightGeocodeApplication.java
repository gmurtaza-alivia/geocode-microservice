package com.absoluteinsight.geocode;
/**
 * @author Ghulam Murtaza 
 * 
 * dated 23/09/2019
 * 
 * main class the run the geocode microservice.
 * 
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
//@EnableElasticsearchRepositories(basePackages = "com.absoluteinsight.geocode.data.searchrepository")
//@EnableJpaRepositories(basePackages = {"com.absoluteinsight.geocode.data.repository"})
public class AbsoluteInsightGeocodeApplication {
	/**
	 * @param args 
	 * @return void
	 * this main method is used to run the spring boot.
	 * 
	 */
	public static void main(String[] args) {
		SpringApplication.run(AbsoluteInsightGeocodeApplication.class, args);
	}

}
