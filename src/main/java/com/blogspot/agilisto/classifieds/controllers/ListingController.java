package com.blogspot.agilisto.classifieds.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.blogspot.agilisto.classifieds.model.Category;
import com.blogspot.agilisto.classifieds.model.Listing;
import com.blogspot.agilisto.classifieds.model.SellerIdentity;
import com.blogspot.agilisto.classifieds.services.CategoryService;
import com.blogspot.agilisto.classifieds.services.ListingService;
import com.blogspot.agilisto.classifieds.services.SellerIdentityService;

@Controller
public class ListingController {

	@Autowired
	ListingService listingService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	SellerIdentityService sellerIdentityService;
	
	@ResponseBody
	@RequestMapping(value = "/listing", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public String createNewListing(@RequestParam("title")String title, @RequestParam("description")String description, 
			@RequestParam("price")String priceAsString, @RequestParam("categoryId")String categoryId,
			@RequestParam("username")String username, @RequestParam("listingTime")String listingTime,
			@RequestParam("expiryTime")String expiryTime) {	
		
		Category category = categoryService.getCategory(categoryId);
		
		if(category == null)
		{
			throw new ClassifiedsBadRequestException("Category cannot be found: " + categoryId);
		}
		
		SellerIdentity sellerIdentity = sellerIdentityService.getSellerIdentity(username);
		
		if(sellerIdentity == null)
		{
			throw new ClassifiedsBadRequestException("SellerIdentity cannot be found: " + username);
		}
	
		Double price = new Double(priceAsString);
		
		Listing listing = new Listing(title, description, price, category.getId(), sellerIdentity.getId(), Long.valueOf(listingTime), Long.valueOf(expiryTime));
		return listingService.save(listing);
	}
	
	@ResponseBody
	@RequestMapping(value = "/listings", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<Listing> getListings(@RequestParam("queryKey")String queryKey, @RequestParam("queryValue")String queryValue) {
		
		return listingService.getListings(queryKey, queryValue);
	}
	
	@ResponseBody
	@RequestMapping(value = "/listing", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Listing getListing(@RequestParam("id")String id) {
		
		return listingService.getListing(id);
	}
	
	@ResponseBody
	@RequestMapping(value = "/listing", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteListing(@RequestParam("id")String id) {
		
		listingService.deleteListing(id);
	}
	
	@ResponseBody
	@RequestMapping(value = "/listing", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void updateListing(@RequestParam("id")String id, @RequestParam("updateKey")String updateKey, @RequestParam("updateValue")String updateValue) {
		
		listingService.updateListing(id, updateKey, updateValue);
	}
}
