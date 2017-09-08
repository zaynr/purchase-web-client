package me.zengzy.controller;

import me.zengzy.dto.AccountInfoBean;
import me.zengzy.dict.Type;
import me.zengzy.dto.AllUserBean;
import me.zengzy.entity.*;
import me.zengzy.repo.*;
import me.zengzy.util.AdminOptUtil;
import me.zengzy.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
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

    @RequestMapping("/adminModify")
    public String getAdminModifyView(HttpServletRequest request){
        if(SessionUtil.getUserType(request) != Type.User.ADMINISTRATOR){
            return "error";
        }
        return "account/info";
    }

    @RequestMapping("/userManage")
    public String getUserManageView(HttpServletRequest request){
        if(SessionUtil.getUserType(request) != Type.User.ADMINISTRATOR){
            return "error";
        }
        return "account/adminConfig";
    }

    @RequestMapping("/queryAllUser.do")
    @ResponseBody
    public ArrayList<AllUserBean> queryAllUser(@RequestParam Map<String, String> param, HttpServletRequest request){
        if(SessionUtil.getUserType(request) != Type.User.ADMINISTRATOR){
            return null;
        }
        int pageIndex = getPageIndex(param) - 1;
        int pageSize = AdminOptUtil.getDftPageSize();
        ArrayList<AllUserBean> beans = new ArrayList<AllUserBean>();
        ArrayList<Users> users = new ArrayList<Users>();
        if(param.get("userType").equals("-1")){
            users = userRepository.queryAllUser(pageIndex*pageSize, pageSize);
        }
        else{
            int userType = Integer.parseInt(param.get("userType"));
            users.add(userRepository.queryUserByPriKey(param.get("mobileNo"), userType));
        }
        for(Users a : users){
            AllUserBean bean = new AllUserBean();
            bean.setMobileNo(a.getMobileNo());
            bean.setPageSize(pageSize);
            bean.setSpaceUsed(a.getSpace_used());
            bean.setUserType(a.getUserType());
            bean.setPwd(a.getPwd());
            beans.add(bean);
        }
        return beans;
    }

    @RequestMapping("/getInfo.do")
    @ResponseBody
    public AccountInfoBean getInfo(@RequestParam Map<String, String> param, HttpServletRequest request){
        int userType = SessionUtil.getUserType(request);
        String mobileNo = SessionUtil.getMobileNo(request);
        String pwd = SessionUtil.getUserPwd(request);
        if(userType == Type.User.ADMINISTRATOR && param.get("uType")!=null){
            userType = Integer.parseInt(param.get("uType"));
            mobileNo = param.get("mno");
            pwd = param.get("pwd");
        }
        AccountInfoBean bean = new AccountInfoBean();
        UserAddress address = addressRepository.queryByPrimaryKey(mobileNo, userType);
        bean.setMobileNo(mobileNo);
        bean.setPwd(pwd);
        if(SessionUtil.getUserType(request) == Type.User.ADMINISTRATOR && param.get("uType")!=null) {
            bean.setUserType("管理员编辑：" + Type.UserTranslate(userType));
        }
        else{
            bean.setUserType(Type.UserTranslate(userType));
        }
        String[] temp = {};
        if(userType == 1){
            Purchasers purchaser = purchasersRepository.getPurchaserByMobileNo(mobileNo);
            temp = purchaser.getPrefer_type().split(",");
        }
        else if(userType == 2){
            Providers provider = providerRepository.getProviderByMobileNo(mobileNo);
            temp = provider.getProvide_type().split(",");
        }
        bean.setAddress(address);
        ArrayList<OrderTypes> orderTypes = new ArrayList<OrderTypes>();
        for(String a : temp){
            orderTypes.add(orderTypeRepository.getTypeByNo(Integer.parseInt(a)));
        }
        bean.setProvideType(orderTypes);
        String userName = userRepository.queryUserByPriKey(mobileNo, userType).getUserName();
        if(userName != null){
            bean.setUserName(userName);
        }
        else{
            bean.setUserName("");
        }
        return bean;
    }

    @RequestMapping("/adminDeleteUser.do")
    @ResponseBody
    public String adminDeleteUser(@RequestParam Map<String, String> param, HttpServletRequest request){
        int userType = SessionUtil.getUserType(request);
        if(userType != Type.User.ADMINISTRATOR){
            return "ERROR";
        }
        String mobileNo = param.get("mobileNo");
        userType = Integer.parseInt(param.get("userType"));
        userRepository.delete(userRepository.queryUserByPriKey(mobileNo, userType));
        addressRepository.delete(addressRepository.queryByPrimaryKey(mobileNo, userType));
        if(userType == 1){
            purchasersRepository.delete(purchasersRepository.getPurchaserByMobileNo(mobileNo));
        }
        else if(userType == 2){
            providerRepository.delete(providerRepository.getProviderByMobileNo(mobileNo));
        }
        return "success";
    }

    @RequestMapping("/updateInfo.do")
    @ResponseBody
    public String updateInfo(@RequestParam Map<String, String> param, HttpServletRequest request){
        int userType = SessionUtil.getUserType(request);
        String mobileNo = SessionUtil.getMobileNo(request);
        if(userType == Type.User.ADMINISTRATOR){
            userType = Integer.parseInt(param.get("uType"));
            mobileNo = param.get("mno");
        }
        Users user = userRepository.queryUserByPriKey(param.get("mobileNo"), userType);
        if(user != null && !param.get("mobileNo").equals(mobileNo)){
            return "already_exist";
        }
        userRepository.delete(userRepository.queryUserByPriKey(mobileNo, userType));
        user = new Users();
        Providers provider = providerRepository.getProviderByMobileNo(mobileNo);
        Purchasers purchaser = purchasersRepository.getPurchaserByMobileNo(mobileNo);
        user.setUserType(userType);
        user.setPwd(param.get("password"));
        user.setMobileNo(param.get("mobileNo"));
        user.setUserName(param.get("userName"));
        if(userType == 1){
            purchasersRepository.delete(purchaser);
            purchaser.setMobile_no(param.get("mobileNo"));
            purchaser.setPrefer_type(param.get("provide_type"));
            purchasersRepository.save(purchaser);
        }
        else if(userType == 2){
            providerRepository.delete(provider);
            provider.setMobile_no(param.get("mobileNo"));
            provider.setProvide_type(param.get("provide_type"));
            providerRepository.save(provider);
        }
        userRepository.save(user);
        if(userType != Type.User.ADMINISTRATOR) {
            SessionUtil.setUserPwd(request, param.get("password"));
            SessionUtil.setMobileNo(request, param.get("mobileNo"));
        }

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

    private int getPageIndex(Map<String, String> param){
        int pageIndex = 1;
        String foo = param.get("pageIndex");
        if(foo != null){
            pageIndex = Integer.parseInt(foo);
        }
        return pageIndex;
    }
}
