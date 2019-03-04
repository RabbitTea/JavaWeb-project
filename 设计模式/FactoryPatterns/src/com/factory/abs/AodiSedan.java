package com.factory.abs;

//奥迪产品族——具体产品——奥迪轿车
public class AodiSedan implements Sedan{

	@Override
	public void run() {
		System.out.println("奥迪轿车启动...");
	}

}
