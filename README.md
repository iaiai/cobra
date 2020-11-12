## 眼镜蛇

##### 介绍
眼镜蛇是一个基于最新 Web 技术的企业级通用管理系统快速开发框架，可以帮助企业极大的提高工作效率，节省开发成本，提升品牌形象。

眼镜蛇是基于iview做的一套前端+后台的基础框架(之前是用element-ui写的，但element-ui已经不再更新，所以全部改为iview)，在创建新项目时总是没有合适的基础框架，故本人所写一套基础框架，在项目中直接拿来就用，所用的技术有:

* vue+vuex+iview+动态生成路由
* springboot
* mybatis
* redis

##### 作者信息：
* QQ: 176291935
* email: 176291935@qq.com
* QQ群: 104286694

眼镜蛇项目完全免费开源，大家可以随便使用。会不断的往上加新功能。谢谢大家支持

```
安装配置(本人所有命令全是在mac中或是centos系统中执行的，windows系统请自行查看命令):

1. 把数据库文件导入到mysql数据库中

2. 前端在cobra.front目录执行命令: yarn
3. 前端的端口请在cobra.front/vue.config.js中配置服务器ip和端口

4. 服务器端配置在cobra.admin中，找到application-*.yml文件，配置对应的mysql地址
5. 相同文件配置redis的ip地址和端口和密码。
6. application-base.yml配置对应阿里云的oss相关参数
7. 在Active profiles是配置对应的启动文件，如:dev，然后即可启动运行后台
8. 登录中有两个验证码，下面那张图片里数字是对应上面那张图片中位置的字符，默认密码为: Aaaaaa1!


发布:
1. 前端编译发布: yarn build，然后把/dist目录上传到服务器配置到nginx中
2. 后端编译发布: ./gradlew build，编译好会生成jar包
3. jar包执行(放到centos系统中然后执行运行): 
    * nohup java -jar xxx.jar --spring.profiles.active=pro >out.log &
    * nohup java -jar -Dspring.config.location=application-pro.yml xxxjar >out.log &
```

##### 注意问题
* 在权限设置时一定要把首页配上

##### 已知问题：
1. 如果是集群部署，websocket服务端的存储方式需要修改一下

##### 界面:
![avatar](https://gitee.com/iaiai/cobra/raw/master/document/%E7%95%8C%E9%9D%A21.png)
![avatar](https://gitee.com/iaiai/cobra/raw/master/document/%E7%95%8C%E9%9D%A22.png)
![avatar](https://gitee.com/iaiai/cobra/raw/master/document/%E7%95%8C%E9%9D%A23.png)
![avatar](https://gitee.com/iaiai/cobra/raw/master/document/%E7%95%8C%E9%9D%A24.png)
![avatar](https://gitee.com/iaiai/cobra/raw/master/document/%E7%95%8C%E9%9D%A25.png)
![avatar](https://gitee.com/iaiai/cobra/raw/master/document/%E7%95%8C%E9%9D%A26.png)
![avatar](https://gitee.com/iaiai/cobra/raw/master/document/%E7%95%8C%E9%9D%A27.png)
