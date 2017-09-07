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

INSERT INTO serial_no_gen(serial_no) VALUES('123');

INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('1', '123', '0', '0', '12', '321', NULL, '1,2,3,', '6');
INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('2', '123', '1', '0', '123', '321', NULL, NULL, '5');
INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('3', '123', '2', '0', '123', '321', NULL, NULL, '4');
INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('4', '123', '3', '0', '123', '321', NULL, NULL, '3');
INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('5', '123', '4', '0', '123', '321', NULL, NULL, '2');
INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('7', '123', '7', '0', '123', '321', NULL, NULL, '4');
INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('8', '123', '8', '0', '12', '321', NULL, NULL, '3');
INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('9', '123', '0', '0', '12', '321', NULL, NULL, '3');

INSERT INTO pro_orders(pro_serial_no, pur_serial_no, order_status, offer_price, provider_name) VALUES('10', '2', '1', '123', '321');
INSERT INTO pro_orders(pro_serial_no, pur_serial_no, order_status, offer_price, provider_name, express_no) VALUES('11', '3', '2', '321', '321', 'A1B2C3E4');
INSERT INTO pro_orders(pro_serial_no, pur_serial_no, order_status, offer_price, provider_name, express_no) VALUES('12', '4', '3', '321', '321', 'A1B2C3E4');
INSERT INTO pro_orders(pro_serial_no, pur_serial_no, order_status, offer_price, provider_name, express_no) VALUES('13', '5', '4', '321', '321', 'A1B2C3E4');
INSERT INTO pro_orders(pro_serial_no, pur_serial_no, order_status, offer_price, provider_name, express_no) VALUES('14', '5', '4', '312', '12321', 'C3E4A1B2');
INSERT INTO pro_orders(pro_serial_no, pur_serial_no, order_status, offer_price, provider_name, express_no) VALUES('15', '6', '6', '321', '321', 'A1B2C3E4');

INSERT INTO `purchase`.`filter_dict` (`order_serial_no`, `province`, `city`, `dist`) VALUES ('1', '浙江省', '杭州市', '滨江区');
INSERT INTO `purchase`.`filter_dict` (`order_serial_no`, `province`, `city`, `dist`) VALUES ('1', '福建省', '泉州市', 'null');
INSERT INTO `purchase`.`filter_dict` (`order_serial_no`, `province`, `city`, `dist`) VALUES ('1', '重庆市', 'null', 'null');

INSERT INTO order_types(type_content, type_unit) VALUES("原油", "桶");
INSERT INTO order_types(type_content, type_unit) VALUES("糖果", "吨");
INSERT INTO order_types(type_content, type_unit) VALUES("饮料", "箱");
INSERT INTO order_types(type_content, type_unit) VALUES("台灯", "部");
INSERT INTO order_types(type_content, type_unit) VALUES("电视", "台");
INSERT INTO order_types(type_content, type_unit) VALUES("化妆品", "箱");