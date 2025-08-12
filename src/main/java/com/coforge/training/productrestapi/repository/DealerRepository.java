package com.coforge.training.productrestapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.coforge.training.productrestapi.model.Dealer;
import com.coforge.training.productrestapi.model.DealerAndAddressProjection;

/**
* Author :Shreshth.Jha
* Date :Aug 4, 2025
* Time :4:30:02 PM
* Project :product-restapi

*/

//Repository for Dealer Registration & Login
public interface DealerRepository extends JpaRepository<Dealer,Long>{
	
	 //By default JpaRepository has pre-defined methods for CRUD operations
	//To fetch a single record based on primary-key field - findById()
	
	//Custom Methods - To fetch a record/object based on  nonId field - email
	public Optional<Dealer> findByEmail(String email);
	
	// To fetch a record/object based on lname field - non Id field
	public List<Dealer> findByLname(String lname);
	
	//Custom Queries - JPQL - Object Oriented Query Language
		@Query("SELECT new com.coforge.training.productrestapi.model.DealerAndAddressProjection"
				+ "(d.id,d.fname,d.lname,d.phoneNo,"
				+ "d.email,a.street,a.city,a.pincode)"
				+ " FROM Dealer d JOIN d.address a")
		List<DealerAndAddressProjection> findSelectedFieldsFromDealerAndAddress(); 
}
