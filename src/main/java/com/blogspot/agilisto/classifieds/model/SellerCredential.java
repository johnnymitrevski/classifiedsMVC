package com.blogspot.agilisto.classifieds.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * SellerCredential domain class<br>
 * <li>Used to represent the username and password information set of a seller.<br>
 */
@Document(collection = "SellerCredential")
public class SellerCredential implements Serializable{
	
	@Id
	String username;
	String password;
	
	public SellerCredential(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
