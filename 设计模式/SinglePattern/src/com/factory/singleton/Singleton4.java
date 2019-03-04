package com.factory.singleton;

public class Singleton4 {
	
	private Singleton4() {
		
	}
	
	/*
	 * 静态内部类实现
	 * 外部类(Singleton4)被加载，内部类没有被加载，除非主动使用
	 */
	private static class InSideClass{
		private static Singleton4 single = new Singleton4();
	}

    public static Singleton4 getInstance() {
    	return InSideClass.single;
    }
}
