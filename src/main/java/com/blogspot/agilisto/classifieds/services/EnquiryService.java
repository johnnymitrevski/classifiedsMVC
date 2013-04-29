package com.blogspot.agilisto.classifieds.services;

import java.util.List;

import com.blogspot.agilisto.classifieds.model.Enquiry;

/**
 * CRUD interface for Enquiry domain model
 */
public interface EnquiryService {
	String save(Enquiry enquiry);
	
	List<Enquiry> getEnquiries(String queryKey, Object queryValue);
	
	void updateEnquiries(String queryKey, Object queryValue, String updateKey, Object updateValue);
	
	void deleteEnquiry(String id);
	
	void deleteEnquiries(String queryKey, Object queryValue);
}
