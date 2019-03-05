package com.decorator;

public class Test {

	public static void main(String[] args) {
		
		//先把准备装饰的基础对象new出来，因为构造器要传参数
		Drink beer = new Beer();
		
		//构建具体的装饰器对象：青岛啤酒
		QingDaoDecorator qingDaoDecorator = new QingDaoDecorator(beer);
		System.out.println(qingDaoDecorator.name() + ":" + qingDaoDecorator.price());
		
		//燕京啤酒
		YanJingDecorator yanJingDecorator = new YanJingDecorator(beer);
		System.out.println(yanJingDecorator.name() + ":" + yanJingDecorator.price());
		
		
		
		//拿出原生可乐
		Drink cola = new Cola();
		
		//可口可乐
		CocoDecorator cocoDecorator = new CocoDecorator(cola);
		System.out.println(cocoDecorator.name() + ":" + cocoDecorator.price());
		
		//百事可乐
		PepsiDecorator pepsiDecorator = new PepsiDecorator(cola);
		System.out.println(pepsiDecorator.name() + ":" + pepsiDecorator.price());
	}

}
