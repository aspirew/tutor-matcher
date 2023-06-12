package com.tutor.matcher.userLogic.database;

public class User {
	
	private Long id;
	private String name;
	private String surname;
	private String phone;
	private String mail;
	private Address address;
	
	public User() {
		super();
	}
	
	public User(Long id, String name, String surname, String phone, String mail, Address address) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.mail = mail;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", surname=" + surname + ", phone=" + phone + ", mail=" + mail
				+ ", address=" + address + "]";
	}
	
	
	
	
	
}
