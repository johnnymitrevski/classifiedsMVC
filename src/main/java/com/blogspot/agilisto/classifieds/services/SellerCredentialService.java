package com.blogspot.agilisto.classifieds.services;

import com.blogspot.agilisto.classifieds.model.SellerCredential;

/**
 * CRUD interface for SellerCredential domain model
 */
public interface SellerCredentialService {
	
	boolean doesUsernameExist(String username);
	
	String save(SellerCredential sellerCredential);
	
	void updateSellerCredential(String username, String password, String queryKey, Object queryValue);
	
	void deleteSellerCredential(String username, String password);
	
	void validateUsernamePassword(String username, String password);
}
