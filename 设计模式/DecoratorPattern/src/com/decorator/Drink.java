package com.decorator;

//饮品：统一接口——被装饰对象的基类
public interface Drink {
	
	public double price(); //饮品价格
	
	public String name();   //饮品名称
}
