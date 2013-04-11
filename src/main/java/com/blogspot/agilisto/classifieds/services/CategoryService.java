package com.blogspot.agilisto.classifieds.services;

import com.blogspot.agilisto.classifieds.model.Category;

/**
 * CRUD interface for Category domain model
 */
public interface CategoryService {
	
	Category save(String category, Category parentCategory);
	
	Category getCategory(String categoryId);
	
	void updateCategory(String categoryId, String key, Object value);
	
	void deleteCategory(String categoryId);
}