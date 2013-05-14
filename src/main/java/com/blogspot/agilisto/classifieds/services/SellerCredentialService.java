package com.blogspot.agilisto.classifieds.services;

import com.blogspot.agilisto.classifieds.model.SellerCredential;

/**
 * CRUD service for SellerCredential domain model
 */
public interface SellerCredentialService {
	
	/**
	 * Check that the username exists
	 * 
	 * @param username The value of the username field in {@link SellerCredential}.
	 * @return <b>true</b> if exists, <b>false</b> otherwise
	 */
	boolean doesUsernameExist(String username);
	
	/**
	 * Store the SellerCredential record.
	 * 
	 * @param sellerCredential
	 * @return The <code>sellerCredential.id</code> of the record created.
	 */
	String save(SellerCredential sellerCredential);
	
	/**
	 * Update the SellerCredential.
	 * 
	 * @param username The value of the username field in {@link SellerCredential}. Used to find the record to update.
	 * @param queryKey The value of the field in {@link SellerCredential}. Used to find the field to update.
	 * @param queryValue The object of the field in {@link SellerCredential} to update.
	 */
	void updateSellerCredential(String username, String queryKey, Object queryValue);
	
	/**
	 * Delete the Seller Credential.<br> 
	 * <b>Note</b>: The Seller Identity is not automatically deleted. 
	 * The calling method will need to call {@link SellerIdentityService} to perform this task.
	 * 
	 * @param username The value of the username field in {@link SellerCredential}
	 */
	void deleteSellerCredential(String username);
	
	/**
	 * Verify that the username/password combination is correct.
	 * 
	 * @param username The value of the username field in {@link SellerCredential}.
	 * @param password The value of the password field in {@link SellerCredential}.
	 * @return <b>true</b> if combination is correct, <b>false</b> otherwise
	 */
	boolean validateUsernamePassword(String username, String password);
}
