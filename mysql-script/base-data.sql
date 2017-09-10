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
INSERT INTO `purchase`.`user_address` (`mobile_no`, `user_type`, `province`, `city`, `dist`, `detail_address`) VALUES ('99980001', '0', '浙江省', '杭州市', '西湖区', '啊啊啊啊');

INSERT INTO `purchase`.`admin_option` (`option_no`, `option_describe`, `option_content`) VALUES ('1', '报价每页条数', '10');
INSERT INTO `purchase`.`admin_option` (`option_no`, `option_describe`, `option_content`) VALUES ('2', '需求每页条数', '10');
INSERT INTO `purchase`.`admin_option` (`option_no`, `option_describe`, `option_content`) VALUES ('3', '默认页面条数', '10');

INSERT INTO serial_no_gen(serial_no) VALUES('100');

INSERT INTO order_types(type_content, type_unit, type_category) VALUES("糖果", "吨", "食品");
INSERT INTO order_types(type_content, type_unit, type_category) VALUES("饮料", "箱", "食品");
INSERT INTO order_types(type_content, type_unit, type_category) VALUES("台灯", "部", "家电");
INSERT INTO order_types(type_content, type_unit, type_category) VALUES("电视", "台", "家电");
INSERT INTO order_types(type_content, type_unit, type_category) VALUES("洗发露", "箱", "日用品");
INSERT INTO order_types(type_content, type_unit, type_category) VALUES("化妆品", "箱", "日用品");

INSERT INTO `purchase`.`message_type` (`type_no`, `receiver`, `type_describe`) VALUES ('1', '2', '未报价订单');
INSERT INTO `purchase`.`message_type` (`type_no`, `receiver`, `type_describe`) VALUES ('2', '2', '待寄送样品');
INSERT INTO `purchase`.`message_type` (`type_no`, `receiver`, `type_describe`) VALUES ('3', '2', '已确认收到样品');
INSERT INTO `purchase`.`message_type` (`type_no`, `receiver`, `type_describe`) VALUES ('4', '2', '待签订合同');
INSERT INTO `purchase`.`message_type` (`type_no`, `receiver`, `type_describe`) VALUES ('5', '1', '需求已被报价');
INSERT INTO `purchase`.`message_type` (`type_no`, `receiver`, `type_describe`) VALUES ('6', '1', '待确认样品');
INSERT INTO `purchase`.`message_type` (`type_no`, `receiver`, `type_describe`) VALUES ('7', '1', '供应商确认合同');
INSERT INTO `purchase`.`message_type` (`type_no`, `receiver`, `type_describe`) VALUES ('8', '1', '供应商拒绝合同');


