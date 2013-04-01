package com.blogspot.agilisto.classifieds.services;

import org.springframework.data.mongodb.core.query.Update;

import com.blogspot.agilisto.classifieds.model.SellerIdentity;

/**
 * CRUD interface for SellerIdentity domain model
 */
public interface SellerIdentityService {
	void save(SellerIdentity sellerIdentity);
	
	SellerIdentity getSellerIdentity(String username);
	
	void updateSellerIdentity(String username, Update update);
	
	void deleteSellerIdentity(String username);
}
