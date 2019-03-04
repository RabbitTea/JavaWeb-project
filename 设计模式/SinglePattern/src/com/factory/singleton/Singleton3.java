package com.factory.singleton;

public class Singleton3 {
	private Singleton3() {
		
	}
	
	
	/*
	 * ����ʽ����(�ӳټ��ط�ʽ)���������ʱ����ʼ��
	 * �����̻߳����»�������single����
	 */
	/*
	 * �������ʽ�����̲߳���ȫ���⣺
	 * 1.ʹ��synchronizedͬ��������ʱע��ؼ��ֹ�Ͻ�ķ�Χ(���ڵ�λ��)���������������ִ��Ч�ʣ����������ķ�ΧԽСԽ�ã�
	 * synchronized��static��ϣ��������࣬��û��static�������ǵ�ǰ��Ķ���
	 */
	private static Singleton3 single = null;
	
	//��һ���̵߳��ø÷���ʱ��������������÷�������ͷ�����Ȼ��ڶ����߳����õ�����
	/*���ּ�����ʽ��Χ̫��ִ��Ч�ʽϵͣ�
	synchronized public static Singleton3 getInstance() {
		if(single == null) {
			
			try {
				//���߳�˯��10����
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			single = new Singleton3();
		}
		return single;
	}
	*/
	
	
	/*
	 * ��Сsynchronized���ķ�Χ����Ӧ����������
	 */
	public static Singleton3 getInstance() {
		//˫�ؼ��
		if(single == null) {
			synchronized (Singleton3.class){
				if(single == null) {
					single = new Singleton3();
				}
			}
		}		
			
		return single;	
	}
}
