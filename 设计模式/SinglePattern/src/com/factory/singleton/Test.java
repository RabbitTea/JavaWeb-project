package com.factory.singleton;

public class Test {

	public static void main(String[] args) {
		//饿汉式单例使用方式
		Singleton1 s = Singleton1.getInstance();  //实例化/创建
		
		//查看懒汉式单例的对象的创建地址情况
		/*
		 * 查看源码的方式：
		 * Object是所有类的父类；选中Singleton3类名，按Ctrl+t，找到父类Object；
		 * 点击Attach Source，选择第二个，路径为jdk下面的src.zip文件；
		 * 
		 * Object的toString方法，说明@后面为创建的实例的地址，是根据hashcode来定义的。
		 */
		/*
		System.out.println(Singleton3.getInstance());
		System.out.println(Singleton3.getInstance());
		System.out.println(Singleton3.getInstance());
		System.out.println(Singleton3.getInstance());
		*/
		
		/*
		 * 用多线程测试懒汉单例的运行情况，在设置睡眠后，不同的线程可能会产生不同的实例，这就违背了单例原则
		 * 懒汉式模式在多线程情况下会线程不安全；
		 * 这里创建线程采用内部类的方式；
		 */
		//第一个线程
		new Thread() {
			@Override
			public void run() {
				System.out.println(Singleton3.getInstance());
			}
		}.start();
		
		//第二个线程
		new Thread() {
			@Override
			public void run() {
				System.out.println(Singleton3.getInstance());
			}
		}.start();
		
		//第三个线程
		new Thread() {
			@Override
			public void run() {
				System.out.println(Singleton3.getInstance());
			}
		}.start();
	}

}
