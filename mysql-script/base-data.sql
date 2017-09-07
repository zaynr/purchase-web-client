USE purchase;

INSERT INTO purchasers(mobile_no, prefer_type) VALUES('123', '2,3,');

INSERT INTO providers(mobile_no, provide_type) VALUES('321', '2,5,6,');
INSERT INTO providers(mobile_no, provide_type) VALUES('12321', '2,5,6,');

INSERT INTO `purchase`.`users` (`mobile_no`, `user_type`, `pwd`, `user_name`, `space_used`) VALUES ('123', '1', 'c4ca4238a0b923820dcc509a6f75849b', '大帅哥', '0');
INSERT INTO `purchase`.`users` (`mobile_no`, `user_type`, `pwd`, `user_name`, `space_used`) VALUES ('12321', '2', 'c4ca4238a0b923820dcc509a6f75849b', '大美女', '0');
INSERT INTO `purchase`.`users` (`mobile_no`, `user_type`, `pwd`, `user_name`, `space_used`) VALUES ('321', '2', 'c4ca4238a0b923820dcc509a6f75849b', '钢铁侠', '0');
INSERT INTO `purchase`.`users` (`mobile_no`, `user_type`, `pwd`, `user_name`, `space_used`) VALUES ('99980001', '0', 'c4ca4238a0b923820dcc509a6f75849b', '管理员', '0');

INSERT INTO `purchase`.`user_address` (`mobile_no`, `user_type`, `province`, `city`, `dist`, `detail_address`) VALUES ('123', '1', '浙江省', '杭州市', '滨江区', '啊啊啊啊');
INSERT INTO `purchase`.`user_address` (`mobile_no`, `user_type`, `province`, `city`, `dist`, `detail_address`) VALUES ('321', '2', '浙江省', '杭州市', '滨江区', '啊啊啊啊');
INSERT INTO `purchase`.`user_address` (`mobile_no`, `user_type`, `province`, `city`, `dist`, `detail_address`) VALUES ('12321', '2', '浙江省', '杭州市', '西湖区', '啊啊啊啊');

INSERT INTO `purchase`.`admin_option` (`option_no`, `option_describe`, `option_content`) VALUES ('1', '报价每页条数', '10');
INSERT INTO `purchase`.`admin_option` (`option_no`, `option_describe`, `option_content`) VALUES ('2', '需求每页条数', '10');
INSERT INTO `purchase`.`admin_option` (`option_no`, `option_describe`, `option_content`) VALUES ('3', '默认页面条数', '10');

INSERT INTO serial_no_gen(serial_no) VALUES('100');

INSERT INTO order_types(type_content, type_unit) VALUES("原油", "桶");
INSERT INTO order_types(type_content, type_unit) VALUES("糖果", "吨");
INSERT INTO order_types(type_content, type_unit) VALUES("饮料", "箱");
INSERT INTO order_types(type_content, type_unit) VALUES("台灯", "部");
INSERT INTO order_types(type_content, type_unit) VALUES("电视", "台");
INSERT INTO order_types(type_content, type_unit) VALUES("化妆品", "箱");