package com.blogspot.agilisto.classifieds.controllers;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;

import com.blogspot.agilisto.classifieds.model.Enquiry;
import com.blogspot.agilisto.classifieds.model.Listing;
import com.blogspot.agilisto.classifieds.mongo.services.EnquiryServiceImpl;
import com.blogspot.agilisto.classifieds.mongo.services.ListingServiceImpl;
import com.blogspot.agilisto.classifieds.services.EnquiryService;
import com.blogspot.agilisto.classifieds.services.ListingService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-servlet-context.xml", "/test-root-context.xml", "/test-mongo-context.xml"})
@WebAppConfiguration
public class EnquiryControllerIntegrationTest {

	@Autowired
	EnquiryController enquiryController;
	
	@Autowired
	EnquiryService enquiryService;
	
	@Autowired
	ListingService listingService;
	
	@Autowired
    MongoTemplate mongoTemplate;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(enquiryController).build();
	}
	
	@Test
	public void testCreateNewEnquiry() throws Exception
	{
		Listing listing = new Listing("title", "description", 35.0d, "123", "123", 0l, 1l);
		mongoTemplate.save(listing, ListingServiceImpl.LISTING_COLLECTION_NAME);
		
		mockMvc.perform(post("/enquiry").contentType(MediaType.APPLICATION_JSON)
				.param("listingId", listing.getId())
				.param("email", "email@my.com")
				.param("comment", "A short comment"))
				.andExpect(status().isCreated());
	}
	
	@Test 
	public void testGetEnquiries() throws Exception {
	
		Listing listing = new Listing("title", "description", 35.0d, "123", "123", 0l, 1l);
		mongoTemplate.save(listing, ListingServiceImpl.LISTING_COLLECTION_NAME);
		
		Enquiry enquiry1 = new Enquiry("123", "email1@email.com", "Comment1");
		Enquiry enquiry2 = new Enquiry("123", "email2@email.com", "Comment2");
		
		mongoTemplate.save(enquiry1, EnquiryServiceImpl.ENQUIRY_COLLECTION_NAME);
		mongoTemplate.save(enquiry2, EnquiryServiceImpl.ENQUIRY_COLLECTION_NAME);
		
		mockMvc.perform(get("/enquiries").contentType(MediaType.APPLICATION_JSON)
				.param("listingId", "123"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString("\"comments\":\"Comment1\"")))
				.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString("email\":\"email1@email.com\"")))
				.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString("\"comments\":\"Comment2\"")))
				.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString("email\":\"email2@email.com\"")));
	}	
	
	@Test
	public void testDeleteEnquiry() throws Exception {
		
		Enquiry enquiry1 = new Enquiry("123", "email1@email.com", "Comment1");
		
		mongoTemplate.save(enquiry1, EnquiryServiceImpl.ENQUIRY_COLLECTION_NAME);
		
		mockMvc.perform(delete("/enquiry").contentType(MediaType.APPLICATION_JSON)
				.param("id", enquiry1.getId()))
				.andExpect(status().isOk());
		
		Assert.assertEquals(null,mongoTemplate.findById(enquiry1.getId(), Enquiry.class));
	}
	
	@Test
	public void testDeleteEnquiries() throws Exception {
		
		Listing listing = new Listing("title", "description", 35.0d, "123", "123", 0l, 1l);
		mongoTemplate.save(listing, ListingServiceImpl.LISTING_COLLECTION_NAME);
		
		Enquiry enquiry1 = new Enquiry("123", "email1@email.com", "Comment1");
		Enquiry enquiry2 = new Enquiry("123", "email2@email.com", "Comment2");
		
		mongoTemplate.save(enquiry1, EnquiryServiceImpl.ENQUIRY_COLLECTION_NAME);
		mongoTemplate.save(enquiry2, EnquiryServiceImpl.ENQUIRY_COLLECTION_NAME);
		
		mockMvc.perform(delete("/enquiries").contentType(MediaType.APPLICATION_JSON)
				.param("queryKey", "listingId")
				.param("queryValue", "123"))
				.andExpect(status().isOk());
		
		Assert.assertEquals(null,mongoTemplate.findById(enquiry1.getId(), Enquiry.class));
		Assert.assertEquals(null,mongoTemplate.findById(enquiry2.getId(), Enquiry.class));
	}
	
	@Test
	public void testUpdateEnquiries() throws Exception {
		
		Listing listing = new Listing("title", "description", 35.0d, "123", "123", 0l, 1l);
		mongoTemplate.save(listing, ListingServiceImpl.LISTING_COLLECTION_NAME);
		
		Enquiry enquiry1 = new Enquiry("123", "email1@email.com", "Comment1");
		Enquiry enquiry2 = new Enquiry("123", "email2@email.com", "Comment2");
		
		mongoTemplate.save(enquiry1, EnquiryServiceImpl.ENQUIRY_COLLECTION_NAME);
		mongoTemplate.save(enquiry2, EnquiryServiceImpl.ENQUIRY_COLLECTION_NAME);
		
		mockMvc.perform(put("/enquiries").contentType(MediaType.APPLICATION_JSON)
				.param("queryKey", "listingId")
				.param("queryValue", "123")
				.param("updateKey", "email")
				.param("updateValue", "abc@com.au"))
				.andExpect(status().isOk());
		
		Assert.assertEquals("abc@com.au",mongoTemplate.findById(enquiry1.getId(), Enquiry.class).getEmail());
		Assert.assertEquals("abc@com.au",mongoTemplate.findById(enquiry2.getId(), Enquiry.class).getEmail());

	}
}
