package com.ustc.bean;

public class HotelsBean {
	private String name;
	private String location;
	private int price;
	private int numRooms;
	private int numAvail;
	
	public HotelsBean() {
		super();
		this.name = null;
		this.location = null;
		this.price = 0;
		this.numRooms = 0;
		this.numAvail = 0;
	}
	
	public HotelsBean(String name, String location, int price, int numRooms, int numAvail) {
		super();
		this.name = name;
		this.location = location;
		this.price = price;
		this.numRooms = numRooms;
		this.numAvail = numAvail;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public int getNumRooms() {
		return numRooms;
	}
	public void setNumRooms(int numRooms) {
		this.numRooms = numRooms;
	}
	public int getNumAvail() {
		return numAvail;
	}
	public void setNumAvail(int numAvail) {
		this.numAvail = numAvail;
	}
	
}
