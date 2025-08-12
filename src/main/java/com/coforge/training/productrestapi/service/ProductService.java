package com.coforge.training.productrestapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


import com.coforge.training.productrestapi.model.Product;
import com.coforge.training.productrestapi.repository.ProductRepository;

/**
* Author :Shreshth.Jha
* Date :Aug 4, 2025
* Time :12:02:25 PM
* Project :product-restapi

@Service annotation is a stereotype annotation that marks a class as a service layer component. 
 * It's one of the core annotations used to structure your application and enable dependency injection.
 * 
 * The @Service annotation tells Spring that the annotated class contains business logic. 
 * It's typically where you'll implement the core functionality of your application, 
 * such as calculations, data retrieval, or external API interactions.

*/

@Service
public class ProductService {
	
	private final ProductRepository prepo;

	public ProductService(ProductRepository prepo) {
		super();
		this.prepo = prepo;
	}
	
	public Product saveProduct(Product p) {
		return prepo.save(p); //invokes pre defined method save() of Jpa Repository
	}
	
	public List<Product> listAll(){
		return prepo.findAll();
	}
	
	// Optional return type is to handle Null Pointer Exception
	   public Optional<Product> getSingleProduct(long pid) {
		   return prepo.findById(pid);            //Invokes pre-defined method findById() of JPA repository
	   }
	   
	   
	   
	   public void deleteProduct(long pid) {
		   prepo.deleteById(pid); //Invokes pre-defined method deleteById() of JPA repository
	   }
	   
	   public List<Product> searchProductsByName(String name){
		   return prepo.findProductsByNameContainingIgnoreCase(name);
	   }
}
