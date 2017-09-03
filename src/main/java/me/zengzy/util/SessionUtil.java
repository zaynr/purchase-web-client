package me.zengzy.util;

import javax.servlet.http.HttpServletRequest;

public class SessionUtil {
    public static String getMobileNo(HttpServletRequest request){
        return String.valueOf(request.getSession().getAttribute("mobileNo"));
    }
    public static void setMobileNo(HttpServletRequest request, String mobileNo){
        request.getSession().setAttribute("mobileNo", mobileNo);
    }

    public static String getUserPwd(HttpServletRequest request){
        return String.valueOf(request.getSession().getAttribute("userPwd"));
    }
    public static void setUserPwd(HttpServletRequest request, String userPwd){
        request.getSession().setAttribute("userPwd", userPwd);
    }

    public static int getUserType(HttpServletRequest request){
        return Integer.parseInt(String.valueOf(request.getSession().getAttribute("userType")));
    }
    public static void setUserType(HttpServletRequest request, String userType){
        request.getSession().setAttribute("userType", userType);
    }
}
