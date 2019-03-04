package com.ustc.bean;

public class CarsBean {
	private String type;
	private String location;
	private int price;
	private int numCars;
	private int numAvail;
	
	public CarsBean() {
		super();
		this.type = null;
		this.location = null;
		this.price = 0;
		this.numCars = 0;
		this.numAvail = 0;
	}
	
	public CarsBean(String type, String location, int price, int numCars, int numAvail) {
		super();
		this.type = type;
		this.location = location;
		this.price = price;
		this.numCars = numCars;
		this.numAvail = numAvail;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getNumCars() {
		return numCars;
	}

	public void setNumCars(int numCars) {
		this.numCars = numCars;
	}

	public int getNumAvail() {
		return numAvail;
	}

	public void setNumAvail(int numAvail) {
		this.numAvail = numAvail;
	}
	
}
