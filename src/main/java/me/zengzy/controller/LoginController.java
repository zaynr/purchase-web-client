package me.zengzy.controller;

import me.zengzy.entity.OrderTypes;
import me.zengzy.entity.Users;
import me.zengzy.repo.OrderTypeRepository;
import me.zengzy.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    UserRepository repository;

    @Autowired
    OrderTypeRepository typeRepository;

    @RequestMapping("/register")
    public String getRegisterView(){
        return "login/register";
    }

    @RequestMapping("/admin-login")
    public String getAdminLoginView(){
        return "login/adminLogin";
    }

    @RequestMapping("/user-login")
    public String getUserLoginView(HttpServletRequest request){
        if(String.valueOf(request.getSession().getAttribute("mobileNo")).equals("null")){
            return "login/commonLogin";
        }
        else{
            return "index";
        }
    }

    @RequestMapping("/login.do")
    @ResponseBody
    public String userLogin(@RequestParam Map<String, String> userInfo, HttpServletRequest request){
        String status;
        Users user = repository.queryUserByPriKey(userInfo.get("mobile_no"), Integer.parseInt(userInfo.get("user_type")));
        if(user != null){
            if(user.getPwd().equals(userInfo.get("password"))){
                status = "log_success";
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(30*60);
//                session.setMaxInactiveInterval(10);
                session.setAttribute("mobileNo", userInfo.get("mobile_no"));
                session.setAttribute("userPwd", userInfo.get("password"));
                session.setAttribute("userType", userInfo.get("user_type"));
            }
            else{
                status = "pwd_wrong";
            }
        }
        else{
            status = "not_found";
        }
        return status;
    }

    @RequestMapping("/register.do")
    @ResponseBody
    public String userRegister(@RequestParam() Map<String, String> userInfo){
        String status;
        Users user = repository.queryUserByPriKey(userInfo.get("mobile_no"), Integer.parseInt(userInfo.get("user_type")));
        if(user != null){
            status = "already_exist";
        }
        else{
            user = new Users();
            user.setMobileNo(userInfo.get("mobile_no"));
            user.setUserType(Integer.parseInt(userInfo.get("user_type")));
            user.setPwd(userInfo.get("password"));
            repository.save(user);
            status = "reg_success";
        }
        return status;
    }


    @RequestMapping("/showOrderType.do")
    @ResponseBody
    public ArrayList<OrderTypes> showOrderTypes(){
        return typeRepository.getAllTypes();
    }
}
