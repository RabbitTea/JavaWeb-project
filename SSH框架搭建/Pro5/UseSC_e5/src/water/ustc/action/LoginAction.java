package water.ustc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sc.ustc.entity.UserBean;
import sc.ustc.controller.*;

public class LoginAction implements ActionDao{
	
	// 构造方法
	public LoginAction() {
		super();
	}
    
	/*
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		System.out.println("username=" + username);
		System.out.println("password=" + password);
		
		// 判断用户名和密码的有效性：规定均为admin则登录成功
		if(username.equals("admin") && password.equals("admin")) {
			request.setAttribute("username", username);  // 将传入的用户名保存
			return "success";
		}else {
			return "failure";
		}
	}
	*/
	public String execute(HttpServletRequest request,
	        HttpServletResponse response) throws Exception{
		
		String userName = request.getParameter("username");
		String userPass = request.getParameter("password");
		
		int userId = (int)(Math.random() * 11);
	    
		// 使用请求的用户名和密码，创建userBean对象，调用signIn方法处理登录业务
		UserBean ub = new UserBean(userId, userName, userPass);
		boolean loginResult = ub.signIn();
		
		// 对结果判断，进行相应的处理
		if(loginResult == true) {
			return "success";
		}else {
			return "failure";
		}

	}
}
