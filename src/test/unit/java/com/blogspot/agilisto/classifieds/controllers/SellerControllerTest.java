package com.blogspot.agilisto.classifieds.controllers;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;

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

	
}
