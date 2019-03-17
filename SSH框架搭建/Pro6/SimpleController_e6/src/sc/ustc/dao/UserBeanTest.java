package sc.ustc.dao;

import java.sql.Driver;
import sc.ustc.dao.*;


public class UserBeanTest {
	private int userId;    // 用户ID
	private String userName;   // 用户名
	private String userPass;   // 用户密码
	
	// 构造方法
	public UserBeanTest() {
		super();
	}

	public UserBeanTest(int userId, String userName, String userPass) {
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
}
