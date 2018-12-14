package water.ustc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginAction{
	
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
}
