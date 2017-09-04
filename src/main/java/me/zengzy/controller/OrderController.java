package me.zengzy.controller;

import me.zengzy.dto.PurOrderBean;
import me.zengzy.dict.Status;
import me.zengzy.entity.OrderTypes;
import me.zengzy.entity.ProOrders;
import me.zengzy.entity.PurOrders;
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
import java.util.Iterator;
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

    @RequestMapping("/viewAllOffer")
    public String getViewAllOfferView(@RequestParam Map<String, String> param, Map<String, String> map){
        String purSerialNo = param.get("serialNo");
        map.put("serialNo", purSerialNo);
        return "order/viewAllOffer";
    }

    @RequestMapping("/viewSample")
    public String getViewSampleView(){
        return "order/viewSample";
    }

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
    public ArrayList<PurOrderBean> getPurOrders(@RequestParam Map<String, String> param, HttpServletRequest request){
        ArrayList<PurOrders> orders = purOrderRepository.getOrderByName(SessionUtil.getMobileNo(request));
        if(param.get("queryType").equals("his")){
            for(int i = 0; i < orders.size(); i++){
                if(orders.get(i).getOrderStatus() != 5 && orders.get(i).getOrderStatus() != 6){
                    orders.remove(i);
                    i--;
                }
            }
        }
        else{
            for(int i = 0; i < orders.size(); i++){
                if(orders.get(i).getOrderStatus() == 5 || orders.get(i).getOrderStatus() == 6){
                    orders.remove(i);
                    i--;
                }
            }
        }
        return packPurOrderBean(orders);
    }

    @RequestMapping("/addOrderType.do")
    @ResponseBody
    public String addOrderType(@RequestParam Map<String, String> orderInfo){
        OrderTypes type = new OrderTypes();
        type.setType_content(orderInfo.get("orderType"));
        type.setType_unit(orderInfo.get("typeUnit"));
        typeRepository.save(type);
        return "success";
    }

    @RequestMapping("/showOrderType.do")
    @ResponseBody
    public ArrayList<OrderTypes> showOrderTypes(){
        return typeRepository.getAllTypes();
    }

    @RequestMapping("/showUnRecOrder.do")
    @ResponseBody
    public ArrayList<PurOrderBean> showUnRecOrder(@RequestParam Map<String, String> param){
        ArrayList<PurOrders> orders;
        if(param.get("queryType").equals("unOffer")) {
            orders = purOrderRepository.getPurOrderByStatus(0);
        }
        else {
            orders = purOrderRepository.getPurOrderByStatus(1);
        }
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
        order.setProvider_name(SessionUtil.getMobileNo(request));
        order.setOffer_price(Double.parseDouble(orderInfo.get("offer_price")));
        proOrderRepository.save(order);
        return "success";
    }

    @RequestMapping("/updateProOrderPrice.do")
    @ResponseBody
    public String updateProOrderPrice(@RequestParam() Map<String, String> orderInfo, HttpServletRequest request){
        proOrderRepository.updateProOrderPrice(Integer.parseInt(orderInfo.get("pur_serial_no")), SessionUtil.getMobileNo(request), Double.parseDouble(orderInfo.get("offer_price")));
        return "success";
    }

    @RequestMapping("/placeOrder.do")
    @ResponseBody
    public String placeOrder(@RequestParam() Map<String, String> orderInfo, HttpServletRequest request){
        PurOrders order = new PurOrders();
        order.setOrderStatus(Status.Order.UN_REC);
        order.setPurchaserName(SessionUtil.getMobileNo(request));
        order.setTypeNo(Integer.parseInt(orderInfo.get("type_no")));
        order.setOrder_amount(Double.parseDouble(orderInfo.get("orderAmount")));
        order.setExpect_price(Double.parseDouble(orderInfo.get("expect")));
        purOrderRepository.save(order);
        return "success";
    }

    private ArrayList<PurOrderBean> packPurOrderBean(ArrayList<PurOrders> orders){
        ArrayList<PurOrderBean> beans = new ArrayList<PurOrderBean>();
        for(PurOrders a : orders){
            OrderTypes type = typeRepository.getTypeByNo(a.getTypeNo());
            ArrayList<ProOrders> proOrders = proOrderRepository.getByPurSerialNo(a.getPurSerialNo());
            PurOrderBean bean = new PurOrderBean();
            bean.setTypeContent(type.getType_content());
            //todo：添加字典表
            bean.setOrderStatus(Status.orderTranslate(a.getOrderStatus()));
            bean.setPurchaserName(a.getPurchaserName());
            bean.setPurSerialNo(a.getPurSerialNo());
            bean.setOrderStatusNo(a.getOrderStatus());
            bean.setProviderName(proOrders.size() + "人次");
            bean.setOrderAmount(a.getOrder_amount() + type.getType_unit());
            bean.setExpectPrice("￥" + a.getExpect_price() + "元");

            beans.add(bean);
        }
        return beans;
    }

}
