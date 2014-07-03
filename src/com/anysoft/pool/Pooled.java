package com.anysoft.pool;

/**
 * 可Pool的对象
 * 
 * @author duanyy
 * @since 1.1.0
 */
public interface Pooled {
	/**
	 * 销毁对象
	 */
	public void destroy();
}
