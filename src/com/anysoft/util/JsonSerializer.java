package com.anysoft.util;

import java.util.Map;

/**
 * JSON序列化接口
 * 
 * @author duanyy
 * 
 * @since 1.0.6
 * 
 */
public interface JsonSerializer {
	/**
	 * 输出到JSON对象
	 * @param json
	 */
	@SuppressWarnings("rawtypes")
	public void toJson(Map json);
	
	/**
	 * 从JSON对象读入
	 * @param json
	 */
	@SuppressWarnings("rawtypes")
	public void fromJson(Map json);
}
