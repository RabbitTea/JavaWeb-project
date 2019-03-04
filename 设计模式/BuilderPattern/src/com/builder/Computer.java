package com.builder;

//产品类：定义属性和方法(其中的配件)
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
	
	
	//shift + alt + s + s 为重写的快捷键
	//这里为了方便查看创建对象的信息，需要重写toString方法；
	@Override
	public String toString() {
		return "Computer [MainBoard=" + MainBoard + ", HardDisk=" + HardDisk + ", CPU=" + CPU + "]";
	}
}
