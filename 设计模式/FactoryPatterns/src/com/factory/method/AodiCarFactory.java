package com.factory.method;

//具体生产工厂——奥迪轿车生产工厂
public class AodiCarFactory implements CarFactory{

	@Override
	public Car produceCar() {
		return new AodiCar_M();   //创建奥迪轿车对象
	}
	
}
