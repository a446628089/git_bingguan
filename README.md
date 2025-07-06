<<<<<<< HEAD
# git_bingguan

#### 介绍
idea测试git

#### 软件架构
软件架构说明


#### 安装教程

1.  xxxx
2.  xxxx
3.  xxxx

#### 使用说明

1.  xxxx
2.  xxxx
3.  xxxx

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
=======
# 智慧宾馆管理系统

## 项目简介
基于B/S架构的宾馆客房管理系统，前端采用HTML+CSS+JavaScript（Bootstrap），后端Spring Boot + Spring Security + Spring Data JPA，数据库为SQL Server，适合课程设计与演示。

## 技术栈
- 前端：HTML5、CSS3、JavaScript、Bootstrap
- 后端：Spring Boot 2.7.x、Spring Security、Spring Data JPA
- 数据库：SQL Server 2017+
- 构建工具：Maven
- JDK：11

## 启动步骤
1. 安装并启动SQL Server，创建数据库`hotel_db`。
2. 修改`src/main/resources/application.properties`中的数据库连接配置（用户名、密码、端口等）。
3. 执行数据库初始化脚本（见下方）。
4. 使用IDEA或命令行运行：
   ```bash
   mvn clean package
   java -jar target/hotel-management-1.0.0.jar
   ```
5. 访问 http://localhost:8082/ 进入系统首页。

## 数据库初始化脚本
见`db_init.sql`，包含建表、插入测试数据等。

## 主要功能
- 客房、员工、客户、评论的增删改查
- 角色分区（管理员/员工/客户）与权限控制
- 员工办理入住/退房，客户入住记录、评论
- 个人信息管理，登录状态管理
- 美观简洁的前端页面

## 演示账号
- 管理员：admin / 123456
- 员工：emp1 / 123456
- 客户：可注册新账号

## 常见问题
- 端口占用：修改`application.properties`中的`server.port`
- 数据库连接失败：检查SQL Server是否启动、用户名密码是否正确
- 其他问题请联系开发者。 
>>>>>>> 523884d (第一次提交到本地仓库)
