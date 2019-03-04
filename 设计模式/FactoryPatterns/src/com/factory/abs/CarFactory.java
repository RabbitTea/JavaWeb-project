/*
 * @Description：抽象工厂模式：适用于工厂可以生产不同等级的产品；分为抽象工厂，具体工厂，抽象产品，具体产品。
 *                其中，抽象产品按产品等级划分，这里为卡车和轿车；具体产品分为产品族，为同一品牌的产品，这里宝马可以生产轿车也可以生产卡车；
 *             抽象工厂为一个，其中包含生产卡车的方法和生产轿车的方法；具体工厂实现工厂接口，按产品品牌来划分，这里为宝马工厂和奥迪工厂。
 */
package com.factory.abs;

//抽象工厂(接口)
public interface CarFactory {
	
	public Sedan produceSedan();  //生产轿车
	public Trunk produceTrunk();  //生产卡车
}
