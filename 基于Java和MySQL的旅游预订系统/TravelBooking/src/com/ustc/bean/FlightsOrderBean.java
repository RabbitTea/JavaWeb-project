package com.ustc.bean;

public class FlightsOrderBean {
	private int fOrderId;
	private String flightType;
	private String custName;
	
	public FlightsOrderBean() {
		super();
		this.fOrderId = 0;
		this.flightType = null;
		this.custName = null;
	}
	
	public FlightsOrderBean(int fOrderId, String flightType, String custName) {
		super();
		this.fOrderId = fOrderId;
		this.flightType = flightType;
		this.custName = custName;
	}

	public int getfOrderId() {
		return this.fOrderId;
	}

	public void setfOrderId(int fOrderId) {
		this.fOrderId = fOrderId;
	}

	public String getFlightType() {
		return flightType;
	}

	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	
}
