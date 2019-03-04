package com.factory.method;

//消费者(测试类)
public class Customer {

	public static void main(String[] args) {
		
		//需求一辆宝马轿车
		//先创建生产宝马轿车的工厂
		CarFactory bmwF = new BmwCarFactory();   //向上转型
		bmwF.produceCar().run();;
		
		//需求一辆奥迪轿车
		//先创建生产奥迪轿车的工厂
		CarFactory aodiF = new AodiCarFactory();
		aodiF.produceCar().run();
	}

}
