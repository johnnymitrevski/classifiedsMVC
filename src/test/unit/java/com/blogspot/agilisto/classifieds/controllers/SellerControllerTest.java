package com.blogspot.agilisto.classifieds.controllers;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;

import com.blogspot.agilisto.classifieds.model.SellerIdentity;
import com.blogspot.agilisto.classifieds.services.SellerCredentialService;
import com.blogspot.agilisto.classifieds.services.SellerIdentityService;

public class SellerControllerTest {
	
	@InjectMocks
	SellerController controller = new SellerController();
	
	@Mock
	SellerIdentityService sellerIdentityService;
	
	@Mock
	SellerCredentialService sellerCredentialService;
	
	MockMvc mockMvc;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testDoesUsernameExistIsFalseWhenNotPresent() throws Exception {
		mockMvc.perform(get("/checkUserExists").contentType(MediaType.APPLICATION_JSON)
				.param("username", "johnDoe"))
				.andExpect(status().isOk())
				.andExpect(content().string("false"));
	}
	
	@Test
	public void testDoesUsernameExistIsTrueWhenPresent() throws Exception {
		Mockito.stub(sellerCredentialService.doesUsernameExist("johnDoe")).toReturn(true);
		
		mockMvc.perform(get("/checkUserExists").contentType(MediaType.APPLICATION_JSON)
				.param("username", "johnDoe"))
				.andExpect(status().isOk())
				.andExpect(content().string("true"));
	}
	
	@Test
	public void testCreateNewListingSameUsernamePassword() throws Exception {
		
		try
		{
			mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
					.param("username", "johnDoe")
					.param("password", "johnDoe")
					.param("email", "email@com")
					.param("firstName", "John")
					.param("lastName", "Doe")
					.param("street", "")
					.param("suburb", "")
					.param("state", "")
					.param("postcode", "")
					.param("country", "")
					.param("latitude", "")
					.param("longitude", "")
					.param("phoneNumber", ""))
					.andExpect(status().isCreated());
		}
		catch(Exception e)
		{
			Assert.assertTrue(e.getMessage().contains("username / password must be different"));
			return;
		}
		
		Assert.assertFalse("Expect exception to be thrown", true);
	}
	
	@Test
	public void testCreateNewListingEmptyUsername() throws Exception {
		
		try
		{
			mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
					.param("username", "")
					.param("password", "password")
					.param("email", "email@com")
					.param("firstName", "John")
					.param("lastName", "Doe")
					.param("street", "")
					.param("suburb", "")
					.param("state", "")
					.param("postcode", "")
					.param("country", "")
					.param("latitude", "")
					.param("longitude", "")
					.param("phoneNumber", ""))
					.andExpect(status().isCreated());
		}
		catch(Exception e)
		{
			Assert.assertTrue(e.getMessage().contains("Empty fields not allowed"));
			return;
		}
		
		Assert.assertFalse("Expect exception to be thrown", true);
	}
	
	@Test
	public void testCreateNewListingPasswordLessThan5Chars() throws Exception {
		
		try
		{
			mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
					.param("username", "username")
					.param("password", "1234")
					.param("email", "email@com")
					.param("firstName", "John")
					.param("lastName", "Doe")
					.param("street", "")
					.param("suburb", "")
					.param("state", "")
					.param("postcode", "")
					.param("country", "")
					.param("latitude", "")
					.param("longitude", "")
					.param("phoneNumber", ""))
					.andExpect(status().isCreated());
		}
		catch(Exception e)
		{
			Assert.assertTrue(e.getMessage().contains("username/password must be greater than 5 characters"));
			return;
		}
		
		Assert.assertFalse("Expect exception to be thrown", true);
	}
	
	@Test
	public void testCreateNewListing() throws Exception {
		Mockito.stub(sellerIdentityService.save(Matchers.isA(SellerIdentity.class))).toReturn("1234");
		mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
				.param("username", "username")
				.param("password", "password")
				.param("email", "email@com")
				.param("firstName", "John")
				.param("lastName", "Doe")
				.param("street", "")
				.param("suburb", "")
				.param("state", "")
				.param("postcode", "")
				.param("country", "")
				.param("latitude", "45")
				.param("longitude", "55")
				.param("phoneNumber", ""))
				.andExpect(status().isCreated())
				.andExpect(content().string("1234"));
	}
	
	@Test
	public void testUpdateUserLocationFailsUsingInvalidDelimeter() throws Exception
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
		
		try
		{
			mockMvc.perform(put("/user").contentType(MediaType.APPLICATION_JSON)
				.param("username", "johnDoe")
				.param("userKey", "location")
				.param("userValue", "90.00:89.00"))
				.andExpect(status().isOk());
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().contains("Invalid location paramater: 90.00:89.00. Must be delimited with a :"));
			return;
		}
		
		Assert.assertFalse("Expected exception to ne thrown", true);
	}
	
	@Test 
	public void getNewListing() throws Exception {
		SellerIdentity sellerId = new SellerIdentity("jojo", null, null, null, null, null, null, null, null, null, null);
		Mockito.stub(sellerIdentityService.getSellerIdentity("jojo")).toReturn(sellerId);
		mockMvc.perform(get("/user").contentType(MediaType.APPLICATION_JSON)
				.param("username", "jojo"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString(":\"jojo")));
	}
	
	@Test
	public void deleteSellerNotFound() throws Exception {
		try
		{
			mockMvc.perform(delete("/user").contentType(MediaType.APPLICATION_JSON)
				.param("username", "johnDoe"));
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().contains("username: johnDoe does not exist"));
			return;
		}
		
		Assert.assertFalse("Exception expected", true);
	}

}
