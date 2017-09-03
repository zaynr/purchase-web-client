package me.zengzy.controller;

import me.zengzy.dto.AccountInfoBean;
import me.zengzy.dict.Type;
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
    public AccountInfoBean getInfo(HttpServletRequest request){
        AccountInfoBean bean = new AccountInfoBean();
        bean.setMobileNo(SessionUtil.getMobileNo(request));
        bean.setPwd(SessionUtil.getUserPwd(request));
        bean.setUserType(Type.UserTranslate(SessionUtil.getUserType(request)));
        bean.setProvideType("null");
        return bean;
    }
}
