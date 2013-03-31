package com.blogspot.agilisto.classifieds.services.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.blogspot.agilisto.classifieds.model.Category;
import com.blogspot.agilisto.classifieds.services.CategoryService;

/**
 * Concrete implementation of the {@link CategoryService} CRUD interface using mongoDB 
 */
@Service
public class CategoryRepository implements CategoryService {

	@Autowired
    MongoTemplate mongoTemplate;
	
	public static String CATEGORY_COLLECTION_NAME = "Category";
	
	@Override
	public Category save(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category getCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category updateCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCategory(Category category) {
		// TODO Auto-generated method stub

	}

}
