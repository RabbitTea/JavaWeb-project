/*
 * @Description：建造者模式：抽象接口承担建造者角色，其可以生产一个产品所需的配件，(比如一台计算机需要主板、硬盘和CPU)，
 *          而这些配件都有不同的型号，具体的型号组成不同的计算机，builder就是进行抽象生产。
 *          特点：将一个复杂的构建与其表示相分离，使得同样的构建过程可以创建不同的表示。
 *          符合<开闭原则>，对于创建新的产品(电脑)，只需要新建实现类BuildComputer实现Builder接口。
 *          
 *          适应性：该模式创建的产品一般具有较多的共同点，其组成部分相似；而若产品间差异性很大，则不适合用建造者模式。
 */
package com.builder;

//抽象生产
public interface Builder {
	//抽象方法
	public void buildMainBoard();   //建造主板
	
	public void buildHD();    //建造硬盘
	
	public void buildCPU();   //建造CPU
	
	public Computer buildComputer();    //最终组装一台电脑
}
