/*
 * @Description：装饰器模式：对于基础对象，添加功能，起到装饰的目的；其提供了比继承更具弹性的替代方案，属于结构型；(继承容易造成类数量爆炸)
 *               以饮品为例，在共同基础上添加不同的装饰(表示)：如饮品有啤酒和可乐两种，啤酒本身有基础的价格和名称(啤酒)，但其
 *               有多个品牌(百威，青岛，燕京...)，啤酒是实现了饮品接口的具体类，而区分不同的啤酒用到了装饰器的功能；
 *               
 *               在不改变原有对象的基础上，将功能添加到对象上；
 *               符合开闭原则；
 *               
 *               缺点：多层装饰比较复杂，可读性较差。
 */
package com.decorator;

/*
 * 关于该类前是否要加abstract关键字：
 *      在其中需要有 由具体装饰者实现的方法时使用
 *      如 public abstract void method();
 *      
 *      抽象类中可以有具体方法；
 */

//装饰器抽象类(基类)——用来进行扩展——被具体的装饰类继承
public class Decorator implements Drink{
	
	/*
	 * 声明为Drink类型的优势是，不论添加了什么产品，装饰器抽象类都不需要修改(啤酒，可乐，果汁...)
	 */
	Drink drink;   //通过接口对象把其中的基础信息拿到，方便具体的装饰者在其基础上进行装饰
	
	//用接口对象进行构造
	public Decorator(Drink drink) {
		this.drink = drink;
	}

	@Override
	public double price() {
		return drink.price();
	}

	@Override
	public String name() {
		return drink.name();
	}
	
}
