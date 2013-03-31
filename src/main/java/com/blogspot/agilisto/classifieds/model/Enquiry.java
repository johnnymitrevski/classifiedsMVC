package com.blogspot.agilisto.classifieds.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Enquiry domain class<br>
 * <li>Used to represent enquiries made on listings<br>
 */
@Document(collection = "Enquiry")
public class Enquiry implements Serializable {
	
	@Id
	private Long enquiryId;
	private Listing listing;
	private String email;
	private String description;
	
	public Long getEnquiryId() {
		return enquiryId;
	}
	
	public void setEnquiryId(Long enquiryId) {
		this.enquiryId = enquiryId;
	}
	
	public Listing getListing() {
		return listing;
	}
	
	public void setListing(Listing listing) {
		this.listing = listing;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
