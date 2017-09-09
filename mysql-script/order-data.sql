use purchase;

INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `read_flag`, `order_cat`) VALUES ('1', '123', '0', '0', '0');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `read_flag`, `order_cat`) VALUES ('2', '123', '1', '0', '0');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `read_flag`, `order_cat`) VALUES ('3', '123', '2', '0', '0');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `read_flag`, `order_cat`) VALUES ('4', '123', '3', '0', '0');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `read_flag`, `order_cat`) VALUES ('5', '123', '4', '0', '0');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `read_flag`, `order_cat`) VALUES ('6', '123', '5', '0', '0');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `read_flag`, `order_cat`) VALUES ('7', '123', '7', '0', '0');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `read_flag`, `order_cat`) VALUES ('8', '123', '8', '0', '0');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `read_flag`, `order_cat`) VALUES ('9', '123', '0', '0', '0');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `read_flag`, `order_cat`) VALUES ('10', '321', '1', '0', '1');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `read_flag`, `order_cat`) VALUES ('11', '321', '2', '0', '1');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `read_flag`, `order_cat`) VALUES ('12', '321', '3', '0', '1');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `read_flag`, `order_cat`) VALUES ('13', '321', '4', '0', '1');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `read_flag`, `order_cat`) VALUES ('14', '12321', '4', '0', '1');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `read_flag`, `order_cat`) VALUES ('15', '321', '6', '0', '1');

INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `read_flag`, `more_detail`, `filter_dict`, `type_no`) VALUES ('1', '123', '0', '0', '12', '321', '0', NULL, '1,2,3,', '6');
INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `read_flag`, `more_detail`, `filter_dict`, `type_no`) VALUES ('2', '123', '1', '0', '123', '321', '0', NULL, NULL, '5');
INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `read_flag`, `more_detail`, `filter_dict`, `type_no`) VALUES ('3', '123', '2', '0', '123', '321', '0', NULL, NULL, '4');
INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `read_flag`, `more_detail`, `filter_dict`, `type_no`) VALUES ('4', '123', '3', '0', '123', '321', '0', NULL, NULL, '3');
INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `read_flag`, `more_detail`, `filter_dict`, `type_no`) VALUES ('5', '123', '4', '0', '123', '321', '0', NULL, NULL, '2');
INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `read_flag`, `more_detail`, `filter_dict`, `type_no`) VALUES ('6', '123', '5', '0', '123', '321', '0', NULL, NULL, '2');
INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `read_flag`, `more_detail`, `filter_dict`, `type_no`) VALUES ('7', '123', '7', '0', '123', '321', '0', NULL, NULL, '4');
INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `read_flag`, `more_detail`, `filter_dict`, `type_no`) VALUES ('8', '123', '8', '0', '12', '321', '0', NULL, NULL, '3');
INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `read_flag`, `more_detail`, `filter_dict`, `type_no`) VALUES ('9', '123', '0', '0', '12', '321', '0', NULL, NULL, '3');

INSERT INTO `purchase`.`pro_orders` (`pro_serial_no`, `pur_serial_no`, `read_flag`, `order_status`, `offer_price`, `express_no`, `provider_name`) VALUES ('10', '2', '0', '1', '123', NULL, '321');
INSERT INTO `purchase`.`pro_orders` (`pro_serial_no`, `pur_serial_no`, `read_flag`, `order_status`, `offer_price`, `express_no`, `provider_name`) VALUES ('11', '3', '0', '2', '321', 'A1B2C3E4', '321');
INSERT INTO `purchase`.`pro_orders` (`pro_serial_no`, `pur_serial_no`, `read_flag`, `order_status`, `offer_price`, `express_no`, `provider_name`) VALUES ('12', '4', '0', '3', '321', 'A1B2C3E4', '321');
INSERT INTO `purchase`.`pro_orders` (`pro_serial_no`, `pur_serial_no`, `read_flag`, `order_status`, `offer_price`, `express_no`, `provider_name`) VALUES ('13', '5', '0', '4', '321', 'A1B2C3E4', '321');
INSERT INTO `purchase`.`pro_orders` (`pro_serial_no`, `pur_serial_no`, `read_flag`, `order_status`, `offer_price`, `express_no`, `provider_name`) VALUES ('14', '5', '0', '4', '312', 'C3E4A1B2', '12321');
INSERT INTO `purchase`.`pro_orders` (`pro_serial_no`, `pur_serial_no`, `read_flag`, `order_status`, `offer_price`, `express_no`, `provider_name`) VALUES ('15', '6', '0', '6', '321', 'A1B2C3E4', '321');

INSERT INTO `purchase`.`filter_dict` (`order_serial_no`, `province`, `city`, `dist`) VALUES ('1', '浙江省', '杭州市', '滨江区');
INSERT INTO `purchase`.`filter_dict` (`order_serial_no`, `province`, `city`, `dist`) VALUES ('1', '福建省', '泉州市', 'null');
INSERT INTO `purchase`.`filter_dict` (`order_serial_no`, `province`, `city`, `dist`) VALUES ('1', '重庆市', 'null', 'null');