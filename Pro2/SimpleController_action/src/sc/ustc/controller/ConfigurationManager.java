package sc.ustc.controller;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
//import org.w3c.dom.Document;
/*
 * 工具类：进行请求(action)名称的获取；解析controller.xml文件，对请求的action名称进行匹配，向控制器传送结果。
 */

public class ConfigurationManager {

	/*
	 * 定义需要的属性和构造函数
	 */
	private String reqURL;
	private String xmlPath;
	private HttpServletRequest request;
	private HttpServletResponse response;
	 
	public ConfigurationManager(String reqURL, HttpServletRequest request, HttpServletResponse response) {
		this.reqURL = reqURL;
		this.xmlPath = "G:\\Eclipse-jee-workspace\\Pro2\\UseSC_conf\\src\\controller.xml";
		this.request = request;
		this.response = response;
	}
	
	
	/*
	 * @function：通过截取URL中的最后一个值，来获取请求action的名称
	 */
	public String getActionName() {
		System.out.println("reqURL= " + reqURL);
		// 按/切分URL路径字符串
		String[] url_units = reqURL.split("/");
		// 取数组最后一个字符串的值，改值包含action的名称
		String actionFull = url_units[url_units.length - 1];
		// 返回.在字符串中最后一次出现的索引
		int actionEnd = actionFull.lastIndexOf(".");
		// 得到action的名称
		String actionName = actionFull.substring(0, actionEnd);  // substring方法不包括结束索引的值
		
		System.out.println("actionName= " + actionName);
		
		return actionName;
	}
	
	
	/*
	 * @function：解析controller.xml，得到action标签list，从父节点依次向下获取
	 */
	public List<Element> getActionLists() throws DocumentException{
		/*
		 * 解析语法将xml读到内存中，以树结构存储。
		 */
		
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(xmlPath));
		Element root = document.getRootElement();     // 获取xml树根节点
		Element controller = root.element("controller");   // 获取controller节点
		
		// 返回所有action标签的list集合
		List<Element> actions = controller.elements("action");
		return actions;
	}
	
	
	/*
	 * @function: 解析controller.xml，根据传入的名称判断是否存在请求的action
	 * @explain：使用DOM4J解析xml文件——Java xml API
	 */
	public String actionExist() throws DocumentException {
		String actionName = getActionName();
		
		// 得到所有action标签的list集合
		List<Element> actionLists = getActionLists();
		
		// 遍历action list
		for(Element ele:actionLists) {
			if(actionName.equals(ele.attributeValue("name"))) {  // 匹配到对应的action
				System.out.println("action_exist:" + ele.attributeValue("name"));
				return "success";
			}
		}
		return null;
	}
	
	/*
	 * @function：找到对应的action后，将请求转到相应的类中处理，返回处理结果
	 */
	public void handleResult() throws DocumentException, ClassNotFoundException, InstantiationException, 
	                                  IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		String actionName = getActionName();
		
		System.out.println("in handleResult");
		
		// 得到所有action标签的list集合
		List<Element> actionLists = getActionLists();
		
		// 遍历action list
		for(Element ele:actionLists) {
			if(actionName.equals(ele.attributeValue("name"))) {  // 匹配到对应的action
				// 得到处理类的全称
				String handleClass = ele.attributeValue("class");
				
				/*
				 * Java反射机制
				 */
				Class handleAction = Class.forName(handleClass);  // 根据完整类名得到类对象
				Method method = handleAction.getMethod("execute", HttpServletRequest.class, HttpServletResponse.class); // 根据方法名得到方法
				String result = (String) method.invoke(handleAction.newInstance(), request, response);   // 调用execute方法
				
				System.out.println("result=" + result);
				
				// 获取action下的result子标签，保存到list
				List<Element> resultLists = ele.elements("result");
				System.out.println("results num=" + resultLists.size());
				
				for(Element res : resultLists) {  // 查找对应结果的处理
					if(result.equals(res.attributeValue("name"))) {
						System.out.println("result type=" + res.attributeValue("type"));
						if("forward".equals(res.attributeValue("type"))) {  
							System.out.println("i'm in result type");
							
							// 请求转发处理
							String toPage = res.attributeValue("value");  // 获得跳转页面的路径
							System.out.println("toPage=" + toPage);
							RequestDispatcher rd = request.getRequestDispatcher(toPage);
							try {
								rd.forward(request, response);
							} catch (ServletException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						
						if("redirect".equals(res.attributeValue("type"))) {   
							// 请求重定向处理
							String toPage2 = res.attributeValue("value");  // 获得重定向页面的路径
							try {
								response.sendRedirect(toPage2);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}

			}
		}
	}
	
	
	// 测试类
	public static void main(String[] args) throws DocumentException {
		//ConfigurationManager request_aciton=new ConfigurationManager("/UseSC/login.sc",request,response);
		//System.out.println(request_aciton.actionExist());
    
	}

}
