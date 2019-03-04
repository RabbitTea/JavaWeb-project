package com.builder;

public class Test {

	public static void main(String[] args) {
		Computer com = Director.constructCom(new BuildComputer()); 
        System.out.println(com);   //打印创建好的电脑
	}

}
