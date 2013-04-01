package com.blogspot.agilisto.classifieds.services;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.blogspot.agilisto.classifieds.model.Enquiry;

/**
 * CRUD interface for Enquiry domain model
 */
public interface EnquiryService {
	void save(Enquiry enquiry);
	
	Enquiry getEnquiry(String id);
	
	List<Enquiry> getEnquiries(Query query);
	
	void updateEnquiries(Query query, Update update);
	
	void deleteEnquiry(String id);
	
	void deleteEnquries(Query query);
}
