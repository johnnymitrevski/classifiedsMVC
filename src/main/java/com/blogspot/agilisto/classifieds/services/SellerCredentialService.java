package com.blogspot.agilisto.classifieds.services;

import com.blogspot.agilisto.classifieds.model.SellerCredential;

/**
 * CRUD interface for SellerCredential domain model
 */
public interface SellerCredentialService {
	void save(SellerCredential sellerCredential);
	
	SellerCredential getSellerCredential(String username);
	
	void updateSellerCredential(String username, String queryKey, Object queryValue);
	
	void deleteSellerCredential(String username);
}
