package me.zengzy.dict;

public class Status {
    public static class Order {
        //0:待接;1:已报价;2:待提供样品;3:已签;4:已完成
        public static final int UNREC = 0;
        public static final int OFFERED_PRICE = 1;
        public static final int OFFERED_SAMPLE = 2;
        public static final int SIGNED = 3;
        public static final int DONE = 4;
    }

}
