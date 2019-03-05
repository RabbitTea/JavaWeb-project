package com.proxy;

//需要调用实际对象(Star)的类(Fans)——外部类
public class Fans {
	
	public static void main(String[] args) {
		//获取代理对象
		Star s = new StarProxy();
		s.seal();  //通过代理对象调用实际对象的方法
	}
}
