/**
 * @auther Ghulam Murtaza
 * @since Oct 16, 2019
 * gmurtaza@aliviaanlytics.com
 * com.absoluteinsight.geocode.controller.WebController.java
 * this controller is used to send request at web pages
 * 
 */
package com.absoluteinsight.geocode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {
	
	
	@GetMapping("/")
	public String athome()
	{
		return "home";
	}

}
