package com.blogspot.agilisto.classifieds.services;

import com.blogspot.agilisto.classifieds.model.SellerCredential;

/**
 * CRUD interface for SellerCredential domain model
 */
public interface SellerCredentialService {
	void save(SellerCredential sellerCredential);
	
	SellerCredential getSellerCredential(String sellerCredential);
	
	void updateSellerCredential(SellerCredential sellerCredential);
	
	void deleteSellerCredential(SellerCredential sellerCredential);
}
