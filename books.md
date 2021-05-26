

## 安装 MySQL

1. 查看 CentOS 下是否已经安装 mysql `yum list installed | grep msyql`

2. 删除已安装 mysql `yum -y remove mysql`

3. 安装 mysql `yum -y install mysql mysql-server`

4. 启动 mysql 服务 `service mysqld start`

5. 修改密码

   ```mysql
   use mysql;
   update user set password='123456' where user='root'
   ```
6. 开放远程登陆权限
	```mysql
	GRANT ALL PRIVILEGES ON . TO 'root'@'%‘ IDENTIFIED BY 'password' WITH GRANT OPTION;
	FLUSH PRIVILEGES;
	```
	

7. 设置开机启动（非必须）

   `chkconfig mysql on`



## 创建 Books 项目

Books主要分为下面四个模块，使用 SpringBoot 进行开发。
├─books-mapper
├─books-model
├─books-service
└─books-web



## 使用 mybatis plus



