package me.zengzy.dict;

import com.qiniu.common.Zone;

public class ApiKey {
    public static class Qiniu{
        final public static String AccessKey = "kRUi8i9_SyN3N0KS8CYNn8rVp2gr7rCg0FEYfPYN";
        final public static String SecretKey = "O8_gYiL9ZBzEVJqHdL3DIjR_P15Vp0mx6ev4yBmr";
        final public static String BucketName = "simpletrade";
        final public static String baseUrl = "http://ovt81175l.bkt.clouddn.com/";
        /*
        *
        * 华东	Zone.zone0()
        * 华北	Zone.zone1()
        * 华南	Zone.zone2()
        *
        * */
        final public static Zone zone = Zone.zone2();
    }
}
