package com.blogspot.agilisto.classifieds.controllers;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import com.blogspot.agilisto.classifieds.model.Category;
import com.blogspot.agilisto.classifieds.services.CategoryService;
import com.blogspot.agilisto.classifieds.services.ListingService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-servlet-context.xml", "/test-root-context.xml", "/test-mongo-context.xml"})
@WebAppConfiguration
public class CategoryControllerIntegrationTest {

	@Autowired
	private CategoryController categoryController;

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ListingService listingService;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
	}
	
	@Test
	public void testCreateNewCategory() throws Exception {
		
		mockMvc.perform(post("/category").contentType(MediaType.APPLICATION_JSON)
				.param("categoryId", "Automotive")
				.param("parentId", ""))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void testGetCategory() throws Exception {
				
		mockMvc.perform(get("/category/Automotive"))
		.andExpect(status().isOk())
		.andExpect(content().string("{\"categoryId\":\"Automotive\",\"parent\":null,\"children\":[]}"));
	}
}
