package com.blogspot.agilisto.classifieds.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Category domain class<br>
 * <li>Used to represent the listing categories. It uses the modal tree structure with a List of child references.<br>
 */
@Document(collection = "Category")
public class Category implements Serializable {
	
	@Id
	private String categoryId;
	private Category parent;
	private List<Category> children;
	
	public Category(String categoryId, Category parent)
	{
		this.categoryId = categoryId;
		this.parent = parent;
	}
	
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}
	
	public List<Category> getChildren() {
		return children;
	}
	
	public void setChildren(List<Category> children) {
		this.children = children;
	}
}
