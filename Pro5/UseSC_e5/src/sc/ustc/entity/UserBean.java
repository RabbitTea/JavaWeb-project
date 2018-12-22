package sc.ustc.entity;

import java.sql.Driver;
import sc.ustc.webdao.*;
import sc.ustc.dao.*;


public class UserBean {
	private int userId;    // 用户ID
	private String userName;   // 用户名
	private String userPass;   // 用户密码
	
	// 构造方法
	public UserBean() {
		super();
	}

	public UserBean(int userId, String userName, String userPass) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPass = userPass;
	}

	// getters & setters
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	
	
	/*
	 * @function：负责处理登录业务
	 */
	public boolean signIn() {
		
		// 根据请求的用户名构造查询语句，如果存在返回用户密码
		String userName = this.userName;
		String sql = "select * from sc_users where userName = userName";
		
		// 构造UserDAO对象
		String url = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=UTF-8";
		String sqlName = "root";
		String sqlPassword = "123456";
		BaseDAO ud = new UserDAO(url, sqlName, sqlPassword);
		
		// 调用UserDAO的query方法
		UserBean user = (UserBean)ud.query(sql);
		
		// 判断结果对象:如果为null返回false；否则取出结果对象的userPass属性，与当前对象中的userPass属性对比，若相同返回true，否则返回false。
		if(user != null) {
			String sqlUserPass = user.getUserPass();
			if(sqlUserPass.equals(this.userPass)) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
}
