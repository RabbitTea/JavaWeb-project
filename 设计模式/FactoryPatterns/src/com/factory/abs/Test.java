package com.factory.abs;

public class Test {

	public static void main(String[] args) {
		
		//��������
		//�������������Ĺ���
		CarFactory  bcf = new BmwFactory();
		//����������
		bcf.produceTrunk().run();
		
		//����µϿ���
		//���������µϳ��Ĺ���
		CarFactory acf = new AodiFactory();
		acf.produceTrunk().run();
	}

}
