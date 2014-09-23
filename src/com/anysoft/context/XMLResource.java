package com.anysoft.context;

import java.io.InputStream;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.anysoft.util.BaseException;
import com.anysoft.util.IOTools;
import com.anysoft.util.Properties;
import com.anysoft.util.Settings;
import com.anysoft.util.Watcher;
import com.anysoft.util.XmlElementProperties;
import com.anysoft.util.XmlTools;
import com.anysoft.util.resource.ResourceFactory;

/**
 * 基于XML文件的配置环境
 * @author duanyy
 *
 * @param <object>
 * 
 * @since 1.5.0
 */
abstract public class XMLResource<object> implements Context<object> {
	protected static final Logger logger = LogManager.getLogger(XMLResource.class);
	/**
	 * Holder
	 */
	protected Holder<object> holder = null;
	
	@Override
	public void close() throws Exception {
		if (holder != null){
			holder.close();
		}
	}

	@Override
	public void configure(Element _e, Properties _properties)
			throws BaseException {
		XmlElementProperties p = new XmlElementProperties(_e,_properties);
		
		String defaultXrc = getDefaultXrc();
		
		String configFile = p.GetValue("xrc.master", defaultXrc);
		String secondaryConfigFile = p.GetValue("xrc.secondary",defaultXrc);
		
		Document doc = loadDocument(configFile,secondaryConfigFile);		
		
		if (doc != null && doc.getDocumentElement() != null){
			holder = new Holder<object>(getDefaultClass(),getObjectName());
			holder.configure(doc.getDocumentElement(), _properties);
		}
	}
	
	abstract public String getObjectName();
	abstract public String getDefaultClass();
	abstract public String getDefaultXrc();

	@Override
	public object get(String id) {
		return holder != null ? holder.get(id) : null;
	}

	@Override
	public void addWatcher(Watcher<object> watcher) {
	}

	@Override
	public void removeWatcher(Watcher<object> watcher) {
	}

	
	/**
	 * 从主/备地址中装入文档
	 * 
	 * @param master 主地址
	 * @param secondary 备用地址
	 * @return XML文档
	 */
	protected static Document loadDocument(String master,String secondary){
		ResourceFactory rm = Settings.getResourceFactory();
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
}
