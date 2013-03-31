package com.blogspot.agilisto.classifieds.services;

import com.blogspot.agilisto.classifieds.model.SellerIdentity;

/**
 * CRUD interface for SellerIdentity domain model
 */
public interface SellerIdentityService {
	SellerIdentity save(SellerIdentity sellerIdentity);
	
	SellerIdentity getSellerIdentity(String sellerIdentity);
	
	SellerIdentity updateSellerIdentity(SellerIdentity sellerIdentity);
	
	void deleteSellerIdentity(SellerIdentity sellerIdentity);
}
