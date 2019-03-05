package com.decorator;

public class Test {

	public static void main(String[] args) {
		
		//�Ȱ�׼��װ�εĻ�������new��������Ϊ������Ҫ������
		Drink beer = new Beer();
		
		//���������װ���������ൺơ��
		QingDaoDecorator qingDaoDecorator = new QingDaoDecorator(beer);
		System.out.println(qingDaoDecorator.name() + ":" + qingDaoDecorator.price());
		
		//�ྩơ��
		YanJingDecorator yanJingDecorator = new YanJingDecorator(beer);
		System.out.println(yanJingDecorator.name() + ":" + yanJingDecorator.price());
		
		
		
		//�ó�ԭ������
		Drink cola = new Cola();
		
		//�ɿڿ���
		CocoDecorator cocoDecorator = new CocoDecorator(cola);
		System.out.println(cocoDecorator.name() + ":" + cocoDecorator.price());
		
		//���¿���
		PepsiDecorator pepsiDecorator = new PepsiDecorator(cola);
		System.out.println(pepsiDecorator.name() + ":" + pepsiDecorator.price());
	}

}
