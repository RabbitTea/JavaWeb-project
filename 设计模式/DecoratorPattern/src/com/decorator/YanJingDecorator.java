package com.decorator;

public class YanJingDecorator extends Decorator{

	public YanJingDecorator(Drink drink) {
		super(drink);
	}
	
	public double price() {
		return drink.price() + 2.00;
	}
	
	public String name() {
		return "Ñà¾©" + drink.name();
	}

}
