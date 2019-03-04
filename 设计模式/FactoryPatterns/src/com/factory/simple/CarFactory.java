/*
 * @description：简单工厂方法：工厂负责创建一种类型的产品，该类型抽象出产品接口，具体产品类通过实现这个接口从而产生多态的表达；
 *               这里工厂是一个具体的类，其业务逻辑和判断逻辑都在该类中，不容易进行扩展。
 *    不满足<开闭原则：对扩展开放，对修改关闭>
 *       △注意：这里工厂类中的方法最好使用静态的(static)，这样可以在调用时直接使用类名来调用，而无需创建对象，减少了代码量
 */

//生产轿车的工厂类
package com.factory.simple;

public class CarFactory {
	
	public static Car produceCar(String type) {
		/*
		 * 用条件语句来实现
		 */
		/*
		if(type.equals("宝马")) {  //String类型判断值相等用equals
			return new BmwCar_S();
		}else if(type.equals("奥迪")) {
			return new AodiCar_S();
		}else {//这里可以抛出一个自定义异常，也可以返回null
			throw new RuntimeException("您需要的车型不存在，可以自己造一辆...");   
		}
		*/
		
		/*
		 * 用switch选择语句来实现
		 */
		switch (type) {
			case "宝马":
				return new BmwCar_S();
			case "奥迪":
				return new AodiCar_S();
			default:
				return null;
		}
	}
}
