package com.blogspot.agilisto.classifieds.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Listing domain class.<br>
 * <li>Used to represent a listing on the site.<br>
 */
@Document(collection = "Listing")
public class Listing implements Serializable{
	
	private String id;
	private String title;
	private String description;
	private Double price;
	private Category category;
	private SellerIdentity sellerIdentity;
	private Date listingDate;
	private Date expiryDate;
	
	public Listing(String title, String description, Double price, Category category, SellerIdentity sellerIdentity,
					Date listingDate, Date expiryDate)
	{
		this.title = title;
		this.description = description;
		this.price = price;
		this.category = category;
		this.sellerIdentity = sellerIdentity;
		this.listingDate = listingDate;
		this.expiryDate = expiryDate;
	}
	
	public String getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public SellerIdentity getSellerIdentity() {
		return sellerIdentity;
	}
	
	public void setSellerIdentity(SellerIdentity sellerIdentity) {
		this.sellerIdentity = sellerIdentity;
	}
	
	public Date getListingDate() {
		return listingDate;
	}
	
	public void setListingDate(Date listingDate) {
		this.listingDate = listingDate;
	}
	
	public Date getExpiryDate() {
		return expiryDate;
	}
	
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
}
