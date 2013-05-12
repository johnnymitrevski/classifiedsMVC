package com.blogspot.agilisto.classifieds.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerHandler extends ResponseEntityExceptionHandler  {

	@ExceptionHandler({ClassifiedsBadRequestException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public String handleValidationException(ClassifiedsBadRequestException cbre) 
	{
		return cbre.getMessage();
	}
}
