package com.coforge.training.productrestapi.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;

import com.coforge.training.productrestapi.model.Address;
import com.coforge.training.productrestapi.model.Dealer;
import com.coforge.training.productrestapi.service.DealerService;


/**
* Author :Shreshth.Jha
* Date :Aug 13, 2025
* Time :10:00:39 AM
* Project :product-restapi

*/
@SpringBootTest
class DealerControllerTest {
	
	@Autowired
	private DealerController dealerController;
	
	@MockBean
	private DealerService dservice;
	
 Dealer dealer;
  
	@BeforeEach
	void setUp() throws Exception {
		dealer=new Dealer();
	}
       
	@AfterEach
	void tearDown() throws Exception {
		dealer=null;
	}

//	@Test
//	void testDealerController() {
//		fail("Not yet implemented");
//	}

	@Test
	void testCreateDealer() throws ParseException {
		dealer.setEmail("jhon@gmail.com");
		dealer.setFname("Jhon");
		dealer.setLname("Mike");
		dealer.setPassword("password123");
		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Date dob=new Date(df.parse("1990-01-01").getTime()); // use java.sql.date
		dealer.setDob(dob);
		
		dealer.setPhoneNo("1234567891");
		
		Address address=new Address();
		address.setStreet("123 Main Street");
		address.setCity("Bengaluru");
		address.setPincode(560001);
		
		dealer.setAddress(address);
		
		/*
		 * Matchers are like regex or wildcards where instead of a specific input (and or output), 
		 * you specify a range/type of input/output based on which stubs/spies can be rest and calls to stubs can be verified.
		 * Matchers are a powerful tool, which enables a shorthand way of setting up stubs as well as verifying invocations on 
		 * the stubs by mentioning argument inputs as generic types to specific values depending on the use-case or scenario.
		 * 
		 * any(java language class) –

		Example: any(ClassUnderTest.class) – This is a more specific variant of any() 
		and will accept only objects of the class type that’s mentioned as the template parameter.
		 * */
		
		when(dservice.registerDealer(any(Dealer.class))).thenReturn(dealer);
		
		ResponseEntity<String> re=dealerController.createDealer(dealer);
		
		assertEquals(HttpStatus.OK, re.getStatusCode());
		assertEquals("Registration Successfull",re.getBody());
		
		verify(dservice,times(1)).registerDealer(any(Dealer.class));

	}

	@Test
	void testLoginDealer() {
		dealer.setEmail("jhon@gmail.com");
		dealer.setPassword("password123");
		
		/*when() Then() method
		It enables stubbing methods. It should be used when we want to mock to return specific values when 
		particular methods are called. In simple terms, "When the XYZ() method is called, 
		then return ABC." It is mostly used when there is some condition to execute.
		 * 
		 * */
		when(dservice.loginDealer("jhon@gmail.com")).thenReturn(Optional.of(dealer));
		
		Dealer x= dservice.loginDealer("jhon@gmail.com").get();
		assertEquals(x.getEmail(), dealer.getEmail());
		assertEquals(x.getPassword(), dealer.getPassword());
		
		// assertion for Login fail
		//assertEquals(x.getPassword(), "password");
		
		ResponseEntity<Boolean> result=dealerController.loginDealer(dealer);
		assertTrue(result.getBody());
		
		/*
		 * The verify() method is used to check whether some specified methods are called or not. 
		 * In simple terms, it validates the certain behavior that happened once in a test. 
		 * It is used at the bottom of the testing code to assure that the defined methods are called.
		 * */
		verify(dservice,times(2)).loginDealer("jhon@gmail.com");
	}

//	@Test
//	void testGetDealerInfo() {
//		fail("Not yet implemented");
//	}

}
