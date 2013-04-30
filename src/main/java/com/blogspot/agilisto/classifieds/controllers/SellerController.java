package com.blogspot.agilisto.classifieds.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.blogspot.agilisto.classifieds.model.SellerCredential;
import com.blogspot.agilisto.classifieds.model.SellerIdentity;
import com.blogspot.agilisto.classifieds.services.SellerCredentialService;
import com.blogspot.agilisto.classifieds.services.SellerIdentityService;

@Controller
public class SellerController {

	@Autowired
	SellerIdentityService sellerIdentityService;
	
	@Autowired
	SellerCredentialService sellerCredentialService;
	
	@ResponseBody
	@RequestMapping(value = "/checkUserExists", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public boolean doesUsernameExist(@RequestParam("username")String username)
	{
		return sellerCredentialService.doesUsernameExist(username);
	}
	
	@ResponseBody
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public String createNewListing(@RequestParam("username")String username, @RequestParam("password")String password, 
			@RequestParam("email")String email, @RequestParam("firstName")String firstName,
			@RequestParam("lastName")String lastName, @RequestParam("street")String street,
			@RequestParam("suburb")String suburb, @RequestParam("state")String state, 
			@RequestParam("postcode")String postcode, @RequestParam("country")String country, 
			@RequestParam("latitude")String latitude, @RequestParam("longitude")String longitude,
			@RequestParam("phoneNumber")String phoneNumber) {
		
		inputValidation(username, password, email, firstName, lastName);
		
		SellerCredential sellerCredential = new SellerCredential(username, password);
		sellerCredentialService.save(sellerCredential);
		
		double[] location = {Double.valueOf(latitude).doubleValue(), Double.valueOf(longitude).doubleValue()};
		
		SellerIdentity sellerIdentity = new SellerIdentity(username, email, firstName, lastName, street, suburb, state, postcode, country, location, phoneNumber);
		return sellerIdentityService.save(sellerIdentity);		
	}

	private void inputValidation(String username, String password, String email, String firstName, String lastName) {
		if(username == null || password == null || email == null || firstName == null || lastName == null)
		{
			throw new ClassifiedsBadRequestException("Empty fields not allowed");
		}
		
		if(username.contains(password) || password.contains(password) )
		{
			throw new ClassifiedsBadRequestException("username / password must be different");
		}
		
		if(username.length() <= 5)
		{
			throw new ClassifiedsBadRequestException("username must be greater than 5 characters");
		}
		
		if(password.length() <= 5 && password.matches("")) //TODO add regex
		{
			throw new ClassifiedsBadRequestException("password must be greater than 5 characters and contain a number as well as a character");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public SellerIdentity getNewListing(@RequestParam("username")String username) {
		return sellerIdentityService.getSellerIdentity(username);
	}
}
