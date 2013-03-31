package com.blogspot.agilisto.classifieds.services.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.blogspot.agilisto.classifieds.model.Listing;
import com.blogspot.agilisto.classifieds.services.ListingService;

/**
 * Concrete implementation of the {@link ListingService} CRUD interface using mongoDB 
 */
@Service
public class ListingRepository implements ListingService {

	@Autowired
    MongoTemplate mongoTemplate;
	
	public static String LISTING_COLLECTION_NAME = "Listing";
	
	@Override
	public Long save(Listing listing) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Listing getListing(String listing) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateListing(Listing listing) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteListing(Listing listing) {
		mongoTemplate.remove(listing, LISTING_COLLECTION_NAME);
	}

}
