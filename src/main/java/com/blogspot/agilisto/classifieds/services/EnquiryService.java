package com.blogspot.agilisto.classifieds.services;

import com.blogspot.agilisto.classifieds.model.Enquiry;

/**
 * CRUD interface for Enquiry domain model
 */
public interface EnquiryService {
	Enquiry save(Enquiry enquiry);
	
	Enquiry getEnquiry(String enquiry);
	
	Enquiry updateEnquiry(Enquiry enquiry);
	
	void deleteEnquiry(Enquiry enquiry);
}
