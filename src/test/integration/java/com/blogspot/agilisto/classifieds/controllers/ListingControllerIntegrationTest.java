package com.blogspot.agilisto.classifieds.controllers;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;

import com.blogspot.agilisto.classifieds.model.Category;
import com.blogspot.agilisto.classifieds.model.Listing;
import com.blogspot.agilisto.classifieds.model.SellerIdentity;
import com.blogspot.agilisto.classifieds.mongo.services.CategoryServiceImpl;
import com.blogspot.agilisto.classifieds.mongo.services.ListingServiceImpl;
import com.blogspot.agilisto.classifieds.mongo.services.SellerIdentityServiceImpl;
import com.blogspot.agilisto.classifieds.services.CategoryService;
import com.blogspot.agilisto.classifieds.services.ListingService;
import com.blogspot.agilisto.classifieds.services.SellerIdentityService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-servlet-context.xml", "/test-root-context.xml", "/test-mongo-context.xml"})
@WebAppConfiguration
public class ListingControllerIntegrationTest {

	@Autowired 
	ListingController listingController;
	
	@Autowired
	CategoryController categoryController;
	
	@Autowired
	ListingService listingService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	SellerIdentityService sellerIdentityService;
	
	@Autowired
    MongoTemplate mongoTemplate;
	
	private MockMvc mockListingsMvc;
	
	@Before
	public void setup() throws Exception {
		mockListingsMvc = MockMvcBuilders.standaloneSetup(listingController).build();
	}
	
	@Test
	public void testCreateNewListing() throws Exception {
	
		double[] location = {45.00d, 44.99d};
		SellerIdentity sellerIdentity = new SellerIdentity("johnDoe", "email", "firstName", "lastName", "street", "suburb",
				"state", "postcode", "country", location, "phoneNumber");
		
		mongoTemplate.save(sellerIdentity, SellerIdentityServiceImpl.SELLER_IDENTITY_COLLECTION_NAME);
		
		Category category = new Category("Automatic", null);
		
		mongoTemplate.save(category, CategoryServiceImpl.CATEGORY_COLLECTION_NAME);
		
		long listingTime = System.currentTimeMillis();
		long expiryTime = listingTime + (30 * 24 * 60 * 60 * 1000);
		
		mockListingsMvc.perform(post("/listing").contentType(MediaType.APPLICATION_JSON)
				.param("title", "Listing title")
				.param("description", "Description of listing")
				.param("price", "35.00")
				.param("categoryId", "Automatic")
				.param("username", "johnDoe")
				.param("listingTime", Long.toString(listingTime))
				.param("expiryTime", Long.toString(expiryTime)))
				.andExpect(status().isCreated())
				.andExpect(content().mimeType("text/plain;charset=ISO-8859-1"));
		
		Listing listing = mongoTemplate.findOne(new Query(Criteria.where("title").is("Listing title")), Listing.class);
		
		Assert.assertEquals(listing.getTitle(), "Listing title");
		Assert.assertEquals(listing.getDescription(), "Description of listing");
		Assert.assertEquals(listing.getPrice(), 35.0d);
		Assert.assertEquals(listing.getCategoryForiegnKey(), category.getId());
		Assert.assertEquals(listing.getListingTime(), listingTime);
		Assert.assertEquals(listing.getExpiryTime(), expiryTime);
	}
	
	@Test
	public void testGetListing() throws Exception
	{
		Listing listing = new Listing("title", "description", 35.0d, "123", "123", 0l, 1l);
		mongoTemplate.save(listing, ListingServiceImpl.LISTING_COLLECTION_NAME);
		
		mockListingsMvc.perform(get("/listing").param("id", listing.getId()))
		.andExpect(status().isOk())
		.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString("\"title\":\"title\"")))
		.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString("\"description\":\"description\"")))
		.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString("\"price\":35.0")))
		.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString("\"listingTime\":0")))
		.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString("\"expiryTime\":1")))
		.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString("\"categoryForiegnKey\":\"123\"")))
		.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString("\"sellerIdentityForiegnKey\":\"123")));
	}
	
	@Test
	public void testGetListings() throws Exception
	{
		Listing listing1 = new Listing("title1", "description1", 31.0d, "1231", "1", 0l, 1l);
		Listing listing2 = new Listing("title2", "description2", 32.0d, "1232", "1", 2l, 3l);
		mongoTemplate.save(listing1, ListingServiceImpl.LISTING_COLLECTION_NAME);
		mongoTemplate.save(listing2, ListingServiceImpl.LISTING_COLLECTION_NAME);
		
		mockListingsMvc.perform(get("/listings").param("queryKey", "sellerIdentityForiegnKey")
				.param("queryValue", listing1.getSellerIdentityForiegnKey()))
		.andExpect(status().isOk())
		.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString("\"title\":\"title1\"")))
		.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString("\"description\":\"description1\"")))
		.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString("\"price\":31.0")))
		.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString("\"listingTime\":0")))
		.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString("\"expiryTime\":1")))
		.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString("\"categoryForiegnKey\":\"1231\"")))
		.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString("\"sellerIdentityForiegnKey\":\"1")))
		.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString("\"title\":\"title2\"")))
		.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString("\"description\":\"description2\"")))
		.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString("\"price\":32.0")))
		.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString("\"listingTime\":2")))
		.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString("\"expiryTime\":3")))
		.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString("\"categoryForiegnKey\":\"1232\"")))
		.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString("\"sellerIdentityForiegnKey\":\"1")));
	}
	
	@Test
	public void testDeleteListing() throws Exception
	{
		Listing listing = new Listing("title", "description", 35.0d, "123", "123", 0l, 1l);
		mongoTemplate.save(listing, ListingServiceImpl.LISTING_COLLECTION_NAME);
		
		mockListingsMvc.perform(delete("/listing").param("id", listing.getId()))
		.andExpect(status().isOk());
		
		Assert.assertTrue("Expected to be null", mongoTemplate.findById(listing.getId(), Listing.class) == null);
	}
	
	@Test
	public void testUpdateListing() throws Exception
	{
		Listing listing = new Listing("title", "description", 35.0d, "123", "123", 0l, 1l);
		mongoTemplate.save(listing, ListingServiceImpl.LISTING_COLLECTION_NAME);
		String newTitle = "New Title";
		
		mockListingsMvc.perform(put("/listing").param("id",listing.getId()).param("updateKey", "title").param("updateValue", newTitle)).andExpect(status().isOk());
		
		Listing result = mongoTemplate.findById(listing.getId(), Listing.class);
		
		Assert.assertEquals(result.getTitle(), newTitle);
	}
	
	@After
	public void dropDatabase() throws Exception {
		
		mongoTemplate.dropCollection("Category");
		mongoTemplate.dropCollection("Listing");
		mongoTemplate.dropCollection("SellerIdentity");
	}
}
