package com.anysoft.util.resource;

import java.io.InputStream;
import java.net.URL;


import com.anysoft.util.BaseException;
import com.anysoft.util.URLocation;

/**
 * 基于Java内部文件的资源装入器
 * @author duanyy
 *
 */
public class JavaResourceLoader implements ResourceLoader {
	/**
	 * 装载输入流
	 * @param _url URL
	 * @param _context 上下文
	 * @return 输入流实例
	 * @throws BaseException 当URL中不包含路径时抛出
	 * @see #load(String, Object)
	 */		
	@Override
	public InputStream load(URLocation _url, Object _context)
			throws BaseException {
		if (!_url.hasPath()) {
			throw new BaseException(JavaResourceLoader.class.getName(),
					"Can not find path from url:" + _url.toString());
		}
		return load(_url.getPath(),_context);
	}

	/**
	 * 装入输入流
	 * @param _path 文件路径
	 * @param _context 上下文
	 * @return　输入流实例
	 * @throws BaseException 
	 */	
	@SuppressWarnings("rawtypes")
	public InputStream load(String _path,Object _context)  throws BaseException {
	    ClassLoader cL = Thread.currentThread().getContextClassLoader();
		InputStream in = cL.getResourceAsStream(_path);
		if (in == null){
			cL = JavaResourceLoader.class.getClassLoader();
			in = cL.getResourceAsStream(_path);
			if (in == null){
				Class _class = getClass();
				if (_context instanceof Class){
					_class = (Class)_context;
				}		
				in = _class.getResourceAsStream(_path);
			}
		}
	    return in;
	}
	/**
	 * 生成资源的标准URL
	 * 
	 * @param _url URL
	 * @param _context 上下文
	 * @return　资源对应的URL
	 * @throws BaseException 
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public URL createURL(URLocation _url, Object _context) throws BaseException {
		if (!_url.hasPath()) {
			throw new BaseException(JavaResourceLoader.class.getName(),
					"Can not find path from url:" + _url.toString());
		}
		
	    ClassLoader cL = Thread.currentThread().getContextClassLoader();
		URL in = cL.getResource(_url.getPath());
		if (in == null){
			cL = JavaResourceLoader.class.getClassLoader();
			in = cL.getResource(_url.getPath());
			if (in == null){
				Class _class = getClass();
				if (_context instanceof Class){
					_class = (Class)_context;
				}		
				in = _class.getResource(_url.getPath());
			}
		}
	    return in;
	}	
}
