package com.anysoft.util;

import java.util.Map;


/**
 * Json的一些工具
 * @author duanyy
 * @since 1.0.6
 * 
 * @version 1.0.8 [20140410 duanyy] <br>
 * + Add {@link com.anysoft.util.JsonTools#setString(Map, String, String) setString(Map, String, String)} <br>
 * + Add {@link com.anysoft.util.JsonTools#setInt(Map, String, int) setInt(Map,String,int)} <br>
 * + Add {@link com.anysoft.util.JsonTools#setBoolean(Map, String, boolean) setBoolean(Map,String,boolean)} <br>
 * + Add {@link com.anysoft.util.JsonTools#getBoolean(Map, String, boolean) getBoolean(Map,String,boolean)} <br>
 * 
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
	 * 向Json对象中设置指定的属性值
	 * @param json Json对象
	 * @param name 属性名
	 * @param value 属性值(String)
	 * 
	 * @since 1.0.8
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void setString(Map json,String name,String value){
		json.put(name, value);
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
	
	/**
	 * 向Json对象中设置指定属性值
	 * @param json Json对象
	 * @param name 属性名
	 * @param value 属性值
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void setInt(Map json,String name,int value){
		json.put(name,value);
	}
	
	/**
	 * 从Json对象中获取指定的属性值
	 * @param json Json对象
	 * @param name 属性名
	 * @param defaultValue 缺省值
	 * @return 属性值
	 * 
	 * @since 1.0.8
	 */
	@SuppressWarnings("rawtypes")
	public static boolean getBoolean(Map json,String name,boolean defaultValue){
		Object found = json.get(name);
		if (found == null){
			return defaultValue;
		}
		
		if (found instanceof Boolean){
			return (Boolean)found;
		}
		
		return found.toString().equals("true")?true:false;
	}
	
	/**
	 * 向Json对象中设置指定的属性值
	 * @param json Json对象
	 * @param name 属性名
	 * @param value 属性值
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setBoolean(Map json,String name,boolean value){
		json.put(name,value?"true":"false");
	}
}
