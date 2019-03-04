package sc.ustc.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	
		/*
		 * 说明：servlet容器主要起控制的作用，负责处理客户请求，调用资源并反馈响应；
		 *     这里采用[请求转发]的方式：服务器在处理request的过程中将request先后委托多个servlet或JSP接替处理的过程；
		 *     最为常见的是先由一个Servlet处理（如实现业务逻辑的计算），然后forward给一个JSP进行视图的渲染，或forward给另一个Servlet进行进一步处理
		 *     该方式[浏览器地址不会发生变化]
		 */
		//ServletContext sc = getServletContext();
		//RequestDispatcher rd = request.getRequestDispatcher("welcome.html");
		//rd.forward(request, response);
		
		/*
		 * 说明：servlet容器主要起控制的作用，负责处理客户请求，调用资源并反馈响应；
		 *     这里采用[响应重定向]的方式：即HTTP重定向；当服务器接收客户端请求后发现资源实际存放在另一个位置，便在返回的
		 *     response中写入该资源正确的URL，并设置response的状态码为301（表示这是一个要求浏览器重定向的response)；
		 *     客户端接收到这个response后就会根据新的URL重新发出请求。
		 *     该方式[浏览器地址会发生变化]
		 */
		//response.sendRedirect("welcome.html");
		
		System.out.print("start");
		
		/*
		 * 说明：这里控制器接收到请求后，对资源进行输出处理，呈现给客户
		 */
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();   // 输出页面内容

		out.println("<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"	<head>\r\n" + 
				"		<meta charset=\"UTF-8\">\r\n" + 
				"		<title>SimpleContr</title>\r\n" + 
				"	</head>\r\n" + 
				"	<body>\r\n" + 
				"		欢迎使用SimpleController！\r\n" + 
				"	</body>\r\n" + 
				"</html>");
		out.flush();
		out.close();
		
		System.out.print("end");
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		// 调用doPost方法
		doPost(request,response); 
	}
}
