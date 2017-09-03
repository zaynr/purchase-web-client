package me.zengzy.controller;

import me.zengzy.bean.PurOrderBean;
import me.zengzy.dict.Status;
import me.zengzy.dto.OrderTypes;
import me.zengzy.dto.ProOrders;
import me.zengzy.dto.PurOrders;
import me.zengzy.repo.OrderTypeRepository;
import me.zengzy.repo.ProOrderRepository;
import me.zengzy.repo.PurOrderRepository;
import me.zengzy.util.SessionUtil;
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
    @Autowired
    ProOrderRepository proOrderRepository;

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

    @RequestMapping("/showPurOrders.do")
    @ResponseBody
    public ArrayList<PurOrderBean> getPurOrders(HttpServletRequest request){
        ArrayList<PurOrders> orders = purOrderRepository.getOrderByName(SessionUtil.getUserName(request));
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

    @RequestMapping("/updatePurOrderStatus.do")
    @ResponseBody
    public String updatePurOrderStatus(@RequestParam Map<String, String> orderInfo){
        purOrderRepository.updateOrderStatus(Integer.parseInt(orderInfo.get("order_status")), Integer.parseInt(orderInfo.get("pur_serial_no")));
        return "success";
    }

    @RequestMapping("/recOrder.do")
    @ResponseBody
    public String recOrder(@RequestParam Map<String, String> orderInfo){
        purOrderRepository.recPurOrder(Integer.parseInt(orderInfo.get("pur_serial_no")));
        return "success";
    }

    @RequestMapping("/placeProOrder.do")
    @ResponseBody
    public String placeProOrder(@RequestParam Map<String, String> orderInfo, HttpServletRequest request){
        ProOrders order = new ProOrders();
        order.setOrder_status(Status.Order.OFFERED_PRICE);
        System.out.println(orderInfo.get("pur_serial_no"));
        order.setPur_serial_no(Integer.parseInt(orderInfo.get("pur_serial_no")));
        order.setProvider_name(SessionUtil.getUserName(request));
        order.setOffer_price(Double.parseDouble(orderInfo.get("offer_price")));
        proOrderRepository.save(order);
        return "success";
    }

    @RequestMapping("/updateProOrderPrice.do")
    @ResponseBody
    public String updateProOrderPrice(@RequestParam() Map<String, String> orderInfo, HttpServletRequest request){
        proOrderRepository.updateProOrderPrice(Integer.parseInt(orderInfo.get("pur_serial_no")), SessionUtil.getUserName(request), Double.parseDouble(orderInfo.get("offer_price")));
        return "success";
    }

    @RequestMapping("/placeOrder.do")
    @ResponseBody
    public String placeOrder(@RequestParam() Map<String, String> orderInfo, HttpServletRequest request){
        PurOrders order = new PurOrders();
        order.setOrderStatus(Status.Order.UN_REC);
        order.setPurchaserName(SessionUtil.getUserName(request));
        order.setTypeNo(Integer.parseInt(orderInfo.get("type_no")));
        purOrderRepository.save(order);
        return "success";
    }

    private ArrayList<PurOrderBean> packPurOrderBean(ArrayList<PurOrders> orders){
        ArrayList<PurOrderBean> beans = new ArrayList<PurOrderBean>();
        for(PurOrders a : orders){
            String typeContent = typeRepository.getTypeByNo(a.getTypeNo()).getType_content();
            ArrayList<ProOrders> proOrders = proOrderRepository.getByPurSerialNo(a.getPurSerialNo());
            PurOrderBean bean = new PurOrderBean();
            bean.setTypeContent(typeContent);
            //todo：添加字典表
            bean.setOrderStatus(Status.orderTranslate(a.getOrderStatus()));
            bean.setPurchaserName(a.getPurchaserName());
            bean.setPurSerialNo(a.getPurSerialNo());
            bean.setOrderStatusNo(a.getOrderStatus());
            if(proOrders.size() > 0) {
                bean.setProviderName(proOrders.get(0).getProvider_name());
            }
            else{
                bean.setProviderName("尚未接单");
            }

            beans.add(bean);
        }
        return beans;
    }

}
