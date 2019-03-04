package com.builder;

//建造者
public class Director {
	
	public static Computer constructCom(Builder bc) {  //传入的参数类型为抽象的构造，注意这里传入抽象接口类型是为了方便扩展
		
		//实现具体的构造过程——这里只装配了其中的一种配置
		bc.buildMainBoard();
		bc.buildHD();
		bc.buildCPU();
		
		return bc.buildComputer();
	}
}
