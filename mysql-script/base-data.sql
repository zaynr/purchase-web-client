USE purchase;

INSERT INTO users(mobile_no, pwd, user_type) VALUES(99980001, 'c4ca4238a0b923820dcc509a6f75849b', 0);

INSERT INTO purchasers(mobile_no, prefer_type) VALUES('1', '3,');

INSERT INTO providers(mobile_no, provide_type) VALUES('1', '2,3,');

INSERT INTO pur_orders(pur_serial_no, purchaser_name, order_status, order_amount, expect_price, type_no) VALUES('1', '1', '1', '123', '321', '4');
INSERT INTO pur_orders(pur_serial_no, purchaser_name, order_status, order_amount, expect_price, type_no) VALUES('2', '1', '0', '12', '321', '6');

INSERT INTO pro_orders(pro_serial_no, pur_serial_no, order_status, offer_price, provider_name) VALUES('1', '1', '1', '123', '1');

INSERT INTO order_types(type_content, type_unit) VALUE("原油", "桶");
INSERT INTO order_types(type_content, type_unit) VALUE("糖果", "吨");
INSERT INTO order_types(type_content, type_unit) VALUE("饮料", "箱");
INSERT INTO order_types(type_content, type_unit) VALUE("台灯", "部");
INSERT INTO order_types(type_content, type_unit) VALUE("电视", "台");
INSERT INTO order_types(type_content, type_unit) VALUE("化妆品", "箱");