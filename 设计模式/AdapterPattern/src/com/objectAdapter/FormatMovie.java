package com.objectAdapter;

/*
 * 对象适配器方式；
 * 其实际上是创建适配者对象，在自己的方法中调用其中的方法；
 */
public class FormatMovie {
	
	PlayMP4 mp4 = new PlayMP4();  //创建适配者对象
	
	public void playMovie() {
		mp4.playMP4Movie();   //调用其中的MP4播放功能                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
	}
}
