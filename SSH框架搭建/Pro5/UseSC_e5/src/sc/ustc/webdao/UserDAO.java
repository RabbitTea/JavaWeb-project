package sc.ustc.webdao;

/*
 * @description：封装与用户相关的数据库操作方法，实现数据的查询、修改、插入和删除。
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sc.ustc.dao.*;
import sc.ustc.entity.UserBean;

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

	@Override
	public boolean insert(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see sc.ustc.dao.BaseDAO#query(java.lang.String)
	 * @function：根据用户名查询该用户是否存在，如果存在，从数据库中取出password属性，构造一个新的UserBean对象；否则返回null
	 */
	public Object query(String sql) {
		// 实例化一个用户对象
		UserBean user = null;
		
		// 调用父类方法获取数据库连接
		Connection conn = openDBConnection();
		
		try {
			// 获取PreparedStatement对象，用于执行数据库查询
			PreparedStatement ps = conn.prepareStatement(sql);
			// 执行查询获取结果集
			ResultSet rs = ps.executeQuery();
			
			// 判断结果集是否有效，若有效，则该用户名存在，返回数据库中的密码属性值，构造一个新的UserBean对象
			while(rs.next()) {
				
				user = new UserBean();
				
				// 对用户对象进行赋值
				user.setUserId(rs.getInt("userId"));
				user.setUserName(rs.getString("userName"));
				user.setUserPass(rs.getString("userPass"));
			}
			
			// 释放资源
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // 关闭数据库连接
			closeDBConnection();
		}
		
		return user;
	}

	@Override
	public boolean update(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
