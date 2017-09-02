package me.zengzy.dict;

public class Status {

    public static class Order {
        public static final int UN_REC = 0;
        public static final int OFFERED_PRICE = 1;
        public static final int UN_OFFERED_SAMPLE = 2;
        public static final int OFFERED_SAMPLE = 3;
        public static final int SIGNED = 4;
        public static final int DONE = 5;
    }

    public static String orderTranslate(int status){
        String res = "undefined";
        switch(status){
            case Order.UN_REC:
                res = "待接";
                break;
            case Order.OFFERED_PRICE:
                res = "已报价";
                break;
            case Order.UN_OFFERED_SAMPLE:
                res = "待提供样品";
                break;
            case Order.OFFERED_SAMPLE:
                res = "已提供样品";
                break;
            case Order.SIGNED:
                res = "已签";
                break;
            case Order.DONE:
                res = "已完成";
                break;
        }
        return res;
    }
}
