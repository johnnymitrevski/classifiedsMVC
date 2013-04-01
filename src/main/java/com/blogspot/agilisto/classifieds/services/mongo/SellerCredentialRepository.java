package com.blogspot.agilisto.classifieds.services.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.blogspot.agilisto.classifieds.model.SellerCredential;
import com.blogspot.agilisto.classifieds.services.SellerCredentialService;

/**
 * Concrete implementation of the {@link SellerCredentialService} CRUD interface using mongoDB 
 */
@Service
public class SellerCredentialRepository implements SellerCredentialService {

	@Autowired
    MongoTemplate mongoTemplate;
	
	public static String SELLER_CREDENTIAL_COLLECTION_NAME = "SellerCredential";
	
	@Override
	public void save(SellerCredential sellerCredential) {
		mongoTemplate.save(sellerCredential, SELLER_CREDENTIAL_COLLECTION_NAME);
	}

	@Override
	public SellerCredential getSellerCredential(String username) {
		return mongoTemplate.findById(username, SellerCredential.class, SELLER_CREDENTIAL_COLLECTION_NAME);
	}

	@Override
	public void updateSellerCredential(String username, Update update) {
		mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(username)), update, SELLER_CREDENTIAL_COLLECTION_NAME);
	}

	@Override
	public void deleteSellerCredential(String username) {
		SellerCredential sellerCredential = getSellerCredential(username);
		mongoTemplate.remove(sellerCredential);
	}

}
