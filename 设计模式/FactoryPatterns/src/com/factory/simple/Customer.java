package com.factory.simple;

//�ͻ���(������)
public class Customer {

	public static void main(String[] args) {
		
		//����һ������γ�
		CarFactory.produceCar("����").run();
		//����һ���µϽγ�
		CarFactory.produceCar("�µ�").run();
		//����һ��������û�еĽγ�
		CarFactory.produceCar("����").run();
	}

}
