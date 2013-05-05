package com.blogspot.agilisto.classifieds.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.blogspot.agilisto.classifieds.model.Enquiry;
import com.blogspot.agilisto.classifieds.model.Listing;
import com.blogspot.agilisto.classifieds.services.EnquiryService;
import com.blogspot.agilisto.classifieds.services.ListingService;

@Controller
public class EnquiryController {

	@Autowired 
	EnquiryService enquiryService;
	
	@Autowired
	ListingService listingService;
	
	@ResponseBody
	@RequestMapping(value = "/enquiry", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public String createNewEnquiry(@RequestParam("listingId")String listingId, 
			@RequestParam("email")String email, @RequestParam("comment")String comment)
	{
		Listing listing = listingService.getListing(listingId);
		
		if(listing == null)
		{
			throw new ClassifiedsBadRequestException("Listing with id: " + listingId + " does not exist");
		}
		
		Enquiry enquiry = new Enquiry(listingId, email, comment);
		return enquiryService.save(enquiry);
	}
	
	@ResponseBody
	@RequestMapping(value = "/enquiries", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<Enquiry> getEnquiries(@RequestParam("listingId")String listingId)
	{
		return enquiryService.getEnquiries("listingId", listingId);
	}
	
	@ResponseBody
	@RequestMapping(value = "/enquiries", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void updateEnquiries(@RequestParam("queryKey")String queryKey, @RequestParam("queryValue")String queryValue, @RequestParam("updateKey")String updateKey, @RequestParam("updateValue")String updateValue)
	{
		enquiryService.updateEnquiries(queryKey, queryValue, updateKey, updateValue);
	}
	
	@ResponseBody
	@RequestMapping(value = "/enquiry", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	public void delteEnquiry(@RequestParam("id")String id)
	{
		enquiryService.deleteEnquiry(id);
	}
	
	@ResponseBody
	@RequestMapping(value = "/enquiries", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler({Exception.class, IOException.class})
	public void deleteEnquiries(@RequestParam("queryKey")String queryKey, @RequestParam("queryValue")String queryValue)
	{
		enquiryService.deleteEnquiries(queryKey, queryValue);
	}
}
