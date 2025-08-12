package com.coforge.training.productrestapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coforge.training.productrestapi.exception.ResourceNotFoundException;
import com.coforge.training.productrestapi.model.Product;
import com.coforge.training.productrestapi.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



/**
* Author :Shreshth.Jha
* Date :Aug 4, 2025
* Time :12:08:33 PM
* Project :product-restapi

*/

@RestController
@CrossOrigin(origins = "http://localhost:4200") 
@RequestMapping(value="/api") //base url
public class ProductController {
	
	//Field dependency Injection
	@Autowired
	private ProductService pservice;
	
	/*Spring RestController annotation is used to create RESTful web services using Spring MVC. 
	 * Spring RestController takes care of mapping request data to the defined request handler method. 
	 * Once response body is generated from the handler method, it converts it to JSON or XML response.
	 *  
	 * @RestController indicates that this class handles HTTP requests and automatically 
	 * serializes the results to JSON.
	 * 
	 * @RequestMapping - maps HTTP request with a path to a controller 
	 * */
	
	
	
	//Open PostMan, make a POST Request - http://localhost:8088/product-hive/api/products
		//Select body -> raw -> JSON 
		//Insert JSON product object.
//	@PostMapping("/products")
//	public Product saveProduct(@Validated @RequestBody Product product) {
//		
//		try {
//			Product p = pservice.saveProduct(product);
//			return p;
//		} catch(Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
	
	@PostMapping("/products")
	public ResponseEntity<Product> saveProduct(@Validated @RequestBody Product product){
		try {
			Product p= pservice.saveProduct(product);   //Invokes method in service layer
			return ResponseEntity.status(HttpStatus.CREATED).body(p);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@GetMapping("/products")
	public ResponseEntity <List<Product>> getAllProducts() {
		try {
			List<Product> products=pservice.listAll();
			return ResponseEntity.ok(products);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	

	
	

	@GetMapping("/products/{pid}")
	public ResponseEntity<Product>getProductById(@PathVariable(value="pid") Long pId) throws ResourceNotFoundException {
	     
	Product p = pservice.getSingleProduct(pId).orElseThrow(()->
	
	new ResourceNotFoundException("Product Not Found For this id :"+pId));
	
	return ResponseEntity.ok(p);
	
	}
	
	@PutMapping("/products/{pid}")
	public ResponseEntity<Product> updateProduct(@PathVariable(value="pid") Long pId, @Validated @RequestBody Product p) throws ResourceNotFoundException {
		
		
		Product product=pservice.getSingleProduct(pId).
				orElseThrow(() -> new ResourceNotFoundException("Product Not Found for this Id :"+pId));
		//Update product with new values
		product.setBrand(p.getBrand());
		product.setMadein(p.getMadein());
		product.setName(p.getName());
		product.setPrice(p.getPrice());

		final Product updatedProduct=pservice.saveProduct(product); // invokes service layer method
		return ResponseEntity.ok().body(updatedProduct);
	}
	
	@DeleteMapping("/products/{pid}")
	public ResponseEntity<Map<String,Boolean>> deleteProduct(@PathVariable(value="pid") Long pId) throws ResourceNotFoundException{
		
		pservice.getSingleProduct(pId).  // invokes service layer method
		orElseThrow(() -> new ResourceNotFoundException("Product Not Found for this Id :"+pId));

		pservice.deleteProduct(pId); // invokes service layer method

		Map<String,Boolean> response=new HashMap<>(); //Map Stores Data in key-value pairs
		response.put("Deleted", Boolean.TRUE);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/search")
    public ResponseEntity<?> searchProductsByName(@RequestParam("name") String name) {
        try {
            List<Product> products = pservice.searchProductsByName(name);
            
            if (products.isEmpty()) {
                return new ResponseEntity<>("No products found with the given name.", HttpStatus.NOT_FOUND);
            }
            
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception ex) {
        	//database error
            return new ResponseEntity<>("An error occurred while searching for products.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	// Flow www
	
	// Client (POSTMAN/Browser) --> request --->FC --> Controller ---> Service --> Repository --> JPA --> DB(MySQL)

	// DB - Response --> JPA --> Repository --> Service ---> Controller ---> FC ---> PostMan/Browser
}
