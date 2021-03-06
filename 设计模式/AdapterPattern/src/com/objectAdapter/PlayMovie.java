package com.objectAdapter;

/*
 * @Description：适配器模式；以播放器为例，<目标 target>是使抽象接口能够播放MP4格式的电影，而<被适配者 adaptee>类实现了播放MP4的功能；
 *              <适配器 adapter>分为类适配器和对象适配器两种，起到了转换器的作用，使两者适配。
 *              分为<类适配器>和<对象适配器>两种。
 *          △注意：适配器模式用来修改一个正常运行的系统的接口，而不是一开始就使用适配器模式。
 *          优点：1.可以让任何两个没有关联的类一起运行；
 *              2.提高了类的复用。
 */

//目标接口(也可以是类)：使其可播放MP4格式的影片
public interface PlayMovie {

	//目前只能播放AVI格式的电影
	public void playMoive();
	
	/*
	 * 不能在其中添加新的方法，因为接口会被很多类实现，如果添加了新的方法则其实现类也要相应的添加。
	 * 这就用到了适配器的作用。
	 */
}
