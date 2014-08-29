package com.anysoft.stream;

import com.anysoft.util.Reportable;
import com.anysoft.util.XMLConfigurable;

/**
 * 数据处理器
 * 
 * @author duanyy
 *
 * @param <data>
 * 
 * @since 1.4.0
 */
public interface Handler<data extends Flowable> extends XMLConfigurable,AutoCloseable,Reportable{
	
	/**
	 * 处理数据
	 * @param _data
	 */
	public void handle(data _data);
	
	/**
	 * 清理缓存
	 */
	public void flush();

	
	/**
	 * 获取Handler的类型
	 * @return
	 */
	public String getHandlerType();
}
