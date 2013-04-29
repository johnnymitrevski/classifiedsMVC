package com.blogspot.agilisto.classifieds.mongo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.blogspot.agilisto.classifieds.model.Enquiry;
import com.blogspot.agilisto.classifieds.services.EnquiryService;

/**
 * Concrete implementation of the {@link EnquiryService} CRUD interface using mongoDB 
 */
@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
    MongoTemplate mongoTemplate;
	
	public static String ENQUIRY_COLLECTION_NAME = "Enquiry";

	@Override
	public String save(Enquiry enquiry) {
		mongoTemplate.save(enquiry, ENQUIRY_COLLECTION_NAME);
		return enquiry.getId();
	}

	@Override
	public List<Enquiry> getEnquiries(String queryKey, Object queryValue) {
		return mongoTemplate.find(new Query(Criteria.where(queryKey).is(queryValue)), Enquiry.class, ENQUIRY_COLLECTION_NAME);
	}

	@Override
	public void updateEnquiries(String queryKey, Object queryValue, String updateKey, Object updateValue) {
		Update update = new Update();
		update.set(updateKey, updateValue);
		
		mongoTemplate.updateMulti(new Query(Criteria.where(queryKey).is(queryValue)), update, Enquiry.class);
	}

	@Override
	public void deleteEnquiry(String id) {
		mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), Enquiry.class);
	}

	@Override
	public void deleteEnquiries(String queryKey, Object queryValue) {		
		mongoTemplate.remove(new Query(Criteria.where(queryKey).is(queryValue)), Enquiry.class);
	}
}
