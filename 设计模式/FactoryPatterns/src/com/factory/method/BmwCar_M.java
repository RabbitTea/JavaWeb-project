/*
 * @description：具体的产品类——宝马轿车
 */
package com.factory.method;

public class BmwCar_M implements Car {

	@Override
	public void run() {
		System.out.println("宝马轿车启动...");
	}
	
}
