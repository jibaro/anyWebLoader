package com.anysoft.webloader;

import javax.servlet.ServletConfig;

import com.anysoft.util.Properties;

/**
 * A Properties wrapper for ServletConfig.
 * 
 * @author duanyy
 *
 */
public class ServletConfigProperties extends Properties {

	protected ServletConfig servletConfig = null;
	
	public ServletConfigProperties(ServletConfig sc){
		servletConfig = sc;
	}
	@Override
	protected void _SetValue(String _name, String _value) {
		//do nothing
	}

	@Override
	protected String _GetValue(String _name) {
		if (servletConfig == null)
			return "";
		String value = servletConfig.getInitParameter(_name);
		return (value == null || value.length() <= 0) ? servletConfig
				.getServletContext().getInitParameter(_name) : value;
	}

	@Override
	public void Clear() {
		// do nothing

	}

}
