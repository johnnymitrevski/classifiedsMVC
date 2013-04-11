package com.blogspot.agilisto.classifieds.services;

import java.util.List;

import com.blogspot.agilisto.classifieds.model.Listing;

/**
 * CRUD interface for Listing domain model
 */
public interface ListingService {
	void save(Listing listing);
	
	Listing getListing(String id);
	
	List<Listing> getListings(String key, Object value);
	
	void updateListing(String id, String key, Object value);
	
	void deleteListing(String id);
}
