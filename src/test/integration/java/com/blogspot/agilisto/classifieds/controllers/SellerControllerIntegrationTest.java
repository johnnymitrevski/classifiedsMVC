package com.blogspot.agilisto.classifieds.controllers;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;

import java.util.List;

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
import org.springframework.web.context.WebApplicationContext;

import com.blogspot.agilisto.classifieds.model.Listing;
import com.blogspot.agilisto.classifieds.model.SellerCredential;
import com.blogspot.agilisto.classifieds.model.SellerIdentity;
import com.blogspot.agilisto.classifieds.mongo.services.ListingServiceImpl;
import com.blogspot.agilisto.classifieds.services.ListingService;
import com.blogspot.agilisto.classifieds.services.SellerCredentialService;
import com.blogspot.agilisto.classifieds.services.SellerIdentityService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-servlet-context.xml", "/test-mongo-context.xml"})
@WebAppConfiguration
public class SellerControllerIntegrationTest {

	@Autowired
	SellerCredentialService sellerCredentialService;
	
	@Autowired
	SellerIdentityService sellerIdentityService;
	
	@Autowired
	ListingService listingService;
	
	@Autowired
    WebApplicationContext wac;
	
	@Autowired
    MongoTemplate mongoTemplate;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.webApplicationContextSetup(wac).build();
	}
	
	@Test
	public void testDoesUsernameExistFalse() throws Exception
	{
		mockMvc.perform(get("/checkUserExists").contentType(MediaType.APPLICATION_JSON)
				.param("username", "johnDoe"))
				.andExpect(status().isOk())
				.andExpect(content().string("false"));
	}
	
	@Test
	public void testDoesUsernameExist() throws Exception
	{
		mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
				.param("username", "johnDoe")
				.param("password", "password")
				.param("email", "email")
				.param("firstName", "firstName")
				.param("lastName", "lastName")
				.param("street", "street")
				.param("suburb", "suburb")
				.param("state", "state")
				.param("postcode", "postcode")
				.param("country", "country")
				.param("latitude", "56.10")
				.param("longitude", "104.56")
				.param("phoneNumber", "0404494309"))
				.andExpect(status().isCreated());
		
		mockMvc.perform(get("/checkUserExists").contentType(MediaType.APPLICATION_JSON)
				.param("username", "johnDoe"))
				.andExpect(status().isOk())
				.andExpect(content().string("true"));
	}
	
	@Test
	public void testCreateUser() throws Exception
	{
		mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
				.param("username", "johnDoe")
				.param("password", "password")
				.param("email", "email")
				.param("firstName", "firstName")
				.param("lastName", "lastName")
				.param("street", "street")
				.param("suburb", "suburb")
				.param("state", "state")
				.param("postcode", "postcode")
				.param("country", "country")
				.param("latitude", "56.10")
				.param("longitude", "104.56")
				.param("phoneNumber", "0404494123"))
				.andExpect(status().isCreated());
		SellerIdentity sellerIdentity = mongoTemplate.findOne(new Query(Criteria.where("username").is("johnDoe")), SellerIdentity.class);
		SellerCredential sellerCredential = mongoTemplate.findOne(new Query(Criteria.where("username").is("johnDoe")), SellerCredential.class);
		
		Assert.assertEquals(sellerIdentity.getUsername(), "johnDoe");
		Assert.assertEquals(sellerIdentity.getCountry(), "country");
		Assert.assertEquals(sellerIdentity.getEmail(), "email");
		Assert.assertEquals(sellerIdentity.getFirstName(), "firstName");
		Assert.assertEquals(sellerIdentity.getLastName(), "lastName");
		Assert.assertEquals(sellerIdentity.getPhoneNumber(), "0404494123");
		Assert.assertEquals(sellerIdentity.getState(), "state");
		Assert.assertEquals(sellerIdentity.getStreet(), "street");
		Assert.assertEquals(sellerIdentity.getPostcode(), "postcode");
		Assert.assertEquals(sellerCredential.getUsername(), "johnDoe");
		Assert.assertEquals(sellerCredential.getPassword(), "password");		
	}
	
	@Test
	public void testDeleteUser() throws Exception
	{		
		mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
				.param("username", "johnDoe")
				.param("password", "password")
				.param("email", "email")
				.param("firstName", "firstName")
				.param("lastName", "lastName")
				.param("street", "street")
				.param("suburb", "suburb")
				.param("state", "state")
				.param("postcode", "postcode")
				.param("country", "country")
				.param("latitude", "56.10")
				.param("longitude", "104.56")
				.param("phoneNumber", "0404494123"))
				.andExpect(status().isCreated());
		
		SellerIdentity sellerIdentity = mongoTemplate.findOne(new Query(Criteria.where("username").is("johnDoe")), SellerIdentity.class);
		
		Listing listing1 = new Listing("title1", "description1", 31.0d, "1231", sellerIdentity.getId(), 0l, 1l);
		Listing listing2 = new Listing("title2", "description2", 32.0d, "1232", sellerIdentity.getId(), 2l, 3l);
		mongoTemplate.save(listing1, ListingServiceImpl.LISTING_COLLECTION_NAME);
		mongoTemplate.save(listing2, ListingServiceImpl.LISTING_COLLECTION_NAME);
		
		mockMvc.perform(delete("/user").contentType(MediaType.APPLICATION_JSON)
				.param("username", "johnDoe"));
		
		sellerIdentity = mongoTemplate.findOne(new Query(Criteria.where("username").is("johnDoe")), SellerIdentity.class);
		SellerCredential sellerCredential = mongoTemplate.findOne(new Query(Criteria.where("username").is("johnDoe")), SellerCredential.class);
		List<Listing> listings = mongoTemplate.findAll(Listing.class);

		Assert.assertEquals(sellerIdentity, null);
		Assert.assertEquals(sellerCredential, null);
		Assert.assertEquals(listings.size(), 0);
	}
	
	@Test
	public void testUpdateUserPassword() throws Exception
	{
		mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
				.param("username", "johnDoe")
				.param("password", "password")
				.param("email", "email")
				.param("firstName", "firstName")
				.param("lastName", "lastName")
				.param("street", "street")
				.param("suburb", "suburb")
				.param("state", "state")
				.param("postcode", "postcode")
				.param("country", "country")
				.param("latitude", "56.10")
				.param("longitude", "104.56")
				.param("phoneNumber", "0404494123"))
				.andExpect(status().isCreated());
		
		mockMvc.perform(put("/user").contentType(MediaType.APPLICATION_JSON)
				.param("username", "johnDoe")
				.param("userKey", "password")
				.param("userValue", "password2"))
				.andExpect(status().isOk());
		
		SellerIdentity sellerIdentity = mongoTemplate.findOne(new Query(Criteria.where("username").is("johnDoe")), SellerIdentity.class);
		SellerCredential sellerCredential = mongoTemplate.findOne(new Query(Criteria.where("username").is("johnDoe")), SellerCredential.class);
		
		Assert.assertEquals(sellerIdentity.getUsername(), "johnDoe");
		Assert.assertEquals(sellerIdentity.getCountry(), "country");
		Assert.assertEquals(sellerIdentity.getEmail(), "email");
		Assert.assertEquals(sellerIdentity.getFirstName(), "firstName");
		Assert.assertEquals(sellerIdentity.getLastName(), "lastName");
		Assert.assertEquals(sellerIdentity.getPhoneNumber(), "0404494123");
		Assert.assertEquals(sellerIdentity.getState(), "state");
		Assert.assertEquals(sellerIdentity.getStreet(), "street");
		Assert.assertEquals(sellerIdentity.getPostcode(), "postcode");
		Assert.assertEquals(sellerCredential.getUsername(), "johnDoe");
		Assert.assertEquals(sellerCredential.getPassword(), "password2");		
	}
	
	@Test
	public void testUpdateUserUsername() throws Exception
	{
		mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
				.param("username", "johnDoe")
				.param("password", "password")
				.param("email", "email")
				.param("firstName", "firstName")
				.param("lastName", "lastName")
				.param("street", "street")
				.param("suburb", "suburb")
				.param("state", "state")
				.param("postcode", "postcode")
				.param("country", "country")
				.param("latitude", "56.10")
				.param("longitude", "104.56")
				.param("phoneNumber", "0404494123"))
				.andExpect(status().isCreated());
		
		mockMvc.perform(put("/user").contentType(MediaType.APPLICATION_JSON)
				.param("username", "johnDoe")
				.param("userKey", "username")
				.param("userValue", "johnDoe2"))
				.andExpect(status().isOk());
		
		SellerIdentity sellerIdentity = mongoTemplate.findOne(new Query(Criteria.where("username").is("johnDoe2")), SellerIdentity.class);
		SellerCredential sellerCredential = mongoTemplate.findOne(new Query(Criteria.where("username").is("johnDoe2")), SellerCredential.class);
		
		Assert.assertEquals(sellerIdentity.getUsername(), "johnDoe2");
		Assert.assertEquals(sellerIdentity.getCountry(), "country");
		Assert.assertEquals(sellerIdentity.getEmail(), "email");
		Assert.assertEquals(sellerIdentity.getFirstName(), "firstName");
		Assert.assertEquals(sellerIdentity.getLastName(), "lastName");
		Assert.assertEquals(sellerIdentity.getPhoneNumber(), "0404494123");
		Assert.assertEquals(sellerIdentity.getState(), "state");
		Assert.assertEquals(sellerIdentity.getStreet(), "street");
		Assert.assertEquals(sellerIdentity.getPostcode(), "postcode");
		Assert.assertEquals(sellerIdentity.getSuburb(), "suburb");
		Assert.assertEquals(sellerCredential.getUsername(), "johnDoe2");
		Assert.assertEquals(sellerCredential.getPassword(), "password");		
	}
	
	@Test
	public void testUpdateUsernamePassword() throws Exception
	{
		mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
				.param("username", "johnDoe")
				.param("password", "password")
				.param("email", "email")
				.param("firstName", "firstName")
				.param("lastName", "lastName")
				.param("street", "street")
				.param("suburb", "suburb")
				.param("state", "state")
				.param("postcode", "postcode")
				.param("country", "country")
				.param("latitude", "56.10")
				.param("longitude", "104.56")
				.param("phoneNumber", "0404494123"))
				.andExpect(status().isCreated());
		
		mockMvc.perform(get("/validateUsernamePassword").contentType(MediaType.APPLICATION_JSON)
				.param("username", "johnDoe")
				.param("password", "password"))
				.andExpect(status().isOk())
				.andExpect(content().string("true"));
	}
	
	@Test
	public void testCreateDuplicateUser() throws Exception
	{
		mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
				.param("username", "johnDoe")
				.param("password", "password")
				.param("email", "email")
				.param("firstName", "firstName")
				.param("lastName", "lastName")
				.param("street", "street")
				.param("suburb", "suburb")
				.param("state", "state")
				.param("postcode", "postcode")
				.param("country", "country")
				.param("latitude", "56.10")
				.param("longitude", "104.56")
				.param("phoneNumber", "0404494123"))
				.andExpect(status().isCreated());
		
		mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
				.param("username", "johnDoe")
				.param("password", "password")
				.param("email", "email")
				.param("firstName", "firstName")
				.param("lastName", "lastName")
				.param("street", "street")
				.param("suburb", "suburb")
				.param("state", "state")
				.param("postcode", "postcode")
				.param("country", "country")
				.param("latitude", "56.10")
				.param("longitude", "104.56")
				.param("phoneNumber", "0404494123"))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("{\"Bad Request Error\": \"username: johnDoe already exists\"}"));
	}
	
	@After
	public void dropDatabase() throws Exception {
		
		mongoTemplate.dropCollection("Category");
		mongoTemplate.dropCollection("Listing");
		mongoTemplate.dropCollection("SellerIdentity");
		mongoTemplate.dropCollection("SellerCredential");
	}
	
}
