# 项目使用说明
启动eureka-server,config-server,user-service后启动此项目，访问http://127.0.0.1:8801/user-gateway/user2/testGet?name=name 将请求转发的了user-service中，且新增了自定义参数extraParameter

# metrics

 - http://localhost:8801/actuator/gateway/globalfilters 查看所有filter
 - http://localhost:8801/actuator/gateway/routefilters
 - http://localhost:8801/actuator/gateway/routes 所有的路由
 - 