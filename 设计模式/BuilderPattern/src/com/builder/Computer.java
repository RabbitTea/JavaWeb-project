package com.builder;

//��Ʒ�ࣺ�������Ժͷ���(���е����)
public class Computer {
	
	private String MainBoard;
	private String HardDisk;
	private String CPU;
	
	
	public String getMainBoard() {
		return MainBoard;
	}
	public void setMainBoard(String mainBoard) {
		MainBoard = mainBoard;
	}
	public String getHardDisk() {
		return HardDisk;
	}
	public void setHardDisk(String hardDisk) {
		HardDisk = hardDisk;
	}
	public String getCPU() {
		return CPU;
	}
	public void setCPU(String cPU) {
		CPU = cPU;
	}
	
	
	//shift + alt + s + s Ϊ��д�Ŀ�ݼ�
	//����Ϊ�˷���鿴�����������Ϣ����Ҫ��дtoString������
	@Override
	public String toString() {
		return "Computer [MainBoard=" + MainBoard + ", HardDisk=" + HardDisk + ", CPU=" + CPU + "]";
	}
}
