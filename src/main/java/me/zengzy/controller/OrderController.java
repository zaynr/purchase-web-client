package me.zengzy.controller;

import com.google.gson.JsonArray;
import com.qiniu.util.Auth;
import me.zengzy.dict.ApiKey;
import me.zengzy.dict.Type;
import me.zengzy.dto.*;
import me.zengzy.dict.Status;
import me.zengzy.entity.*;
import me.zengzy.repo.*;
import me.zengzy.util.AdminOptUtil;
import me.zengzy.util.CloudFileUtil;
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
import java.text.SimpleDateFormat;
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
    @Autowired
    AdminOptionRepository adminOptionRepository;
    @Autowired
    ContractRepository contractRepository;

    ///////////////
    //管理员页面组
    ///////////////
    @RequestMapping("/modifyProOrder")
    public String getModifyProOrderView(HttpServletRequest request){
        if(SessionUtil.getUserType(request) != Type.User.ADMINISTRATOR){
            return "error";
        }
        return "order/adminConfProOrd";
    }

    @RequestMapping("/adminGetAll")
    public String getAdminGetAllView(HttpServletRequest request){
        if(SessionUtil.getUserType(request) != Type.User.ADMINISTRATOR){
            return "error";
        }
        return "order/adminGetAll";
    }

    @RequestMapping("/addOrderType")
    public String getAddOrderTypeView(HttpServletRequest request){
        if(SessionUtil.getUserType(request) != Type.User.ADMINISTRATOR){
            return "error";
        }
        return "order/addOrderType";
    }

    ///////////////
    //供应商页面组
    ///////////////
    @RequestMapping("/sendSample")
    public String getSendSampleView(HttpServletRequest request){
        if(SessionUtil.getUserType(request) == Type.User.PURCHASER){
            return "error";
        }
        return "order/sendSample";
    }

    @RequestMapping("/viewProOrder")
    public String getViewProOrderView(HttpServletRequest request){
        if(SessionUtil.getUserType(request) == Type.User.PURCHASER){
            return "error";
        }
        return "order/viewProOrder";
    }

    @RequestMapping("/recOrder")
    public String getRecOrderView(HttpServletRequest request){
        if(SessionUtil.getUserType(request) == Type.User.PURCHASER){
            return "error";
        }
        return "order/recOrder";
    }

    ///////////////
    //采购商页面组
    ///////////////

    @RequestMapping("/placeOrder")
    public String getPlaceOrderView(HttpServletRequest request){
        if(SessionUtil.getUserType(request) == Type.User.PROVIDER){
            return "error";
        }
        return "order/placeOrder";
    }

    @RequestMapping("/viewAllOffer")
    public String getViewAllOfferView(HttpServletRequest request){
        if(SessionUtil.getUserType(request) == Type.User.PROVIDER){
            return "error";
        }
        return "order/viewAllOffer";
    }

    @RequestMapping("/confirmSample")
    public String getConfirmSampleView(HttpServletRequest request){
        if(SessionUtil.getUserType(request) == Type.User.PROVIDER){
            return "error";
        }
        return "order/confirmSample";
    }

    @RequestMapping("/modifyPurOrder")
    public String getModifyPurOrderView(HttpServletRequest request){
        if(SessionUtil.getUserType(request) == Type.User.PROVIDER){
            return "error";
        }
        return "order/placeOrder";
    }

    @RequestMapping("/showPurOrders")
    public String getPurOrderView(HttpServletRequest request){
        if(SessionUtil.getUserType(request) == Type.User.PROVIDER){
            return "error";
        }
        return "order/showPurOrders";
    }

    ///////////////////////////////////
    //公共页面
    ///////////////////////////////////

    @RequestMapping("/showAddOn")
    public String getShowAddOnView(){
        return "order/showAddOn";
    }

    @RequestMapping("/allContract")
    public String getAllContractView(HttpServletRequest request){
        return "order/allContract";
    }

    ///////////////////////////////////
    //ACTIONS
    ///////////////////////////////////

    @RequestMapping("getAllOrder.do")
    @ResponseBody
    public ArrayList<AllOrderBean> confirmSample(@RequestParam Map<String, String> param, HttpServletRequest request){
        if(SessionUtil.getUserType(request) != 0){
            return null;
        }
        ArrayList<AllOrderBean> beans = new ArrayList<AllOrderBean>();
        ArrayList<AllOrders> orders = new ArrayList<AllOrders>();
        int pageIndex = getPageIndex(param) - 1;
        int pageSize = AdminOptUtil.getDftPageSize();
        if(param.get("userType").equals("-1")){
            if(param.get("serialNo").equals("-1")){
                orders = ordersRepository.getAll(pageIndex*pageSize, pageSize);
            }
            else{
                int sn = Integer.parseInt(param.get("serialNo"));
                orders.add(ordersRepository.getBySerialNo(sn));
            }
        }
        else{
            int userType = Integer.parseInt(param.get("userType"));
            switch (userType){
                case Type.User.PURCHASER:
                    orders = ordersRepository.getByPurName(param.get("mobileNo"), pageIndex*pageSize, pageSize);
                    break;
                case Type.User.PROVIDER:
                    orders = ordersRepository.getByProName(param.get("mobileNo"), pageIndex*pageSize, pageSize);
                    break;
            }
        }
        for(AllOrders a : orders){
            AllOrderBean bean = new AllOrderBean();
            bean.setSerialNo(a.getSerial_no());
            bean.setOrderType(Type.OrderTranslate(a.getOrder_cat()));
            bean.setOrderStatus(Status.orderTranslate(a.getOrder_status()));
            bean.setPageSize(pageSize);
            beans.add(bean);
        }
        return beans;
    }

    @RequestMapping("getAllContract.do")
    @ResponseBody
    public ArrayList<ContractBean> getAllContract(@RequestParam() Map<String, String> param, HttpServletRequest request){
        ArrayList<ContractBean> beans = new ArrayList<ContractBean>();
        ArrayList<PurOrders> purOrders;
        ArrayList<ProOrders> proOrders;
        int pageSize = AdminOptUtil.getDftPageSize();
        int pageIndex = getPageIndex(param) - 1;
        int userType = SessionUtil.getUserType(request);
        int queryStatus;
        String mobileNO = SessionUtil.getMobileNo(request);
        if(param.get("queryType").equals("current")){
            queryStatus = Status.Order.OFFERED_CONTRACT;
        }
        else{
            queryStatus = Status.Order.SIGNED;
        }
        if(userType == 1){
            purOrders = purOrderRepository.getPurOrderByNameAndStatus(mobileNO, queryStatus, pageIndex*pageSize, pageSize);
            for(PurOrders a : purOrders){
                ContractBean bean = new ContractBean();
                Contract contract = contractRepository.getByPurSn(a.getPurSerialNo());
                bean.setPageSize(pageSize);
                bean.setUserType(userType);
                bean.setContractSn(contract.getContract_serial_no());
                bean.setPurOrdSn(a.getPurSerialNo());
                bean.setProOrdSn(contract.getPro_serial_no());
                bean.setAddonUrl(addonsRepository.queryByOrderSerialNo(contract.getContract_serial_no()).get(0).getAddon_url());
                beans.add(bean);
            }
        }
        else if(userType == 2){
            proOrders = proOrderRepository.getProOrderByNameAndStatus(mobileNO, queryStatus, pageIndex*pageSize, pageSize);
            for(ProOrders a : proOrders){
                ContractBean bean = new ContractBean();
                Contract contract = contractRepository.getByProSn(a.getPro_serial_no());
                bean.setPageSize(pageSize);
                bean.setUserType(userType);
                bean.setContractSn(contract.getContract_serial_no());
                bean.setPurOrdSn(a.getPro_serial_no());
                bean.setProOrdSn(contract.getPro_serial_no());
                bean.setAddonUrl(addonsRepository.queryByOrderSerialNo(contract.getContract_serial_no()).get(0).getAddon_url());
                beans.add(bean);
            }
        }
        return beans;
    }

    @RequestMapping("confirmSample.do")
    @ResponseBody
    public void confirmSample(@RequestParam() Map<String, String> orderInfo){
        ChangeBothOrderStatus(orderInfo, Status.Order.CONFIRM_SAMPLE);
    }

    @RequestMapping("deleteAddon.do")
    @ResponseBody
    public String deleteAddon(@RequestParam() Map<String, String> param){
        if(param.get("addonSerial") == null){
            return "ERROR";
        }
        CloudFileUtil fileUtil = new CloudFileUtil(param.get("fileKey"));
        fileUtil.deleteFile();
        AllAddons addon = addonsRepository.queryByPrimaryKey(Integer.parseInt(param.get("addonSerial")));
        addonsRepository.delete(addon);
        return "success";
    }

    @RequestMapping("showAddOn.do")
    @ResponseBody
    public ArrayList<AddonBean> showAddOn(@RequestParam() Map<String, String> orderInfo, HttpServletRequest request){
        ArrayList<AllAddons> allAddons = addonsRepository.queryByOrderSerialNo(Integer.parseInt(orderInfo.get("serialNo")));
        ArrayList<AddonBean> addonBeans = new ArrayList<AddonBean>();
        for(AllAddons a : allAddons){
            AddonBean bean = new AddonBean();
            bean.setAddonSerialNo(a.getAddon_serial_no());
            bean.setAddonUrl(a.getAddon_url());
            bean.setFileKey(a.getFile_key());
            bean.setFileName(a.getFile_name());
            bean.setOrderSerialNo(a.getOrder_serial_no());
            bean.setUserType(SessionUtil.getUserType(request));
            addonBeans.add(bean);
        }
        return addonBeans;
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
        return packProOrderBeans(orders);
    }

    @RequestMapping("querySentSample.do")
    @ResponseBody
    public ArrayList<ProOrderBean> querySentSample(HttpServletRequest request){
        ArrayList<PurOrders> orders = purOrderRepository.getPurOrderByNameAndStatus(SessionUtil.getMobileNo(request), Status.Order.OFFERED_SAMPLE);
        ArrayList<ProOrderBean> beans = new ArrayList<ProOrderBean>();
        for(PurOrders a : orders){
            beans.addAll(packProOrderBeans(proOrderRepository.getByPurSerialNo(a.getPurSerialNo())));
        }
        return beans;
    }

    @RequestMapping("getAllOffer.do")
    @ResponseBody
    public ArrayList<ProOrderBean> getAllOffer(@RequestParam Map<String, String> param){
        int purSerialNo = Integer.parseInt(param.get("serialNo"));
        int pageIndex = getPageIndex(param) - 1;
        int pageSize = AdminOptUtil.getOfferPageSize();
        PurOrders purOrder = purOrderRepository.getPurOrderBySerialNo(purSerialNo);
        ArrayList<ProOrders> proOrders;
        if(purOrder.getExpect_price() == -1){
            proOrders = proOrderRepository.getByPurOrdSnIncre(purSerialNo, pageIndex*pageSize, pageSize);
        }
        else{
            proOrders = proOrderRepository.getByPurOrdSerWithExp(purSerialNo, purOrder.getExpect_price(), pageIndex*pageSize, pageSize);
        }
        ArrayList<ProOrderBean> beans = packProOrderBeans(proOrders);
        for(ProOrderBean a : beans){
            a.setPageIndex(pageIndex);
            a.setPageSize(AdminOptUtil.getOfferPageSize());
        }
        return beans;
    }

    @RequestMapping("viewProOrder.do")
    @ResponseBody
    public ArrayList<ProOrderBean> viewProOrder(HttpServletRequest request){
        ArrayList<ProOrders> proOrders = proOrderRepository.getByProviderName(SessionUtil.getMobileNo(request));
        return packProOrderBeans(proOrders);
    }

    @RequestMapping("/showPurOrders.do")
    @ResponseBody
    public ArrayList<PurOrderBean> getPurOrders(@RequestParam Map<String, String> param, HttpServletRequest request){
        ArrayList<PurOrders> orders;
        int pageIndex = getPageIndex(param) - 1;
        int pageSize = AdminOptUtil.getDftPageSize();
        if(param.get("queryType").equals("his")){
            String statusSet = String.valueOf(Status.Order.DONE) + "," + String.valueOf(Status.Order.CANCEL);
            orders = purOrderRepository.getPurOrderByNameAndStatus(SessionUtil.getMobileNo(request), statusSet, pageIndex*pageSize, pageSize);
        }
        else{
            String statusSet = String.valueOf(Status.Order.UN_REC) + "," +
                    String.valueOf(Status.Order.OFFERED_PRICE) + "," +
                    String.valueOf(Status.Order.REQUIRE_SAMPLE) + "," +
                    String.valueOf(Status.Order.OFFERED_SAMPLE) + "," +
                    String.valueOf(Status.Order.CONFIRM_SAMPLE) + "," +
                    String.valueOf(Status.Order.SIGNED);
            orders = purOrderRepository.getPurOrderByNameAndStatus(SessionUtil.getMobileNo(request), statusSet, pageIndex*pageSize, pageSize);
        }
        ArrayList<PurOrderBean> beans = packPurOrderBeans(orders, request);
        for(PurOrderBean a : beans){
            a.setPageIndex(pageIndex);
            a.setPageSize(pageSize);
        }
        return beans;
    }

    @RequestMapping("/addOrderType.do")
    @ResponseBody
    public String addOrderType(@RequestParam Map<String, String> orderInfo){
        OrderTypes type = typeRepository.getByContent(orderInfo.get("orderType"));
        if(type != null){
            type.setType_category(orderInfo.get("typeCategory"));
            type.setType_content(orderInfo.get("orderType"));
            type.setType_unit(orderInfo.get("typeUnit"));
            return "existed";
        }
        else {
            type = new OrderTypes();
            type.setType_category(orderInfo.get("typeCategory"));
            type.setType_content(orderInfo.get("orderType"));
            type.setType_unit(orderInfo.get("typeUnit"));
            typeRepository.save(type);
        }
        return "success";
    }

    @RequestMapping("/delOrderType.do")
    @ResponseBody
    public String delOrderType(@RequestParam Map<String, String> orderInfo){
        OrderTypes type = typeRepository.getByContent(orderInfo.get("orderType"));
        if(type != null){
            typeRepository.delete(type);
            return "success";
        }
        return "not_exist";
    }

    @RequestMapping("/showOrdTypeGrpByCat.do")
    @ResponseBody
    public ArrayList<OrderCategoryBean> showOrdTypeGrpByCat(){
        ArrayList<String> categories = typeRepository.getAllCategory();
        ArrayList<OrderCategoryBean> beans = new ArrayList<OrderCategoryBean>();
        for(String a : categories){
            OrderCategoryBean bean = new OrderCategoryBean();
            bean.setName(a);
            ArrayList<OrderTypeBean> foo = new ArrayList<OrderTypeBean>();
            for(OrderTypes b : typeRepository.getByCategory(a)){
                OrderTypeBean c = new OrderTypeBean();
                c.setName(b.getType_content());
                c.setTypeCategory(b.getType_category());
                c.setTypeNo(b.getType_no());
                c.setTypeUnit(b.getType_unit());
                foo.add(c);
            }
            bean.setTypes(foo);
            beans.add(bean);
        }
        return beans;
    }

    @RequestMapping("/showOrderType.do")
    @ResponseBody
    public ArrayList<OrderTypes> showOrderTypes(@RequestParam Map<String, String> param){
        String pattern = param.get("typeContent");
        if(pattern == null || pattern.trim().equals("")) {
            return typeRepository.getLimitedTypes();
        }
        else{
            return typeRepository.patternMatch("%"+pattern+"%");
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
        else if(param.get("queryType").equals("sendSample")){//requireSample
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

    @RequestMapping("/updateAdminOption.do")
    @ResponseBody
    public String updateAdminOption(@RequestParam Map<String, String> param){
        if(param.get("offerPageSize") != null){
            AdminOption option = adminOptionRepository.queryByPrimaryKey(1);
            option.setOption_content(param.get("offerPageSize"));
            adminOptionRepository.save(option);
        }
        return "SUCCESS";
    }

    @RequestMapping("/adminDeleteOrder.do")
    @ResponseBody
    public String adminDeleteOrder(@RequestParam Map<String, String> param, HttpServletRequest request){
        if(SessionUtil.getUserType(request) != Type.User.ADMINISTRATOR){
            return "ERROR";
        }
        int sn = Integer.parseInt(param.get("serialNo"));
        ordersRepository.delete(ordersRepository.getBySerialNo(sn));
        if(param.get("orderType").equals("采购需求")){
            filterDictRepository.delete(filterDictRepository.getByOrderSerialNo(sn));
            addonsRepository.delete(addonsRepository.queryByOrderSerialNo(sn));
            purOrderRepository.delete(purOrderRepository.getPurOrderBySerialNo(sn));
        }
        else if(param.get("orderType").equals("供应报价")){
            proOrderRepository.delete(proOrderRepository.getByProSerialNo(sn));
        }
        return "success";
    }

    @RequestMapping("/adminMdyProOrder.do")
    @ResponseBody
    public void adminMdyProOrder(@RequestParam Map<String, String> param, HttpServletRequest request){
        if(SessionUtil.getUserType(request) != Type.User.ADMINISTRATOR){
            return;
        }
        ProOrders order = proOrderRepository.getByProSerialNo(Integer.parseInt(param.get("proSerialNo")));
        if(!param.get("pro_mobile_no").equals("-1")){
            order.setProvider_name(param.get("pro_mobile_no"));
        }
        if(!param.get("express_no").equals("-1")){
            order.setExpress_no(param.get("express_no"));
        }
        if(!param.get("pur_serial_no").equals("-1")){
            order.setPur_serial_no(Integer.parseInt(param.get("pur_serial_no")));
        }
        if(!param.get("offer_price").equals("-1")){
            order.setOffer_price(Double.parseDouble(param.get("offer_price")));
        }
        proOrderRepository.save(order);
    }

    @RequestMapping("/getProNewCont.do")
    @ResponseBody
    public int getProNewCont(HttpServletRequest request){
        ArrayList<ProOrders> orders = proOrderRepository.getByProviderName(SessionUtil.getMobileNo(request));
        int cnt = 0;
        for(ProOrders a : orders){
            if(a.getOrder_status() == Status.Order.OFFERED_CONTRACT){
                cnt++;
            }
        }
        return cnt;
    }

    @RequestMapping("/getContractSn.do")
    @ResponseBody
    public int getContractSn(@RequestParam Map<String, String> param){
        if(param.get("pur_serial_no") == null){
            return -1;
        }
        Contract contract = contractRepository.getByPurSn(Integer.parseInt(param.get("pur_serial_no")));
        return contract.getContract_serial_no();
    }

    @RequestMapping("/genContract.do")
    @ResponseBody
    public String genContract(@RequestParam Map<String, String> param, HttpServletRequest request){
        if(param.get("hash")==null){
            return "ERROR";
        }
        SerialNoGen gen = getSerialNo();
        int purSerialNo = Integer.parseInt(param.get("purSerialNo"));
        int proSerialNo = Integer.parseInt(param.get("proSerialNo"));
        fileUpload(param, gen, SessionUtil.getMobileNo(request), SessionUtil.getUserType(request));
        Contract contract = new Contract();
        contract.setContract_serial_no(gen.getSerial_no());
        contract.setPro_serial_no(proSerialNo);
        contract.setPur_serial_no(purSerialNo);
        Date date = new Date();
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        contract.setSign_time(sdf.format(date));
        contractRepository.save(contract);

        //更改状态：未被接受
        ArrayList<ProOrders> orders = proOrderRepository.getByPurSerialNo(purSerialNo);
        for(ProOrders a : orders){
            a.setOrder_status(Status.Order.UN_SIGNED);
            AllOrders b = ordersRepository.getBySerialNo(a.getPro_serial_no());
            b.setOrder_status(Status.Order.UN_SIGNED);
            ordersRepository.save(b);
            proOrderRepository.save(a);
        }

        //更改状态：已提供合同
        AllOrders bar = ordersRepository.getBySerialNo(proSerialNo);
        ProOrders foo = proOrderRepository.getByProSerialNo(proSerialNo);
        foo.setOrder_status(Status.Order.OFFERED_CONTRACT);
        bar.setOrder_status(Status.Order.OFFERED_CONTRACT);
        proOrderRepository.save(foo);
        ordersRepository.save(bar);
        bar = ordersRepository.getBySerialNo(purSerialNo);
        PurOrders foobar = purOrderRepository.getPurOrderBySerialNo(purSerialNo);
        foobar.setOrderStatus(Status.Order.OFFERED_CONTRACT);
        bar.setOrder_status(Status.Order.OFFERED_CONTRACT);
        purOrderRepository.save(foobar);
        ordersRepository.save(bar);
        return "success";
    }

    @RequestMapping("/getPageSize.do")
    @ResponseBody
    public int getPageSize(){
        return AdminOptUtil.getOfferPageSize();
    }

    @RequestMapping("/getProOrder.do")
    @ResponseBody
    public ProOrders getProOrder(@RequestParam Map<String, String> param, HttpServletRequest request){
        if(SessionUtil.getUserType(request) != Type.User.ADMINISTRATOR){
            return null;
        }
        if(param.get("serialNo") != null) {
            int sn = Integer.parseInt(param.get("serialNo"));
            return proOrderRepository.getByProSerialNo(sn);
        }
        else{
            return null;
        }
    }

    @RequestMapping("/modifyPurOrder.do")
    @ResponseBody
    public String modifyPurOrder(@RequestParam Map<String, String> param, HttpServletRequest request){
        SerialNoGen gen = new SerialNoGen();
        gen.setSerial_no(Integer.parseInt(param.get("pur_serial_no")));
        PurOrders order = purOrderRepository.getPurOrderBySerialNo(gen.getSerial_no());
        String mobileNo = SessionUtil.getMobileNo(request);
        int userType = SessionUtil.getUserType(request);
        if(SessionUtil.getUserType(request) == 0){
            mobileNo = order.getPurchaserName();
            userType = 1;
        }
        filterDictRepository.delete(filterDictRepository.getByOrderSerialNo(gen.getSerial_no()));
        return updatePurOrderAndRelated(param, mobileNo, userType, gen, order);
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
        String mobileNo = SessionUtil.getMobileNo(request);
        int userType = SessionUtil.getUserType(request);
        return updatePurOrderAndRelated(param, mobileNo, userType, gen, order);
    }

    /*
    * 更新需求表（pur_orders） 及其关联表
    * 关联表
    *       地址过滤表（filter_dict）
    *       附件表（all_addons）
    * */
    private String updatePurOrderAndRelated(@RequestParam Map<String, String> param, String mobileNo, int userType, SerialNoGen gen, PurOrders order){
        //向需求表添加数据
        order.setPurSerialNo(gen.getSerial_no());
        order.setOrderStatus(Status.Order.UN_REC);
        order.setPurchaserName(mobileNo);
        if(param.get("type_no") == null){
            return "no_such_type";
        }
        order.setTypeNo(Integer.parseInt(param.get("type_no")));
        order.setOrder_amount(Double.parseDouble(param.get("orderAmount")));
        if(param.get("showExpect").equals("true")){
            order.setExpect_status(Status.Expect.HIDE);
        }
        else{
            order.setExpect_status(Status.Expect.SHOW);
        }
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
            fileUpload(param, gen, mobileNo, userType);
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

    private ArrayList<ProOrderBean> packProOrderBeans(ArrayList<ProOrders> proOrders){
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
        bean.setShowExpectPrice(a.getExpect_status());
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

    private int getPageIndex(Map<String, String> param){
        int pageIndex = 1;
        String foo = param.get("pageIndex");
        if(foo != null){
            pageIndex = Integer.parseInt(foo);
        }
        return pageIndex;
    }

    private void fileUpload(Map<String, String> param, SerialNoGen gen, String mobileNo, int userType){
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
                addon.setAddon_url(ApiKey.Qiniu.baseUrl + fileHash + "?attname=" + fileName);
                addonsRepository.save(addon);
            }
            //记录用户上传空间用量
            userRepository.updateUsedSpace(mobileNo, userType, usedSpaceMb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
