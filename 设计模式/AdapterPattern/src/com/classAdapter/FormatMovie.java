package com.classAdapter;

//适配器：适配器模式的核心
/*
 * 类适配器方式；
 * 其关键字是extends，继承被适配者类
 */
public class FormatMovie extends PlayMP4 implements PlayMovie{

	@Override
	public void playMoive() {
		super.playMP4Movie();
	}
	
}
