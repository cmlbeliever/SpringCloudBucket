
## 工程说明 ##

SpringCloud 研究与学习，内包含Zuul,Eureka,Config,Feign,Ribbon等框架的使用

## 项目内各工程说明 ##

 - config-server 配置中心，作为configserver
 - eureka-server eureka服务工程
 - user-service 用户服务工程
 - auth-service 授权认证服务，用于用户权限校验
 - order-service 订单服务
 - api-getway 网关服务工程，使用Zuul对服务进行分发和权限校验

## 使用说明 ##
 

 1. 启动配置中心：config-server
 2. 启动服务中心：eureka-server
 3. 启动用户服务：user-service
 4. 启动订单服务：order-service
 5. 启动授权服务：auth-service
 6. 启动网关服务：api-getway

步骤1,2需要严格按照顺序启动，步骤1启动成功后才可以继续启动步骤2。步骤2启动成功后，3-6的服务可以同时启动

步骤2启动成功后，访问http://localhost:8761/即可查看到启动的服务,全都启动成功后即可以进行后续的测试。

## 概论 ##
通常来说，微服务之间的通信是通过内网来的，与外部连接是通过网关来转发的。
所以基本上对外开放的工程都集成在api-getway工程上。通过网关服务，对其他服务调用前可以进行基本的权限校验和数据拦截
 

