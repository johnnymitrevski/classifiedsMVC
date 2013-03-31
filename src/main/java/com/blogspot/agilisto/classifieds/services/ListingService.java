package com.blogspot.agilisto.classifieds.services;

import com.blogspot.agilisto.classifieds.model.Listing;

public interface ListingService {
	Listing save(Listing listing);
	
	Listing getListing(String listing);
	
	Listing updateListing(Listing listing);
	
	void deleteListing(Listing listing);
}
