package com.factory.singleton;

public class Singleton4 {
	
	private Singleton4() {
		
	}
	
	/*
	 * ��̬�ڲ���ʵ��
	 * �ⲿ��(Singleton4)�����أ��ڲ���û�б����أ���������ʹ��
	 */
	private static class InSideClass{
		private static Singleton4 single = new Singleton4();
	}

    public static Singleton4 getInstance() {
    	return InSideClass.single;
    }
}
