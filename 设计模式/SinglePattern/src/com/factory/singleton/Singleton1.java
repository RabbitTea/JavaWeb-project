/*
 * @Description：单例模式：指该类在程序中只创建一个实例(只允许被new一次)，配合多线程可看出其效果。
 *         1.单例类只能有一个实例：构造方法限定为private避免了类在外部被实例化；
 *         2.单例类必须自己创建自己的一个实例；
 *         3.单例类必须给所有其他对象提供这一实例。
 *        优点：只有一个实例，减少了内存的开销；避免对资源的多重占用(比如写文件操作)。
 *        缺点：扩展很困难，因为private不能被继承（反射可以，打破private的规则）。
 */
package com.factory.singleton;

public class Singleton1 {
	
	private Singleton1() {  //△使一个类只能new一次的方法是将构造函数变为私有，同时在类中创建该类的对象，并向外界提供获取方法；
		
	}
	
	/*
	 * 饿汉式单例(立即加载方式)：1.在类加载时就完成了初始化；  2.创建的静态对象除非系统重启，否则不会改变，即是线程安全的
	 * 在类被加载时，实例化该类对象，如果其他对象需要使用，则从静态区获取即可。
	 * 书写方式一
	 */
	private static Singleton1 single = new Singleton1();

	public static Singleton1 getInstance() {
		return single;
	}
	
}
