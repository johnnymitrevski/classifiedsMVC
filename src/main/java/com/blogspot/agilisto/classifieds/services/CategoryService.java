package com.blogspot.agilisto.classifieds.services;

import com.blogspot.agilisto.classifieds.model.Category;

/**
 * CRUD service for Category domain model
 */
public interface CategoryService {
	
	/**
	 * Store the Category record.
	 * 
	 * @param categoryId The name of the category to create
	 * @param parentCategoryId The name of the parent category
	 * @return The <code>category</code> of the record created.
	 */
	Category save(String categoryId, Category parentCategoryId);
	
	/**
	 * Get the Category record.
	 * 
	 * @param categoryId The name of the category to find
	 * @return <code>category</code> of the record found.
	 */
	Category getCategory(String categoryId);
	
	/**
	 * Update a category record.
	 * 
	 * @param categoryId The name of the category to update.
	 * @param updateKey The field in {@link Category} to update.
	 * @param updateValue The object to update.
	 */
	void updateCategory(String categoryId, String updateKey, Object updateValue);
	
	/**
	 * Delete a category record.
	 * 
	 * @param categoryId The name of the category to delete.
	 */
	void deleteCategory(String categoryId);
}