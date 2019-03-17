package sc.ustc.dao;

import java.util.ArrayList;
import java.util.List;

/*
 * 封装xml文件中实体名和表名的对应信息；
 * 创建BeanInfo和BeanPropertyToTable两个实体类方便转换时进行信息的分离；
 */

public class BeanInfo {
	private String name;   //实体类名
	private String table;   //实体类对应的表名
	private String userID;   
	
	private BeanPropertyToTable id;   //表示BeanPropertyToTable对象的个数，对应table表的id字段值
	private List<BeanPropertyToTable> beanPropertyToTables = new ArrayList<>();   //创建列表，保存封装属性映射信息的对象
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public BeanPropertyToTable getId() {
		return id;
	}
	public void setId(BeanPropertyToTable id) {
		this.id = id;
	}
	public List<BeanPropertyToTable> getBeanPropertyToTables() {
		return beanPropertyToTables;
	}
	public void setBeanPropertyToTables(List<BeanPropertyToTable> beanPropertyToTables) {
		beanPropertyToTables.add(getId());
		this.beanPropertyToTables = beanPropertyToTables;
	}
}
