package com.decorator;

//�������Ʒ��ơ�ơ������屻װ�εĶ���
public class Beer implements Drink{

	@Override
	public double price() {
		return 2.00;    //������Ϊ2.00
	}

	@Override
	public String name() {
		return "ơ��";
	}
	
}
