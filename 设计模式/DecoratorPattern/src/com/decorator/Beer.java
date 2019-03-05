package com.decorator;

//具体的饮品：啤酒——具体被装饰的对象
public class Beer implements Drink{

	@Override
	public double price() {
		return 2.00;    //基础价为2.00
	}

	@Override
	public String name() {
		return "啤酒";
	}
	
}
