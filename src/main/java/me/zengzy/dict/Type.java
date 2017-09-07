package me.zengzy.dict;

public class Type {
    public static class User{
        public final static int ADMINISTRATOR = 0;
        public final static int PURCHASER = 1;
        public final static int PROVIDER = 2;
    }
    public static String UserTranslate(int type){
        String res = "undefined";
        switch (type){
            case User.ADMINISTRATOR:
                res = "管理员";
                break;
            case User.PURCHASER:
                res = "采购商";
                break;
            case User.PROVIDER:
                res = "供应商";
                break;
        }
        return res;
    }

    public static class Order{
        public final static int PURCHASE_ORDER = 0;
        public final static int PROVIDE_ORDER = 1;
    }

    public static String OrderTranslate(int type){
        String res = "undefined";
        switch (type){
            case Order.PURCHASE_ORDER:
                res = "采购需求";
                break;
            case Order.PROVIDE_ORDER:
                res = "供应报价";
                break;
        }
        return res;
    }
}
