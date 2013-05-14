package com.blogspot.agilisto.classifieds.services;

import com.blogspot.agilisto.classifieds.model.SellerIdentity;

/**
 * CRUD service for SellerIdentity domain model
 */
public interface SellerIdentityService {
	
	/**
	 * Store the SellerIdentity record.
	 * 
	 * @param sellerIdentity
	 * @return The <code>sellerIdentity.id</code> of the record created.
	 */
	String save(SellerIdentity sellerIdentity);
	
	/**
	 * Get the SellerIdentity identified by the <code>username</code>
	 * 
	 * @param username The value of the username field in {@link SellerIdentity}. Used to find the record to return.
	 * @return SellerIdentity record
	 */
	SellerIdentity getSellerIdentity(String username);
	
	/**
	 * Update the SellerIdentity<br>
	 * 
	 * @param username The value of the username field in {@link SellerIdentity}. Used to find the record to update.
	 * @param queryKey The value of the field in {@link SellerIdentity}. Used to find the field to update.
	 * @param queryValue The object of the field in {@link SellerIdentity} to update.
	 */
	void updateSellerIdentity(String username, String queryKey, Object queryValue);
	
	/**
	 * Delete the Seller Identity.<br> 
	 * <b>Note</b>: The Seller Credential is not automatically deleted. 
	 * The calling method will need to call {@link SellerCredentialService} to perform this task.
	 * 
	 * @param username The value of the username field in {@link SellerIdentity}
	 */
	void deleteSellerIdentity(String username);
}
