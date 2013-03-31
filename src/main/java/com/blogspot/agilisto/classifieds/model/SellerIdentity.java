package com.blogspot.agilisto.classifieds.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * SellerIdentity domain class<br>
 * <li>Used to represent the information set of a seller.<br>
 */
@Document
public class SellerIdentity implements Serializable{

	@Id
	SellerCredential sellerCredential;
	String email;
	String firstName;
	String lastName;
	String street;
	String suburb;
	String state;
	String postcode;
	String country;	
	double[] location;
	String phoneNumber;
	
	public SellerCredential getSellerCredential() {
		return sellerCredential;
	}
	
	public void setSellerCredential(SellerCredential sellerCredential) {
		this.sellerCredential = sellerCredential;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getSuburb() {
		return suburb;
	}
	
	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getPostcode() {
		return postcode;
	}
	
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public double[] getLocation() {
		return location;
	}
	
	public void setLocation(double[] location) {
		this.location = location;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
