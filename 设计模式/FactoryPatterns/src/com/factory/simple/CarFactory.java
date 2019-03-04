/*
 * @description���򵥹����������������𴴽�һ�����͵Ĳ�Ʒ�������ͳ������Ʒ�ӿڣ������Ʒ��ͨ��ʵ������ӿڴӶ�������̬�ı�
 *               ���﹤����һ��������࣬��ҵ���߼����ж��߼����ڸ����У������׽�����չ��
 *    ������<����ԭ�򣺶���չ���ţ����޸Ĺر�>
 *       ��ע�⣺���﹤�����еķ������ʹ�þ�̬��(static)�����������ڵ���ʱֱ��ʹ�����������ã������贴�����󣬼����˴�����
 */

//�����γ��Ĺ�����
package com.factory.simple;

public class CarFactory {
	
	public static Car produceCar(String type) {
		/*
		 * �����������ʵ��
		 */
		/*
		if(type.equals("����")) {  //String�����ж�ֵ�����equals
			return new BmwCar_S();
		}else if(type.equals("�µ�")) {
			return new AodiCar_S();
		}else {//��������׳�һ���Զ����쳣��Ҳ���Է���null
			throw new RuntimeException("����Ҫ�ĳ��Ͳ����ڣ������Լ���һ��...");   
		}
		*/
		
		/*
		 * ��switchѡ�������ʵ��
		 */
		switch (type) {
			case "����":
				return new BmwCar_S();
			case "�µ�":
				return new AodiCar_S();
			default:
				return null;
		}
	}
}
