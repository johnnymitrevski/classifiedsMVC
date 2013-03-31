package com.blogspot.agilisto.classifieds.services.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.blogspot.agilisto.classifieds.model.Enquiry;
import com.blogspot.agilisto.classifieds.services.EnquiryService;

/**
 * Concrete implementation of the {@link EnquiryService} CRUD interface using mongoDB 
 */
@Service
public class EnquiryRepository implements EnquiryService {

	@Autowired
    MongoTemplate mongoTemplate;
	
	public static String ENQUIRY_COLLECTION_NAME = "Enquiry";
	
	@Override
	public Enquiry save(Enquiry enquiry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enquiry getEnquiry(String enquiry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enquiry updateEnquiry(Enquiry enquiry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteEnquiry(Enquiry enquiry) {
		// TODO Auto-generated method stub

	}

}
