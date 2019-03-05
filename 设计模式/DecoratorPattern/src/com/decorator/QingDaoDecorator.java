package com.decorator;

//�����װ���ߡ��������ൺơ��
public class QingDaoDecorator extends Decorator{

	public QingDaoDecorator(Drink drink) {
		super(drink);
	}

	public double price() {
		return drink.price() + 5.00;
	}
	
	public String name() {
		return "�ൺ" + drink.name();
	}
}
