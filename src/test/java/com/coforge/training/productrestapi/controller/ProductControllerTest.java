package com.coforge.training.productrestapi.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.coforge.training.productrestapi.model.Product;
import com.coforge.training.productrestapi.service.ProductService;

/**
* Author :Shreshth.Jha
* Date :Aug 12, 2025
* Time :4:42:15 PM
* Project :product-restapi

*/

//Junit with mockito
@SpringBootTest
class ProductControllerTest {
	
	@Autowired
	private ProductController productController;
	
	@MockBean
	private ProductService pservice;
	
	Product product;
	
	
	@BeforeEach
	void setUp() throws Exception {
		product = new Product();
	}

	@AfterEach
	void tearDown() throws Exception {
		product = null;
	}

	@Test
	void testSaveProduct() {
		
		product.setName("Optical Mouse");
		product.setBrand("Logitech");
		product.setMadein("Finland");
		product.setPrice(500.00f);
		
		when(pservice.saveProduct(any(Product.class))).thenReturn(product);
		
		ResponseEntity<Product> re=productController.saveProduct(product);
		
		assertEquals(HttpStatus.CREATED,re.getStatusCode());
		assertEquals("Optical Mouse",re.getBody().getName());
		assertEquals("Logitech",re.getBody().getBrand());
		assertEquals("Finland",re.getBody().getMadein());
		assertEquals(500.00,re.getBody().getPrice());
		
		verify(pservice,times(1)).saveProduct(any(Product.class));
	}

	@Test
	void testGetAllProducts() {
		List<Product> mockProducts=new ArrayList<>();
		mockProducts.add(new Product(1L,"Pen","Reynolds","India",20.0f));
		mockProducts.add(new Product(2L,"HDD","Seagate","India",5000.0f));
		
		when(pservice.listAll()).thenReturn(mockProducts);
		
		ResponseEntity<List<Product>> responseProducts =productController.getAllProducts();
		
		assertEquals(2, responseProducts.getBody().size());
		assertEquals("Pen",responseProducts.getBody().get(0).getName());
		assertEquals("HDD",responseProducts.getBody().get(1).getName());
		
		verify(pservice,times(1)).listAll();
	}

	@Test
	void testGetProductById() {
		 Product mockProduct =new Product(2L,"HDD","Seagate","India",5000.0f);
			
			when(pservice.getSingleProduct(2L)).thenReturn(Optional.of(mockProduct));
			
			ResponseEntity<Product> re=productController.getProductById(2L);
			
			// test fails
//			ResponseEntity<Product> re=productController.getProductById(1L);
			
			assertEquals(HttpStatus.OK,re.getStatusCode());
			assertEquals("HDD", re.getBody().getName());
			assertEquals("Seagate", re.getBody().getBrand());
			assertEquals("India", re.getBody().getMadein());
			assertEquals(5000.0, re.getBody().getPrice());
			
			verify(pservice,times(1)).getSingleProduct(2L);
	}

	@Test
	void testUpdateProduct() {
		 Product existingProduct =new Product(2L,"HDD","Seagate","India",5000.0f);
			Product updatedProduct =new Product(2L,"HDD","Seagate","USA",6000.0f);
			
			when(pservice.getSingleProduct(2L)).thenReturn(Optional.of(existingProduct));
			when(pservice.saveProduct(any(Product.class))).thenReturn(updatedProduct);
			
			ResponseEntity<Product> re=productController.updateProduct(2L, updatedProduct);
			
			assertEquals(HttpStatus.OK, re.getStatusCode());
			assertEquals("HDD", re.getBody().getName());
			assertEquals("Seagate", re.getBody().getBrand());
			assertEquals("USA", re.getBody().getMadein());
			assertEquals(6000.0, re.getBody().getPrice());
			
	}

	@Test
	void testDeleteProduct() {
		 Product existingProduct =new Product(2L,"HDD","Seagate","India",5000.0f);
			
			when(pservice.getSingleProduct(2L)).thenReturn(Optional.of(existingProduct));
			doNothing().when(pservice).deleteProduct(2L);
			
			ResponseEntity<Map<String,Boolean>> response=productController.deleteProduct(2L);
			
			assertTrue(response.getBody().containsKey("Deleted"));
			assertTrue(response.getBody().get("Deleted"));
			
			verify(pservice,times(1)).getSingleProduct(2L);
			verify(pservice,times(1)).deleteProduct(2L);
	}

	@Test
	void testSearchProductsByName() {
		 String name="Pen";
			List<Product> mockProducts=new ArrayList<>();
			mockProducts.add(new Product(1L,"Pen","Reynolds","India",20.00f));
			
			when(pservice.searchProductsByName(name)).thenReturn(mockProducts);
			
			ResponseEntity<?> re=productController.searchProductsByName(name);
			
			assertNotNull(re);
			assertEquals(HttpStatus.OK,re.getStatusCode());
			assertEquals(mockProducts, re.getBody());
	    
	}
	
	 	@Test
		void testSearchProductsByName_NoProductsFound() {
			String name="Pencil";
			List<Product> mockProducts=new ArrayList<>();
					
			when(pservice.searchProductsByName(name)).thenReturn(mockProducts);
			
			ResponseEntity<?> re=productController.searchProductsByName(name);
			
			assertNotNull(re);
			assertEquals(HttpStatus.NOT_FOUND,re.getStatusCode());
			assertEquals("No products found with the given name.", re.getBody());
		}
}
