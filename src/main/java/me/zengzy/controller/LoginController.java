package me.zengzy.controller;

import me.zengzy.dto.Users;
import me.zengzy.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/users")
public class LoginController {
    @Autowired
    UserRepository repository;

    @RequestMapping("/login.do")
    @ResponseBody
    public String userLogin(@RequestParam() Map<String, String> userInfo){
        String status;
        Users user = repository.queryUserByMobileNo(userInfo.get("userInfo[mobile_no]"));
        if(user != null){
            if(user.getPwd().equals(userInfo.get("userInfo[password]")) && user.getUserType()==Integer.parseInt(userInfo.get("userInfo[user_type]"))){
                status = "log_success";
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
        Users user = repository.queryUserByMobileNo(userInfo.get("userInfo[mobile_no]"));
        if(user != null){
            status = "already_exist";
        }
        else{
            repository.createNewUser(userInfo.get("userInfo[mobile_no]"), userInfo.get("userInfo[password]"), Integer.parseInt(userInfo.get("userInfo[user_type]")));
            status = "reg_success";
        }
        return status;
    }
}
