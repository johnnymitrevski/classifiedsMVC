package com.blogspot.agilisto.classifieds.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Category domain class<br>
 * <li>Used to represent the listing categories. It uses the modal tree structure with a List of child references.<br>
 */
@Document(collection = "Category")
public class Category implements Serializable {
	
	@Id
	private String category;
	private List<Category> children;
	
	public Category(String category, List<Category> children)
	{
		this.category = category;
		this.children = children;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public List<Category> getChildren() {
		return children;
	}
	
	public void setChildren(List<Category> children) {
		this.children = children;
	}
}
