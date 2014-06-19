anyWebLoader
============

A framework which help you download your web library and load web context.

设想一下这样的需求：

当在应用服务器中构建一个Context的时候，我们希望在远程存放这个Context所需的jar库文件， 并希望应用服务器能够下载并动态加载这些库文件，以达到版本集中发布和更新的目的。

于是有了webloader这个工具，这个工具的功能包括：

1. 将ServletContextListener功能映射到WebApp
2. 将HttpServlet功能映射到ServletHandler
3. 自动下载library指定的目录，并动态加载

#### ServletContextListener的配置

在web.xml中配置Listener，见WebAppContextListener.

#### SevletHandler的配置

在web.xml中配置Servlet，见ServletAgent.

#### 库文件服务端配置

首先，需要将库文件存放在一个公共服务器之上，可以通过标准URL进行访问，例如file,http等.

接着，在管理服务器上发布一个更新信息服务，更新信息服务返回XML文档，记录库文件的名称，下载地址，MD5校验码等。例如:

    <?xml version="1.0" encoding="utf-8" standalone="no"?>
    <root>
        <module jar="anyLogicbus.jar" md5="937a43fd16c480b9e4d221d6828cb467"
        url="file:///D:\ecloud\18923882238\logicbus\libs\anyLogicbus.jar"/>
    </root>

#### Version
- 1.0.1
    + add ServletRequestProperties which is a Properties wrapper for HttpServletRequest.
    + add ServletConfigProperties which is a Properties wrapper for ServletConfig.
    
- 1.0.2 
	+ add servletContext parameter to {@link com.anysoft.webloader.WebApp#init(DefaultProperties, ServletContext) WebApp.init()} 
	and {@link com.anysoft.webloader.WebApp#destroy(ServletContext) WebApp.destroy()}
	
- 1.0.3 [20140325 duanyy]
    + Add some varibles to global settings.
    
- 1.0.4 [20140326 duanyy]
    + 可定制装入资源的Java类，解决以前跨ClassLoader无法取资源文件的问题
    
- 1.0.5 [20140326 duanyy]
    + URLocation增加对windows路径的支持(支持\)
    
- 1.0.6 [20140408 duanyy]
    + 增加JSON的序列化接口及一些JSON工具类
    + 增加缓存模型包com.anysoft.cache
    
- 1.0.7 [20140409 duanyy]
    + 增加{@link com.anysoft.cache.CacheManager#_get(String)
    + 增加{@link com.anysoft.cache.CacheManager#_add(String, Cachable)

- 1.0.8 [20140410 duanyy]
    + 修改{@link com.anysoft.util.JsonTools JsonTools},增加一些Json读写工具
    
- 1.0.9 [20140414 duanyy]
    + {@link com.anysoft.util.Factory Factory},增加{@link com.anysoft.util.Factory#newInstance(String, Properties) newInstance(String, Properties)}方法，使之能够通过Properties直接初始化.
    
- 1.0.10 [20140423 duanyy]
	+ 增加{@link com.anysoft.util.Confirmer Confirmer}接口

- 1.0.11 [20140428 duanyy]
	+ 增加压缩/解压的封装类
	
- 1.0.12 [20140430 duanyy]
	+ 增加加密/解密的封装类
	+ 增加简单缓存对象的实现类{@link com.anysoft.cache.SimpleModel SimpleModel}
	
- 1.0.13 [20140605 duanyy]
	+ 增加MD5,SHA1加密封装类
	
- 1.0.14 [20140615 duanyy]
	+ 增加一些JSON工具
	+ 增加{@link com.anysoft.cache.XMLResourceSimpleModelProvider XMLResourceSimpleModelProvider}
	
- 1.0.15 [20140616 duanyy]
	+ {@link com.anysoft.util.URLocation URLocation}的Path,Query,Fragment等Get函数增加unescape