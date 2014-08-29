package com.anysoft.stream;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.anysoft.util.Factory;
import com.anysoft.util.IOTools;
import com.anysoft.util.Properties;
import com.anysoft.util.XmlTools;

/**
 * 集线器
 * 
 * @author duanyy
 *
 * @param <data>
 * 
 * @since 1.4.0
 */
public class HubHandler<data extends Flowable> extends AbstractHandler<data> {
	protected static Logger logger = LogManager.getLogger(HubHandler.class);
	/**
	 * handlers
	 */
	protected List<Handler<data>> handlers = new ArrayList<Handler<data>>();

	@Override
	protected void onHandle(data _data) {
		for (Handler<data> h:handlers){
			if (h != null){
				h.handle(_data);
			}
		}
	}

	@Override
	protected void onFlush() {
		for (Handler<data> h:handlers){
			if (h != null){
				h.flush();
			}
		}
	}
	public void close() throws Exception{
		super.close();
		for (Handler<data> h:handlers){
			if (h != null){
				IOTools.close(h);
			}
		}
		handlers.clear();
	}
	
	@Override
	protected void onConfigure(Element e, Properties p) {
		NodeList children = XmlTools.getNodeListByPath(e, getHandlerType());
		
		Factory<Handler<data>> factory = new Factory<Handler<data>>();
		
		for (int i = 0 ; i < children.getLength() ; i ++){
			Node n = children.item(i);
			
			if (n.getNodeType() != Node.ELEMENT_NODE){
				continue;
			}
			
			try {
				Handler<data> newHandler = factory.newInstance((Element)n,p);
				if (newHandler != null){
					handlers.add(newHandler);
				}
			}catch (Exception ex){
				logger.error("Can not create handler instance",ex);
			}
		}
	}

}
