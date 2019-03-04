package com.builder;

//抽象的实现——具体的构建
public class BuildComputer implements Builder{

	private Computer c = new Computer();   //创建一个计算机对象，注意这里一定要new，才会在内存分配空间，否则会报空指针异常
	
	//构建具体的配件
	@Override
	public void buildMainBoard() {
		c.setMainBoard("华硕");
	}

	@Override
	public void buildHD() {
		c.setHardDisk("希捷500G");
	}

	@Override
	public void buildCPU() {
		c.setCPU("Intel i5");
	}

	@Override
	//最终将生产好的电脑返回
	public Computer buildComputer() {
		return c;  
	}

}
