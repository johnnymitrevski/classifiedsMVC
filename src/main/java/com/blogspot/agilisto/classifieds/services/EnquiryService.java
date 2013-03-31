package com.blogspot.agilisto.classifieds.services;

import com.blogspot.agilisto.classifieds.model.Enquiry;

/**
 * CRUD interface for Enquiry domain model
 */
public interface EnquiryService {
	Long save(Enquiry enquiry);
	
	Enquiry getEnquiry(String enquiry);
	
	void updateEnquiry(Enquiry enquiry);
	
	void deleteEnquiry(Enquiry enquiry);
}
