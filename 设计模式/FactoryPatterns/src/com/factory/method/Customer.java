package com.factory.method;

//������(������)
public class Customer {

	public static void main(String[] args) {
		
		//����һ������γ�
		//�ȴ�����������γ��Ĺ���
		CarFactory bmwF = new BmwCarFactory();   //����ת��
		bmwF.produceCar().run();;
		
		//����һ���µϽγ�
		//�ȴ��������µϽγ��Ĺ���
		CarFactory aodiF = new AodiCarFactory();
		aodiF.produceCar().run();
	}

}
