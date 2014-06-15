package com.anysoft.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


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
 * @version 1.0.14 [20140615 duanyy] <br>
 * + 增加一下Json工具
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
	 * @since 1.0.14
	 */
	@SuppressWarnings("rawtypes")
	public static long getLong(Map json,String name,long defaultValue){
		Object found = json.get(name);
		if (found == null){
			return defaultValue;
		}
		
		if (found instanceof Integer){
			return (Long)found;
		}
		
		try {
			return Long.parseLong(found.toString());
		}catch (Exception ex){
			return defaultValue;
		}
	}
	
	/**
	 * 向Json对象中设置指定属性值
	 * @param json Json对象
	 * @param name 属性名
	 * @param value 属性值
	 * 
	 * @since 1.0.14
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void setLong(Map json,String name,long value){
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void xml2Json(Element root, Map json) {
		NodeList children = root.getChildNodes();

		for (int i = 0, length = children.getLength(); i < length; i++) {
			Node n = children.item(i);
			int nodeType = n.getNodeType();

			switch (nodeType) {
			case Node.TEXT_NODE:
				// JSON模式下放弃Text节点
				break;
			case Node.ELEMENT_NODE:
				Element e = (Element) n;
				String key = e.getNodeName();
				List array = null;
				Object found = json.get(key);
				if (found != null){
					if (found instanceof List){
						array = (List)found;
					}else{
						array = new ArrayList();
						Object removed = json.remove(key);
						if (removed != null)
						array.add(removed);
						json.put(key, array);
					}
				}
				
				Map map = new HashMap();
				//clone attribute
				{
					NamedNodeMap attrs = e.getAttributes();
					for (int j = 0; j < attrs.getLength(); j++) {
						Node attr = attrs.item(j);
						map.put(attr.getNodeName(), attr.getNodeValue());
					}
				}
				
				xml2Json(e,map);
				
				if (array != null){
					array.add(map);		
				}else{
					json.put(key, map);		
				}
				break;
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void json2Xml(List json,Element e,String key){
		Document doc = e.getOwnerDocument();
		for (Object item:json){
			if (item instanceof Map){
				Element newElem = doc.createElement(key);
				json2Xml((Map)item,newElem);
				e.appendChild(newElem);
			}else{
				if (item instanceof List){
					json2Xml((List)item,e,key);
				}else{
					Element newElem = doc.createElement(key);
					newElem.setAttribute(key, item.toString());
					e.appendChild(newElem);
				}
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void json2Xml(Map json,Element e){
		Set<?> keys = json.keySet();
		Iterator iter = keys.iterator();
		Document doc = e.getOwnerDocument();
		
		while (iter.hasNext()){
			String key = iter.next().toString();
			Object data = json.get(key);
			if (data instanceof List){
				List list = (List)data;
				json2Xml(list,e,key);
				continue;
			}
			if (data instanceof Map){
				Element newElem = doc.createElement(key);
				json2Xml((Map)data,newElem);
				e.appendChild(newElem);
				continue;
			}
			e.setAttribute(key, data.toString());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void clone(List src,List dest){
		for (Object item:src){
			if (item instanceof Map){
				Map newData = new HashMap();
				clone((Map)item,newData);
				dest.add(newData);
			}else{
				if (item instanceof List){
					List newList = new ArrayList();
					clone((List)item,newList);
				}else{
					dest.add(item.toString());
				}
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void clone(Map src,Map dest){
		Set<?> keys = src.keySet();
		Iterator iter = keys.iterator();
		
		while (iter.hasNext()){
			String key = iter.next().toString();
			Object data = src.get(key);
			if (data instanceof List){
				List list = (List)data;
				List newList = new ArrayList();
				clone(list,newList);
				dest.put(key, newList);
				continue;
			}
			if (data instanceof Map){
				Map newData = new HashMap();
				clone((Map)data,newData);
				dest.put(key, newData);
				continue;
			}
			dest.put(key, data.toString());
		}
	}	
}
