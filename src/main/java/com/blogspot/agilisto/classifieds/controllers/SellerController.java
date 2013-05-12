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
import com.blogspot.agilisto.classifieds.services.ListingService;
import com.blogspot.agilisto.classifieds.services.SellerCredentialService;
import com.blogspot.agilisto.classifieds.services.SellerIdentityService;

@Controller
public class SellerController {

	@Autowired
	SellerIdentityService sellerIdentityService;
	
	@Autowired
	SellerCredentialService sellerCredentialService;
	
	@Autowired
	ListingService listingService;
	
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
	public String createNewSeller(@RequestParam("username")String username, @RequestParam("password")String password, 
			@RequestParam("email")String email, @RequestParam("firstName")String firstName,
			@RequestParam("lastName")String lastName, @RequestParam("street")String street,
			@RequestParam("suburb")String suburb, @RequestParam("state")String state, 
			@RequestParam("postcode")String postcode, @RequestParam("country")String country, 
			@RequestParam("latitude")String latitude, @RequestParam("longitude")String longitude,
			@RequestParam("phoneNumber")String phoneNumber) {
		
		inputValidation(username, password, email, firstName, lastName);
		
		SellerCredential sellerCredential = new SellerCredential(username, password);
		sellerCredentialService.save(sellerCredential);
		
		double[] location = null;
		
		if(latitude != "" && longitude != "")
		{
			location = new double[]{Double.valueOf(latitude).doubleValue(), Double.valueOf(longitude).doubleValue()};
		}
		
		SellerIdentity sellerIdentity = new SellerIdentity(username, email, firstName, lastName, street, suburb, state, postcode, country, location, phoneNumber);
		return sellerIdentityService.save(sellerIdentity);		
	}

	private void inputValidation(String username, String password, String email, String firstName, String lastName) {
		if(username == "" || password == "" || email == "" || firstName == "" || lastName == "")
		{
			throw new ClassifiedsBadRequestException("Empty fields not allowed");
		}
		
		if(username.contains(password) || password.contains(username) )
		{
			throw new ClassifiedsBadRequestException("username / password must be different");
		}
		
		if(username.length() <= 5 || password.length() <= 5)
		{
			throw new ClassifiedsBadRequestException("username/password must be greater than 5 characters");
		}
		
		if(sellerCredentialService.doesUsernameExist(username))
		{
			throw new ClassifiedsBadRequestException("username: " + username + " already exists");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public SellerIdentity getSeller(@RequestParam("username")String username) {
		return sellerIdentityService.getSellerIdentity(username);
	}
	
	@ResponseBody
	@RequestMapping(value = "/user", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteSeller(@RequestParam("username")String username) {
		SellerIdentity sellerIdentity = sellerIdentityService.getSellerIdentity(username);
		
		if(sellerIdentity == null)
		{
			throw new ClassifiedsBadRequestException("username: " + username + " does not exist");
		}
		
		listingService.deleteListings("sellerIdentityForiegnKey", sellerIdentity.getId());
		sellerIdentityService.deleteSellerIdentity(username);
		sellerCredentialService.deleteSellerCredential(username);
	}
	
	@ResponseBody
	@RequestMapping(value = "/user", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void updateSeller(@RequestParam("username")String username, @RequestParam("userKey")String userKey, @RequestParam("userValue")String userValue) {
		if(userKey == "username" || userKey == "password")
		{
			sellerCredentialService.updateSellerCredential(username, userKey, userValue);
		}
		
		if(userKey != "password")
		{
			sellerIdentityService.updateSellerIdentity(username, userKey, userValue);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/validateUsernamePassword", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public boolean validateUsernamePassword(@RequestParam("username")String username, @RequestParam("password")String password)
	{
		return sellerCredentialService.validateUsernamePassword(username, password);
	}
}
