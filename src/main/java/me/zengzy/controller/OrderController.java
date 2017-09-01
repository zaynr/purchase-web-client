package me.zengzy.controller;

import me.zengzy.dto.PurOrders;
import me.zengzy.repo.PurOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequestMapping("/order")
@Controller
public class OrderController {
    @Autowired
    PurOrderRepository purOrderRepository;

    @RequestMapping("/get-order-view.do")
    public String getOrderView(@RequestParam() Map<String, String> orderInfo){
        String res = "orderManage";
        return res;
    }

    @RequestMapping("/place-order.do")
    @ResponseBody
    public String placeOrder(@RequestParam() Map<String, String> orderInfo){
        String status = null;

        return status;
    }

}
