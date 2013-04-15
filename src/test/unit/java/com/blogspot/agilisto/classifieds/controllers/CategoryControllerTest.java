package com.blogspot.agilisto.classifieds.controllers;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.ResultMatcher;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;
import org.springframework.web.util.NestedServletException;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post; 
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.redirectedUrl; 
import static org.springframework.test.web.server.result.MockMvcResultMatchers.request; 
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.content;

import com.blogspot.agilisto.classifieds.model.Category;
import com.blogspot.agilisto.classifieds.model.Listing;
import com.blogspot.agilisto.classifieds.model.SellerIdentity;
import com.blogspot.agilisto.classifieds.mongo.services.CategoryRepository;
import com.blogspot.agilisto.classifieds.services.CategoryService;
import com.blogspot.agilisto.classifieds.services.ListingService;

public class CategoryControllerTest{
	
	private static final String CATEGORY_ID_AUTOMOTIVE_PARENT_NULL_CHILDREN = "{\"categoryId\":\"Automotive\",\"parent\":null,\"id\":null,\"children\":[]}";

	@InjectMocks
	private CategoryController controller = new CategoryController();
	
	@Mock
	CategoryService mockCategoryService;
	
	@Mock
	ListingService mockListingService;
	
	MockMvc mockMvc;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
    public void testGetCategoryDoesNotExist() throws Exception {
		
        mockMvc.perform(get("/category/Automotive"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
	
	@Test
	public void testGetCategoryDoesExist() throws Exception {
		Category category = new Category("Automotive", null);
		Mockito.when(mockCategoryService.getCategory("Automotive")).thenReturn(category);
		
		mockMvc.perform(get("/category/Automotive"))
        .andExpect(status().isOk())
        .andExpect(content().string(CATEGORY_ID_AUTOMOTIVE_PARENT_NULL_CHILDREN));
	}
	
	@Test
	public void testCreateNewCategoryWithNullParent() throws Exception {
		mockMvc.perform(post("/category").contentType(MediaType.APPLICATION_JSON)
				.param("categoryId", "Automotive")
				.param("parentId", ""))
				.andExpect(status().isCreated());
		
		Mockito.verify(mockCategoryService).getCategory("");
		Mockito.verify(mockCategoryService).save("Automotive", null);
	}
	
	@Test
	public void testCreateNewCategoryWithParent() throws Exception {
		
		String parentCategoryId = "Automotive";
		String childCategoryId = "BMW";
		
		Category parent = new Category(parentCategoryId, null);
		Category category = new Category(childCategoryId, parent);
		
		Mockito.stub(mockCategoryService.getCategory(parentCategoryId)).toReturn(parent);
		Mockito.stub(mockCategoryService.save(childCategoryId, parent)).toReturn(category);
		
		mockMvc.perform(post("/category").contentType(MediaType.APPLICATION_JSON)
				.param("categoryId", childCategoryId)
				.param("parentId", parentCategoryId))
				.andExpect(status().isCreated());

		Mockito.verify(mockCategoryService).getCategory(parentCategoryId);
		Mockito.verify(mockCategoryService).save(childCategoryId, parent);
		Mockito.verify(mockCategoryService).updateCategory(Mockito.eq(parentCategoryId), Mockito.eq("children"), Mockito.any(List.class));
	}
	
	@Test
	public void testDeleteCategory() throws Exception {
		Category category = new Category("Automotive", null);
		Mockito.stub(mockCategoryService.getCategory("Automotive")).toReturn(category);
		
		mockMvc.perform(delete("/category").param("categoryId", "Automotive"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteCategoryWithChildrenExpectExceptionThrown() throws Exception {
		Category parent = new Category("Automotive", null);
		
		Vector<Category> children = new Vector<Category>();
		Category child = new Category("BMW", parent);
		children.add(child);
		
		parent.setChildren(children);
		
		Mockito.stub(mockCategoryService.getCategory("Automotive")).toReturn(parent);
		
		try {
			mockMvc.perform(delete("/category").param("categoryId", "Automotive"));
		}
		catch(Exception ex) {
			Assert.assertTrue(ex.getMessage().contains("Category can not be deleted when it has children associated with it"));
			return;
		}
		
		Assert.assertFalse("Should have thrown an exception", true);
	}
	
	@Test
	public void testDeleteCategoryWithListingsExpectExceptionThrown() throws Exception {
		Vector<Listing> listings = new Vector<Listing>();
		listings.add(new Listing("title", "description", 30.0, null, null, null, null));
		
		Mockito.stub(mockListingService.getListings(Mockito.anyString(), Mockito.any(Category.class))).toReturn(listings);
				
		try {
			mockMvc.perform(delete("/category").param("categoryId", "Automotive"));
		}
		catch(Exception ex) {
			Assert.assertTrue(ex.getMessage().contains("Category can not be deleted when it has listings associated with it"));
			return;
		}
		
		Assert.assertFalse("Should have thrown an exception", true);
	}
}
