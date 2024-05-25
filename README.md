`下面有在线预览地址`

> 本项目免费开源完整可查阅文档，由公众号 JavaPub 维护更新、可以协助适配不同操作系统及存储平台。

## 适合人群

1. 本项目强依赖只有mysql，整个项目安装非常简单、新手更着视频5分钟即可安装部署成功，快试试吧。
2. 适合入门java的同学，如果你已经java入门，那么学习 SpringBoot+VUE 是非常重要的。
3. 适合后端同学，如果你已经掌握了后端开发，通过俩万这个项目你可以快速了解和学习vue的使用。
4. 适合做一个脚手架，本项目所有功能都是以通用性开发的，你只需要在项目上建立你自己的模块。
5. 面试项目，如果你缺少一个拿出手的面试项目。这个项目包含用户角色、权限管理，flowable工作流，支付宝、微信支付功能，第三方登录等，足够你在面试中大放异彩。

# liawan-vue

一个通用管理系统

![logl](doc/logo.png)

[GitHub](https://github.com/Rodert/liawan-vue) | [Gitee](https://gitee.com/rodert/liawan-vue)

## 俩万

俩万是一个不错的名字，俩万是在前后分离的新版本。目前基于SpringBoot和VUE已经是主流，掌握前后端技术更是未来的趋势，希望这个项目对你有所帮助。

愿景：实现一个通用、简单易上手的VUE工程，本工程主要针对不懂前端的后端工程师。致力于提供一个高扩展、方便使用的通用后台工程。


## 收录

|  收录   | 地址                                        |
|  ----  |-------------------------------------------|
| 谷歌收录  | https://www.google.com.hk/search?q=liawan |
| 百度收录  | https://www.baidu.com/s?wd=liawan        |
| 必应收录  | https://www4.bing.com/search?q=liawan    |
| 360收录  | https://www.so.com/s?q=liawan            |
| 搜狗收录  | https://www.sogou.com/web?query=liawan   |


## 在线预览

预览见：<http://liawan.javapub.net.cn/>

不要忘记点个start

**账号：**


## 文档

致力于提供一份详尽可供参考的文档

- [redis安装参考文档及视频](/doc/redis安装教程.md)

- [环境搭建](/doc/环境搭建.md)

建设中...

> 需要更多辅助，可以微信留言，微信号见文末【JavaPub】

## 技术栈

SpringBoot
Spring Security
JWT
MyBatis
Druid
Vue
Vuex
Element-ui
Axios
Sass
Quill
docker-compose、Kafka、Redis、Apollo、prometheus、Grafana

## 环境搭建

搭建视频地址：<https://www.bilibili.com/video/BV1Wu41167Na?share_source=copy_web>

**启动前端：**

1. 依赖环境：

- npm
- nodejs

2. 加载依赖：

```
npm install --registry=https://registry.npm.taobao.org
```

3. 启动：

```
npm run dev
```

**启动后端：**

1. 依赖环境：

- jdk8（及以上）
- mysql（建议v5.7）
- redis

2. 启动

执行：main

```
com/javapub/liawan/admin/LiaWanApplication.java
```

## 测试账号

系统管理员：

username：amdin 

password：admin123 

普通账号：

username：liawan 

password：admin123

监控账号：

username：liawan 

password：123456

## 系统展示

登录页

![image](img/image.png)

首页

![image](img/2.png)

系统管理

![image](img/3.png)

数据监控

![image](img/4.png)

服务监控

![image](img/5.png)



## 打包部署

**前端项目：**

1. 编译打包

```
cd liawan-ui-admin

npm run build:prod
```

2. 部署目录下的 dist 文件

**后端项目：**

1. 编译打包

```bash
mvn clean package -DskipTests
```

2. 部署

部署目录 liawan-admin 下 liawan-admin.jar 

## 开发计划 DOTO list

- [ ] 持续更新文档，提供一套详尽可供参考的文档，助力金九银十

---

- [ ] Activiti + Flowable 工作流
- [ ] 第三方登录
- [ ] 支付
- [ ] 短信
- [ ] 支持 RBAC 动态权限、数据权限
- [ ] 接入商城
- [ ] 系统监控
- [ ] docker构建




## 最后

鸣谢： 若依 


## 项目更新日志

- 2022.05.15 初始化项目、并完成部署
- 2022.05.19 演示环境新增写操作功能





---





## 推荐阅读（附源码-附安装视频）

`无套路，免费领取`



中国象棋：[下载地址1](https://javapub.blog.csdn.net/article/details/124503370) | [下载地址2](http://javapub.net.cn/project/game/chinese-chess-game.html)

植物大战僵尸：[下载地址1](https://javapub.blog.csdn.net/article/details/124238828) | [下载地址2](http://javapub.net.cn/project/game/plants-vs-zombies-game.html)

俄罗斯方块：[下载地址1](https://javapub.blog.csdn.net/article/details/124471774) | [下载地址2](http://javapub.net.cn/project/game/super-mario-game.html)

超级马里奥：[下载地址1](https://javapub.blog.csdn.net/article/details/124463555) | [下载地址2](http://javapub.net.cn/project/game/super-mario-game.html)

吃豆人游戏：[下载地址1](https://javapub.blog.csdn.net/article/details/124463461) | [下载地址2](http://javapub.net.cn/project/game/super-mario-game.html)

打地鼠：[下载地址1](https://javapub.blog.csdn.net/article/details/124463376) | [下载地址2](http://javapub.net.cn/project/game/super-mario-game.html)

捕鱼达人：[下载地址1](https://javapub.blog.csdn.net/article/details/123834030) | [下载地址2](http://javapub.net.cn/project/game/catch-fish-game.html)

打飞机：[下载地址1](https://javapub.blog.csdn.net/article/details/123699508) | [下载地址2](http://javapub.net.cn/project/game/super-mario-game.html)

坦克大战：[下载地址1](https://javapub.blog.csdn.net/article/details/123779963) | [下载地址2](http://javapub.net.cn/project/game/super-mario-game.html)

1024：[下载地址1](https://javapub.blog.csdn.net/article/details/123832950) | [下载地址2](http://javapub.net.cn/project/game/super-mario-game.html)

贪吃蛇：[下载地址1](https://javapub.blog.csdn.net/article/details/123833575) | [下载地址2](http://javapub.net.cn/project/game/super-mario-game.html)

3D赛车：[下载地址1](https://javapub.blog.csdn.net/article/details/124462822) | [下载地址2](http://javapub.net.cn/project/game/3d-racing-game.html)




汇总地址：[下载地址1](https://blog.csdn.net/qq_40374604/category_11788364.html) | [下载地址2](http://javapub.net.cn/category/%E5%B0%8F%E6%B8%B8%E6%88%8F/)



## 当前目录：

1. [springbootfirstdemo 【springboot入门初始化】](https://github.com/Rodert/SpringBoot-javapub/tree/main/springbootfirstdemo)
2. [spring-boot整合MyBatis批量更新](https://github.com/Rodert/SpringBoot-javapub/tree/main/spring-boot-mybatis)
3. [spring-boot自定义注解+AOP切面日志](https://github.com/Rodert/SpringBoot-javapub/tree/main/spring-boot-annotation )
4. [spring-boot整合docker打包jar](https://github.com/Rodert/SpringBoot-javapub/tree/main/spring-boot-docker)
5. [spring-boot 整合elasticsearch手脚架](https://github.com/Rodert/SpringBoot-javapub/tree/main/spring-boot-elasticsearch)
6. [spring-boot整合解析excel](https://github.com/Rodert/SpringBoot-javapub/tree/main/spring-boot-excel)
7. [spring-boot实现全链路日志traceId](https://github.com/Rodert/SpringBoot-javapub/tree/main/spring-boot-trace)
8. [springboot整合flowable工作流](https://github.com/Rodert/springboot-flowable)   [GitHub](https://github.com/Rodert/springboot-flowable) | [Gitee](https://gitee.com/rodert/springboot-flowable)
9. 


#### spring

1. [firstSpringProject 【spring初始化工程】](firstSpringProject)
2. [ssm_helloworld_web 【SSM整合】](ssm_helloworld_web)




## 最后

[编程大礼包](https://kazjsfecs3y.feishu.cn/wiki/VgaMwdkV6i6vdKk9OMhc5ZYVnvc)
