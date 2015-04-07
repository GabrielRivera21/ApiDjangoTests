package com.deeplogics.models;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

public class Projects {
	
	private Long id;
	
	private Long owner;
	
	private String title;
	
	private String description;
	
	private double cost;
	
	@SerializedName("desired_cost")
	private double desiredCost;
	
	private double latitude;
	
	private double longitude;
	
	private String city;
	
	private String country;
	
	private String status;
	
	private String category;
	
	@SerializedName("created_at")
	private String createdAt;
	
	public Projects() {}

	public Projects(String title, String description, double desiredCost,
			double latitude, double longitude, String city, String country,
			String category) {
		super();
		this.title = title;
		this.description = description;
		this.desiredCost = desiredCost;
		this.latitude = latitude;
		this.longitude = longitude;
		this.city = city;
		this.country = country;
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOwner() {
		return owner;
	}

	public void setOwner(Long owner) {
		this.owner = owner;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getDesiredCost() {
		return desiredCost;
	}

	public void setDesiredCost(double desiredCost) {
		this.desiredCost = desiredCost;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	@Override
	public int hashCode() {
		// Google Guava provides great utilities for hashing
		return Objects.hashCode(title, description, owner);
	}
	
	/**
	 * If both Projects have the same title, description and owner
	 * then they are the same Project.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Projects) {
			Projects other = (Projects) obj;
			// Google Guava provides great utilities for equals too!
			return Objects.equal(title, other.title) &&
					Objects.equal(description, other.description) &&
					Objects.equal(owner, other.owner);
		} else {
			return false;
		}
	}
}
