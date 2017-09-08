use purchase;

INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `order_cat`) VALUES ('1', '123', '0', '0');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `order_cat`) VALUES ('2', '123', '1', '0');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `order_cat`) VALUES ('3', '123', '2', '0');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `order_cat`) VALUES ('4', '123', '3', '0');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `order_cat`) VALUES ('5', '123', '4', '0');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `order_cat`) VALUES ('6', '123', '5', '0');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `order_cat`) VALUES ('7', '123', '7', '0');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `order_cat`) VALUES ('8', '123', '8', '0');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `order_cat`) VALUES ('9', '123', '0', '0');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `order_cat`) VALUES ('10', '321', '1', '1');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `order_cat`) VALUES ('11', '321', '2', '1');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `order_cat`) VALUES ('12', '321', '3', '1');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `order_cat`) VALUES ('13', '321', '4', '1');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `order_cat`) VALUES ('14', '12321', '4', '1');
INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `order_cat`) VALUES ('15', '321', '6', '1');

-- INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `order_cat`) VALUES ('20', '123', '0', '0');
-- INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `order_cat`) VALUES ('21', '123', '0', '0');
-- INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `order_cat`) VALUES ('22', '123', '0', '0');
-- INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `order_cat`) VALUES ('23', '123', '0', '0');
-- INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `order_cat`) VALUES ('24', '123', '0', '0');
-- INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `order_cat`) VALUES ('25', '123', '0', '0');
-- INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `order_cat`) VALUES ('26', '123', '0', '0');
-- INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `order_cat`) VALUES ('27', '123', '0', '0');
-- INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `order_cat`) VALUES ('28', '123', '0', '0');
-- INSERT INTO `purchase`.`all_orders` (`serial_no`, `mobile_no`, `order_status`, `order_cat`) VALUES ('29', '123', '0', '0');

INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('1', '123', '0', '0', '12', '321', NULL, '1,2,3,', '6');
INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('2', '123', '1', '0', '123', '321', NULL, NULL, '5');
INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('3', '123', '2', '0', '123', '321', NULL, NULL, '4');
INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('4', '123', '3', '0', '123', '321', NULL, NULL, '3');
INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('5', '123', '4', '0', '123', '321', NULL, NULL, '2');
INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('6', '123', '5', '0', '123', '321', NULL, NULL, '2');
INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('7', '123', '7', '0', '123', '321', NULL, NULL, '4');
INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('8', '123', '8', '0', '12', '321', NULL, NULL, '3');
INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('9', '123', '0', '0', '12', '321', NULL, NULL, '3');

-- INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('20', '123', '0', '0', '222', '21313213', '123321', NULL, '3');
-- INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('21', '123', '0', '0', '222', '23123211', '123321', NULL, '1');
-- INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('22', '123', '0', '1', '123321', '321123', '212121    public void setDict(String dict) {\n        this.dict = dict;\n    }\n    public void setDict(String dict) {\n        this.dict = dict;\n    }\n', NULL, '2');
-- INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('23', '123', '0', '0', '132', '321123', '212121    public void setDict(String dict) {\n        this.dict = dict;\n    }\n    public void setDict(String dict) {\n        this.dict = dict;\n    }\n', NULL, '2');
-- INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('24', '123', '0', '0', '132', '321123', '212121    public void setDict(String dict) {\n        this.dict = dict;\n    }\n    public void setDict(String dict) {\n        this.dict = dict;\n    }\n', NULL, '2');
-- INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('25', '123', '0', '0', '132', '321123', '212121    public void setDict(String dict) {\n        this.dict = dict;\n    }\n    public void setDict(String dict) {\n        this.dict = dict;\n    }\n', NULL, '2');
-- INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('26', '123', '0', '0', '132', '321123', '212121    public void setDict(String dict) {\n        this.dict = dict;\n    }\n    public void setDict(String dict) {\n        this.dict = dict;\n    }\n', NULL, '2');
-- INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('27', '123', '0', '0', '21321', '321321', '糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果糖果', NULL, '2');
-- INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('28', '123', '0', '0', '321', '-1', '未添加详细需求', NULL, '1');
-- INSERT INTO `purchase`.`pur_orders` (`pur_serial_no`, `purchaser_name`, `order_status`, `expect_status`, `order_amount`, `expect_price`, `more_detail`, `filter_dict`, `type_no`) VALUES ('29', '123', '0', '0', '123', '-1', '未添加详细需求', NULL, '3');

INSERT INTO pro_orders(pro_serial_no, pur_serial_no, order_status, offer_price, provider_name) VALUES('10', '2', '1', '123', '321');
INSERT INTO pro_orders(pro_serial_no, pur_serial_no, order_status, offer_price, provider_name, express_no) VALUES('11', '3', '2', '321', '321', 'A1B2C3E4');
INSERT INTO pro_orders(pro_serial_no, pur_serial_no, order_status, offer_price, provider_name, express_no) VALUES('12', '4', '3', '321', '321', 'A1B2C3E4');
INSERT INTO pro_orders(pro_serial_no, pur_serial_no, order_status, offer_price, provider_name, express_no) VALUES('13', '5', '4', '321', '321', 'A1B2C3E4');
INSERT INTO pro_orders(pro_serial_no, pur_serial_no, order_status, offer_price, provider_name, express_no) VALUES('14', '5', '4', '312', '12321', 'C3E4A1B2');
INSERT INTO pro_orders(pro_serial_no, pur_serial_no, order_status, offer_price, provider_name, express_no) VALUES('15', '6', '6', '321', '321', 'A1B2C3E4');

INSERT INTO `purchase`.`filter_dict` (`order_serial_no`, `province`, `city`, `dist`) VALUES ('1', '浙江省', '杭州市', '滨江区');
INSERT INTO `purchase`.`filter_dict` (`order_serial_no`, `province`, `city`, `dist`) VALUES ('1', '福建省', '泉州市', 'null');
INSERT INTO `purchase`.`filter_dict` (`order_serial_no`, `province`, `city`, `dist`) VALUES ('1', '重庆市', 'null', 'null');