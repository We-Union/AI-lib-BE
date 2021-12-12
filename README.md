# AI-lib-BE
## 相关链接
分析程序：[AIlib-algorithm](https://github.com/We-Union/AIlib-algorithm/)
前端：[AI-lib-FE](https://github.com/We-Union/AI-lib-FE)
## 使用说明
1. 配置applicationContext.xml中的configs bean。
+ 配置`remote_url`为分析端地址。
## 环境依赖
jdk：17 

tomcat:9.0.54(windows x64)
## 全局错误码
| 错误码 | 说明 |
| --- | --- |
| 2001 |缺少所需参数|
|2002|参数格式不正确|
|4004|请求的资源不存在|
|4003|没有权限完成请求|
|6xxx|为分析端报错，具体信息请见分析端的说明|



## 部分参数要求说明
+ 昵称：2-18位，只能包含大小写字母、汉字、以及“-”，“_”。
+ 用户名：6-15位,只能包含大小写字母以及数字。
+ 密码：大小写字母、数字以及“.@$!%*#_~?&^”。
