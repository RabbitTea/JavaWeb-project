package sc.ustc.dao;

/*
 * 封装xml文件中映射的属性列名对应信息
 * 多个属性-列名的对应就有多个该对象
 */

public class BeanPropertyToTable {
	private String name;    //实体属性名
	private String column;    //实体属性对应的列名
	private String type;    //属性类型
	private boolean lazy;     //是否懒加载
	
	public BeanPropertyToTable() {
		
	}

	public BeanPropertyToTable(String name, String column, String type, boolean lazy) {
		super();
		this.name = name;
		this.column = column;
		this.type = type;
		this.lazy = lazy;
	}

	public String getName() {
		System.out.println("this.name=" + name);
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isLazy() {
		return lazy;
	}

	public void setLazy(boolean lazy) {
		this.lazy = lazy;
	}
	
}
