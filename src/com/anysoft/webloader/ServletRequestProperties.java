package com.anysoft.webloader;

import javax.servlet.http.HttpServletRequest;

import com.anysoft.util.Properties;

/**
 * A Properties Wrapper for HttpServletRequest.
 * 
 * @author Administrator
 * @version 1.0.1
 *
 */
public class ServletRequestProperties extends Properties {

	protected HttpServletRequest request = null;
	
	/**
	 * Constructor
	 * @param req instance of {@code HttpServletRequest}
	 */
	public ServletRequestProperties(HttpServletRequest req){
		request = req;
	}
	
	@Override
	protected void _SetValue(String _name, String _value) {
		//do nothing
	}

	@Override
	protected String _GetValue(String _name) {
		return request != null ? request.getParameter(_name): "";
	}

	@Override
	public void Clear() {
		//do nothing
	}

}
