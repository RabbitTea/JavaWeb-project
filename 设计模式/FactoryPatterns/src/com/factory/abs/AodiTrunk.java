package com.factory.abs;

//宝马产品族——具体产品——奥迪卡车
//这里产品族可以理解为一个品牌的产品，那么在创建具体工厂时，可按品牌来划分，不同品牌创建一个具体工厂类
//而抽象工厂为一个，其中的方法按产品等级来划分，这里为生产卡车和生产轿车；
public class AodiTrunk implements Trunk{

	@Override
	public void run() {
		System.out.println("奥迪卡车启动...");
	}
	
}
