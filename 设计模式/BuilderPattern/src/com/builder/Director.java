package com.builder;

//������
public class Director {
	
	public static Computer constructCom(Builder bc) {  //����Ĳ�������Ϊ����Ĺ��죬ע�����ﴫ�����ӿ�������Ϊ�˷�����չ
		
		//ʵ�־���Ĺ�����̡�������ֻװ�������е�һ������
		bc.buildMainBoard();
		bc.buildHD();
		bc.buildCPU();
		
		return bc.buildComputer();
	}
}
