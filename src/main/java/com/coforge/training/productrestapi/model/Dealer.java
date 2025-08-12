package com.coforge.training.productrestapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.*;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name="dealers")
public class Dealer {
	
  
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "dealer_seq")
	 @SequenceGenerator(name="dealer_seq",initialValue = 101,allocationSize = 1)
	 @Column(name="did")
	private Long id;
	
	@Column(unique=true,nullable = false)    //Unique Constraint
	private String email;
	
	@Column(name="first_name",nullable = false) //not null constraint
	private String fname;
	
	@Column(name="last_name",nullable = false)
    private String lname;
	
	@NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    @Column(name = "password", nullable = false)
	private String password;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(nullable = false)
	private Date dob;
	
	@Column(name="phone",unique = true,nullable = false)
	private String phoneNo;
	
	/*Primary Key - Foreign Key Relationship
	 * Model 1-1 Bi-Directional Mapping b/n Dealer & Address
	 * */
	@OneToOne(mappedBy = "dealer", cascade = CascadeType.ALL)
	private Address address; //has-a relationship

	public Dealer(Long id, String email, String fname, String lname,
			@NotBlank(message = "Password cannot be blank") @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters") String password,
			Date dob, String phoneNo) {
		super();
		this.id = id;
		this.email = email;
		this.fname = fname;
		this.lname = lname;
		this.password = password;
		this.dob = dob;
		this.phoneNo = phoneNo;
	}

	public Dealer(Long id, String email, String fname, String lname,
			@NotBlank(message = "Password cannot be blank") @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters") String password,
			Date dob, String phoneNo, Address address) {
		super();
		this.id = id;
		this.email = email;
		this.fname = fname;
		this.lname = lname;
		this.password = password;
		this.dob = dob;
		this.phoneNo = phoneNo;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		Base64.Encoder encoder = Base64.getEncoder();  //Use Base64 class for password Encryption
        String normalString = password;
        String encodedString = encoder.encodeToString(   // encrypt password in database field
        normalString.getBytes(StandardCharsets.UTF_8) );
        this.password = encodedString;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Dealer() {
		super();
	}
	
	

}
