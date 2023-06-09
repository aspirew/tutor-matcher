package com.tutor.matcher.matcherLogic.database;

public class Address {
	
	private Long id;
	private String street;
    private String city;
    private String zip;
    
	public Address() {
		super();
	}

	public Address(Long id, String street, String city, String zip) {
		super();
		this.id = id;
		this.street = street;
		this.city = city;
		this.zip = zip;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", street=" + street + ", city=" + city + ", zip=" + zip + "]";
	}
	
	public String toGoogleString() {
		return zip + " " + city + " " + street;
	}
	
	
	
}
