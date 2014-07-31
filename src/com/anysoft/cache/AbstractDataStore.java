package com.anysoft.cache;

import org.w3c.dom.Element;

import com.anysoft.util.BaseException;
import com.anysoft.util.Properties;
import com.anysoft.util.XMLConfigurable;
import com.anysoft.util.XmlElementProperties;

/**
 * DataStore的虚类
 * 
 * @author duanyy
 *
 * @param <data>
 */
abstract public class AbstractDataStore<data extends Cacheable> implements DataStore<data>,XMLConfigurable {

	@Override
	public data load(String id) {
		return load(id,true);
	}

	@Override
	public void configure(Element _e, Properties _properties)
			throws BaseException {
		XmlElementProperties props = new XmlElementProperties(_e,_properties);
		create(props);
		onConfigure(_e,props);
	}

	/**
	 * configure时间处理
	 * @param _e
	 * @param props
	 */
	protected void onConfigure(Element _e, XmlElementProperties props) {
		
	}

	@Override
	public void addChangeListener(ChangeAware<data> listener) {
		changeAware.add(listener);
	}

	@Override
	public void removeChangeListener(ChangeAware<data> listener) {
		changeAware.remove(listener);
	}	
	/**
	 * ChangeAwareListener
	 */
	protected ChangeAwareHub<data> changeAware = new ChangeAwareHub<data>();	
}
