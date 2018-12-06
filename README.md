# prd-start

#### 项目介绍
spring-cloud2.0微服务架构, 数据库采用阿里云durid连接池，集成redis缓存，包含了

#### 软件架构
软件架构说明
分为：
prd-eureka 注册中心
prd-client 服务消费者（controller,断路器，服务降级）
prd-modules 服务提供者（service）
prd-config 配置中心，配置了数据库信息、redis信息
prd-db 数据库连接池初始化、redis初始化
prd-oauth2 单点登陆服务，包含了 session和token两种验证方式
prd-utils 共同的工具类
prd-zuul 网关
prd-admin-ui 采用easyUI的封装 TopJUI 进行页面展示

#### 安装教程
本地需要安装mysql、redis数据库，配置信息在prd-config  config目录下
配置完成后即可按顺序启动，顺序为：prd-eureka、prd-config、prd-modules、prd-client、prd-oauth2、prd-zuul、prd-admin-ui
访问页面：http://localhost:8080/login.html

#### 使用说明
目前只做到前台页面登陆到菜单显示

#### 参与贡献
1、zongwt
2、