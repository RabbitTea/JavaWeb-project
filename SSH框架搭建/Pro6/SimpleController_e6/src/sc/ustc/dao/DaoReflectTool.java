package sc.ustc.dao;

import java.lang.reflect.Method;

/*
 * ORM映射的关键在于，获取xml文件规定的对应关系后，如何将对对象的CURD操作转化为对数据库表的CURD操作；
 *          此时涉及到sql的拼接，需要获取对象属性域的值，这里不能直接根据xml文件中的字符串值进行获取，因为其对于实际编程是不可见的，
 *          所以需要有1-2个实体类存储xml文件的映射信息，提供get和set方法，以供外界进行获取；
 *          get和set方法的命名是根据属性域的名称的，有一定的规律，所以可以根据反射构建对应的get和set方法。
 */

public class DaoReflectTool {
	
	//定义静态方法方便直接通过类名调用
	public static Method getGetMethod(Class objClass, String fieldName) { //得到get方法对象
		StringBuilder getMethod = new StringBuilder();   //保存方法名字符串
		
		getMethod.append("get");
		getMethod.append(fieldName.substring(0, 1).toUpperCase());  //属性域第一个字母大写
		getMethod.append(fieldName.substring(1));    //表示取第二个字符到最后的所有字符
		
		try {
			/*
			 * 反射；注意这里需要调用[对应类]的getMethod方法，根据方法名参数，得到方法对象
			 */
			return objClass.getMethod(getMethod.toString());
			
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}   
		
		return null;  //抛出异常返回null值
	}
	
	public static Method getSetMethod(Class objClass, String fieldName) {
		StringBuilder setMethod = new StringBuilder();
		
		setMethod.append("set");
		setMethod.append(fieldName.substring(0, 1).toUpperCase());
		setMethod.append(fieldName.substring(1));
		
		try {
			return objClass.getMethod(setMethod.toString());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
