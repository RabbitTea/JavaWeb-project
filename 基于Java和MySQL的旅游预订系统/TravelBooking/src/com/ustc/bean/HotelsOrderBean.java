package com.ustc.bean;

public class HotelsOrderBean {
	private int hOrderId;
	private String hotelType;
	private String custName;
	private String hotelRoom;
	
	public HotelsOrderBean() {
		super();
		this.hOrderId = 0;
		this.hotelType = null;
		this.custName = null;
		this.hotelRoom = null;
	}
	
	public HotelsOrderBean(int hOrderId, String hotelType, String custName, String hotelRoom) {
		super();
		this.hOrderId = hOrderId;
		this.hotelType = hotelType;
		this.custName = custName;
		this.hotelRoom = hotelRoom;
	}

	public int gethOrderId() {
		return hOrderId;
	}

	public void sethOrderId(int hOrderId) {
		this.hOrderId = hOrderId;
	}

	public String getHotelType() {
		return hotelType;
	}

	public void setHotelType(String hotelType) {
		this.hotelType = hotelType;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getHotelRoom() {
		return hotelRoom;
	}

	public void setHotelRoom(String hotelRoom) {
		this.hotelRoom = hotelRoom;
	}
	
}
