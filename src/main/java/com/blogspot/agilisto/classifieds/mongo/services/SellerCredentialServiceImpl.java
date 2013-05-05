package com.blogspot.agilisto.classifieds.mongo.services;

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
public class SellerCredentialServiceImpl implements SellerCredentialService {

	@Autowired
    MongoTemplate mongoTemplate;
	
	public static String SELLER_CREDENTIAL_COLLECTION_NAME = "SellerCredential";
	
	@Override
	public boolean doesUsernameExist(String username)
	{
		return (mongoTemplate.findOne(findByUsernameQuery(username), SellerCredential.class) != null);
	}

	private Query findByUsernameQuery(String username) {
		return new Query(Criteria.where("username").is(username));
	}
	
	@Override
	public String save(SellerCredential sellerCredential) {
		
		mongoTemplate.save(sellerCredential, SELLER_CREDENTIAL_COLLECTION_NAME);
		return sellerCredential.getId();
	}

	@Override
	public void updateSellerCredential(String username, String updateKey, Object updateValue) {
		
		Update update = new Update();
		update.set(updateKey, updateValue);
		
		mongoTemplate.updateFirst(findByUsernameQuery(username), update, SELLER_CREDENTIAL_COLLECTION_NAME);
		mongoTemplate.updateMulti(findByUsernameQuery(username), update, SellerIdentityServiceImpl.SELLER_IDENTITY_COLLECTION_NAME);
	}

	@Override
	public void deleteSellerCredential(String username) {

			mongoTemplate.remove(findByUsernameQuery(username), SellerCredential.class);
	}

	@Override
	public boolean validateUsernamePassword(String username, String password) {
		SellerCredential sellerCredential = mongoTemplate.findOne(findByUsernameQuery(username), SellerCredential.class);
		
		if(sellerCredential.getPassword().equals(password))
		{
			return true;
		}
		
		return false;
	}

}
