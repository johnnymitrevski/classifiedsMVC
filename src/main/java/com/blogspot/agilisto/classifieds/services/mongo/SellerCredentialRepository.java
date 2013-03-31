package com.blogspot.agilisto.classifieds.services.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
	public SellerCredential getSellerCredential(String sellerCredential) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSellerCredential(SellerCredential sellerCredential) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteSellerCredential(SellerCredential sellerCredential) {
		mongoTemplate.remove(sellerCredential, SELLER_CREDENTIAL_COLLECTION_NAME);
	}

}
