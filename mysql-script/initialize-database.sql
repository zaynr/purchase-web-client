CREATE DATABASE IF NOT EXISTS purchase;
USE purchase;
DROP TABLE IF EXISTS users;
CREATE TABLE users(
	user_name nvarchar(63) NOT NULL PRIMARY KEY,
    pwd varchar(255),
    user_type int	#0：管理员；1：采购商；2：供应商
);