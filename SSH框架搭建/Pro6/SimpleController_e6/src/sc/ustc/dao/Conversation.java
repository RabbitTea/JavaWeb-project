package sc.ustc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.print.attribute.standard.RequestingUserName;

import org.dom4j.DocumentException;

import java.lang.reflect.*;

/*
 * @description：负责完成 将对象操作映射为数据表操作；
 *              即定义数据操作CRUD方法，每个方法将对象操作解释成目标数据库的DML或DDL，并通过JDBC完成数据持久化。
 */

public class Conversation {
	private static Configuration conf;
	private static Connection conn;   //连接为全局变量
	private static Map<String, String> ormInfo = new HashMap<>();  //存储映射关系
	
	//数据库访问对象
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	//构造方法：打开数据库连接，并更新映射关系全局变量
	public Conversation() {
		conf = new Configuration();
		startDBConn();    //打开数据库连接
	    try {
			ormInfo = conf.getORMInfo();   //得到映射关系
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	
	//调用Configuration中的方法打开数据库连接
	public void startDBConn() {
		conn = conf.openDBConnection();   //更新连接变量
	}
	
	
	/*
	 * 利用反射机制，根据传入的对象找到其对应的类，获取类中的属性域，构造获取得到属性值的方法的方法；
	 *           最后将对象的所有属性名和其值保存到map中。
	 */
	public Map<String, String> getObjFieldAndValue(Object object) throws InstantiationException {
		Map<String, String> objFieldInfo = new HashMap<>();
		
		try {
			/*
			 * 找到object对象所属的类，注意getClass方法得到的是[Class 类名]
			 * 所以我们要先取得类名，再用Class.forName()方法 
			 */
			Class clazz = Class.forName(object.getClass().toString().substring(6));
			System.out.println("class=" + clazz.toString());
					
			Field[] fields = clazz.getDeclaredFields();   //将类的属性域存储到Field对象数组中
			for(Field f : fields) {
				
				System.out.println("type=" + f.getGenericType());  //输出属性域的类型
				
				if(f.getGenericType() == int.class) {  //如果该属性域的类型是int则退出
					System.out.println("i'm int");
					//break;
				}
				else {
					//使得在外部能够获取类中私有成员的值
					f.setAccessible(true);
					//构造取得该属性域的值的方法
					Method getFieldMethod = DaoReflectTool.getGetMethod(clazz, f.getName());
					//调用获得方法
					System.out.println("get对应方法调用得到的值：" + getFieldMethod.invoke(object));
					String value = (String) getFieldMethod.invoke(object);
					if(value != null) {
						objFieldInfo.put(f.getName(), value);
					}
				}	
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return objFieldInfo;
	}
	
	
	/*
	 * Query()
	 * 定义数据操作查询方法：根据用户名查询用户的密码，并进行用户名是否存在的判断
	 */
	public String query(Object o) throws DocumentException, InstantiationException {
		StringBuilder sql = new StringBuilder();    //StringBuilder是非线程安全的可变字符串数组
		
		String userName = null;
		String userPass = null;
		
		String columnName = null;
		String columnPass = null;
		
		/*
		 * beanInfo对象中保存了：
		 *  1.进行查询的实体类名；
		 *  2.实体类对应的表名；
		 *  3.表中各个字段的信息，包括列名，类型和是否懒加载
		 */
		BeanInfo beanInfo = conf.getORMBeanInfo(); 
		List<BeanPropertyToTable> lists = beanInfo.getBeanPropertyToTables(); //取得属性映射信息
		
		System.out.println("lists:" + lists);
		
		/*测试
		for(BeanPropertyToTable bptt : lists) {
			System.out.println("bptt:" + bptt.getName().toString());
		}
		*/
		
		//得到传入的对象的属性域和对应值的信息
		Map<String, String> objFieldInfo = getObjFieldAndValue(o); 
		
		System.out.println("传入obj对象属性域映射的大小为：" + objFieldInfo.size());
		
		//遍历Map，找到object的属性对应的列名
		for(Entry<String, String> entry : objFieldInfo.entrySet()) {
			
			//System.out.println("i'm in traverse map...");
			
			if(entry.getKey().equals("userName")) {
				userName = entry.getValue();   //取出该对象的用户名的值
				
				System.out.println("userName= " + userName);
				
				System.out.println("lists1:" + lists);
				
				if(lists != null) {
					for(int i=0; i<lists.size()-1;i++) {
						BeanPropertyToTable beanPropertyToTable = lists.get(i);
						System.out.println("对象：" + beanPropertyToTable);
						
						if(beanPropertyToTable.getName().equals("userName")) {
							columnName = beanPropertyToTable.getColumn();   //取出用户名在数据库中对应的列名，用以构造SQL语句
							//break;
						}
					}
				}
			}
			else if(entry.getKey().equals("userPass")) {
				userPass = entry.getValue();    //取出传入对象的密码的值
				
				//System.out.println("obj的userPass=" + userPass);
				
				//System.out.println("lists2:" + lists);
				
				if(lists != null) {
					
					//System.out.println("pass lists:" + lists);
					
					for(int i=0; i<lists.size()-1;i++) {
						BeanPropertyToTable beanPropertyToTable = lists.get(i);
						//System.out.println(beanPropertyToTable.getName().toString());
						//System.out.println("对象：" + beanPropertyToTable);
						
						if(beanPropertyToTable.getName().equals("userPass")) {
							columnPass = beanPropertyToTable.getColumn();   //取出密码在数据库汇总对应的列名，用以构造SQL语句
						}
					}
				}
			}
		}
		
		//得到表名
		String tableName = beanInfo.getTable();
		
		//构造SQL语句：根据用户名查找对应的密码
		sql.append("select " + columnPass + " from " + tableName + " where " + columnName + " = " + "'Sandra'");
		
		System.out.println("sql=" + sql.toString());
		
		String returnPassFDB = null;
		try {
			//执行SQL语句
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			if(rs == null) {
				return null;   //数据库中不存在该用户名，返回null，用以做判断
			}
			while(rs.next()) {  //若仅一条内容也要用这种while的方式
				returnPassFDB = rs.getString(columnPass);
				
				System.out.println("getPassbyName() = " + returnPassFDB);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnPassFDB;    //函数返回根据用户名取得的密码值
	}
	
	
	/*
	 * 定义数据插入操作方法：向数据库中插入一条用户信息
	 */
	public boolean insert(Object o) throws DocumentException, InstantiationException {
		StringBuilder sql = new StringBuilder();
		
		BeanInfo beanInfo = conf.getORMBeanInfo();   //得到表名和实体名映射的信息
		String table = beanInfo.getTable();   //得到表名
		
		sql.append("insert into " + table + " values");   //构造前半部分SQL语句
		
		Map<String, String> objFieldInfo = getObjFieldAndValue(o);    //得到传入对象的属性域及其值
		int userID = (int) (Math.random()*11);   //id用随机数生成
		
		String userName = null;
		String userPass = null;
		for(Entry<String, String> entry : objFieldInfo.entrySet()) {
			if(entry.getKey().equals("userName")) {
				userName = entry.getValue();
			}
			else if(entry.getKey().equals("userPass")) {
				userPass = entry.getValue();
			}
		}
		
		//完成构造SQL语句
		sql.append("(" + userID + "," + "\"" + userName + "\"," + "\"" + userPass + "\"" + ")");
		
		System.out.println("insert sql=" + sql);
		
		//执行SQL
		try {
			ps = conn.prepareStatement(sql.toString());
			
			System.out.println("create ps..");
			
			int res = ps.executeUpdate(sql.toString());
			
			System.out.println("res = " + res);
			if(res != 0) {
				System.out.println("插入成功...");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//测试
	public static void main(String[] args) {
		//UserBeanTest uBeanTest = new UserBeanTest(1, "Sandra", "123456");
		UserBeanTest uBeanTest = new UserBeanTest(0, "yy", "123");
		Conversation conv = new Conversation();
		try {
			Boolean res = conv.insert(uBeanTest);
			
			//String returnPass = conv.getPassbyName(uBeanTest).toString();
			//System.out.println("getDBPass=" + returnPass );
		
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
