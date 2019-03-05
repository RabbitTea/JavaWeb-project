package com.decorator;

public class CocoDecorator extends Decorator{

	public CocoDecorator(Drink drink) {
		super(drink);
	}
	
	public double price() {
		return drink.price() + 5.00;
	}
	
	public String name() {
		return "¿É¿Ú" + drink.name();
	}
}
