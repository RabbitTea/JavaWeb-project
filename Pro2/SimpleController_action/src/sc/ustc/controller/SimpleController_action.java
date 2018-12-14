package sc.ustc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

public class SimpleController_action extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// super.doGet(request, response);
		
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// super.doPost(request, response);
		
		System.out.println("start  ");
		
		// 获取HTTP请求的部分URL路径（去掉协议、端口等，从工程名开始）
		String reqURL = request.getRequestURI();
		
		/*
		 * 写一个工具类，辅助进行action名称的获取，controller.xml的解析，在这里进行调用；
		 */
		// 创建工具类对象
		ConfigurationManager confman = new ConfigurationManager(reqURL, request, response);
		
		
		// 返回请求的action是否存在
		try {
			String matchResult = confman.actionExist();
			
			// 如果不存在向浏览器输出失败信息
			if(matchResult == null) {
				PrintWriter out = response.getWriter();
				out.println("错误！不可识别的action请求");
			}
			else {
				/*
				 * 调用工具类处理请求，并进行响应
				*/
				ConfigurationManager cm = new ConfigurationManager(reqURL, request, response);
				cm.handleResult();
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		System.out.println("end");
	}	
}
