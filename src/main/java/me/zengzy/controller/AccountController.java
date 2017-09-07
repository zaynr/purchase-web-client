package me.zengzy.controller;

import me.zengzy.dto.AccountInfoBean;
import me.zengzy.dict.Type;
import me.zengzy.entity.*;
import me.zengzy.repo.*;
import me.zengzy.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProviderRepository providerRepository;
    @Autowired
    PurchasersRepository purchasersRepository;
    @Autowired
    OrderTypeRepository orderTypeRepository;
    @Autowired
    UserAddressRepository addressRepository;

    @RequestMapping("/info")
    public String getInfoView(){
        return "account/info";
    }

    @RequestMapping("/getInfo.do")
    @ResponseBody
    public AccountInfoBean getInfo(HttpServletRequest request){
        AccountInfoBean bean = new AccountInfoBean();
        UserAddress address = addressRepository.queryByPrimaryKey(SessionUtil.getMobileNo(request), SessionUtil.getUserType(request));
        bean.setMobileNo(SessionUtil.getMobileNo(request));
        bean.setPwd(SessionUtil.getUserPwd(request));
        bean.setUserType(Type.UserTranslate(SessionUtil.getUserType(request)));
        String[] temp = {};
        if(SessionUtil.getUserType(request) == 1){
            Purchasers purchaser = purchasersRepository.getPurchaserByMobileNo(SessionUtil.getMobileNo(request));
            temp = purchaser.getPrefer_type().split(",");
        }
        else if(SessionUtil.getUserType(request) == 2){
            Providers provider = providerRepository.getProviderByMobileNo(SessionUtil.getMobileNo(request));
            temp = provider.getProvide_type().split(",");
        }
        bean.setAddress(address);
        ArrayList<OrderTypes> orderTypes = new ArrayList<OrderTypes>();
        for(String a : temp){
            orderTypes.add(orderTypeRepository.getTypeByNo(Integer.parseInt(a)));
        }
        bean.setProvideType(orderTypes);
        String userName = userRepository.queryUserByPriKey(SessionUtil.getMobileNo(request), SessionUtil.getUserType(request)).getUserName();
        if(userName != null){
            bean.setUserName(userName);
        }
        else{
            bean.setUserName("");
        }
        return bean;
    }

    @RequestMapping("/updateInfo.do")
    @ResponseBody
    public String updateInfo(@RequestParam Map<String, String> param, HttpServletRequest request){
        Users user = userRepository.queryUserByPriKey(param.get("mobileNo"), SessionUtil.getUserType(request));
        if(user != null && !param.get("mobileNo").equals(SessionUtil.getMobileNo(request))){
            return "already_exist";
        }
        userRepository.delete(userRepository.queryUserByPriKey(SessionUtil.getMobileNo(request), SessionUtil.getUserType(request)));
        user = new Users();
        Providers provider = providerRepository.getProviderByMobileNo(SessionUtil.getMobileNo(request));
        Purchasers purchaser = purchasersRepository.getPurchaserByMobileNo(SessionUtil.getMobileNo(request));
        user.setUserType(SessionUtil.getUserType(request));
        user.setPwd(param.get("password"));
        user.setMobileNo(param.get("mobileNo"));
        user.setUserName(param.get("userName"));
        if(SessionUtil.getUserType(request) == 1){
            purchasersRepository.delete(purchaser);
            purchaser.setMobile_no(param.get("mobileNo"));
            purchasersRepository.save(purchaser);
        }
        else if(SessionUtil.getUserType(request) == 2){
            providerRepository.delete(provider);
            provider.setMobile_no(param.get("mobileNo"));
            providerRepository.save(provider);
        }
        userRepository.save(user);
        SessionUtil.setUserPwd(request, param.get("password"));
        SessionUtil.setMobileNo(request, param.get("mobileNo"));

        UserAddress address = new UserAddress();
        address.setUser_type(user.getUserType());
        address.setMobile_no(user.getMobileNo());
        address.setProvince(param.get("province"));
        address.setCity(param.get("city"));
        address.setDist(param.get("dist"));
        address.setDetail_address(param.get("detail_address"));
        addressRepository.save(address);
        return "success";
    }
}
