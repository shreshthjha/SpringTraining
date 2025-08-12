package com.coforge.training.productrestapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Author :Shreshth.Jha
* Date :Aug 5, 2025
* Time :11:42:17 AM
* Project :product-restapi
*/
@NoArgsConstructor
@AllArgsConstructor
@Data
//Model class to perform Inner Join Dealer & Address classes
public class DealerAndAddressProjection {
	
	  	private Long id;
		private String fname;
		private String lname;
		private String phoneNo;
		private String email;
		private String street;
		private  String city;
		private  int pincode;
}
