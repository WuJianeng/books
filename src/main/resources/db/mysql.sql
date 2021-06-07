# 创建 user 表
DROP TABLE IF EXISTS user;

CREATE TABLE user
(
id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
name VARCHAR(30) NOT NULL COMMENT '姓名',
password VARCHAR(30) NOT NULL COMMENT '密码',
gender VARCHAR(5) NULL DEFAULT NULL COMMENT '性别',
age INT(11) NULL DEFAULT NULL COMMENT '年龄',
email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
PRIMARY KEY (id)
);


INSERT INTO user (id, name, password, gender, age, email) VALUES
(1, 'Jone', '123456', 'F', 18, 'test1@baomidou.com'),
(2, 'Jack', '123456', 'M', 20, 'test2@baomidou.com'),
(3, 'Tom', '123456', 'M', 28, 'test3@baomidou.com'),
(4, 'Sandy', '123456', 'M', 21, 'test4@baomidou.com'),
(5, 'Billie', '123456', 'F', 24, 'test5@baomidou.com');


#-----------------------------------------
# 创建 UserAddress 表

DROP TABLE IF EXISTS user_address;
CREATE TABLE user_address (
    id BIGINT(20) NOT NULL  PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT(20) NOT NULL COMMENT '用户 id',
    address VARCHAR(64) NOT NULL COMMENT '用户地址'
);

INSERT INTO books.user_address (id, user_id, address) VALUES (1, 1, '北京市海淀区');
INSERT INTO books.user_address (id, user_id, address) VALUES (2, 1, '浙江省杭州市');
INSERT INTO books.user_address (id, user_id, address) VALUES (3, 2, '湖北省武汉市');
INSERT INTO books.user_address (id, user_id, address) VALUES (4, 3, '湖北省黄冈市');
INSERT INTO books.user_address (id, user_id, address) VALUES (5, 4, '浙江省宁波市');
INSERT INTO books.user_address (id, user_id, address) VALUES (6, 5, '天津市津南区');
INSERT INTO books.user_address (id, user_id, address) VALUES (7, 5, '天津市南开区');
INSERT INTO books.user_address (id, user_id, address) VALUES (8, 1, '日本大阪市');
INSERT INTO books.user_address (id, user_id, address) VALUES (9, 8, '浙江杭州');
INSERT INTO books.user_address (id, user_id, address) VALUES (10, 8, '湖北黄冈');
INSERT INTO books.user_address (id, user_id, address) VALUES (11, 8, '北京海淀');

#------------------------------------------
# 创建 BookClass 表
DROP TABLE IF EXISTS book_class;
CREATE TABLE book_class (
    id BIGINT(20) NOT NULL  PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT(20) NOT NULL COMMENT '用户 id',
    class_name VARCHAR(64) NOT NULL COMMENT '书籍分类名称'
);

INSERT INTO book_class (id, user_id, class_name) VALUES
(1, 1, '工具书'),
(2, 1, '技术'),
(3, 1, '人文'),
(4, 2, '历史'),
(5, 3, '生活'),
(6, 4, '养生'),
(7, 5, '学习'),
(8, 5, '人文');


# 创建 Book 表
DROP TABLE IF EXISTS book;

CREATE TABLE book (
    id BIGINT(20) NOT NULL  PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT(20) NOT NULL COMMENT '用户 id',
    address_id BIGINT(20) NOT NULL COMMENT '用户地址 id',
    book_class_id BIGINT(20) NOT NULL COMMENT '用户书籍分类 id',
    book_name VARCHAR(64) NOT NULL COMMENT '书名',
    read_page INT NULL DEFAULT NULL COMMENT '已阅读到的书页数'
);

INSERT INTO book (id, user_id, address_id, book_class_id, book_name) VALUES
(1, 1, 1, 1, '英语词典'),
(2, 1, 2, 2, 'Python 实战'),
(3, 2, 3, 2, '中国历代政治历史发展变革');
