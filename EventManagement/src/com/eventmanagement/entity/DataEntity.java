package com.eventmanagement.entity;

public class DataEntity {
	
	private int id;
	private String categoryname;
	private String name;
	private String address;
	private String contact;
	private String rates;
	private String imagePath;
	
	public DataEntity(int id, String categoryname, String name, String address,  String contact,   String rates, String imagePath ){
		this.id=id;
		this.categoryname=categoryname;
		this.name=name;
		this.address=address;
		this.contact=contact;
		this.rates=rates;
		this.imagePath=imagePath;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getCategoryname() {
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getRates() {
		return rates;
	}
	public void setRates(String rates) {
		this.rates = rates;
	}
}
