package com.blogspot.agilisto.classifieds.mongo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
	public void save(Listing listing) {
		mongoTemplate.save(listing, LISTING_COLLECTION_NAME);
	}

	@Override
	public Listing getListing(String id) {
		return mongoTemplate.findById(id, Listing.class, LISTING_COLLECTION_NAME);
	}

	@Override
	public List<Listing> getListings(String key, Object value) {
		Query query = new Query();
		query.addCriteria(Criteria.where(key).is(value));
		return mongoTemplate.find(query, Listing.class, LISTING_COLLECTION_NAME);
	}

	@Override
	public void updateListing(String id, String key, Object value) {
		Update update = new Update();
		update.set(key, value);
		mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(id)), update, LISTING_COLLECTION_NAME);
	}

	@Override
	public void deleteListing(String id) {
		Listing listing = getListing(id);
		mongoTemplate.remove(listing,LISTING_COLLECTION_NAME);
	}
	
	

}
