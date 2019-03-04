package com.builder;

//�����ʵ�֡�������Ĺ���
public class BuildComputer implements Builder{

	private Computer c = new Computer();   //����һ�����������ע������һ��Ҫnew���Ż����ڴ����ռ䣬����ᱨ��ָ���쳣
	
	//������������
	@Override
	public void buildMainBoard() {
		c.setMainBoard("��˶");
	}

	@Override
	public void buildHD() {
		c.setHardDisk("ϣ��500G");
	}

	@Override
	public void buildCPU() {
		c.setCPU("Intel i5");
	}

	@Override
	//���ս������õĵ��Է���
	public Computer buildComputer() {
		return c;  
	}

}
