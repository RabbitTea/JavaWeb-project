package com.factory.abs;

//宝马产品族——具体产品——宝马卡车
public class BmwTrunk implements Trunk{

	@Override
	public void run() {
		System.out.println("宝马卡车启动...");
	}

}
