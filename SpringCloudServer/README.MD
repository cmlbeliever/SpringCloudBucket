application:表示应用名称,在client中通过spring.config.name配置

获取git上的资源信息遵循如下规则：

/{application}/{profile}[/{label}]
/{application}-{profile}.yml
/{label}/{application}-{profile}.yml
/{application}-{profile}.properties
/{label}/{application}-{profile}.properties

如果application名称为foo，则可以采用如下方式访问：

http://localhost:8888/cloud-config/dev 可以获取到数据
{"name":"cloud-config","profiles":["dev"],"label":null,"version":null,"state":null,"propertySources":[{"name":"https://git.oschina.net/cmlbeliever/springcloudconfig.git/cloud-config-repo/cloud-config-dev.properties","source":{"mysqldb.datasource.username":"root","mysqldb.datasource.password":"admin","mysqldb.datasource.url":"jdbc:mysql://localhost/springboot?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull","logging.level.org.springframework.web":"DEBUG"}}]}