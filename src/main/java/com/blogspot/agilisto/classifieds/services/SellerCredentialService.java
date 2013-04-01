package com.blogspot.agilisto.classifieds.services;

import org.springframework.data.mongodb.core.query.Update;

import com.blogspot.agilisto.classifieds.model.SellerCredential;

/**
 * CRUD interface for SellerCredential domain model
 */
public interface SellerCredentialService {
	void save(SellerCredential sellerCredential);
	
	SellerCredential getSellerCredential(String username);
	
	void updateSellerCredential(String username, Update update);
	
	void deleteSellerCredential(String username);
}
