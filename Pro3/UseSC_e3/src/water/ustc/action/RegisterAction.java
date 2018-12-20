package water.ustc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import sc.ustc.controller.*;

public class RegisterAction implements ActionDao{
	
	public String execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String repassword = request.getParameter("repassword");
		
		String reg = "^[a-zA-Z][a-zA-Z0-9_]*$";
		if(!username.matches(reg)) {
			JOptionPane.showMessageDialog(null, "用户名以英文字母开头，只能包含英文字母、数字、下划线!");
			return "failure";
		}
		else if(password.length() > 5) {
			JOptionPane.showMessageDialog(null, "密码必须大于五位!");
	        return "failure";
		}
		else if(!repassword.equals(password)) {
			JOptionPane.showMessageDialog(null, "两次密码不一致！");
			return "failure";
		}
		else {
			JOptionPane.showMessageDialog(null, "注册成功，即将跳转到登录页面");
			return "success";
		}
	}
}
