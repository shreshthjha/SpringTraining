package com.coforge.training.productrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
* Author :Shreshth.Jha
* Date :Aug 4, 2025
* Time :2:24:05 PM
* Project :product-restapi

*/

/*ResourceNotFoundException - Custom Exception class
 * It is used to signal when a requested resource (such as a product with a specific ID) 
 * is not available in the database or other data sources.
 * By creating a custom exception, you can provide more meaningful error messages and 
 * handle specific cases of missing resources in a standardized way.
 */

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
	
}
