anyWebLoader
============

A framework which help you download your web library and load web context.

<p>设想一下这样的需求：

<p>当在应用服务器中构建一个Context的时候，我们希望在远程存放这个Context所需的jar库文件， 并希望应用服务器能够下载并动态加载这些库文件，以达到版本集中发布和更新的目的。
于是有了webloader这个工具，这个工具的功能包括：

1. 将ServletContextListener功能映射到WebApp
2. 将HttpServlet功能映射到ServletHandler
3. 自动下载library指定的目录，并动态加载

<h3>ServletContextListener的配置</h3>

<p>在web.xml中配置Listener，见WebAppContextListener.

<h3>SevletHandler的配置</h3> 

h3在web.xml中配置Servlet，见ServletAgent.

<h3>库文件服务端配置</h3>

<p>首先，需要将库文件存放在一个公共服务器之上，可以通过标准URL进行访问，例如file,http等.
<p>接着，在管理服务器上发布一个更新信息服务，更新信息服务返回XML文档，记录库文件的名称，下载地址，MD5校验码等。例如:

<pre>
<?xml version="1.0" encoding="utf-8" standalone="no"?>
<root>
  <module jar="anyLogicbus.jar" md5="937a43fd16c480b9e4d221d6828cb467" 
  url="file:///D:\ecloud\18923882238\logicbus\libs\anyLogicbus.jar"
  />
</root>
</pre>
