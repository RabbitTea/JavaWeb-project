package com.ustc.bean;

public class CustomersBean {
	private String custName;
	private String passWord;

	public CustomersBean(String custName, String passWord) {
		super();
		this.custName = custName;
		this.passWord = passWord;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

}
 