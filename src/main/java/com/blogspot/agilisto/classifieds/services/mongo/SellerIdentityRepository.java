package com.blogspot.agilisto.classifieds.services.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
	public SellerIdentity getSellerIdentity(String sellerIdentity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSellerIdentity(SellerIdentity sellerIdentity) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteSellerIdentity(SellerIdentity sellerIdentity) {
		mongoTemplate.remove(sellerIdentity, SELLER_IDENTITY_COLLECTION_NAME);
	}

}
