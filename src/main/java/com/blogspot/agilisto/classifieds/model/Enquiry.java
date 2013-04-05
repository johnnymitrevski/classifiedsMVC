package com.blogspot.agilisto.classifieds.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Enquiry domain class<br>
 * <li>Used to represent enquiries made on listings<br>
 */
@Document(collection = "Enquiry")
public class Enquiry {
	
	private String id;
	private String listingId;
	private String email;
	private String comments;
	
	public Enquiry(String listingId, String email, String comments)
	{
		this.listingId = listingId;
		this.email = email;
		this.comments = comments;
	}
	
	public String getId() {
		return id;
	}
	
	public String getListingId() {
		return listingId;
	}
	
	public void setListingId(String listingId) {
		this.listingId = listingId;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
}
