package com.factory.abs;

public class Test {

	public static void main(String[] args) {
		
		//需求宝马卡车
		//创建生产宝马车的工厂
		CarFactory  bcf = new BmwFactory();
		//创建宝马卡车
		bcf.produceTrunk().run();
		
		//需求奥迪卡车
		//创建生产奥迪车的工厂
		CarFactory acf = new AodiFactory();
		acf.produceTrunk().run();
	}

}
