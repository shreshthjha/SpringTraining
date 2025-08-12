package com.coforge.training.productrestapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coforge.training.productrestapi.exception.ResourceNotFoundException;
import com.coforge.training.productrestapi.model.Address;
import com.coforge.training.productrestapi.model.Dealer;
import com.coforge.training.productrestapi.model.DealerAndAddressProjection;
import com.coforge.training.productrestapi.service.DealerService;

/**
* Author :Shreshth.Jha
* Date :Aug 4, 2025
* Time :4:36:03 PM
* Project :product-restapi

*/

@RestController
@CrossOrigin(origins = "http://localhost:4200") 
@RequestMapping(value="/api")
public class DealerController {
	
	private final DealerService dservice;
	
	Logger logger=LoggerFactory.getLogger(DealerController.class);

	public DealerController(DealerService dservice) {
		super();
		this.dservice = dservice;
	}
	
	//Open PostMan, make a POST Request - http://localhost:8088/product-hive/api/register
		//Select body -> raw -> JSON 
		//Insert JSON Dealer object.
	
		@PostMapping("/register")
		public ResponseEntity<String> createDealer(@Validated @RequestBody Dealer dealer){
			try {
				Address address=dealer.getAddress(); //get data from secondary Table

				//Establish bi-directional Mapping
				address.setDealer(dealer);
				dealer.setAddress(address);

				Dealer registeredDealer=dservice.registerDealer(dealer); //save dealer details
				if(registeredDealer !=null) {
					return ResponseEntity.ok("Registration Successfull");
				}else {
					return ResponseEntity.badRequest().body("Registration Failed");
				}
			}catch(Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
						body("An Error Occurred :"+e.getMessage());
			}
		}
		
		 //Open PostMan, make a POST Request - http://localhost:8088/product-hive/api/login
		//Select body -> raw -> JSON 
		//Insert JSON Dealer object with email & password.
	@PostMapping("/login")
	public ResponseEntity<Boolean> loginDealer(@Validated @RequestBody Dealer dealer) 
			throws ResourceNotFoundException
	{
		Boolean isLogin=false;
		String email = dealer.getEmail();
		String password =  dealer.getPassword();
		
		Dealer d= dservice.loginDealer(email).orElseThrow(() -> //Invokes loginDealer() method with email parameter
		new ResourceNotFoundException("Dealer doen't Exists :: " +email));
		
		if(email.equals(d.getEmail()) && password.equals(d.getPassword())) {
			isLogin=true;
		}
		return ResponseEntity.ok(isLogin);
	}
	
	//Open PostMan, make a GET Request - http://localhost:8088/product-hive/api/dealers
	@GetMapping("/dealers")
	public ResponseEntity<List<DealerAndAddressProjection>> getDealerInfo(){
		try {
			List<DealerAndAddressProjection> selectedFields=dservice.getDealerInfo();
			logger.info("Dealer Information fetched Successfully");
			return ResponseEntity.ok(selectedFields);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("Dealer Information fetched UnSuccessfully");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
