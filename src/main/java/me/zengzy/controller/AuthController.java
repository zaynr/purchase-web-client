package me.zengzy.controller;

import me.zengzy.util.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AuthController {
    @RequestMapping("/")
    public String auth(){
        return "index";
    }

    @RequestMapping("/error")
    public String error(){
        return "error";
    }

    @RequestMapping("/check-auth.do")
    @ResponseBody
    public String checkAuth(HttpServletRequest request){
        String mobileNo = SessionUtil.getMobileNo(request);
        if(mobileNo.equals("null")){
            return "unAuth";
        }
        else{
            return String.valueOf(request.getSession().getAttribute("userType"));
        }
    }

    @RequestMapping("/logout.do")
    @ResponseBody
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "success";
    }
}
