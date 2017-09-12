package me.zengzy.dict;

public class Status {

    public static class Order {
        public static final int UN_REC = 0;//待报价
        public static final int OFFERED_PRICE = 1;//已报价
        public static final int REQUIRE_SAMPLE = 2;//待提供样品
        public static final int OFFERED_SAMPLE = 3;//已寄送样品
        public static final int CONFIRM_SAMPLE = 4;//已确认样品
        public static final int SIGNED = 5;//已签合同
        public static final int UN_SIGNED = 6;//未被采纳
        public static final int DECLINE_CONTRACT = 7;//合同被拒绝
        public static final int CANCEL = 8;//撤销
        public static final int OFFERED_CONTRACT = 9;//已发送合同
        public static final int DONE = 10;//撤销
        public static final int ACC_SAMPLE = 11;//撤销
        public static final int DEC_SAMPLE = 12;//撤销
        public static final int EXAM_SAMPLE = 13;//撤销
    }

    public static String orderTranslate(int status){
        String res = "undefined";
        switch(status){
            case Order.UN_REC:
                res = "待报价";
                break;
            case Order.OFFERED_PRICE:
                res = "已报价";
                break;
            case Order.REQUIRE_SAMPLE:
                res = "待寄送样品";
                break;
            case Order.OFFERED_SAMPLE:
                res = "已寄送样品";
                break;
            case Order.SIGNED:
                res = "已签合同";
                break;
            case Order.DECLINE_CONTRACT:
                res = "合同被拒绝";
                break;
            case Order.CANCEL:
                res = "撤销";
                break;
            case Order.CONFIRM_SAMPLE:
                res = "已确认样品";
                break;
            case Order.UN_SIGNED:
                res = "未被采纳";
                break;
            case Order.OFFERED_CONTRACT:
                res = "已发送合同";
                break;
            case Order.DONE:
                res = "已完成";
                break;
            case Order.ACC_SAMPLE:
                res = "样品合格";
                break;
            case Order.DEC_SAMPLE:
                res = "样品不合格";
                break;
            case Order.EXAM_SAMPLE:
                res = "检验样品";
                break;
        }
        return res;
    }

    public static class Expect{
        final public static int SHOW = 0;
        final public static int HIDE = 1;
    }

    public static String expectTranslate(int status){
        String res = "undefined";
        switch (status){
            case Expect.SHOW:
                res = "显示期望报价";
                break;
            case Expect.HIDE:
                res = "隐藏期望报价";
                break;
        }
        return res;
    }
}
