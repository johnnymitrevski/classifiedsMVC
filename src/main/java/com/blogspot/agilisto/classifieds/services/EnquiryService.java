package com.blogspot.agilisto.classifieds.services;

import com.blogspot.agilisto.classifieds.model.Enquiry;

public interface EnquiryService {
	Enquiry save(Enquiry enquiry);
	
	Enquiry getEnquiry(String enquiry);
	
	Enquiry updateEnquiry(Enquiry enquiry);
	
	void deleteEnquiry(Enquiry enquiry);
}
