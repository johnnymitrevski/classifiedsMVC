package com.blogspot.agilisto.classifieds.mongo.services;

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
public class CategoryServiceImpl implements CategoryService {

	@Autowired
    MongoTemplate mongoTemplate;
	
	public static String CATEGORY_COLLECTION_NAME = "Category";
	
	@Override
	public Category save(String categoryId, Category parentCategory) {
		Category category = new Category(categoryId, parentCategory);
		mongoTemplate.save(category, CATEGORY_COLLECTION_NAME);
		return category;
	}

	@Override
	public Category getCategory(String categoryId) {
		return mongoTemplate.findOne(findCategoryQuery(categoryId), Category.class, CATEGORY_COLLECTION_NAME);
	}
	
	private Query findCategoryQuery(String categoryId) {
		return new Query(Criteria.where("categoryId").is(categoryId));
	}

	@Override
	public void updateCategory(String categoryId, String updateKey, Object updateValue) {
		Update update = new Update();
		update.set(updateKey, updateValue);
		mongoTemplate.updateFirst(findCategoryQuery(categoryId), update, CATEGORY_COLLECTION_NAME);
	}

	@Override
	public void deleteCategory(String categoryId) {
		mongoTemplate.remove(findCategoryQuery(categoryId), CATEGORY_COLLECTION_NAME);
	}
}
