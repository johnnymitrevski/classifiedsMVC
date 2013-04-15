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
	
	@Autowired
    MongoTemplate mongoTemplate;
	
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
		
		Category category = mongoTemplate.findOne(findCategoryQuery("Automotive"), Category.class);
		
		Assert.assertEquals(category.getCategoryId(), "Automotive");
		Assert.assertEquals(category.getParent(), null);
		Assert.assertEquals(category.getChildren().size(), 0);
	}

	private Query findCategoryQuery(String categoryId) {
		return new Query(Criteria.where("categoryId").is(categoryId));
	}
	
	@Test
	public void testGetCategory() throws Exception {
		
		testCreateNewCategory();		
		String categoryId = "{\"categoryId\":\"Automotive\"";
		
		mockMvc.perform(get("/category/Automotive"))
		.andExpect(status().isOk())
		.andExpect(content().string(org.junit.matchers.JUnitMatchers.containsString(categoryId)));
	}
	
	@Test
	public void testDeleteCategory() throws Exception {
		
		testCreateNewCategory();
		
		mockMvc.perform(delete("/category").param("categoryId", "Automotive"))
		.andExpect(status().isOk());
		
		Category category = mongoTemplate.findOne(findCategoryQuery("Automotive"), Category.class);
		
		Assert.assertEquals(category, null);
	}
	
	@Test
	public void testUpdateCategory() throws Exception {
		
		testCreateNewCategory();
		
		mockMvc.perform(put("/category").contentType(MediaType.APPLICATION_JSON)
				.param("categoryId", "Automotive")
				.param("updateKey", "categoryId")
				.param("updateValue", "Boats"))
				.andExpect(status().isOk());
		
		Category category = mongoTemplate.findOne(findCategoryQuery("Automotive"), Category.class);
		Assert.assertEquals(category, null);
		
		category = mongoTemplate.findOne(findCategoryQuery("Boats"), Category.class);
		
		Assert.assertEquals(category.getCategoryId(), "Boats");
		Assert.assertEquals(category.getParent(), null);
		Assert.assertEquals(category.getChildren().size(), 0);
	}
	
	@After
	public void dropDatabase() throws Exception {
		
		mongoTemplate.dropCollection("Category");
	}
 }
