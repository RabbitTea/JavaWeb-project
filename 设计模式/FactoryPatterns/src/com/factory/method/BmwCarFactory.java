package com.factory.method;

//��������������������γ���������
public class BmwCarFactory implements CarFactory{

	@Override
	public Car produceCar() {
		return new BmwCar_M();    //��������γ�����
	}

}
