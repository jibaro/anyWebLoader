package com.anysoft.util;


import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;


import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import com.anysoft.util.resource.ResourceFactory;


/**
 * 全局的配置变量集
 * <p>包含两个方面的功能：</p>
 * <li>维护一个全局性的变量集
 * <li>维护一个全局性的对象列表
 * 
 * @author duanyy
 *
 */
public class Settings extends DefaultProperties implements XmlSerializer{
	/**
	 * logger of log4j
	 */
	protected static Logger logger = LogManager.getLogger(Settings.class);
	/**
	 * JRE环境变量集，作为本变量集的父节点
	 */
	protected static Properties system = new SystemProperties();
	
	/**
	 * 全局对象列表
	 */
	protected Hashtable<String, Object> objects = new Hashtable<String, Object>();
	
	/**
	 * 构造函数
	 */
	protected Settings(){
		super("Settings",system);
	}
	/**
	 * 全局唯一实例
	 */
	protected static Settings instance = null;
	
	/**
	 * 获取全局唯一实例
	 * @return 对象实例
	 */
	synchronized public static Settings get(){
		if (null == instance){
			instance = new Settings();
		}
		return instance;
	}
	
	/**
	 * 装入指定的xrc文件，并读入xrc文件中的变量信息
	 * @param _url xrc文件的url
	 * @param secondary xrc文件的备用url
	 * @param _rm ResourceFactory实例
	 * @see #loadFromDocument(Document)
	 */
	public void addSettings(String _url,String secondary,ResourceFactory _rm){
		ResourceFactory rm = _rm;
		if (null == _rm){
			rm = new ResourceFactory();
		}
		
		InputStream in = null;
		try {
			in = rm.load(_url,secondary, null);
			Document doc = XmlTools.loadFromInputStream(in);		
			loadFromDocument(doc);			
		} catch (Exception ex){
			logger.error("Error occurs when load xml file,source=" + _url, ex);
		}finally {
			IOTools.closeStream(in);
		}
	}
	
	/**
	 * 从一个DefaultProperties复制变量列表
	 * @param p DefaultProperties实例
	 */
	public void addSettings(DefaultProperties p){
		Enumeration<String> __keys = p.keys();
		while (__keys.hasMoreElements()){
			String __name = (String)__keys.nextElement();
			String __value = p.GetValue(__name,"",false,true);
			if (__value != null && __value.length() > 0)
				SetValue(__name, __value);
		}
	}
	
	/**
	 * 从XML文档中读入变量信息
	 * @param doc XML文档实例
	 * @see #toXML(Element)
	 */
	protected void loadFromDocument(Document doc){
		if (doc == null){
			return ;
		}		
		Element root = doc.getDocumentElement();
		fromXML(root);
	}
	
	/**
	 * 将变量集写出到XML文档
	 * @param doc XML文档实例
	 * @see #toXML(Element)
	 */
	protected void saveToDocument(Document doc){
		if (doc == null)return ;
		Element root = doc.getDocumentElement();
		toXML(root);
	}

	/**
	 * 输出到XML节点
	 * @param root 输出信息的根节点
	 */
	@Override
	public void toXML(Element root) {
		// TODO Auto-generated method stub
		//为了输出文件的美观，添加一个\n文件节点
		Document doc = root.getOwnerDocument();
		root.appendChild(doc.createTextNode("\n"));
		Enumeration<?> ids = keys();
		
		while (ids.hasMoreElements()){
			String id = (String)ids.nextElement();
			String value = _GetValue(id);
			if (value.length() <= 0 || id.length() <= 0){
				continue;
			}
			Element e = doc.createElement("parameter");
			e.setAttribute("id",id);
			e.setAttribute("value",value);
			root.appendChild(e);
			//为了输出文件的美观，添加一个\n文件节点
			root.appendChild(doc.createTextNode("\n"));
		}		
	}

	/**
	 * 从XML节点中读入
	 * @param root 读入信息的根节点
	 */
	@Override
	public void fromXML(Element root) {
		// TODO Auto-generated method stub
		NodeList nodeList = root.getChildNodes();	
		for (int i = 0 ; i < nodeList.getLength() ; i ++){
			Node node = nodeList.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE){
				continue;
			}
			if (node.getNodeName().equals("parameter")){
				Element e = (Element)node;
				String id = e.getAttribute("id");
				String value = e.getAttribute("value");
				//支持final标示,如果final为true,则不覆盖原有的取值
				boolean isFinal = e.getAttribute("final").equals("true")?true:false;
				if (isFinal){
					String oldValue = this._GetValue(id);
					if (oldValue == null || oldValue.length() <= 0){
						SetValue(id,value);
					}
				}else{
					SetValue(id,value);
				}
			}
		}		
	}
	
	/**
	 * 获取对象列表中指定id的对象
	 * @param name
	 * @return
	 */
	public Object get(String id){
		return objects.get(id);
	}
	
	/**
	 * 向对象列表注册已经创建好的对象
	 * @param id 对象id
	 * @param obj 对象实例
	 */
	public void registerObject(String id,Object obj){
		if (obj != null){
			objects.put(id, obj);
		}
	}

	/**
	 * 创建指定的对象并注册到对象列表
	 * @param id 对象id
	 * @param className 对象的类名
	 */
	public void registerObject(String id, String className) {
		try {
			registerObject(id,Class.forName(className).newInstance());
		} catch (Exception ex){
			logger.error("Can not register object:" + className,ex);
		}
	}		
	
	/**
	 * 从对象列表中注销指定ID的对象
	 * @param id
	 */
	public void unregisterObject(String id){
		objects.remove(id);
	}
}
