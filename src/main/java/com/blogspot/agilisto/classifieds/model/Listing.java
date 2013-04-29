package com.blogspot.agilisto.classifieds.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Listing domain class.<br>
 * <li>Used to represent a listing on the site.<br>
 */
@Document(collection = "Listing")
public class Listing {
	
	@Id
	private String id;
	private String title;
	private String description;
	private double price;
	private String categoryForiegnKey;
	private String sellerIdentityForiegnKey;
	private long listingTime;
	private long expiryTime;
	
	public Listing(String title, String description, double price, String categoryForiegnKey, String sellerIdentityForiegnKey,
					long listingTime, long expiryTime)
	{
		this.title = title;
		this.description = description;
		this.price = price;
		this.categoryForiegnKey = categoryForiegnKey;
		this.sellerIdentityForiegnKey = sellerIdentityForiegnKey;
		this.listingTime = listingTime;
		this.expiryTime = expiryTime;
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
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getCategoryForiegnKey() {
		return categoryForiegnKey;
	}
	
	public void setCategoryForiegnKey(String categoryForiegnKey) {
		this.categoryForiegnKey = categoryForiegnKey;
	}
	
	public String getSellerIdentityForiegnKey() {
		return sellerIdentityForiegnKey;
	}
	
	public void setSellerIdentity(String sellerIdentityForiegnKey) {
		this.sellerIdentityForiegnKey = sellerIdentityForiegnKey;
	}
	
	public long getListingTime() {
		return listingTime;
	}
	
	public void setListingTime(long listingTime) {
		this.listingTime = listingTime;
	}
	
	public long getExpiryTime() {
		return expiryTime;
	}
	
	public void setExpiryTime(long expiryTime) {
		this.expiryTime = expiryTime;
	}
}
