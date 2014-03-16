package com.anysoft.util;

import java.io.Closeable;

/**
 * IO的工具类
 * @author duanyy
 *
 */
public class IOTools {
	/**
	 * 关闭一个或多个输入/输出流
	 * @param closeables 输入输出流列表
	 */
	public static void closeStream(Closeable... closeables) {
		for (Closeable c:closeables){
			if (null != c){
				try{
					c.close();
				}catch (Exception ex){
					
				}
			}
		}
	}
}
