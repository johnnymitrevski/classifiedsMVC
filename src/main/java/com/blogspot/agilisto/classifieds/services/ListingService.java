package com.blogspot.agilisto.classifieds.services;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.blogspot.agilisto.classifieds.model.Listing;

/**
 * CRUD interface for Listing domain model
 */
public interface ListingService {
	void save(Listing listing);
	
	Listing getListing(String id);
	
	List<Listing> getListings(Query query);
	
	void updateListing(String id, Update update);
	
	void deleteListing(String id);
}
