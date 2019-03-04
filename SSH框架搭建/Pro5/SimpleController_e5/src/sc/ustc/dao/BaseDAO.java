package sc.ustc.dao;

/*
 * @description：不使用框架，用原始的JDBC来连接并访问数据库
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseDAO {
	// protected Driver driver;  // 数据库驱动类
	protected String url;     // 数据库访问路径
	protected String sqlName;   // 数据库用户名
	protected String sqlPassword;   // 数据库用户密码
	
	// 构造方法
	public BaseDAO(String url, String sqlName, String sqlPassword) {
		super();
		this.url = url;
		this.sqlName = sqlName;
		this.sqlPassword = sqlPassword;
	}
	
	/*
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	*/

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUserName(String userName) {
		this.sqlName = userName;
	}

	public void setUserPassword(String userPassword) {
		this.sqlPassword = userPassword;
	}
	
	
    /*
     * @function：实现的方法——打开数据库连接
     */
	public Connection openDBConnection() {
		
		Connection conn = null;
		try {
			
			// 加载数据库驱动程序
			Class.forName("com.mysql.jdbc.Driver");
			// 获取数据库连接
			String url = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=UTF-8";
			conn = DriverManager.getConnection(url, sqlName, sqlPassword);
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	
	/*
	 * @function：实现的方法——关闭数据库连接
	 */
	public boolean closeDBConnection() {
		Connection conn = openDBConnection();
		try {
			if(conn != null) {
				conn.close();
				System.out.println("数据库连接已关闭");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	/*
	 * @description：下面为抽象方法
	 */
	// 负责执行sql语句进行查询，并返回结果对象；
	public abstract Object query(String sql);
	
	// 负责执行sql语句进行插入，并返回执行结果；
	public abstract boolean insert(String sql);
	
	// 负责执行sql语句进行更新，并返回执行结果；
	public abstract boolean update(String sql);
	
	// 负责执行sql语句进行删除，并返回执行结果；
	public abstract boolean delete(String sql);
	
}
