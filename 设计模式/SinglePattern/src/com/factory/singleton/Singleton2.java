package com.factory.singleton;

public class Singleton2 {
	
	private Singleton2() {
		
	}
	
	/*
	 * ����ʽ����
	 * ��д��ʽ����ʹ�þ�̬�����(���౻����ʱ��ʼ��)
	 */
	
	private static Singleton2 single = null;
	
	static {
		single = new Singleton2();
	}
	
	public static Singleton2 getInstance() {
		return single;
	}
}
