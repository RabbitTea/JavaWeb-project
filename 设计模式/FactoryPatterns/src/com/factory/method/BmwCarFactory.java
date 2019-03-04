package com.factory.method;

//具体生产工厂——宝马轿车生产工厂
public class BmwCarFactory implements CarFactory{

	@Override
	public Car produceCar() {
		return new BmwCar_M();    //创建宝马轿车对象
	}

}
