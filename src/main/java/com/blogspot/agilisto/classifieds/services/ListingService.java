package com.blogspot.agilisto.classifieds.services;

import java.util.List;

import com.blogspot.agilisto.classifieds.model.Listing;

/**
 * CRUD interface for Listing domain model
 */
public interface ListingService {
	
	String save(Listing listing);
	
	Listing getListing(String id);
	
	List<Listing> getListings(String queryKey, Object queryValue);
	
	void updateListing(String id, String updateKey, Object updateValue);
	
	void updateListings(String queryKey, Object queryValue, String updateKey, Object updateValue);
	
	void deleteListing(String id);
	
	void deleteListings(String queryKey, Object queryValue);

}
