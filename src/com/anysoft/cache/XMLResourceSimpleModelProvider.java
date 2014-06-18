package com.anysoft.cache;

import java.io.InputStream;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.anysoft.util.IOTools;
import com.anysoft.util.Properties;
import com.anysoft.util.Settings;
import com.anysoft.util.XmlTools;
import com.anysoft.util.resource.ResourceFactory;


/**
 * 基于XMLResource的简单模型Provider
 * @author duanyy
 * @since 1.0.14
 */
abstract public class XMLResourceSimpleModelProvider<model extends Cachable> implements Provider<model> {
	public XMLResourceSimpleModelProvider(Properties props){
		doc = loadResource(props);
	}
	
	@Override
	public model load(String id) {
		if (doc == null) return null;
		Node found = XmlTools.getNodeByPath(doc.getDocumentElement(), "model[@id='" + id + "']");
		
		if (found == null || found.getNodeType() != Node.ELEMENT_NODE) return null;
	
		Element e = (Element) found;
		
		return newModel(id,e);
	}

	protected abstract Document loadResource(Properties props);
	
	protected abstract model newModel(String id,Element e);
	
	@Override
	public void addChangeListener(ChangeAware<model> listener) {
		// to do noting
	}

	protected Document doc = null;

	/**
	 * 从主/备地址中装入文档
	 * 
	 * @param master 主地址
	 * @param secondary 备用地址
	 * @return XML文档
	 */
	protected static Document loadDocument(String master,String secondary){
		Settings profile = Settings.get();
		ResourceFactory rm = (ResourceFactory) profile.get("ResourceFactory");
		if (null == rm){
			rm = new ResourceFactory();
		}
		
		Document ret = null;
		InputStream in = null;
		try {
			in = rm.load(master,secondary, null);
			ret = XmlTools.loadFromInputStream(in);		
		} catch (Exception ex){
			logger.error("Error occurs when load xml file,source=" + master, ex);
		}finally {
			IOTools.closeStream(in);
		}		
		return ret;
	}	
	
	protected static Logger logger = LogManager.getLogger(XMLResourceSimpleModelProvider.class);
}
