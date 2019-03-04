package com.factory.simple;

//客户类(测试类)
public class Customer {

	public static void main(String[] args) {
		
		//创建一辆宝马轿车
		CarFactory.produceCar("宝马").run();
		//创建一辆奥迪轿车
		CarFactory.produceCar("奥迪").run();
		//创建一辆工厂中没有的轿车
		CarFactory.produceCar("大众").run();
	}

}
