package com.proxy;

//��Ҫ����ʵ�ʶ���(Star)����(Fans)�����ⲿ��
public class Fans {
	
	public static void main(String[] args) {
		//��ȡ�������
		Star s = new StarProxy();
		s.seal();  //ͨ������������ʵ�ʶ���ķ���
	}
}
