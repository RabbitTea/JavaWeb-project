package com.decorator;

//具体的装饰者——构建青岛啤酒
public class QingDaoDecorator extends Decorator{

	public QingDaoDecorator(Drink drink) {
		super(drink);
	}

	public double price() {
		return drink.price() + 5.00;
	}
	
	public String name() {
		return "青岛" + drink.name();
	}
}
