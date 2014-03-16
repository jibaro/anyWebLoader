package com.anysoft.webloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.swing.filechooser.FileFilter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.anysoft.util.IOTools;

/**
 * Servlet代理
 * 
 * 可作为Servlet配置在web.xml中，并将服务路由给{@link com.anysoft.webloader.ServletHandler ServletHandler}。
 * 一个典型的配置如下：<br>
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * <servlet>
 *  <display-name>MessageRouter</display-name>
 *  <servlet-name>MessageRouter</servlet-name>
 *  <servlet-class>com.anysoft.webloader.ServletAgent</servlet-class>
 *  <init-param>
 *   <param-name>handler</param-name>
 *   <param-value>[My ServletHandler]</param-value> 
 *  </init-param>
 *  <load-on-startup>1</load-on-startup> 
 * </servlet>
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 
 * @author duanyy
 * 
 */
public class ServletAgent extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * a logger of log4j
	 */
	protected static Logger logger = LogManager
			.getLogger(WebAppContextListener.class);

	/**
	 * Servlet处理器
	 */
	private ServletHandler handler = null;

	/**
	 * 初始化servlet
	 * 
	 * @param servletConfig servlet配置信息
	 */
	public void init(ServletConfig servletConfig) throws ServletException {
		String handlerClass = servletConfig.getInitParameter("handler");

		ServletContext sc = servletConfig.getServletContext();
		ClassLoader classLoader = (ClassLoader) sc.getAttribute("classLoader");
		if (classLoader != null) {
			try {
				handler = (ServletHandler) classLoader.loadClass(handlerClass)
						.newInstance();
				handler.init(servletConfig);
			} catch (Exception e) {
				logger.error("Error occurs when creating handler:"
						+ handlerClass, e);
			}
		} else {
			logger.error("Can not find classLoader");
		}
	}

	/**
	 * Get
	 */
	protected void doGet(javax.servlet.http.HttpServletRequest req,
			javax.servlet.http.HttpServletResponse resp)
			throws javax.servlet.ServletException, java.io.IOException {
		if (handler != null)
			handler.doService(req, resp, "get");
	}

	/**
	 * Put
	 */
	protected void doPut(javax.servlet.http.HttpServletRequest req,
			javax.servlet.http.HttpServletResponse resp)
			throws javax.servlet.ServletException, java.io.IOException {
		if (handler != null)
			handler.doService(req, resp, "put");
	}

	/**
	 * Head
	 */
	protected void doHead(javax.servlet.http.HttpServletRequest req,
			javax.servlet.http.HttpServletResponse resp)
			throws javax.servlet.ServletException, java.io.IOException {
		if (handler != null)
			handler.doService(req, resp, "head");
	}

	/**
	 * Post
	 */
	protected void doPost(javax.servlet.http.HttpServletRequest req,
			javax.servlet.http.HttpServletResponse resp)
			throws javax.servlet.ServletException, java.io.IOException {
		if (handler != null)
			handler.doService(req, resp, "post");
	}

	/**
	 * Delete
	 */
	protected void doDelete(javax.servlet.http.HttpServletRequest req,
			javax.servlet.http.HttpServletResponse resp)
			throws javax.servlet.ServletException, java.io.IOException {
		if (handler != null)
			handler.doService(req, resp, "delete");
	}

	/**
	 * 销毁Servlet
	 */
	public void destroy() {
		if (handler != null)
			handler.destroy();
	}
	
	public static void changeDir(File input,File output){
		if (!input.exists() || !input.isDirectory()){
			return ;
		}
		
		if (!output.exists()){
			output.mkdirs();
		}
			
		File [] children = input.listFiles();
		
		for (File file:children){
			if (file.isDirectory()){
				logger.info("Enter dir:" + file.getName());
				changeDir(file,new File(output,file.getName()));
				continue;
			}
			if (file.getName().endsWith(".java")){
				logger.info("Process " + file.getName());
				
				FileInputStream in = null;
				FileOutputStream out = null;
				
				try {
					
					in = new FileInputStream(file);
					out = new FileOutputStream(new File(output,file.getName()));
					
					byte [] buffer = new byte[20*1024];
					int byteread = 0;
					while ((byteread = in.read(buffer)) != -1) {
						String buf = new String(buffer,0,byteread,"gbk");	
						byte [] result = buf.getBytes("utf-8");
						out.write(result, 0, result.length);
					}
					
				}catch (Exception ex){
					ex.printStackTrace();
				}finally {
					IOTools.closeStream(in,out);
				}
			}
		}
	}
	public static void main(String [] args){
		String path = "D:\\work-workspace\\anyLogicBus\\src";
		String output = "D:\\temp\\src";
		
		changeDir(new File(path),new File(output));
	}
}
