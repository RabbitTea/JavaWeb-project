package com.decorator;

public class PepsiDecorator extends Decorator{

	public PepsiDecorator(Drink drink) {
		super(drink);
	}

	public double price() {
		return drink.price() + 5.00;
	}
	
	public String name() {
		return "°ÙÊÂ" + drink.name();
	}
}
