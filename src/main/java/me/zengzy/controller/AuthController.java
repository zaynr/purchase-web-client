package me.zengzy.controller;

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
        String userName = String.valueOf(request.getSession().getAttribute("userName"));
        if(userName.equals("null")){
            return "unAuth";
        }
        else{
            return String.valueOf(request.getSession().getAttribute("userType"));
        }
    }

    @RequestMapping("/logout.do")
    @ResponseBody
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("userName", "null");
        session.setAttribute("userPwd", "null");
        session.setAttribute("userType", "null");
        return "success";
    }
}
