package com.factory.singleton;

public class Singleton2 {
	
	private Singleton2() {
		
	}
	
	/*
	 * 饿汉式单例
	 * 书写方式二：使用静态代码块(在类被加载时初始化)
	 */
	
	private static Singleton2 single = null;
	
	static {
		single = new Singleton2();
	}
	
	public static Singleton2 getInstance() {
		return single;
	}
}
