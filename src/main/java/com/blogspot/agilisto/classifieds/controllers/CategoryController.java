package com.blogspot.agilisto.classifieds.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.blogspot.agilisto.classifieds.model.Category;
import com.blogspot.agilisto.classifieds.services.CategoryService;

@Controller
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;

	@ResponseBody
	@RequestMapping(value = "/category", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void createNewCategory(@RequestParam("categoryId")String categoryId, @RequestParam("parentId")String parentId)
	{
		//Find the parent
		Category parent = categoryService.getCategory(parentId);
		
		//Create the new category
		Category category = new Category(categoryId, parent);
		categoryService.save(category);
		
		//Add new category as a child to the parent
		List<Category> childCategories = parent.getChildren();
		childCategories.add(category);
		
		//Update parent in database
		Update update = new Update();
		update.set("children", childCategories);
		categoryService.updateCategory(parentId, update);
	}
	
	@ResponseBody
	@RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET, produces = "application/json")
	public Category getCategory(@PathVariable("categoryId")String categoryId)
	{
		return categoryService.getCategory(categoryId);
	}
	
	@ResponseBody
	@RequestMapping(value = "/category/{categoryId}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteCategory(@PathVariable("categoryId")String categoryId)
	{
		Category category = categoryService.getCategory(categoryId);
		List<Category> children = category.getChildren();
		categoryService.deleteCategory(categoryId);
		//TODO I need a cascading delete here
	}
}
