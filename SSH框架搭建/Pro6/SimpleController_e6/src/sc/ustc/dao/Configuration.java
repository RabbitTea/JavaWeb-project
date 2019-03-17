package sc.ustc.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.RootPaneContainer;
import javax.xml.namespace.QName;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/*
 * @description：负责解析UseSC工程的配置文件or_mapping.xml;
 *                 这里映射信息和数据库配置信息统一到一个xml文件中；         
 *                 使用DOM4J方式[需要导入dom4j的jar包];
 *              负责连接数据库和关闭数据库;
 */

public class Configuration {
	private static String xmlPath = "G:\\Eclipse-jee-workspace\\Pro6\\UseSC_e6\\src\\or_mapping.xml";
	
	/*
	 * 数据库连接信息
	 */
	private static String driClass;   //数据库驱动类
	private static String url;   //数据库访问路径
	private static String sqlName;   //数据库用户名
	private static String sqlPass;   //数据库用户密码
	
	private static Connection conn;
	
	/*
	 * 实体-关系映射信息
	 */
	
	
	/*
	 * 构造函数：调用getDBConInfo()方法，更新全局变量的值
	 */
	public Configuration() {
		try {
			getDBConInfo();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	 * 解析数据库连接配置，更新数据库配置参数；
	 */
	public static void getDBConInfo() throws DocumentException {
		
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(xmlPath));  //使用SAXReader对象的方法将xml文件读到内存中，存储为Document对象
		
		//获取xml的根标签<OR-Mappings>
		Element root = document.getRootElement();
		//获取<jdbc>数据库配置标签
		Element jdbc = root.element("jdbc");
		
		//获取所有<property>标签元素，存入列表
		//QName qName = new QName("property");   //在xml文件中，元素和属性都是用QName表示的
		List<Element> properties = jdbc.elements();
		//依次获取连接参数的值，并更新全局变量
		for(int i=0;i<properties.size();i++) {
			Element property = properties.get(i);
			Element name  = property.element("name");
			//System.out.println("name=" + name.getText());
		    if(name.getText().equals("driver_class")) {
		    	driClass = property.element("value").getText();
		    	//System.out.println(property.element("value").getText());
		    }
		    else if(name.getText().equals("url_path")) {
		    	url = property.element("value").getText();
		    }
		    else if(name.getText().equals("db_username")) {
		    	sqlName = property.element("value").getText();
		    }
		    else if(name.getText().equals("db_userpassword")) {
		    	sqlPass = property.element("value").getText();
		    }
		}
		//System.out.println(sqlName);  //测试
		
		/*测试
		for(int i=0;i<properties.size();i++) {
			System.out.print(properties.get(i)+ " ");
		}
		*/
	}
	
	
	/*
	 * 根据配置信息，打开数据库连接
	 */
	public Connection openDBConnection() {
		
		try {
			//加载数据库驱动程序
			Class.forName(driClass);
			//获取数据库连接
			conn = DriverManager.getConnection(url, sqlName, sqlPass);
			
			System.out.println("server connection start...");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	
	/*
	 * 关闭数据库连接
	 */
	public boolean closeDBConnection() {
		
		try {
			if(conn != null) {
				conn.close();
				System.out.println("server connection close..");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	
	/*
	 * 解析对象-关系映射信息，保存到map结构中，key为对象属性名，value为关系表中对应的列名；
	 */
	public static Map<String, String> getORMInfo() throws DocumentException{
		Map<String, String> ormInfo = new HashMap<>();  //存储映射关系
		
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(xmlPath));
		Element root = document.getRootElement();
		Element ormClass = root.element("class");
		
		//添加对象名和表名对应关系
		String objName = ormClass.elementText("name");
		String table = ormClass.elementText("table");
		ormInfo.put(objName, table);
		
		//添加ID属性和列名的对应关系
		String userID = ormClass.element("id").elementText("name");
		ormInfo.put(userID, userID);
		
		//添加其他属性和列名的对应关系
		List<Element> properties = ormClass.elements("property");
		for(Element property : properties) {
			String name = property.elementText("name");
			String column = property.elementText("column");
			ormInfo.put(name, column);
		}
		
		/*测试——在for each循环中使用entry来遍历，适用于键和值都要输出时
		for(Map.Entry<String, String> entry : ormInfo.entrySet()) {
			System.out.println("key=" + entry.getKey() + ",value=" + entry.getValue());
		}
		*/
		
		return ormInfo;
	}
	
	
	/*
	 * 解析实体和关系表属性的映射关系，保存到BeanPropertyToTable对象
	 */
	public static List<BeanPropertyToTable> getORMBeanToTableInfo() throws DocumentException {
		List<BeanPropertyToTable> lists = new ArrayList<>();
		
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(xmlPath));
		Element root = document.getRootElement();
		Element ormClass = root.element("class");
		
		List<Element> properties = ormClass.elements("property");
		for(Element property : properties) {
			BeanPropertyToTable bptt = new BeanPropertyToTable();
			
			String name = property.elementText("name");
			String column = property.elementText("column");
			String type = property.elementText("type");
			String isLazy = property.elementText("lazy");
			Boolean lazy = null;
			
			if(isLazy.equals("false")) {
				lazy = false;
			}else {
				lazy = true;
			}
			
			bptt.setName(name);
			bptt.setColumn(column);
			bptt.setType(type);
			bptt.setType(type);
			bptt.setLazy(lazy);
			
			lists.add(bptt);
		}
		
		return lists;
	}
	
	
	/*
	 * 解析实体名和表名的映射关系，保存到BeanInfo对象
	 */
	public static BeanInfo getORMBeanInfo() throws DocumentException {
		BeanInfo beanInfo = new BeanInfo();
		
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(xmlPath));
		Element root = document.getRootElement();
		Element ormClass = root.element("class");
		
		//添加对象名和表名对应关系
		String objName = ormClass.elementText("name");
		String table = ormClass.elementText("table");
		String userID = ormClass.elementText("id");
		
		List<BeanPropertyToTable> lists = getORMBeanToTableInfo();
		
		beanInfo.setName(objName);
		beanInfo.setTable(table);
		beanInfo.setUserID(userID);
		beanInfo.setBeanPropertyToTables(lists);
		
		return beanInfo;
	}
	
	
	//测试
	public static void main(String[] args) {
		try {
			//getDBConInfo();
			Map<String, String> ormInfo = getORMInfo();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
}
