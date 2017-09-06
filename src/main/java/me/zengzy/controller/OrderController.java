package me.zengzy.controller;

import com.google.gson.JsonArray;
import com.qiniu.util.Auth;
import me.zengzy.dict.ApiKey;
import me.zengzy.dict.Type;
import me.zengzy.dto.ProOrderBean;
import me.zengzy.dto.PurOrderBean;
import me.zengzy.dict.Status;
import me.zengzy.entity.*;
import me.zengzy.repo.*;
import me.zengzy.util.SessionUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.util.*;
import java.util.List;

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
    @Autowired
    AllAddonsRepository addonsRepository;
    @Autowired
    AllOrdersRepository ordersRepository;
    @Autowired
    SerialNoGenRepository genRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserAddressRepository addressRepository;
    @Autowired
    FilterDictRepository filterDictRepository;

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

    @RequestMapping("/showAddOn")
    public String getShowAddOnView(){
        return "order/showAddOn";
    }

    @RequestMapping("/recOrder")
    public String getRecOrderView(){
        return "order/recOrder";
    }

    @RequestMapping("/placeOrder")
    public String getPlaceOrderView(){
        return "order/placeOrder";
    }


    @RequestMapping("/modifyPurOrder")
    public String getModifyPurOrderView(){
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

    @RequestMapping("showAddOn.do")
    @ResponseBody
    public ArrayList<AllAddons> showAddOn(@RequestParam() Map<String, String> orderInfo){
        return addonsRepository.queryByOrderSerialNo(Integer.parseInt(orderInfo.get("serialNo")));
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
        return packPurOrderBeans(orders, request);
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
    public ArrayList<OrderTypes> showOrderTypes(@RequestParam Map<String, String> param){
        String pattern = param.get("typeContent");
        if(pattern == null || pattern.trim().equals("")) {
            return typeRepository.getAllTypes();
        }
        else{
            return typeRepository.patternMatch(pattern);
        }
    }

    @RequestMapping("/getCurrentPurOrder.do")
    @ResponseBody
    public PurOrderBean getCurrentPurOrder(@RequestParam Map<String, String> map, HttpServletRequest request){
        String serialNo = map.get("serialNo");
        if(serialNo == null){
            return null;
        }
        return packPurOrderBean(purOrderRepository.getPurOrderBySerialNo(Integer.parseInt(serialNo)), request);
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
                ArrayList<FilterDict> filters = filterDictRepository.getByOrderSerialNo(orders.get(i).getPurSerialNo());
                UserAddress address = addressRepository.queryByPrimaryKey(SessionUtil.getMobileNo(request), SessionUtil.getUserType(request));
                boolean flag = true;
                if (filters.size() > 0) {
                    for (FilterDict a : filters) {
                        if (a.getProvince().equals(address.getProvince())) {
                            if (a.getCity().equals(address.getCity())) {
                                if (a.getDist().equals(address.getDist())) {
                                    flag = false;
                                    break;
                                }
                                else if(a.getDist().equals("null")){
                                    flag = false;
                                }
                            }
                            else if(a.getCity().equals("null")){
                                flag = false;
                            }
                        }
                        else if(a.getProvince().equals("null")){
                            flag = false;
                        }
                    }
                }
                else{
                    flag = false;
                }
                if (!types.contains(String.valueOf(orders.get(i).getTypeNo())) || flag) {
                    orders.remove(i);
                    i--;
                }
            }
        }
        return packPurOrderBeans(orders, request);
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
        int serialNo = Integer.parseInt(orderInfo.get("pur_serial_no"));
        AllOrders order = ordersRepository.getBySerialNo(serialNo);
        PurOrders purOrder = purOrderRepository.getPurOrderBySerialNo(serialNo);
        order.setOrder_status(Status.Order.OFFERED_PRICE);
        purOrder.setOrderStatus(Status.Order.OFFERED_PRICE);
        ordersRepository.save(order);
        purOrderRepository.save(purOrder);
        return "success";
    }

    @RequestMapping("/placeProOrder.do")
    @ResponseBody
    public String placeProOrder(@RequestParam Map<String, String> orderInfo, HttpServletRequest request){
        ProOrders order = new ProOrders();
        AllOrders allOrder = new AllOrders();
        SerialNoGen gen = getSerialNo();
        allOrder.setSerial_no(gen.getSerial_no());
        allOrder.setMobile_no(SessionUtil.getMobileNo(request));
        allOrder.setOrder_status(Status.Order.OFFERED_PRICE);
        ordersRepository.save(allOrder);
        allOrder.setOrder_cat(Type.Order.PROVIDE_ORDER);
        order.setPro_serial_no(gen.getSerial_no());
        order.setOrder_status(Status.Order.OFFERED_PRICE);
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

    @RequestMapping("/modifyPurOrder.do")
    @ResponseBody
    public String modifyPurOrder(){
        return "success";
    }

    @RequestMapping("/placeOrder.do")
    @ResponseBody
    public String placeOrder(@RequestParam Map<String, String> param, HttpServletRequest request){
        PurOrders order = new PurOrders();
        AllOrders allOrder = new AllOrders();
        SerialNoGen gen = getSerialNo();
        //向用户主表添加数据
        allOrder.setSerial_no(gen.getSerial_no());
        allOrder.setMobile_no(SessionUtil.getMobileNo(request));
        allOrder.setOrder_status(Status.Order.UN_REC);
        allOrder.setOrder_cat(Type.Order.PURCHASE_ORDER);
        ordersRepository.save(allOrder);
        //向需求表添加数据
        order.setPurSerialNo(gen.getSerial_no());
        order.setOrderStatus(Status.Order.UN_REC);
        order.setPurchaserName(SessionUtil.getMobileNo(request));
        if(param.get("type_no") == null){
            return "no_such_type";
        }
        order.setTypeNo(Integer.parseInt(param.get("type_no")));
        order.setOrder_amount(Double.parseDouble(param.get("orderAmount")));
        if (!param.get("expect").trim().equals("")) {
            order.setExpect_price(Double.parseDouble(param.get("expect")));
        } else {
            order.setExpect_price(-1);
        }
        if (!param.get("more_detail").trim().equals("")) {
            order.setMore_detail(param.get("more_detail"));
        } else {
            order.setMore_detail("未添加详细需求");
        }
        //文件上传数据
        if(param.get("hash") != null) {
            try {
                JSONArray array = new JSONArray(param.get("hash"));
                double usedSpaceMb = 0;
                for (int i = 0; i < array.length(); i++) {
                    String fileHash = array.getJSONObject(i).getString("hash");
                    String fileName = array.getJSONObject(i).getString("name");
                    String extension = array.getJSONObject(i).getString("extension");
                    double fileSizeMb = array.getJSONObject(i).getDouble("size");
                    usedSpaceMb += fileSizeMb;
                    //记录附件上传数据
                    AllAddons addon = new AllAddons();
                    addon.setOrder_serial_no(gen.getSerial_no());
                    addon.setFile_key(fileHash);
                    addon.setFile_name(fileName);
                    addon.setFile_size(fileSizeMb);
                    addon.setAddon_url(ApiKey.Qiniu.baseUrl + fileHash + "?attname=" + fileHash + "." + extension);
                    addonsRepository.save(addon);
                }
                //记录用户上传空间用量
                userRepository.updateUsedSpace(SessionUtil.getMobileNo(request), SessionUtil.getUserType(request), usedSpaceMb);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(param.get("filter") != null) {
            try {
                //设置采购商对供应商的地址过滤
                JSONArray array = new JSONArray(param.get("filter"));
                for (int i = 0; i < array.length(); i++) {
                    FilterDict dict = new FilterDict();
                    dict.setOrder_serial_no(gen.getSerial_no());
                    dict.setProvince(array.getJSONObject(i).getString("province"));
                    dict.setCity(array.getJSONObject(i).getString("city"));
                    dict.setDist(array.getJSONObject(i).getString("dist"));
                    filterDictRepository.save(dict);
                }
                ArrayList<FilterDict> filters = filterDictRepository.getByOrderSerialNo(gen.getSerial_no());
                StringBuilder builder = new StringBuilder();
                for(FilterDict a : filters){
                    String foo = a.getSerial_no() + ",";
                    builder.append(foo);
                }
                order.setFilter_dict(builder.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            UserAddress address = addressRepository.queryByPrimaryKey(purOrder.getPurchaserName(), Type.User.PROVIDER);
            if(address != null){
                bean.setPurAddress(address.getProvince() + address.getCity() + address.getDist() + address.getDetail_address());
            }
            else{
                bean.setPurAddress("未提供，请联系采购商！");
            }

            beans.add(bean);
        }
        return beans;
    }

    private PurOrderBean packPurOrderBean(PurOrders a, HttpServletRequest request){
        OrderTypes type = typeRepository.getTypeByNo(a.getTypeNo());
        ProOrders order = proOrderRepository.getByPurSalNoAndName(a.getPurSerialNo(), SessionUtil.getMobileNo(request));
        PurOrderBean bean = new PurOrderBean();
        ArrayList<AllAddons> addons = addonsRepository.queryByOrderSerialNo(a.getPurSerialNo());

        bean.setTypeContent(type.getType_content());
        //todo：添加字典表
        bean.setOrderStatus(Status.orderTranslate(a.getOrderStatus()));
        bean.setPurchaserName(a.getPurchaserName());
        bean.setPurSerialNo(a.getPurSerialNo());
        bean.setOrderStatusNo(a.getOrderStatus());
        ArrayList<ProOrders> proOrders = proOrderRepository.getByPurSerialNo(a.getPurSerialNo());
        bean.setProviderName(proOrders.size() + "人次");
        bean.setMoreDetail(a.getMore_detail());
        bean.setFilters(filterDictRepository.getByOrderSerialNo(a.getPurSerialNo()));
        if(order != null) {
            bean.setOfferedPrice(order.getOffer_price());
        }
        else{
            bean.setOfferedPrice(-1);
        }
        bean.setOrderAmount(a.getOrder_amount());
        bean.setTypeUnit(type.getType_unit());
        bean.setTypeNo(type.getType_no());
        bean.setExpectPrice(a.getExpect_price());
        if(addons.size() > 0){
            bean.setAddonNum(addons.size() + " 个附件");
        }
        else{
            bean.setAddonNum("未添加附件");
        }
        return bean;
    }

    private ArrayList<PurOrderBean> packPurOrderBeans(ArrayList<PurOrders> orders, HttpServletRequest request){
        ArrayList<PurOrderBean> beans = new ArrayList<PurOrderBean>();
        for(PurOrders a : orders){
            beans.add(packPurOrderBean(a, request));
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

    private SerialNoGen getSerialNo(){
        SerialNoGen gen = genRepository.getSerialNo();
        gen.setSerial_no(gen.getSerial_no() + 1);
        genRepository.save(gen);
        return gen;
    }

}
