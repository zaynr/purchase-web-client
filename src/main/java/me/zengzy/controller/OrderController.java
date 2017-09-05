package me.zengzy.controller;

import com.qiniu.util.Auth;
import me.zengzy.dict.ApiKey;
import me.zengzy.dto.ProOrderBean;
import me.zengzy.dto.PurOrderBean;
import me.zengzy.dict.Status;
import me.zengzy.entity.OrderTypes;
import me.zengzy.entity.ProOrders;
import me.zengzy.entity.Providers;
import me.zengzy.entity.PurOrders;
import me.zengzy.repo.*;
import me.zengzy.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RequestMapping("/order")
@Controller
public class OrderController {
    @Autowired
    PurOrderRepository purOrderRepository;
    @Autowired
    OrderTypeRepository typeRepository;
    @Autowired
    ProOrderRepository proOrderRepository;
    @Autowired
    ProviderRepository providerRepository;
    @Autowired
    PurchasersRepository purchasersRepository;

    @RequestMapping("/sendSample")
    public String getSendSampleView(){
        return "order/sendSample";
    }

    @RequestMapping("/viewProOrder")
    public String getViewProOrderView(){
        return "order/viewProOrder";
    }

    @RequestMapping("/viewAllOffer")
    public String getViewAllOfferView(){
        return "order/viewAllOffer";
    }

