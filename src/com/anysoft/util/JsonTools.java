package com.anysoft.util;

import java.util.Map;


/**
 * Json的一些工具
 * @author duanyy
 * @since 1.0.6
 */
public class JsonTools {
	
	/**
	 * 从Json对象中获取指定的属性值
	 * 
	 * @param json Json对象
	 * @param name 属性名
	 * @param defaultValue 缺省值
	 * @return 属性值
	 */
	@SuppressWarnings("rawtypes")
	public static String getString(Map json,String name,String defaultValue){
		Object found = json.get(name);
		if (found == null){
			return defaultValue;
		}
		
		if (found instanceof String){
			return (String)found;
		}
		return found.toString();
	}
	
	/**
	 * 从Json对象中获取指定的属性值
	 * @param json Json对象
	 * @param name 属性名
	 * @param defaultValue 缺省值
	 * @return 属性值
	 */
	@SuppressWarnings("rawtypes")
	public static int getInt(Map json,String name,int defaultValue){
		Object found = json.get(name);
		if (found == null){
			return defaultValue;
		}
		
		if (found instanceof Integer){
			return (Integer)found;
		}
		
		try {
			return Integer.parseInt(found.toString());
		}catch (Exception ex){
			return defaultValue;
		}
	}
}
