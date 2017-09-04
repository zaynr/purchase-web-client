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
}
