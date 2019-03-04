package com.factory.singleton;

public class Singleton3 {
	private Singleton3() {
		
	}
	
	
	/*
	 * 懒汉式单例(延迟加载方式)：在类加载时不初始化
	 * △多线程环境下会产生多个single对象
	 */
	/*
	 * 解决懒汉式单例线程不安全问题：
	 * 1.使用synchronized同步锁，这时注意关键字管辖的范围(所在的位置)——这样可以提高执行效率，即让其锁的范围越小越好；
	 * synchronized和static结合，锁的是类，而没有static，锁的是当前类的对象；
	 */
	private static Singleton3 single = null;
	
	//第一个线程调用该方法时会获得锁，运行完该方法后会释放锁，然后第二个线程再拿到锁；
	/*这种加锁方式范围太大，执行效率较低；
	synchronized public static Singleton3 getInstance() {
		if(single == null) {
			
			try {
				//让线程睡眠10毫秒
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			single = new Singleton3();
		}
		return single;
	}
	*/
	
	
	/*
	 * 缩小synchronized锁的范围，其应该锁的是类
	 */
	public static Singleton3 getInstance() {
		//双重检测
		if(single == null) {
			synchronized (Singleton3.class){
				if(single == null) {
					single = new Singleton3();
				}
			}
		}		
			
		return single;	
	}
}
