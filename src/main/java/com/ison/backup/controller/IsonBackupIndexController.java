package com.ison.backup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * IsonBackupIndex Controller class
 * Jeyaprakash Ganesan
 * 
 */
@Controller
@RequestMapping("/")
public class IsonBackupIndexController {

	  @RequestMapping(method = RequestMethod.GET)
	    public String getIndexPage() {
	        return "Login";
	    }

}