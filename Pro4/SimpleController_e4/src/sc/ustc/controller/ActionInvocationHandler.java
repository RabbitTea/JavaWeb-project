package sc.ustc.controller;

/*
 * @description：代理类
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ActionInvocationHandler implements InvocationHandler {
	
	Object actionTarget;
	
	String xmlPath;
	Element actionNode;
	String actionName;
	
	//HttpServletRequest request;
	//HttpServletResponse response;
	
	//List<Element> actionLists;
	
	public ActionInvocationHandler() {
		super();
	}
	
	public ActionInvocationHandler(Object actionTarget) {
		super();
		this.actionTarget = actionTarget;
	}

	// 构造方法
	public ActionInvocationHandler(Object actionTarget, String xmlPath, Element actionNode, String actionName) {
		super();
		this.actionTarget = actionTarget;
		this.xmlPath = xmlPath;
		this.actionNode = actionNode;
		this.actionName = actionName;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		/*
		 * △执行action类之前的操作；
		 * 解析controller.xml文件的interceptor标签，利用反射机制找到对应类和preAction方法进行处理
		 */
		System.out.println("------before " + actionName + " " + method.getName() + "------");
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(xmlPath));
		Element root = document.getRootElement(); // 获取xml树根节点
		Element interceptor = root.element("interceptor"); // 获取interceptor节点

		// 找到interceptor完整类名
		String className = interceptor.attributeValue("class");
		// 根据完整类名得到类对象
		Class loginInterceptor = Class.forName(className);
		// 根据方法名得到方法对象
		Method preAction = loginInterceptor.getMethod("preAction", HttpServletRequest.class, HttpServletResponse.class);
		// 调用preAction方法
		//preAction.invoke(loginInterceptor.newInstance(), request, response);
		preAction.invoke(loginInterceptor.newInstance(), args);

		
		/*
		 * 执行action类方法
		 * 解析controller.xml文件的action标签，利用反射机制进行对应请求的处理
		 */
		String result = (String) method.invoke(actionTarget, args); // 调用execute方法

		System.out.println("result=" + result);

		// 获取action下的result子标签，保存到list
		List<Element> resultLists = actionNode.elements("result");
		System.out.println("results num=" + resultLists.size());

		for (Element res : resultLists) { // 查找对应结果的处理
			if (result.equals(res.attributeValue("name"))) {
				
				System.out.println("result type=" + res.attributeValue("type"));
				if ("forward".equals(res.attributeValue("type"))) {
					System.out.println("i'm in result type");

					// 请求转发处理
					String toPage = res.attributeValue("value"); // 获得跳转页面的路径
					
					System.out.println("toPage=" + toPage);
					
					/*
					 *  @description：如果返回result的资源后缀为 *_view.xml，由控制器根据xml文件的定义，动态生成推送至客户端的视图(浏览器可执行的HTML页面)
					 *  △ 这里使用XSLT将xml文档转换为其它文档。[XSL为扩展样式表语言，其描述如何来显示xml文档；XSLT为XSL转换]]
					 */
					if(toPage.equals("pages/success_view.xml")) {
						
						System.out.println("i'm in xslt");

						/*
						try {
							TransformerFactory transFactory = TransformerFactory.newInstance();
							
							// 转换参照的xsl样式
							Transformer transformer = 
									transFactory.newTransformer
									(new javax.xml.transform.stream.StreamSource
											("G:\\Eclipse-jee-workspace\\Pro4\\UseSC_e4\\WebContent\\pages\\success_view.xsl"));
							
							transformer.transform
							(new javax.xml.transform.stream.StreamSource
									("G:\\Eclipse-jee-workspace\\Pro4\\UseSC_e4\\WebContent\\pages\\success_view.xml"), 
							 new javax.xml.transform.stream.StreamResult
							        (new FileOutputStream("G:\\Eclipse-jee-workspace\\Pro4\\UseSC_e4\\WebContent\\pages\\success_view.html")));
						} catch (TransformerConfigurationException e) {
							e.printStackTrace();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (TransformerFactoryConfigurationError e) {
							e.printStackTrace();
						} catch (TransformerException e) {
							e.printStackTrace();
						}
						
						System.out.println("transform successly");
						*/
					
//			
					    try {
					    	Thread.sleep(5000);
					    } catch (Exception e) {  
				            e.printStackTrace();  
				        }  
						
						// △这里修改为跳转到转换xml样式后得到的HTML页面
						//RequestDispatcher rd = (RequestDispatcher)((HttpServletRequest) args[0]).getRequestDispatcher("/pages/success_view.html");
						RequestDispatcher rd = (RequestDispatcher)((HttpServletRequest) args[0]).getServletContext().getRequestDispatcher("/pages/success_view.html");
						try {
							
							rd.forward((HttpServletRequest)args[0], (HttpServletResponse) args[1]);
						} catch (ServletException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				if ("redirect".equals(res.attributeValue("type"))) {
					// 请求重定向处理
					String toPage2 = res.attributeValue("value"); // 获得重定向页面的路径
					try {
						((HttpServletResponse) args[1]).sendRedirect(toPage2);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		
		/*
		 * △执行action类之后的操作；
		 * 利用反射机制找到对应类和afterAction方法进行处理
		 */
		System.out.println("------after " + actionName + " " + method.getName() + "------");
        Method afterAction = loginInterceptor.getMethod("afterAction", HttpServletRequest.class, HttpServletResponse.class);
        afterAction.invoke(loginInterceptor.newInstance(), args);
		
		return null;
	}

}
