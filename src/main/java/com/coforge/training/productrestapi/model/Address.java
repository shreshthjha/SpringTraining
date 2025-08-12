package com.coforge.training.productrestapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


/**
* Author :Shreshth.Jha
* Date :Aug 4, 2025
* Time :3:33:01 PM
* Project :product-restapi

*/


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Address {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)//Auto Numbering from 1
	
	private Long addressId;
	
	private @NonNull String street;
	private @NonNull String city;
	private int pincode;
	
	/*Foreign key Relationship*/
	@OneToOne     //one-one mapping
	@JoinColumn(name="dealer_id")// foreign key field
	
	private Dealer dealer;//reference class object

	public Address(@NonNull String street, @NonNull String city, int pincode) {
		super();
		this.street = street;
		this.city = city;
		this.pincode = pincode;
	}
	
	
	
}
