package me.zengzy.util;

import javax.servlet.http.HttpServletRequest;

public class SessionUtil {
    public static String getUserName(HttpServletRequest request){
        return String.valueOf(request.getSession().getAttribute("userName"));
    }

    public static String getUserType(HttpServletRequest request){
        return String.valueOf(request.getSession().getAttribute("userType"));
    }
}
