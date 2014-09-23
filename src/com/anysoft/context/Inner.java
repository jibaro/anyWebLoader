package com.anysoft.context;

import org.w3c.dom.Element;

import com.anysoft.util.BaseException;
import com.anysoft.util.Properties;
import com.anysoft.util.Watcher;

/**
 * Source文件内置的Context
 * 
 * @author duanyy
 *
 * @param <object>
 * 
 * @since 1.5.0
 */
abstract public class Inner<object> implements Context<object> {
	
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
		holder = new Holder<object>(getDefaultClass(),getObjectName());
		holder.configure(_e, _properties);
	}
	
	abstract public String getObjectName();
	abstract public String getDefaultClass();

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

}
