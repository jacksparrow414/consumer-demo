# RabbitMQ学习例子Consumer端
## 项目说明
此项目是RabbitMQ结合Spring AMQP实际使用的实例仓库
## 启动必看
Consumer端依赖Producer端的api配置包，所以，启动之前，请在Producer端构建api包，本地install一下即可。  

第一步进入Producer端项目文件下
```
cd /${ProducerDirectory}
```
第二步执行构建命令
```bash
mvn -f export-config-package install
```
Producer的仓库见[这里](https://github.com/jacksparrow414/rabbitmqexample)
 
