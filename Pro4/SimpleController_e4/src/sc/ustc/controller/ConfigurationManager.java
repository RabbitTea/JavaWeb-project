package sc.ustc.controller;

/*
 * 工具类：进行请求(action)名称的获取；解析controller.xml文件，对请求的action名称进行匹配，调用拦截器方法和对应的action处理方法，并向控制器传送结果。
 */


import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ConfigurationManager {

	private final static String XSL_PATH = "G:\\Eclipse-jee-workspace\\Pro4\\UseSC_e4\\WebContent\\pages\\success_view.xsl";
	private final static String XML_PATH = "G:\\Eclipse-jee-workspace\\Pro4\\UseSC_e4\\WebContent\\pages\\success_view.xml";
	private final static String HTML_PATH = "G:\\Eclipse-jee-workspace\\Pro4\\UseSC_e4\\WebContent\\pages\\success_view.html";
	
	/*
	 * 定义需要的属性和构造函数
	 */
	private String reqURL;
	private String xmlPath;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public ConfigurationManager(String reqURL, HttpServletRequest request, HttpServletResponse response) {
		this.reqURL = reqURL;
		this.xmlPath = "G:\\Eclipse-jee-workspace\\Pro4\\UseSC_e4\\src\\controller.xml";
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
		String actionName = actionFull.substring(0, actionEnd); // substring方法不包括结束索引的值

		System.out.println("actionName= " + actionName);

		return actionName;
	}

	/*
	 * @function：解析controller.xml，得到action标签list，从父节点依次向下获取
	 */
	public List<Element> getActionLists() throws DocumentException {
		/*
		 * 解析语法将xml读到内存中，以树结构存储。
		 */

		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(xmlPath));
		Element root = document.getRootElement(); // 获取xml树根节点
		Element controller = root.element("controller"); // 获取controller节点

		// 返回所有action标签的list集合
		List<Element> actions = controller.elements("action");
		return actions;
	}

	/*
	 * @function: 解析controller.xml，根据传入的名称判断是否存在请求的action
	 * 
	 * @explain：使用DOM4J解析xml文件——Java xml API
	 */
	public String actionExist() throws DocumentException {
		String actionName = getActionName();

		// 得到所有action标签的list集合
		List<Element> actionLists = getActionLists();

		// 遍历action list
		for (Element ele : actionLists) {
			if (actionName.equals(ele.attributeValue("name"))) { // 匹配到对应的action
				System.out.println("action_exist:" + ele.attributeValue("name"));
				return "success";
			}
		}
		return null;
	}

	/*
	 * @function：找到对应的action后，将请求转到相应的类中处理，返回处理结果
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String handleResult()
			throws DocumentException, ClassNotFoundException, InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		String actionName = getActionName();

		System.out.println("in handleResult");

		// 得到所有action标签的list集合
		List<Element> actionLists = getActionLists();

		// 声明结果对象
		String result = null;

		// 遍历action list
		for (Element ele : actionLists) {
			if (actionName.equals(ele.attributeValue("name"))) { // 匹配到对应的action
				// 得到处理类的全称
				String handleClass = ele.attributeValue("class");

				/*
				 * Java反射机制
				 */
				Class handleAction = Class.forName(handleClass); // 根据完整类名得到类对象
				Method method = handleAction.getMethod("execute", HttpServletRequest.class, HttpServletResponse.class); // 根据方法名得到方法
				result = (String) method.invoke(handleAction.newInstance(), request, response); // 调用execute方法

				System.out.println("result=" + result);
			}
		}
		return result;
	}

	
	/*
	 * @function：执行拦截器方法和调用action类进行处理
	 */
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public void handleInterceptor() throws Exception {

		String actionName = getActionName();

		System.out.println("in handleInterceptor");

		// 得到所有action标签的list集合
		List<Element> actionLists = getActionLists();

		// 遍历action list
		for (Element ele : actionLists) {
			if (actionName.equals(ele.attributeValue("name"))) {
				/*
				 * 判断匹配的action中是否配置了拦截器
				 */
				
				System.out.println("拦截器标签=" + ele.elementText("interceptor-ref"));
				Element interceptor_ref = ele.element("interceptor-ref");
				System.out.println("拦截器引用名称=" + interceptor_ref.attributeValue("name"));
				
				if ("log".equals(ele.element("interceptor-ref").attributeValue("name"))) {
					System.out.println("配置了拦截器");
					
					/*
					 * @function：将xml定义的视图转化为HTML
					 */
					TransformerFactory transFactory = TransformerFactory.newInstance();
					
					// 转换参照的xsl样式
					StreamSource streamSource1 =  new StreamSource(XSL_PATH);
					Transformer transformer = transFactory.newTransformer(streamSource1);
					
					StreamSource streamSource2 = new StreamSource(XML_PATH);
					FileOutputStream fileOutputStream = new FileOutputStream(HTML_PATH);
					StreamResult streamResult = new StreamResult(fileOutputStream);
					transformer.transform(streamSource2, streamResult);
					
					fileOutputStream.flush();
					fileOutputStream.close();
				
					
					/*
					 * 通过Java动态代理机制(InvocationHandler，Proxy，cglib)实现action执行前后拦截器方法的调用；
					 * 需要被代理的类是登录和注册的action类，即每次在访问目标action时，先生成该action的代理，在代理中实施拦截功能。
					 * △知识点：AOP的拦截功能是由Java的动态代理机制实现的，即在目标类的基础上增加切面逻辑，可以在目标类函数执行之前、之后、抛出异常时执行。
					 * AOP的源码中同时用到两种动态代理来实现拦截切入：jdk动态代理和cglib动态代理：
					 *        其中，jdk动态代理由Java的内部反射机制来实现，前提是目标类必须基于统一的接口；cglib动态代理底层借助asm来实现。
					 * 
					 * 注：这里使用的是jdk动态代理的方式；写了一个简单的action类接口，定义了execute方法，使LoginAction和RegisterAction都实现这个接口。
					 */
                    
					// 得到处理action类的全称
					String handleClass = ele.attributeValue("class");
					/*
					 * Java反射机制找到对应的action类，并执行相应的方法。
					 */
					Class handleAction = Class.forName(handleClass); // 根据完整类名得到类对象
					Method method = handleAction.getMethod("execute", HttpServletRequest.class, HttpServletResponse.class); // 根据方法名得到方法对象
					
					// 获得对应action类的实例对象
					Object actionTarget = handleAction.newInstance();
					// 实例化调用处理器对象
					InvocationHandler actionIH = new ActionInvocationHandler(actionTarget, xmlPath, ele, actionName);
					// 实例化代理类对象，处理器实例对象作为构造参数
					ActionDao actionProxy = (ActionDao)Proxy.newProxyInstance(handleAction.getClassLoader(), handleAction.getInterfaces(), actionIH);
					// 使用代理类对象执行委托类的方法
					actionProxy.execute(request, response);
				}
			}
		}

	}
	
	

	// 测试类
	public static void main(String[] args) throws Exception {
		/*
		HttpServletRequest request = null;
		HttpServletRequest response = null;
		ConfigurationManager request_aciton = new ConfigurationManager("/UseSC/login.sc",request,(HttpServletResponse) response);
		// System.out.println(request_aciton.actionExist());
        request_aciton.handleInterceptor();
        */
	}

}
