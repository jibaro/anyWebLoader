package com.anysoft.context;

import com.anysoft.util.Watcher;
import com.anysoft.util.XMLConfigurable;


/**
 * 通用配置环境
 * 
 * @author duanyy
 *
 * @param <object> 配置对象
 * 
 * @since 1.5.0
 */
public interface Context<object> extends XMLConfigurable, AutoCloseable {
	
	/**
	 * 通过ID获取对象
	 * 
	 * @param id
	 * @return
	 */
	 
	public object get(String id);
	
	/**
	 * 注册监控器
	 * @param watcher
	 */
	public void addWatcher(Watcher<object> watcher);
	
	/**
	 * 注销监控器
	 * @param watcher
	 */
	public void removeWatcher(Watcher<object> watcher);
}
