package me.zengzy.controller;

import me.zengzy.bean.PurOrderBean;
import me.zengzy.dict.Status;
import me.zengzy.dto.OrderTypes;
import me.zengzy.dto.PurOrders;
import me.zengzy.repo.OrderTypeRepository;
import me.zengzy.repo.PurOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/order")
@Controller
public class OrderController {
    @Autowired
    PurOrderRepository purOrderRepository;
    @Autowired
    OrderTypeRepository typeRepository;

    @RequestMapping("/recOrder")
    public String getRecOrderView(){
        return "order/recOrder";
    }

    @RequestMapping("/placeOrder")
    public String getPlaceOrderView(){
        return "order/placeOrder";
    }

    @RequestMapping("/showPurOrders")
    public String getPurOrderView(){
        return "order/showPurOrders";
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

    public ArrayList<PurOrderBean> packPurOrderBean(ArrayList<PurOrders> orders){
        ArrayList<PurOrderBean> beans = new ArrayList<PurOrderBean>();
        for(PurOrders a : orders){
            String typeContent = typeRepository.getTypeByNo(a.getTypeNo()).getType_content();
            PurOrderBean bean = new PurOrderBean();
            bean.setTypeContent(typeContent);
            //todo：添加字典表
            bean.setOrderStatus(Status.orderTranslate(a.getOrderStatus()));
            bean.setPurchaserName(a.getPurchaserName());
            bean.setPurSerialNo(a.getPurSerialNo());
            bean.setOrderStatusNo(a.getOrderStatus());

            beans.add(bean);
        }
        return beans;
    }

    @RequestMapping("/showPurOrders.do")
    @ResponseBody
    public ArrayList<PurOrderBean> getPurOrders(HttpServletRequest request){
        String userName = String.valueOf(request.getSession().getAttribute("userName"));
        ArrayList<PurOrders> orders = purOrderRepository.getOrderByName(userName);
        return packPurOrderBean(orders);
    }

    @RequestMapping("/addOrderType.do")
    @ResponseBody
    public String addOrderType(@RequestParam Map<String, String> orderInfo){
        typeRepository.addNewType(orderInfo.get("orderType"));
        return "success";
    }

    @RequestMapping("/showOrderType.do")
    @ResponseBody
    public ArrayList<OrderTypes> showOrderTypes(){
        return typeRepository.getAllTypes();
    }

    @RequestMapping("/showUnRecOrder.do")
    @ResponseBody
    public ArrayList<PurOrderBean> showUnRecOrder(){
        ArrayList<PurOrders> orders = purOrderRepository.getAllUnRecOrder();
        return packPurOrderBean(orders);
    }

    @RequestMapping("/placeOrder.do")
    @ResponseBody
    public String placeOrder(@RequestParam() Map<String, String> orderInfo, HttpServletRequest request){
        HttpSession session = request.getSession();
        PurOrders order = new PurOrders();
        order.setOrderStatus(Status.Order.UN_REC);
        order.setPurchaserName(String.valueOf(session.getAttribute("userName")));
        order.setTypeNo(Integer.parseInt(orderInfo.get("type_no")));
        purOrderRepository.save(order);
        return "success";
    }

}
