package com.ustc.bean;

public class FlightsBean {
	private String flightNum;
	private int price;
	private int numSeats;
	private int numAvail;
	private String FromCity;
	private String ArivCity;
	
	public FlightsBean() {
		super();
		this.flightNum = null;
		this.price = 0;
		this.numSeats = 0;
		this.numAvail = 0;
		this.FromCity = null;
		this.ArivCity = null;
	}
	
	public FlightsBean(String flightNum, int price, int numSeats, int numAvail, String fromCity, String arivCity) {
		super();
		this.flightNum = flightNum;
		this.price = price;
		this.numSeats = numSeats;
		this.numAvail = numAvail;
		FromCity = fromCity;
		ArivCity = arivCity;
	}

	public String getFlightNum() {
		return flightNum;
	}

	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getNumSeats() {
		return numSeats;
	}

	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
	}

	public int getNumAvail() {
		return numAvail;
	}

	public void setNumAvail(int numAvail) {
		this.numAvail = numAvail;
	}

	public String getFromCity() {
		return FromCity;
	}

	public void setFromCity(String fromCity) {
		FromCity = fromCity;
	}

	public String getArivCity() {
		return ArivCity;
	}

	public void setArivCity(String arivCity) {
		ArivCity = arivCity;
	}
}
