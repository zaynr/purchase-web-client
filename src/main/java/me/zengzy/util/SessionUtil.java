package me.zengzy.util;

import javax.servlet.http.HttpServletRequest;

public class SessionUtil {
    public static String getMobileNo(HttpServletRequest request){
        return String.valueOf(request.getSession().getAttribute("mobileNo"));
    }


    public static String getUserPwd(HttpServletRequest request){
        return String.valueOf(request.getSession().getAttribute("userPwd"));
    }

    public static int getUserType(HttpServletRequest request){
        return Integer.parseInt(String.valueOf(request.getSession().getAttribute("userType")));
    }
}
