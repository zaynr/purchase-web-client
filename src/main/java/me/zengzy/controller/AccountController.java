package me.zengzy.controller;

import me.zengzy.util.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/account")
public class AccountController {
    @RequestMapping("/info")
    public String getInfoView(){
        return "account/info";
    }

    @RequestMapping("/getInfo.do")
    @ResponseBody
    public void getInfo(HttpServletRequest request){
    }
}
