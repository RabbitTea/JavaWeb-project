package com.factory.singleton;

public class Test {

	public static void main(String[] args) {
		//����ʽ����ʹ�÷�ʽ
		Singleton1 s = Singleton1.getInstance();  //ʵ����/����
		
		//�鿴����ʽ�����Ķ���Ĵ�����ַ���
		/*
		 * �鿴Դ��ķ�ʽ��
		 * Object��������ĸ��ࣻѡ��Singleton3��������Ctrl+t���ҵ�����Object��
		 * ���Attach Source��ѡ��ڶ�����·��Ϊjdk�����src.zip�ļ���
		 * 
		 * Object��toString������˵��@����Ϊ������ʵ���ĵ�ַ���Ǹ���hashcode������ġ�
		 */
		/*
		System.out.println(Singleton3.getInstance());
		System.out.println(Singleton3.getInstance());
		System.out.println(Singleton3.getInstance());
		System.out.println(Singleton3.getInstance());
		*/
		
		/*
		 * �ö��̲߳����������������������������˯�ߺ󣬲�ͬ���߳̿��ܻ������ͬ��ʵ�������Υ���˵���ԭ��
		 * ����ʽģʽ�ڶ��߳�����»��̲߳���ȫ��
		 * ���ﴴ���̲߳����ڲ���ķ�ʽ��
		 */
		//��һ���߳�
		new Thread() {
			@Override
			public void run() {
				System.out.println(Singleton3.getInstance());
			}
		}.start();
		
		//�ڶ����߳�
		new Thread() {
			@Override
			public void run() {
				System.out.println(Singleton3.getInstance());
			}
		}.start();
		
		//�������߳�
		new Thread() {
			@Override
			public void run() {
				System.out.println(Singleton3.getInstance());
			}
		}.start();
	}

}
