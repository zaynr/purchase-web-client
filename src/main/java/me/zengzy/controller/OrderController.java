package me.zengzy.controller;

import me.zengzy.repo.OrderTypeRepository;
import me.zengzy.repo.PurOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RequestMapping("/order")
@Controller
public class OrderController {
    @Autowired
    PurOrderRepository purOrderRepository;
    @Autowired
    OrderTypeRepository typeRepository;

    @RequestMapping("/placeOrder")
    public String getOrderView(){
        return "order/placeOrder";
    }

    @RequestMapping("/addOrderType")
    public String getAddOrderTypeView(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(String.valueOf(session.getAttribute("userType")).equals("0")){
            return "order/addOrderType";
        }
        else{
            return "error";
        }
    }

    @RequestMapping("/addOrderType.do")
    @ResponseBody
    public String addOrderType(@RequestParam Map<String, String> orderInfo){
        typeRepository.addNewType(orderInfo.get("orderType"));
        return "success";
    }

    @RequestMapping("/place-order.do")
    @ResponseBody
    public String placeOrder(@RequestParam() Map<String, String> orderInfo){
        String status = null;
        return status;
    }

}
