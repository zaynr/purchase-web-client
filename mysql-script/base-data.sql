USE purchase;

INSERT INTO users(mobile_no, pwd, user_type) VALUES('99980001', 'c4ca4238a0b923820dcc509a6f75849b', '0');

INSERT INTO users(mobile_no, pwd, user_type) VALUES('123', 'c4ca4238a0b923820dcc509a6f75849b', '1');
INSERT INTO purchasers(mobile_no, address, prefer_type) VALUES('123', '浙江省杭州市江南大道', '2,3,');

INSERT INTO users(mobile_no, pwd, user_type) VALUES('321', 'c4ca4238a0b923820dcc509a6f75849b', '2');
INSERT INTO providers(mobile_no, provide_type) VALUES('321', '2,5,6,');
INSERT INTO users(mobile_no, pwd, user_type) VALUES('12321', 'c4ca4238a0b923820dcc509a6f75849b', '2');
INSERT INTO providers(mobile_no, provide_type) VALUES('12321', '2,5,6,');

INSERT INTO pur_orders(pur_serial_no, purchaser_name, order_status, order_amount, expect_price, type_no) VALUES('1', '123', '0', '12', '321', '6');
INSERT INTO pur_orders(pur_serial_no, purchaser_name, order_status, order_amount, expect_price, type_no) VALUES('2', '123', '1', '123', '321', '5');
INSERT INTO pur_orders(pur_serial_no, purchaser_name, order_status, order_amount, expect_price, type_no) VALUES('3', '123', '2', '123', '321', '4');
INSERT INTO pur_orders(pur_serial_no, purchaser_name, order_status, order_amount, expect_price, type_no) VALUES('4', '123', '3', '123', '321', '3');
INSERT INTO pur_orders(pur_serial_no, purchaser_name, order_status, order_amount, expect_price, type_no) VALUES('5', '123', '4', '123', '321', '2');
INSERT INTO pur_orders(pur_serial_no, purchaser_name, order_status, order_amount, expect_price, type_no) VALUES('6', '123', '5', '123', '321', '3');
INSERT INTO pur_orders(pur_serial_no, purchaser_name, order_status, order_amount, expect_price, type_no) VALUES('7', '123', '7', '123', '321', '4');
INSERT INTO pur_orders(pur_serial_no, purchaser_name, order_status, order_amount, expect_price, type_no) VALUES('8', '123', '8', '12', '321', '3');
INSERT INTO pur_orders(pur_serial_no, purchaser_name, order_status, order_amount, expect_price, type_no) VALUES('9', '123', '0', '12', '321', '3');

INSERT INTO pro_orders(pro_serial_no, pur_serial_no, order_status, offer_price, provider_name) VALUES('1', '2', '1', '123', '321');
INSERT INTO pro_orders(pro_serial_no, pur_serial_no, order_status, offer_price, provider_name, express_no) VALUES('2', '3', '2', '321', '321', 'A1B2C3E4');
INSERT INTO pro_orders(pro_serial_no, pur_serial_no, order_status, offer_price, provider_name, express_no) VALUES('3', '4', '3', '321', '321', 'A1B2C3E4');
INSERT INTO pro_orders(pro_serial_no, pur_serial_no, order_status, offer_price, provider_name, express_no) VALUES('4', '5', '4', '321', '321', 'A1B2C3E4');
INSERT INTO pro_orders(pro_serial_no, pur_serial_no, order_status, offer_price, provider_name, express_no) VALUES('5', '5', '4', '312', '12321', 'C3E4A1B2');
INSERT INTO pro_orders(pro_serial_no, pur_serial_no, order_status, offer_price, provider_name, express_no) VALUES('6', '6', '6', '321', '321', 'A1B2C3E4');

INSERT INTO order_types(type_content, type_unit) VALUES("原油", "桶");
INSERT INTO order_types(type_content, type_unit) VALUES("糖果", "吨");
INSERT INTO order_types(type_content, type_unit) VALUES("饮料", "箱");
INSERT INTO order_types(type_content, type_unit) VALUES("台灯", "部");
INSERT INTO order_types(type_content, type_unit) VALUES("电视", "台");
INSERT INTO order_types(type_content, type_unit) VALUES("化妆品", "箱");