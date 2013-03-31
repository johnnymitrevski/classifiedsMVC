package com.blogspot.agilisto.classifieds.services;

import com.blogspot.agilisto.classifieds.model.Category;

/**
 * CRUD interface for Category domain model
 */
public interface CategoryService {
	Category save(Category category);
	
	Category getCategory(String category);
	
	Category updateCategory(Category category);
	
	void deleteCategory(Category category);
}