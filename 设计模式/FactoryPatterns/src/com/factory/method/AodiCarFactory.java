package com.factory.method;

//�����������������µϽγ���������
public class AodiCarFactory implements CarFactory{

	@Override
	public Car produceCar() {
		return new AodiCar_M();   //�����µϽγ�����
	}
	
}
