package com.ustc.bean;

import java.util.ArrayList;

import com.ustc.dao.jdbcHelper;

public class MainApplication {
	public static void main(String[] args) {
		jdbcHelper helper = new jdbcHelper();
		ArrayList<CustomersBean> custs = helper.searchCustomer();
		
		//helper.cusRegister(cust);
		//helper.searchFlight();
		for(int i=0;i<custs.size();i++) {
			System.out.println("name="+custs.get(i).getCustName());
			System.out.println("pass="+custs.get(i).getPassWord());
		}
	}
}
