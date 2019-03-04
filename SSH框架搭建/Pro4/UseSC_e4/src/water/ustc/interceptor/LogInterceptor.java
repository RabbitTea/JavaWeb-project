/*
 * @description：原生API实现简单的拦截器——POJO类
 */
package water.ustc.interceptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import sc.ustc.controller.ConfigurationManager;

public class LogInterceptor {
	
	
	/*
	 * @function：记录每次客户端请求的action名称<name>和访问开始时间<s-time>
	 *           将信息追加至日志文件log.xml
	 */
	public static void preAction(HttpServletRequest request, HttpServletResponse response) {
		
		// 获取HTTP请求的部分URL路径（去掉协议、端口等，从工程名开始）
		String reqURL = request.getRequestURI();  
		
		// 对路径做处理，获得请求action的名称
		String[] urlUnits = reqURL.split("/");
		String actionFull = urlUnits[urlUnits.length - 1];
		int actionEnd = actionFull.lastIndexOf(".");   // 返回.在字符串中最后一次出现的索引
		String actionName = actionFull.substring(0, actionEnd);
		
		// 获取当前系统时间，为请求访问开始时间，并使用SimpleDateFormat进行格式化处理
		Date startTime = new Date();
		System.out.println("startTimeOld=" + startTime);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTimeFormat = sdf.format(startTime);
		System.out.println("startTimeFormat=" + startTimeFormat);
		
		/*
		 * 应用DOM4J方式写入xml：如果该xml文件不存在，则新建一个文件；如果存在，则以添加的方式写入
		 */
		
		try {
			File file = new File("G:\\Eclipse-jee-workspace\\Pro3\\log.xml");
			
			if(!file.exists()) {  // 文件不存在则新建根元素log，层次建立标签
				
				// 获取一个Document实例，代表整个xml文件内容
				Document doc = DocumentHelper.createDocument(); 
			
				// 创建根元素<log>
				Element log = doc.addElement("log");
				// 在log下创建子元素<action>
				Element action = log.addElement("action");
				// 在action下创建子元素<name>、<s-time>，并添加获取的内容
				Element name = action.addElement("name");
				name.setText(actionName);
				Element s_time = action.addElement("s-time");
				s_time.setText(startTimeFormat);
				//Element e_time = action.addElement("e-time");
				//Element result = action.addElement("result");
				
				
				// 生成xml文件
				OutputFormat format = OutputFormat.createPrettyPrint(); // 设置xml文件格式，以缩进和换行
				format.setEncoding("UTF-8");  // 设置编码格式
				XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
				writer.setEscapeText(false);   // 处理转义字符，如"<"；false代表不转义，默认为true
				
				// 将document写入创建好的xml文件
				writer.write(doc);
			}
			else {  // 文件已经存在，获取根元素log
				SAXReader reader = new SAXReader();
				Document document = reader.read(file);   // 获取xml文件的整个目录结构
				
				Element log = document.getRootElement();    // 获取xml树根节点<log>
				Element action = log.addElement("action");
				// 在action下创建子元素<name>、<s-time>，并添加获取的内容
				Element name = action.addElement("name");
				name.setText(actionName);
				Element s_time = action.addElement("s-time");
				s_time.setText(startTimeFormat);
				
				
				// 生成xml文件
				OutputFormat format = OutputFormat.createPrettyPrint(); // 设置xml文件格式，以缩进和换行
				format.setEncoding("UTF-8");  // 设置编码格式
				XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
				writer.setEscapeText(false);   // 处理转义字符，如"<"；false代表不转义，默认为true
				
				// 将document写入创建好的xml文件
				writer.write(document);
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	 * @function：记录每次客户端请求的访问结束时间<e-time>和请求返回结果<result>
	 *           将信息追加至日志文件log.xml
	 */
	public static void afterAction(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, InstantiationException,
	                   IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, DocumentException {
		
		// 获取当前系统时间，为请求访问结束时间，并使用SimpleDateFormat进行格式化处理
		Date endTime = new Date();
		System.out.println("endTime=" + endTime);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String endTimeFormat = sdf.format(endTime);
		System.out.println("endTimeFormat=" + endTimeFormat);
		
		// 仍需获取action的名称，与xml文件进行匹配
		String reqURL = request.getRequestURI();
		String[] urlUnits = reqURL.split("/");
		String actionFull = urlUnits[urlUnits.length - 1];
		int actionEnd = actionFull.lastIndexOf(".");   // 返回.在字符串中最后一次出现的索引
		String actionName = actionFull.substring(0, actionEnd);	
		
		// 获取请求访问结果<result>
	    ConfigurationManager cm = new ConfigurationManager(reqURL, request, response);
		String reqResult = cm.handleResult();
		
		/*
		 * 应用DOM4J方式写入xml
		 */
		try {
			// 因为是action处理之后的拦截器方法，所以log.xml文件已经生成，无需创建根节点
			File file = new File("G:\\Eclipse-jee-workspace\\Pro3\\log.xml");
			SAXReader reader = new SAXReader();
			Document document = reader.read(file);   // 获取xml文件的整个目录结构
			
			Element log = document.getRootElement();    // 获取xml树根节点<log>
			List<Element> actions = log.elements("action");   // 获取所有的<action>标签，得到集合
			
			// 匹配对应的action
			for(int i=0;i<actions.size();i++) {
				Element action = actions.get(i);
				Element name = action.element("name");
				if(name.getText().equals(actionName)) {
					// 在action下创建子元素<e-time>、<result>，并添加获取的内容
					Element e_time = action.addElement("e-time");
					e_time.setText(endTimeFormat);
					Element result = action.addElement("result");
					result.setText(reqResult);
				}
			}
			
			// 生成xml文件
			OutputFormat format = OutputFormat.createPrettyPrint(); // 设置xml文件格式，以缩进和换行
			format.setEncoding("UTF-8");  // 设置编码格式
			XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
			writer.setEscapeText(false);   // 处理转义字符，如"<"；false代表不转义，默认为true
			
			// 将document写入创建好的xml文件
			writer.write(document);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	// 测试模块
	public static void main(String[] args) {
		
	}
}
