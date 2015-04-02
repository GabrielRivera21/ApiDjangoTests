package com.deeplogics.models;

import java.util.Date;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

public class Users {
	
	private Long id;
	
	private String email; 
	
	private String password;
	
	@SerializedName("first_name")
	private String firstName;
	
	@SerializedName("last_name")
	private String lastName;
	
	@SerializedName("about_me")
	private String aboutMe;
	
	@SerializedName("street_1")
	private String street1;
	
	@SerializedName("street_2")
	private String street2;
	
	private String country;
	
	private String city;
	
	@SerializedName("zip_code")
	private String zipCode;
	
	@SerializedName("profile_pic")
	private String profilePic;
	
	@SerializedName("created_at")
	private Date createdAt;
	
	protected Users() {}
	
	public Users(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public Users(String email, String password, String firstName,
			String lastName, String aboutMe, String street1, String street2,
			String country, String city, String zipCode, String profilePic) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.aboutMe = aboutMe;
		this.street1 = street1;
		this.street2 = street2;
		this.country = country;
		this.city = city;
		this.zipCode = zipCode;
		this.profilePic = profilePic;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getProfilePic() {
		return profilePic;
	}
	
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	/**
	 * Two Users will generate the same hashcode if they have exactly the same
	 * values for their email.
	 * 
	 */
	@Override
	public int hashCode() {
		// Google Guava provides great utilities for hashing
		return Objects.hashCode(email);
	}
	
	/**
	 * If both Users have the same email
	 * then they are the same user.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Users) {
			Users other = (Users) obj;
			// Google Guava provides great utilities for equals too!
			return Objects.equal(email, other.email);
		} else {
			return false;
		}
	}
}
