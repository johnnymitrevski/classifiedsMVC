package com.blogspot.agilisto.classifieds.controllers;

public class ClassifiedsBadRequestException extends RuntimeException {

	private static final long serialVersionUID = -6604029066438967024L;

	public ClassifiedsBadRequestException(String exception) {
		super("{\"Bad Request Error\": \"" + exception + "\"}");
	}
}
