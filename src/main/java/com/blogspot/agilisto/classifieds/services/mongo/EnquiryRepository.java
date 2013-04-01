package com.blogspot.agilisto.classifieds.services.mongo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
	public void save(Enquiry enquiry) {
		mongoTemplate.save(enquiry, ENQUIRY_COLLECTION_NAME);
	}
	
	@Override
	public Enquiry getEnquiry(String id) {
		return mongoTemplate.findById(id, Enquiry.class, ENQUIRY_COLLECTION_NAME);
	}

	@Override
	public List<Enquiry> getEnquiries(Query query) {
		return mongoTemplate.find(query, Enquiry.class, ENQUIRY_COLLECTION_NAME);
	}

	@Override
	public void updateEnquiries(Query query, Update update) {
		mongoTemplate.updateMulti(query, update, ENQUIRY_COLLECTION_NAME);
	}

	@Override
	public void deleteEnquiry(String id) {
		Enquiry enquiry = getEnquiry(id);
		mongoTemplate.remove(enquiry, ENQUIRY_COLLECTION_NAME);
	}

	@Override
	public void deleteEnquries(Query query) {
		mongoTemplate.remove(query, ENQUIRY_COLLECTION_NAME);
	}
}