    @RequestMapping("/confirmSample")
    public String getConfirmSampleView(){
        return "order/confirmSample";
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

    @RequestMapping("confirmSample.do")
    @ResponseBody
    public void confirmSample(@RequestParam() Map<String, String> orderInfo){
        ChangeBothOrderStatus(orderInfo, Status.Order.CONFIRM_SAMPLE);
    }

    @RequestMapping("sendSample.do")
    @ResponseBody
    public void sendSample(@RequestParam() Map<String, String> orderInfo){
        ChangeBothOrderStatus(orderInfo, Status.Order.OFFERED_SAMPLE);
        ProOrders proOrder = proOrderRepository.getByProSerialNo(Integer.parseInt(orderInfo.get("proSerialNo")));
        proOrder.setExpress_no(orderInfo.get("expressNo"));
        proOrderRepository.save(proOrder);
    }

    @RequestMapping("queryRequiredSample.do")
    @ResponseBody
    public ArrayList<ProOrderBean> queryRequiredSample(HttpServletRequest request){
        ArrayList<ProOrders> orders = proOrderRepository.getProOrderByNameAndStatus(SessionUtil.getMobileNo(request), Status.Order.REQUIRE_SAMPLE);
        return packProOrderBean(orders);
    }

    @RequestMapping("querySentSample.do")
    @ResponseBody
    public ArrayList<ProOrderBean> querySentSample(HttpServletRequest request){
        ArrayList<PurOrders> orders = purOrderRepository.getPurOrderByNameAndStatus(SessionUtil.getMobileNo(request), Status.Order.OFFERED_SAMPLE);
        ArrayList<ProOrderBean> beans = new ArrayList<ProOrderBean>();
        for(PurOrders a : orders){
            beans.addAll(packProOrderBean(proOrderRepository.getByPurSerialNo(a.getPurSerialNo())));
        }
        return beans;
    }

    @RequestMapping("getAllOffer.do")
    @ResponseBody
    public ArrayList<ProOrderBean> getAllOffer(@RequestParam Map<String, String> param){
        int purSerialNo = Integer.parseInt(param.get("serialNo"));
        ArrayList<ProOrders> proOrders = proOrderRepository.getByPurSerialNo(purSerialNo);
        return packProOrderBean(proOrders);
    }

    @RequestMapping("viewProOrder.do")
    @ResponseBody
    public ArrayList<ProOrderBean> viewProOrder(HttpServletRequest request){
        ArrayList<ProOrders> proOrders = proOrderRepository.getByProviderName(SessionUtil.getMobileNo(request));
        return packProOrderBean(proOrders);
    }

    @RequestMapping("/showPurOrders.do")
    @ResponseBody
    public ArrayList<PurOrderBean> getPurOrders(@RequestParam Map<String, String> param, HttpServletRequest request){
        ArrayList<PurOrders> orders = purOrderRepository.getOrderByName(SessionUtil.getMobileNo(request));
        if(param.get("queryType").equals("his")){
            for(int i = 0; i < orders.size(); i++){
                if(orders.get(i).getOrderStatus() != Status.Order.DONE && orders.get(i).getOrderStatus() != Status.Order.CANCEL){
                    orders.remove(i);
                    i--;
                }
            }
        }
        else{
            for(int i = 0; i < orders.size(); i++){
                if(orders.get(i).getOrderStatus() == Status.Order.DONE || orders.get(i).getOrderStatus() == Status.Order.CANCEL){
                    orders.remove(i);
                    i--;
                }
            }
        }
        return packPurOrderBean(orders, request);
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

    @RequestMapping("/getUploadToken.do")
    @ResponseBody
    public String getUploadToken(){
        //genUploadKey
        Auth auth = Auth.create(ApiKey.Qiniu.AccessKey, ApiKey.Qiniu.SecretKey);
        return auth.uploadToken(ApiKey.Qiniu.BucketName);
    }

    @RequestMapping("/showSpicStatusPurOrder.do")
    @ResponseBody
    public ArrayList<PurOrderBean> showUnRecOrder(@RequestParam Map<String, String> param, HttpServletRequest request){
        ArrayList<PurOrders> orders = null;
        Providers provider = providerRepository.getProviderByMobileNo(SessionUtil.getMobileNo(request));
        List<String> types = Arrays.asList(provider.getProvide_type().split(","));
        if(param.get("queryType").equals("unOffer")) {
            orders = purOrderRepository.getPurOrderByStatus(Status.Order.UN_REC);
        }
        else if(param.get("queryType").equals("sendSample")){
            orders = purOrderRepository.getPurOrderByStatus(Status.Order.REQUIRE_SAMPLE);
        }
        else if(param.get("queryType").equals("offered")){
            orders = purOrderRepository.getPurOrderByStatus(Status.Order.OFFERED_PRICE);
        }
        if(orders != null) {
            for (int i = 0; i < orders.size(); i++) {
                if (!types.contains(String.valueOf(orders.get(i).getTypeNo()))) {
                    orders.remove(i);
                    i--;
                }
            }
        }
        return packPurOrderBean(orders, request);
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
        System.out.println("pur_serial_no:::" + orderInfo.get("pur_serial_no"));
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

    @RequestMapping("/requestSample.do")
    @ResponseBody
    public String requestSample(@RequestParam() Map<String, String> orderInfo){
        ProOrders proOrder = proOrderRepository.getByProSerialNo(Integer.parseInt(orderInfo.get("proSerialNo")));
        PurOrders purOrder = purOrderRepository.getPurOrderBySerialNo(Integer.parseInt(orderInfo.get("purSerialNo")));
        proOrder.setOrder_status(Status.Order.REQUIRE_SAMPLE);
        purOrder.setOrderStatus(Status.Order.REQUIRE_SAMPLE);
        proOrderRepository.save(proOrder);
        purOrderRepository.save(purOrder);
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
        if(!orderInfo.get("expect").trim().equals("")) {
            order.setExpect_price(Double.parseDouble(orderInfo.get("expect")));
        }
        else{
            order.setExpect_price(-1);
        }
        if(!orderInfo.get("hash").trim().equals("")) {
            order.setAddon_url(ApiKey.Qiniu.baseUrl + orderInfo.get("hash"));
        }
        else{
            order.setAddon_url("未上传附件");
        }
        if(!orderInfo.get("more_detail").trim().equals("")) {
            order.setMore_detail(orderInfo.get("more_detail"));
        }
        else{
            order.setAddon_url("未添加详细需求");
        }

        purOrderRepository.save(order);
        return "success";
    }

    private ArrayList<ProOrderBean> packProOrderBean(ArrayList<ProOrders> proOrders){
        ArrayList<ProOrderBean> beans = new ArrayList<ProOrderBean>();
        for(ProOrders order : proOrders){
            PurOrders purOrder = purOrderRepository.getPurOrderBySerialNo(order.getPur_serial_no());
            OrderTypes type = typeRepository.getTypeByNo(purOrder.getTypeNo());
            ProOrderBean bean = new ProOrderBean();
            bean.setProSerialNo(order.getPro_serial_no());
            bean.setOfferPrice("￥" + order.getOffer_price() + "元");
            bean.setProviderMobileNo(order.getProvider_name());
            bean.setPurSerialNo(purOrder.getPurSerialNo());
            bean.setOrderAmount(purOrder.getOrder_amount() + type.getType_unit());
            bean.setOrderType(type.getType_content());
            bean.setOrderTypeNo(type.getType_no());
            bean.setOrderStatus(Status.orderTranslate(order.getOrder_status()));
            if(order.getExpress_no() != null){
                bean.setExpressNo(order.getExpress_no());
            }
            else{
                bean.setExpressNo("对方未寄送");
            }
            bean.setPurchaserMobileNo(purOrder.getPurchaserName());
            String address = purchasersRepository.getPurchaserByMobileNo(purOrder.getPurchaserName()).getAddress();
            if(address != null){
                bean.setPurAddress(address);
            }
            else{
                bean.setPurAddress("未提供，请联系采购商！");
            }

            beans.add(bean);
        }
        return beans;
    }

    private ArrayList<PurOrderBean> packPurOrderBean(ArrayList<PurOrders> orders, HttpServletRequest request){
        ArrayList<PurOrderBean> beans = new ArrayList<PurOrderBean>();
        for(PurOrders a : orders){
            OrderTypes type = typeRepository.getTypeByNo(a.getTypeNo());
            ProOrders order = proOrderRepository.getByPurSalNoAndName(a.getPurSerialNo(), SessionUtil.getMobileNo(request));
            PurOrderBean bean = new PurOrderBean();

            bean.setTypeContent(type.getType_content());
            //todo：添加字典表
            bean.setOrderStatus(Status.orderTranslate(a.getOrderStatus()));
            bean.setPurchaserName(a.getPurchaserName());
            bean.setPurSerialNo(a.getPurSerialNo());
            bean.setOrderStatusNo(a.getOrderStatus());
            ArrayList<ProOrders> proOrders = proOrderRepository.getByPurSerialNo(a.getPurSerialNo());
            bean.setProviderName(proOrders.size() + "人次");
            if(order != null) {
                bean.setOfferedPrice("￥" + order.getOffer_price() + "元");
            }
            else{
                bean.setOfferedPrice("未报价");
            }
            bean.setOrderAmount(a.getOrder_amount() + type.getType_unit());
            if(a.getExpect_price() == -1){
                bean.setExpectPrice("采购商未展示");
            }
            else {
                bean.setExpectPrice("￥" + a.getExpect_price() + "元");
            }

            beans.add(bean);
        }
        return beans;
    }

    private void ChangeBothOrderStatus(Map<String, String> orderInfo, int status){
        ProOrders proOrder = proOrderRepository.getByProSerialNo(Integer.parseInt(orderInfo.get("proSerialNo")));
        PurOrders purOrder = purOrderRepository.getPurOrderBySerialNo(Integer.parseInt(orderInfo.get("purSerialNo")));
        proOrder.setOrder_status(status);
        purOrder.setOrderStatus(status);
        proOrderRepository.save(proOrder);
        purOrderRepository.save(purOrder);
    }

}
