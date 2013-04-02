package com.blogspot.agilisto.classifieds.mongo.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
	public void save(Category category) {
		mongoTemplate.save(category, CATEGORY_COLLECTION_NAME);
	}

	@Override
	public Category getCategory(String categoryId) {
		return mongoTemplate.findById(categoryId, Category.class, CATEGORY_COLLECTION_NAME);
	}

	@Override
	public void updateCategory(String categoryId, Update update) {
		mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(categoryId)), update, CATEGORY_COLLECTION_NAME);
	}

	@Override
	public void deleteCategory(String categoryId) {
		Category category = this.getCategory(categoryId);
		mongoTemplate.remove(category, CATEGORY_COLLECTION_NAME);
	}
	
	 @PostConstruct
	    private void init() {
		 Category category = new Category("Automotive", null);
		 mongoTemplate.save(category);
	 }

}
