package sc.ustc.webdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import sc.ustc.entity.*;
import sc.ustc.dao.*;

/*
 * @description：封装与用户相关的数据库操作方法，实现数据的查询、修改、插入和删除。
 */

import sc.ustc.dao.*;

public class UserDAO extends BaseDAO{
	
	protected String url;
	protected String sqlName;  // 数据库用户名
	protected String sqlPassword;  // 数据库用户密码
	
	// 构造方法
	public UserDAO(String url, String sqlName, String sqlPassword) {
		super(url, sqlName, sqlPassword);
	}

	
	@Override
	public boolean delete(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	
	/*
	 * @function：插入一条用户信息，将原来对数据表的操作修改为对对象的操作
	 */
	public boolean insert(Object o) {
		Conversation conversation = new Conversation();
		Boolean res = conversation.insert(o);
		
		return res;
	}


	@Override
	public boolean update(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}


	/*
	 * (non-Javadoc)
	 * @see sc.ustc.dao.BaseDAO#query(java.lang.String)
	 * @function：将原来对数据表的查询操作修改为对对象的查询操作，实际调用SimpleController_e6中对数据表的操作
	 *           传入对象，查询其在数据库中对应的密码
	 */
	public Object query(Object o) {
		String returnPassFDB = null;
		
		Conversation conversation = new Conversation();
		returnPassFDB = conversation.query(o);
		
		return returnPassFDB;
	}


	@Override
	public boolean insert(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Object query(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
