package me.zengzy.controller;

import me.zengzy.entity.*;
import me.zengzy.repo.*;
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
    ProviderRepository providerRepository;
    @Autowired
    PurchasersRepository purchasersRepository;
    @Autowired
    OrderTypeRepository typeRepository;
    @Autowired
    UserAddressRepository addressRepository;

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
        if(userInfo.get("user_type") == null){
            return "ERROR";
        }
        int userType = Integer.parseInt(userInfo.get("user_type"));
        Users user = repository.queryUserByPriKey(userInfo.get("mobile_no"), userType);
        if(user != null){
            if(user.getPwd().equals(userInfo.get("password"))){
                status = "log_success";
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(30*60);
//                session.setMaxInactiveInterval(10);
                session.setAttribute("mobileNo", userInfo.get("mobile_no"));
                session.setAttribute("userPwd", userInfo.get("password"));
                session.setAttribute("userType", userType);
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
        int userType = 1;
        if(userInfo.get("user_type") != null) {
            userType = Integer.parseInt(userInfo.get("user_type"));
        }
        else{
            return "ERROR";
        }
        if(repository.queryUserByPriKey(userInfo.get("mobile_no"), userType) != null){
            status = "already_exist";
        }
        else{
            Users user = new Users();
            user.setUserName(userInfo.get("user_name"));
            user.setMobileNo(userInfo.get("mobile_no"));
            user.setUserType(userType);
            user.setPwd(userInfo.get("password"));
            repository.save(user);
            UserAddress address = new UserAddress();
            address.setUser_type(user.getUserType());
            address.setMobile_no(user.getMobileNo());
            address.setProvince(userInfo.get("province"));
            address.setCity(userInfo.get("city"));
            address.setDist(userInfo.get("dist"));
            address.setDetail_address(userInfo.get("detail_address"));
            addressRepository.save(address);
            if(userType == 1){
                Purchasers purchaser = new Purchasers();
                purchaser.setMobile_no(userInfo.get("mobile_no"));
                purchaser.setPrefer_type(userInfo.get("provide_type"));
                purchasersRepository.save(purchaser);
            }
            else if(userType == 2){
                Providers provider = new Providers();
                provider.setMobile_no(userInfo.get("mobile_no"));
                provider.setProvide_type(userInfo.get("provide_type"));
                providerRepository.save(provider);
            }
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
