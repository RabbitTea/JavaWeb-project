package com.classAdapter;

public class Test {
	
	public static void main(String[] args) {
		PlayMovie pm = new FormatMovie();    //适配器对象向上转型为目标PlayMovie对象
		pm.playMoive();   //调用playMovie()方法，这个方法不是适配器自己的方法，而是适配器继承了被适配者后，调用的父类的方法；
	}
}
