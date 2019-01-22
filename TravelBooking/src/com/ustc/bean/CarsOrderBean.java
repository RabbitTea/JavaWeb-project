package com.ustc.bean;

public class CarsOrderBean {
	private int cOrderId;   //出租车订单号
	private String custName;   //顾客名称
	private String carType;    //出租车类型
	
	public CarsOrderBean() {
		super();
		this.cOrderId = 0;
		this.custName = null;
		this.carType = null;
	}
	
	public CarsOrderBean(int cOrderId, String custName, String carType) {
		super();
		this.cOrderId = cOrderId;
		this.custName = custName;
		this.carType = carType;
	}

	public int getcOrderId() {
		return cOrderId;
	}

	public void setcOrderId(int cOrderId) {
		this.cOrderId = cOrderId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}
	
}
