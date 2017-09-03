CREATE DATABASE IF NOT EXISTS purchase;
USE purchase;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS providers;
DROP TABLE IF EXISTS purchasers;
DROP TABLE IF EXISTS order_types;
DROP TABLE IF EXISTS pur_orders;
DROP TABLE IF EXISTS pro_orders;
DROP TABLE IF EXISTS contacts;
DROP TABLE IF EXISTS contracts;

#用户主表
CREATE TABLE users(
  mobile_no nvarchar(12) NOT NULL,
  user_type int NOT NULL,	# 0:管理员;1:采购商;2:供应商
  pwd varchar(255),
  user_name nvarchar(63),
  PRIMARY KEY(mobile_no, user_type)
);
#供应商
CREATE TABLE providers(
  mobile_no nvarchar(12) NOT NULL PRIMARY KEY,
  provide_type nvarchar(255)
);
#采购商
CREATE TABLE purchasers(
  mobile_no nvarchar(12) NOT NULL PRIMARY KEY
);
#订单类型
CREATE TABLE order_types(
  type_no int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  type_content nvarchar(255)
);
#采购订单表
CREATE TABLE pur_orders(
  pur_serial_no int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  purchaser_name nvarchar(63),
  order_status int,
  order_amount varchar(10),
  type_no int
);
#供应报价表
CREATE TABLE pro_orders(
  pro_serial_no int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	pur_serial_no int,
  order_status int,
  offer_price double,
  provider_name nvarchar(63)
);
#联系人
CREATE TABLE contacts(
  serial_no int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  purchaser_name nvarchar(63),
  provider_name nvarchar(63)
);
#合同表
CREATE TABLE contracts(
  contract_serial_no int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  pro_serial_no int,
	pur_serial_no int,
  contract_status int,	# 0:正在进行;1:已完成
  sign_time datetime
);