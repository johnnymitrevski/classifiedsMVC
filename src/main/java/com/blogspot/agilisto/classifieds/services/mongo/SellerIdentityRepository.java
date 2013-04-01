package com.blogspot.agilisto.classifieds.services.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.blogspot.agilisto.classifieds.model.SellerIdentity;
import com.blogspot.agilisto.classifieds.services.SellerIdentityService;

/**
 * Concrete implementation of the {@link SellerIdentityService} CRUD interface using mongoDB 
 */
@Service
public class SellerIdentityRepository implements SellerIdentityService {

	@Autowired
    MongoTemplate mongoTemplate;
	
	public static String SELLER_IDENTITY_COLLECTION_NAME = "SellerIdentity";
	
	@Override
	public void save(SellerIdentity sellerIdentity) {
		mongoTemplate.save(sellerIdentity, SELLER_IDENTITY_COLLECTION_NAME);
	}

	@Override
	public SellerIdentity getSellerIdentity(String username) {
		return mongoTemplate.findById(username, SellerIdentity.class, SELLER_IDENTITY_COLLECTION_NAME);
	}

	@Override
	public void updateSellerIdentity(String username, Update update) {
		mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(username)), update, SELLER_IDENTITY_COLLECTION_NAME);
	}

	@Override
	public void deleteSellerIdentity(String username) {
		SellerIdentity sellerIdentity = getSellerIdentity(username);
		mongoTemplate.remove(sellerIdentity);
	}

}
