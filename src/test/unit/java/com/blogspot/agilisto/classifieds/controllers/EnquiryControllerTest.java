package com.blogspot.agilisto.classifieds.controllers;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;

import org.junit.Assert;
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

import com.blogspot.agilisto.classifieds.model.Enquiry;
import com.blogspot.agilisto.classifieds.model.Listing;
import com.blogspot.agilisto.classifieds.services.EnquiryService;
import com.blogspot.agilisto.classifieds.services.ListingService;

public class EnquiryControllerTest {

	@InjectMocks
	private EnquiryController controller = new EnquiryController();
	
	@Mock
	ListingService listingService;
	
	@Mock
	EnquiryService enquiryService;
	
	MockMvc mockMvc;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	public void testCreateNewEnquiry() throws Exception {
		
		Mockito.when(listingService.getListing("123")).thenReturn(new Listing(null, null, 0, null, null, 0, 0));
		
		mockMvc.perform(post("/enquiry").contentType(MediaType.APPLICATION_JSON)
				.param("listingId", "123")
				.param("email", "email@my.com")
				.param("comment", "A short comment"))
				.andExpect(status().isCreated());
		
		Mockito.verify(enquiryService).save(Matchers.isA(Enquiry.class));
	}
	
	@Test
	public void testCreateNewEnquiryWithNullListing() throws Exception {
		
		try
		{
			mockMvc.perform(post("/enquiry").contentType(MediaType.APPLICATION_JSON)
					.param("listingId", "123")
					.param("email", "email@my.com")
					.param("comment", "A short comment"))
					.andExpect(status().isCreated());
		}
		catch(Exception e)
		{
			return;
		}
		
		Assert.assertFalse("Exception expected", true);
	}
	
	@Test 
	public void testGetEnquiries() throws Exception {
		mockMvc.perform(get("/enquiries").contentType(MediaType.APPLICATION_JSON)
				.param("listingId", "123"))
				.andExpect(status().isOk());
		
		Mockito.verify(enquiryService).getEnquiries("listingId", "123");
	}
	
	@Test
	public void testDeleteEnquiry() throws Exception {
		mockMvc.perform(delete("/enquiry").contentType(MediaType.APPLICATION_JSON)
				.param("id", "123"))
				.andExpect(status().isOk());
		
		Mockito.verify(enquiryService).deleteEnquiry("123");
	}
	
	@Test
	public void testDeleteEnquiries() throws Exception {
		mockMvc.perform(delete("/enquiries").contentType(MediaType.APPLICATION_JSON)
				.param("queryKey", "listingId")
				.param("queryValue", "123"))
				.andExpect(status().isOk());
		
		Mockito.verify(enquiryService).deleteEnquiries("listingId","123");
	}
	
	@Test
	public void testUpdateEnquiries() throws Exception {
		mockMvc.perform(put("/enquiries").contentType(MediaType.APPLICATION_JSON)
				.param("queryKey", "listingId")
				.param("queryValue", "123")
				.param("updateKey", "email")
				.param("updateValue", "abc@com.au"))
				.andExpect(status().isOk());
		
		Mockito.verify(enquiryService).updateEnquiries("listingId","123","email","abc@com.au");
	}
}
