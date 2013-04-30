package com.blogspot.agilisto.classifieds.services;

import com.blogspot.agilisto.classifieds.model.SellerIdentity;

/**
 * CRUD interface for SellerIdentity domain model
 */
public interface SellerIdentityService {
	String save(SellerIdentity sellerIdentity);
	
	SellerIdentity getSellerIdentity(String username);
	
	void updateSellerIdentity(String username, String queryKey, Object queryValue);
	
	void deleteSellerIdentity(String username);
}
