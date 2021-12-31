DEMO
============================

##########环境依赖
--前端(vscode, vue-cli)
  "dependencies": {
    "axios": "^0.21.0",
    "element-ui": "^2.15.6",
    "vue": "^2.6.10",
    "vue-router": "^3.0.3",
    "vuex": "^3.0.1"
  },
  "devDependencies": {
    "@vue/cli-plugin-eslint": "^3.12.0",
    "@vue/cli-service": "^3.12.0",
    "@vue/eslint-config-prettier": "^5.0.0",
    "babel-eslint": "^10.0.1",
    "eslint": "^5.16.0",
    "eslint-plugin-prettier": "^3.1.0",
    "eslint-plugin-vue": "^5.0.0",
    "prettier": "^1.18.2",
    "vue-template-compiler": "^2.6.10"
  }
}
--后端(idea2018)
"Spring-boot-starter-web"
"mybatis-spring-boot-starter": 2.2.0
"mysql-connector-java"
"lombok":1.18.4
"mybatis-plus-boot-starter": 3.4.3.1
"hutool-all" :5.7.17
"jdk" 1.8



##########前端部署步骤

# vue

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Lints and fixes files
```
npm run lint
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).



##########后端项目部署
将项目文件Sptingboot导入IDEA
等待IDEA安装加载依赖。

若实体类的get和set方法标红，需要手动导入lombok插件
添加Lombok插件:file–>setting–>plugins中搜索安装Lombok插件

依赖加载好后，启动项目。
若控制台无报错且输出类似信息Started DemoApplication ......
即部署成功

##########数据库
mysql 8.0
localhost:3306
数据库名：vue-database
characterEncoding=UTF-8
serverTimezone=GMT
useSSL=false
username=root
password=123456

##########用户登入
学生
用户名：2
密码：2
姓名：宁瑶
（普通用户数据还有很多）

教师
用户名：1
密码：111
姓名：姚老师
（学生和老师用户可以创建很多）