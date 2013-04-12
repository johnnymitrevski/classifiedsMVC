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
	public List<Enquiry> getEnquiries(String queryKey, Object queryValue) {
		Query query = new Query();
		query.addCriteria(Criteria.where(queryKey).is(queryValue));
		
		return mongoTemplate.find(query, Enquiry.class, ENQUIRY_COLLECTION_NAME);
	}

	@Override
	public void updateEnquiries(String queryKey, Object queryValue, String updateKey, Object updateValue) {
		Update update = new Update();
		update.set(updateKey, updateValue);
		
		Query query = new Query();
		query.addCriteria(Criteria.where(queryKey).is(queryValue));
		mongoTemplate.updateMulti(query, update, ENQUIRY_COLLECTION_NAME);
	}

	@Override
	public void deleteEnquiry(String id) {
		Enquiry enquiry = getEnquiry(id);
		mongoTemplate.remove(enquiry, ENQUIRY_COLLECTION_NAME);
	}

	@Override
	public void deleteEnquries(String queryKey, Object queryValue) {
		Query query = new Query();
		query.addCriteria(Criteria.where(queryKey).is(queryValue));
		
		mongoTemplate.remove(query, ENQUIRY_COLLECTION_NAME);
	}
}
