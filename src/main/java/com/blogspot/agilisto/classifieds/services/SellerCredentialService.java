package com.blogspot.agilisto.classifieds.services;

import com.blogspot.agilisto.classifieds.model.SellerCredential;

/**
 * CRUD interface for SellerCredential domain model
 */
public interface SellerCredentialService {
	SellerCredential save(SellerCredential sellerCredential);
	
	SellerCredential getSellerCredential(String sellerCredential);
	
	SellerCredential updateSellerCredential(SellerCredential sellerCredential);
	
	void deleteSellerCredential(SellerCredential sellerCredential);
}
