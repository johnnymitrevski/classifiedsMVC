package com.blogspot.agilisto.classifieds.services;

import java.util.List;

import com.blogspot.agilisto.classifieds.model.Listing;

/**
 * CRUD service for Listing domain model
 */
public interface ListingService {
	
	/**
	 * Store the Listing record.
	 * 
	 * @param listing
	 * @return The <code>listing.id</code> of the record created.
	 */
	String save(Listing listing);
	
	/**
	 * Get the Listing record.
	 * 
	 * @param id The value of the id field in {@link Listing}. Used to find the record to return.
	 * @return Listing object, <code>null</code> if not found.
	 */
	Listing getListing(String id);
	
	/**
	 * Get all the Listing records associated with the query.
	 * 
	 * @param queryKey The field in {@link Listing} to match.
	 * @param queryValue The value of the field to match.
	 * @return List<Listing> object, <code>null</code> if not found.
	 */
	List<Listing> getListings(String queryKey, Object queryValue);
	
	/**
	 * Update a single Listing
	 * 
	 * @param id The <code>Listing.id</code> of the record to update.
	 * @param updateKey The field in {@link Listing} to update.
	 * @param updateValue The object to update.
	 */
	void updateListing(String id, String updateKey, Object updateValue);
	
	/**
	 * Update multiple Listing(s)
	 * 
	 * @param queryKey The field in {@link Listing} to match.
	 * @param queryValue The value of the field to match.
	 * @param updateKey The field in <code>Listing</code> to update.
	 * @param updateValue The object to update.
	 */
	void updateListings(String queryKey, Object queryValue, String updateKey, Object updateValue);

	/**
	 * Delete a single listing.
	 * 
	 * @param id The <code>Listing.id</code> of the record to delete.
	 */
	void deleteListing(String id);
	
	/**
	 * Delete multiple listings.
	 * 
	 * @param queryKey The field in {@link Listing} to match.
	 * @param queryValue The value of the field to match.
	 */
	void deleteListings(String queryKey, Object queryValue);

}
