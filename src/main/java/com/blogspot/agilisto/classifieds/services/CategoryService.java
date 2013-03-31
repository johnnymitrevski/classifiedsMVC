package com.blogspot.agilisto.classifieds.services;

import com.blogspot.agilisto.classifieds.model.Category;

/**
 * CRUD interface for Category domain model
 */
public interface CategoryService {
	void save(Category category);
	
	Category getCategory(String category);
	
	void updateCategory(Category category);
	
	void deleteCategory(Category category);
}