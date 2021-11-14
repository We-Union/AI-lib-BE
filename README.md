# AI-lib-BE
## 使用说明
1. 配置applicationContext.xml中的configs bean。
+ python_path:"要执行的python文件的路径"。
+ python_exec:"执行python的命令，例如`python`、`python3`、`usr\bin\python`"。
该项目仅为后端，分析的python请见另一个仓库，将该仓库整体下载后保存到文件夹，将路径输入python_path即可。
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
