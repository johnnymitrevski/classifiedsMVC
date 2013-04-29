package com.blogspot.agilisto.classifieds.controllers;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;
import static org.junit.matchers.JUnitMatchers.containsString;

import java.util.Vector;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;

import com.blogspot.agilisto.classifieds.model.Category;
import com.blogspot.agilisto.classifieds.model.Listing;
import com.blogspot.agilisto.classifieds.model.SellerIdentity;
import com.blogspot.agilisto.classifieds.services.CategoryService;
import com.blogspot.agilisto.classifieds.services.ListingService;
import com.blogspot.agilisto.classifieds.services.SellerIdentityService;

public class ListingControllerTest {

	@InjectMocks 
	ListingController controller = new ListingController();
	
	@Mock
	ListingService listingService;
	
	@Mock
	CategoryService categoryService;
	
	@Mock
	SellerIdentityService sellerIdentityService;
	
	MockMvc mockMvc;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	public void testCreateNewListing() throws Exception {
	
		long listingTime = System.currentTimeMillis();
		long expiryTime = listingTime + (30 * 24 * 60 * 60 * 1000);
		
		Category category = new Category("Automotive", null);
		Mockito.stub(categoryService.getCategory("Automotive")).toReturn(category);
		
		SellerIdentity sellerIdentity = new SellerIdentity("johnDoe", null, null, null, null, null, null, null, null, null, null);
		Mockito.stub(sellerIdentityService.getSellerIdentity("johnDoe")).toReturn(sellerIdentity);
		
		mockMvc.perform(post("/listing").contentType(MediaType.APPLICATION_JSON)
				.param("title", "Listing title")
				.param("description", "Description of listing")
				.param("price", "35.00")
				.param("categoryId", "Automotive")
				.param("username", "johnDoe")
				.param("listingTime", Long.toString(listingTime))
				.param("expiryTime", Long.toString(expiryTime)))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void testCreateNewListingNoCategory() throws Exception {
	
		long listingTime = System.currentTimeMillis();
		long expiryTime = listingTime + (30 * 24 * 60 * 60 * 1000);
		
		Mockito.stub(categoryService.getCategory("Automotive")).toReturn(null);
		
		SellerIdentity sellerIdentity = new SellerIdentity("johnDoe", null, null, null, null, null, null, null, null, null, null);
		Mockito.stub(sellerIdentityService.getSellerIdentity("johnDoe")).toReturn(sellerIdentity);
		
		try
		{
			mockMvc.perform(post("/listing").contentType(MediaType.APPLICATION_JSON)
					.param("title", "Listing title")
					.param("description", "Description of listing")
					.param("price", "35.00")
					.param("categoryId", "Automotive")
					.param("username", "johnDoe")
					.param("listingTime", Long.toString(listingTime))
					.param("expiryTime", Long.toString(expiryTime)))
					.andExpect(status().isCreated());
		}
		catch(Exception ex)
		{
			Assert.assertTrue(ex.getMessage().contains("Category cannot be found: Automotive"));
			return;
		}
		
		Assert.assertFalse("Should have thrown an exception", true);
	}
	
	@Test
	public void testCreateNewListingNoSellerIdentity() throws Exception {
	
		long listingTime = System.currentTimeMillis();
		long expiryTime = listingTime + (30 * 24 * 60 * 60 * 1000);
		
		Mockito.stub(categoryService.getCategory("Automotive")).toReturn(null);
		
		Category category = new Category("Automotive", null);
		Mockito.stub(categoryService.getCategory("Automotive")).toReturn(category);
		
		try
		{
			mockMvc.perform(post("/listing").contentType(MediaType.APPLICATION_JSON)
					.param("title", "Listing title")
					.param("description", "Description of listing")
					.param("price", "35.00")
					.param("categoryId", "Automotive")
					.param("username", "johnDoe")
					.param("listingTime", Long.toString(listingTime))
					.param("expiryTime", Long.toString(expiryTime)))
					.andExpect(status().isCreated());
		}
		catch(Exception ex)
		{
			Assert.assertTrue(ex.getMessage().contains("SellerIdentity cannot be found: johnDoe"));
			return;
		}
		
		Assert.assertFalse("Should have thrown an exception", true);
	}
	
	@Test
	public void testGetMultipleListingsDoesNotExist() throws Exception {
				
		mockMvc.perform(get("/listings").contentType(MediaType.APPLICATION_JSON)
				.param("queryKey", "")
				.param("queryValue", ""))
				.andExpect(status().isOk())
				.andExpect(content().string("[]")); //Return an empty list
	}
	
	@Test
	public void testGetMultipleListingsDoesExist() throws Exception {
		
		Vector<Listing> listings = new Vector<Listing>();
		
		Listing listing1 = new Listing("title1", "description1", Double.valueOf(35.00), "categoryKey1", "sellerIdentityKey1", 0l, 1l);
		Listing listing2 = new Listing("title2", "description2", Double.valueOf(38.00), "categoryKey2", "sellerIdentityKey2", 2l, 3l);

		listings.add(listing1);
		listings.add(listing2);
		Mockito.stub(listingService.getListings("", "")).toReturn(listings);
		
		mockMvc.perform(get("/listings").contentType(MediaType.APPLICATION_JSON)
				.param("queryKey", "")
				.param("queryValue", ""))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("\"title\":\"title1\"")))
				.andExpect(content().string(containsString("\"description\":\"description1\"")))
				.andExpect(content().string(containsString("\"categoryForiegnKey\":\"categoryKey1\"")))
				.andExpect(content().string(containsString("price\":35.0")))
				.andExpect(content().string(containsString("listingTime\":0")))
				.andExpect(content().string(containsString("expiryTime\":1")))
				.andExpect(content().string(containsString("\"title\":\"title2\"")))
				.andExpect(content().string(containsString("\"description\":\"description2\"")))
				.andExpect(content().string(containsString("\"categoryForiegnKey\":\"categoryKey2\"")))
				.andExpect(content().string(containsString("price\":38.0")))
				.andExpect(content().string(containsString("listingTime\":2")))
				.andExpect(content().string(containsString("expiryTime\":3")));
	}
	
	@Test
	public void testDeleteListing() throws Exception {

		mockMvc.perform(delete("/listing").contentType(MediaType.APPLICATION_JSON)
				.param("id", ""))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testUpdateListing() throws Exception {
		
		mockMvc.perform(delete("/listing").contentType(MediaType.APPLICATION_JSON)
				.param("id", "")
				.param("updateKey", "")
				.param("updateValue", ""))
				.andExpect(status().isOk());
	}
}
