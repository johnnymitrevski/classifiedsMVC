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
public class ListingServiceImpl implements ListingService {

	@Autowired
    MongoTemplate mongoTemplate;
	
	public static String LISTING_COLLECTION_NAME = "Listing";

	@Override
	public String save(Listing listing) {
		mongoTemplate.save(listing, LISTING_COLLECTION_NAME);
		return listing.getId();
	}

	@Override
	public Listing getListing(String id) {
		return mongoTemplate.findById(id, Listing.class, LISTING_COLLECTION_NAME);
	}

	@Override
	public List<Listing> getListings(String queryKey, Object queryValue) {
		Query query = new Query();
		query.addCriteria(Criteria.where(queryKey).is(queryValue));
		return mongoTemplate.find(query, Listing.class, LISTING_COLLECTION_NAME);
	}

	@Override
	public void updateListing(String id, String updateKey, Object updateValue) {
		Update update = new Update();
		update.set(updateKey, updateValue);
		mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(id)), update, Listing.class);
	}
	
	@Override
	public void updateListings(String queryKey, Object queryValue, String updateKey, Object updateValue) {
		Update update = new Update();
		update.set(updateKey, updateValue);
		mongoTemplate.updateMulti(new Query(Criteria.where(queryKey).is(queryValue)), update, Listing.class);
	}

	@Override
	public void deleteListing(String id) {
		mongoTemplate.remove(new Query(Criteria.where("_id").is(id)),Listing.class);
	}

	@Override
	public void deleteListings(String queryKey, Object queryValue) {
		mongoTemplate.remove(new Query(Criteria.where(queryKey).is(queryValue)),Listing.class);	
	}
}
