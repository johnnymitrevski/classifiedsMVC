package com.blogspot.agilisto.classifieds.services;

import org.springframework.data.mongodb.core.query.Update;

import com.blogspot.agilisto.classifieds.model.Category;

/**
 * CRUD interface for Category domain model
 */
public interface CategoryService {
	void save(Category category);
	
	Category getCategory(String categoryId);
	
	void updateCategory(String categoryId, Update update);
	
	void deleteCategory(String categoryId);
}