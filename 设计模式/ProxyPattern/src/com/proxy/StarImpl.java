package com.proxy;

//具体的明星——委托类(需要被代理)
public class StarImpl implements Star{

	@Override
	public void seal() {
		System.out.println("白敬亭");
	}

}
