package com.demo.resource.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceServerController {
	
	@RequestMapping(value = "/secured", method = RequestMethod.GET)
	public String securedResource() {
		return "This is Secured Resource";
	}
	
	@RequestMapping(value = "/nonsecured", method = RequestMethod.GET)
	public String nonSecuredResource() {
		return "This is Non Secured Resource";
	}

}
