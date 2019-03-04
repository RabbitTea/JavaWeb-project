package com.factory.abs;

//宝马产品族——具体产品——宝马轿车
public class BmwSedan implements Sedan{

	@Override
	public void run() {
		System.out.println("宝马轿车启动...");
	}

}
