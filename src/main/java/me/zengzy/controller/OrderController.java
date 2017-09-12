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
import java.text.DecimalFormat;
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
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    MessageTypeRepository messageTypeRepository;
    @Autowired
    MessageRepository messageRepository;

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

    @RequestMapping("/purOrderDetail")
    public String getPurOrderDetailView(HttpServletRequest request){
        if(SessionUtil.getUserType(request) == Type.User.PURCHASER){
            return "error";
        }
        return "order/purOrderDetail";
    }

    @RequestMapping("/viewHisProOrder")
    public String getViewHisProOrderView(HttpServletRequest request){
        if(SessionUtil.getUserType(request) == Type.User.PURCHASER){
            return "error";
        }
        return "order/viewHisProOrder";
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

    @RequestMapping("/showHisPurOrder")
    public String getShowHisPurOrderView(HttpServletRequest request){
        if(SessionUtil.getUserType(request) == Type.User.PROVIDER){
            return "error";
        }
        return "order/showHisPurOrder";
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

    @RequestMapping("/allContacts")
    public String getAllContactsView(){
        return "order/allContacts";
    }

    @RequestMapping("/allContract")
    public String getAllContractView(HttpServletRequest request){
        return "order/allContract";
    }

    @RequestMapping("/viewContract")
    public String getViewContractView(HttpServletRequest request){
        return "order/viewContract";
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
        String queryType;
        if(param.get("queryType").equals("current")){
            queryType = appendCurrentType();
        }
        else{
            queryType = appendHisType();
        }
        if(param.get("userType").equals("-1")){
            if(param.get("serialNo").equals("-1")){
                orders = ordersRepository.getByStatusSet(queryType, pageIndex*pageSize, pageSize);
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
                    orders = ordersRepository.getByCatAndNameAndStatusSet(queryType, param.get("mobileNo"), 0, pageIndex*pageSize, pageSize);
                    break;
                case Type.User.PROVIDER:
                    orders = ordersRepository.getByCatAndNameAndStatusSet(queryType, param.get("mobileNo"), 1, pageIndex*pageSize, pageSize);
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

    @RequestMapping("getContact.do")
    @ResponseBody
    public ArrayList<ContactsBean> getContact(@RequestParam() Map<String, String> param, HttpServletRequest request){
        ArrayList<Contacts> contacts;
        ArrayList<ContactsBean> beans = new ArrayList<ContactsBean>();
        int userType = SessionUtil.getUserType(request);
        String mobileNo = SessionUtil.getMobileNo(request);
        int pageIndex = getPageIndex(param) - 1;
        int pageSize = AdminOptUtil.getDftPageSize();
        if(userType == Type.User.PURCHASER){
            contacts = contactRepository.queryByPurMobNo(mobileNo, pageIndex*pageSize, pageSize);
        }
        else if(userType == Type.User.PROVIDER){
            contacts = contactRepository.queryByProMobNo(mobileNo, pageIndex*pageSize, pageSize);
        }
        else{
            contacts = contactRepository.queryAll(pageIndex*pageSize, pageSize);
        }
        for(Contacts a : contacts){
            ContactsBean bean = new ContactsBean();
            bean.setPageSize(pageSize);
            bean.setUserType(userType);
            bean.setCoop_count(a.getCoop_count());
            bean.setProvider_mobile_no(a.getProvider_mobile_no());
            bean.setPurchaser_mobile_no(a.getPurchaser_mobile_no());
            bean.setSerial_no(a.getSerial_no());

            beans.add(bean);
        }
        return beans;
    }

    @RequestMapping("getContactDetail.do")
    @ResponseBody
    public ArrayList<ContactDetailBean> getContactDetail(@RequestParam Map<String, String> param, HttpServletRequest request){
        String mobileNo = param.get("mobileNo");
        int userType = SessionUtil.getUserType(request);
        if(mobileNo == null){
            return null;
        }
        ArrayList<ContactDetailBean> beans = new ArrayList<ContactDetailBean>();
        String statusSet = String.valueOf(Status.Order.DONE) + "," + String.valueOf(Status.Order.SIGNED);
        if(userType == 1){
            ArrayList<ProOrders> orders = proOrderRepository.getByProviderNameAndStatus(mobileNo, statusSet);
            for(ProOrders a : orders){
                PurOrders foo = purOrderRepository.getPurOrderBySerialNo(a.getPur_serial_no());
                OrderTypes type = typeRepository.getTypeByNo(foo.getTypeNo());
                Contract contract = contractRepository.getByProSn(a.getPro_serial_no());
                ContactDetailBean bean = new ContactDetailBean();
                bean.setAddonUrl(addonsRepository.queryByOrderSerialNo(contract.getContract_serial_no()).get(0).getAddon_url());
                bean.setContractSn(contract.getContract_serial_no());
                bean.setDatetime(ordersRepository.getBySerialNo(a.getPro_serial_no()).getGen_date());
                bean.setOrderType(type.getType_content());
                bean.setOrderAmount(foo.getOrder_amount() + type.getType_unit());

                beans.add(bean);
            }
        }
        else if(userType == 2){
            ArrayList<PurOrders> orders = purOrderRepository.getPurOrderByNameAndStatus(mobileNo, statusSet);
            for(PurOrders a : orders){
                OrderTypes type = typeRepository.getTypeByNo(a.getTypeNo());
                Contract contract = contractRepository.getByPurSn(a.getPurSerialNo());
                ContactDetailBean bean = new ContactDetailBean();
                bean.setAddonUrl(addonsRepository.queryByOrderSerialNo(contract.getContract_serial_no()).get(0).getAddon_url());
                bean.setContractSn(contract.getContract_serial_no());
                bean.setDatetime(ordersRepository.getBySerialNo(a.getPurSerialNo()).getGen_date());
                bean.setOrderType(type.getType_content());
                bean.setOrderAmount(a.getOrder_amount() + type.getType_unit());

                beans.add(bean);
            }
        }
        return beans;
    }

    @RequestMapping("acceptContract.do")
    @ResponseBody
    public String acceptContract(@RequestParam() Map<String, String> param, HttpServletRequest request){
        if(param.get("contractSn") == null || SessionUtil.getUserType(request) != Type.User.PROVIDER){
            return "ERROR";
        }
        int contractSn = Integer.parseInt(param.get("contractSn"));
        String proMobileNo, purMobileNo;
        Contract contract = contractRepository.getByPrimaryKey(contractSn);
        ProOrders proOrder = proOrderRepository.getByProSerialNo(contract.getPro_serial_no());
        PurOrders purOrder = purOrderRepository.getPurOrderBySerialNo(contract.getPur_serial_no());
        //更改其他报价状态：未被接受
        ArrayList<ProOrders> orders = proOrderRepository.getByPurSerialNo(contract.getPur_serial_no());
        for(ProOrders a : orders){
            a.setOrder_status(Status.Order.UN_SIGNED);
            AllOrders b = ordersRepository.getBySerialNo(a.getPro_serial_no());
            b.setOrder_status(Status.Order.UN_SIGNED);
            ordersRepository.save(b);
            proOrderRepository.save(a);
        }
        //更改状态：已签
        proOrder.setOrder_status(Status.Order.SIGNED);
        purOrder.setOrderStatus(Status.Order.SIGNED);
        proMobileNo = proOrder.getProvider_name();
        purMobileNo = purOrder.getPurchaserName();
        proOrderRepository.save(proOrder);
        purOrderRepository.save(purOrder);
        AllOrders foo = ordersRepository.getBySerialNo(proOrder.getPro_serial_no());
        foo.setOrder_status(Status.Order.SIGNED);
        ordersRepository.save(foo);
        foo = ordersRepository.getBySerialNo(purOrder.getPurSerialNo());
        foo.setOrder_status(Status.Order.SIGNED);
        ordersRepository.save(foo);
        //add contact
        Contacts contact = contactRepository.queryByBothMobNo(purMobileNo, proMobileNo);
        if(contact != null){
            contact.setCoop_count(contact.getCoop_count() + 1);
            contactRepository.save(contact);
        }
        else{
            contact = new Contacts();
            contact.setCoop_count(1);
            contact.setProvider_mobile_no(proMobileNo);
            contact.setPurchaser_mobile_no(purMobileNo);
            contactRepository.save(contact);
        }
        return "success";
    }

    @RequestMapping("getTypeUnit.do")
    @ResponseBody
    public OrderTypes getTypeUnit(@RequestParam() Map<String, String> param){
        OrderTypes type = typeRepository.getTypeByNo(Integer.parseInt(param.get("type_no")));
        return type;
    }

    @RequestMapping("newAddon.do")
    @ResponseBody
    public String newAddon(@RequestParam() Map<String, String> param, HttpServletRequest request){
        SerialNoGen gen = new SerialNoGen();
        gen.setSerial_no(Integer.parseInt(param.get("serialNo")));
        fileUpload(param, gen, SessionUtil.getMobileNo(request), SessionUtil.getUserType(request));
        if(contractRepository.getByPrimaryKey(gen.getSerial_no()) != null){
            AllAddons allAddons = addonsRepository.queryByOrderSerialNo(gen.getSerial_no()).get(0);
            CloudFileUtil util = new CloudFileUtil(allAddons.getFile_key());
            util.deleteFile();
            addonsRepository.delete(allAddons);
        }
        return "success";
    }

    @RequestMapping("declineContract.do")
    @ResponseBody
    public String declineContract(@RequestParam() Map<String, String> param, HttpServletRequest request){
        if(param.get("contractSn") == null || SessionUtil.getUserType(request) != Type.User.PROVIDER){
            return "ERROR";
        }
        int contractSn = Integer.parseInt(param.get("contractSn"));
        Contract contract = contractRepository.getByPrimaryKey(contractSn);
        ProOrders proOrder = proOrderRepository.getByProSerialNo(contract.getPro_serial_no());
        PurOrders purOrder = purOrderRepository.getPurOrderBySerialNo(contract.getPur_serial_no());
        contractRepository.delete(contract);
        proOrder.setOrder_status(Status.Order.DECLINE_CONTRACT);
        purOrder.setOrderStatus(Status.Order.DECLINE_CONTRACT);
        proOrderRepository.save(proOrder);
        purOrderRepository.save(purOrder);
        AllOrders foo = ordersRepository.getBySerialNo(proOrder.getPro_serial_no());
        foo.setOrder_status(Status.Order.DECLINE_CONTRACT);
        ordersRepository.save(foo);
        foo = ordersRepository.getBySerialNo(purOrder.getPurSerialNo());
        foo.setOrder_status(Status.Order.DECLINE_CONTRACT);
        ordersRepository.save(foo);
        //delete addon(contract)
        AllAddons addon = addonsRepository.queryByOrderSerialNo(contractSn).get(0);
        CloudFileUtil fileUtil = new CloudFileUtil(addon.getFile_key());
        fileUtil.deleteFile();
        addonsRepository.delete(addon);
        int userType = Type.User.PURCHASER;
        String mobileNo = addon.getUploader_moble_no();
        Users user = userRepository.queryUserByPriKey(mobileNo, userType);
        user.setSpace_used(user.getSpace_used() - addon.getFile_size());
        userRepository.save(user);
        return "success";
    }

    @RequestMapping("getAllContract.do")
    @ResponseBody
    public ArrayList<ContractBean> getAllContract(@RequestParam() Map<String, String> param, HttpServletRequest request){
        ArrayList<ContractBean> beans = new ArrayList<ContractBean>();
        ArrayList<PurOrders> purOrders = new ArrayList<PurOrders>();
        ArrayList<ProOrders> proOrders = new ArrayList<ProOrders>();
        int pageSize = AdminOptUtil.getDftPageSize();
        int pageIndex = getPageIndex(param) - 1;
        int userType = SessionUtil.getUserType(request);
        String mobileNO = SessionUtil.getMobileNo(request);
        if(userType == 1){
            int queryStatus;
            String statusSet;
            if(param.get("purSerialNo") != null || !param.get("purSerialNo").equals("")){
                int sn = Integer.parseInt(param.get("purSerialNo"));
                purOrders.add(purOrderRepository.getPurOrderBySerialNo(sn));
            }
            else if(param.get("queryType").equals("current")){
                queryStatus = Status.Order.OFFERED_CONTRACT;
                purOrders = purOrderRepository.getPurOrderByNameAndStatus(mobileNO, queryStatus, pageIndex*pageSize, pageSize);
            }
            else{
                statusSet = String.valueOf(Status.Order.SIGNED) + "," + String.valueOf(Status.Order.DONE);
                purOrders = purOrderRepository.getPurOrderByNameAndStatus(mobileNO, statusSet, pageIndex*pageSize, pageSize);
            }
            for(PurOrders a : purOrders){
                ContractBean bean = new ContractBean();
                Contract contract = contractRepository.getByPurSn(a.getPurSerialNo());
                bean.setPageSize(pageSize);
                bean.setUserType(userType);
                if(a.getOrderStatus() == Status.Order.DONE){
                    bean.setStatus(1);
                }
                else{
                    bean.setStatus(0);
                }
                bean.setContractSn(contract.getContract_serial_no());
                bean.setPurOrdSn(contract.getPur_serial_no());
                bean.setProOrdSn(contract.getPro_serial_no());
                bean.setAddonUrl(addonsRepository.queryByOrderSerialNo(contract.getContract_serial_no()).get(0).getAddon_url());
                beans.add(bean);
            }
        }
        else if(userType == 2){
            int queryStatus;
            String statusSet;
            if(param.get("proSerialNo") != null || !param.get("proSerialNo").equals("")){
                int sn = Integer.parseInt(param.get("proSerialNo"));
                proOrders.add(proOrderRepository.getByProSerialNo(sn));
            }
            else if(param.get("queryType").equals("current")){
                queryStatus = Status.Order.OFFERED_CONTRACT;
                proOrders = proOrderRepository.getProOrderByNameAndStatus(mobileNO, queryStatus, pageIndex*pageSize, pageSize);
            }
            else if(param.get("queryType").equals("his")){
                statusSet = String.valueOf(Status.Order.SIGNED) + "," + String.valueOf(Status.Order.DONE);
                proOrders = proOrderRepository.getProOrderByNameAndStatus(mobileNO, statusSet, pageIndex*pageSize, pageSize);
            }
            for(ProOrders a : proOrders){
                ContractBean bean = new ContractBean();
                Contract contract = contractRepository.getByProSn(a.getPro_serial_no());
                bean.setPageSize(pageSize);
                bean.setUserType(userType);
                bean.setContractSn(contract.getContract_serial_no());
                bean.setPurOrdSn(contract.getPur_serial_no());
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
        ProOrders order = proOrderRepository.getByProSerialNo(Integer.parseInt(orderInfo.get("proSerialNo")));
        changeProOrdStatus(order, Status.Order.CONFIRM_SAMPLE);
        changePurOrdStatus(purOrderRepository.getPurOrderBySerialNo(order.getPur_serial_no()), Status.Order.EXAM_SAMPLE);
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
        int userType = Type.User.PURCHASER;
        String mobileNo = addon.getUploader_moble_no();
        Users user = userRepository.queryUserByPriKey(mobileNo, userType);
        user.setSpace_used(user.getSpace_used() - addon.getFile_size());
        userRepository.save(user);
        return "success";
    }

    @RequestMapping("finishOrder.do")
    @ResponseBody
    public String finishOrder(@RequestParam() Map<String, String> param, HttpServletRequest request){
        String temp = param.get("contractSn");
        if(temp == null){
            return "ERROR";
        }
        int contractSn = Integer.parseInt(temp);
        Contract contract = contractRepository.getByPrimaryKey(contractSn);
        ProOrders proOrder = proOrderRepository.getByProSerialNo(contract.getPro_serial_no());
        PurOrders purOrder = purOrderRepository.getPurOrderBySerialNo(contract.getPur_serial_no());
        proOrder.setOrder_status(Status.Order.DONE);
        purOrder.setOrderStatus(Status.Order.DONE);
        proOrder.setOrder_status(Status.Order.DONE);
        AllOrders foo = ordersRepository.getBySerialNo(proOrder.getPro_serial_no());
        foo.setOrder_status(Status.Order.DONE);
        ordersRepository.save(foo);
        foo = ordersRepository.getBySerialNo(purOrder.getPurSerialNo());
        foo.setOrder_status(Status.Order.DONE);
        ordersRepository.save(foo);
        purOrderRepository.save(purOrder);
        proOrderRepository.save(proOrder);
        contract.setContract_status(1);
        contractRepository.save(contract);
        return "success";
    }

    @RequestMapping("showAddOn.do")
    @ResponseBody
    public ArrayList<AddonBean> showAddOn(@RequestParam() Map<String, String> orderInfo, HttpServletRequest request){
        ArrayList<AllAddons> allAddons;
        int userType = SessionUtil.getUserType(request);
        int privilege = 1;
        if(orderInfo.get("serialNo") == null || orderInfo.get("serialNo").equals("")){
            privilege = 0;
            allAddons = addonsRepository.queryByUploader(SessionUtil.getMobileNo(request));
        }
        else {
            int sn = Integer.parseInt(orderInfo.get("serialNo"));
            if(contractRepository.getByPrimaryKey(sn) != null){
                privilege = 0;
            }
            allAddons = addonsRepository.queryByOrderSerialNo(sn);
        }
        ArrayList<AddonBean> addonBeans = new ArrayList<AddonBean>();
        for(AllAddons a : allAddons){
            AddonBean bean = new AddonBean();
            bean.setAddonSerialNo(a.getAddon_serial_no());
            bean.setAddonUrl(a.getAddon_url());
            DecimalFormat df = new DecimalFormat("#0.00");
            double usedSpace = a.getFile_size();
            if(usedSpace / 1024 > 1) {
                bean.setFileSize(df.format(usedSpace / 1024) + " MB");
            }
            else{
                bean.setFileSize(df.format(usedSpace) + " KB");
            }
            bean.setFileKey(a.getFile_key());
            bean.setFileName(a.getFile_name());
            bean.setOrderSerialNo(a.getOrder_serial_no());
            bean.setUserType(userType);
            bean.setPrivilege(privilege);
            addonBeans.add(bean);
        }
        return addonBeans;
    }

    @RequestMapping("sendSample.do")
    @ResponseBody
    public void sendSample(@RequestParam() Map<String, String> orderInfo){
        ProOrders proOrder = proOrderRepository.getByProSerialNo(Integer.parseInt(orderInfo.get("proSerialNo")));
        proOrder.setExpress_no(orderInfo.get("expressNo"));
        changeProOrdStatus(proOrder, Status.Order.OFFERED_SAMPLE);
    }

    @RequestMapping("queryRequiredSample.do")
    @ResponseBody
    public ArrayList<ProOrderBean> queryRequiredSample(@RequestParam Map<String, String> param, HttpServletRequest request){
        ArrayList<ProOrders> orders = new ArrayList<ProOrders>();
        if(param.get("proSerialNo") == null || param.get("proSerialNo").equals("")) {
            orders = proOrderRepository.getProOrderByNameAndStatus(SessionUtil.getMobileNo(request), Status.Order.REQUIRE_SAMPLE);
        }
        else{
            orders.add(proOrderRepository.getByProSerialNo(Integer.parseInt(param.get("proSerialNo"))));
        }
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
        String queryType = param.get("queryType");
        String statusSet = "";
        PurOrders purOrder = purOrderRepository.getPurOrderBySerialNo(purSerialNo);
        ArrayList<ProOrders> proOrders;
        if(queryType.equals("查看所有报价")){
            if(purOrder.getExpect_price() == -1){
                proOrders = proOrderRepository.getByPurOrdSn(purSerialNo, pageIndex*pageSize, pageSize);
            }
            else{
                proOrders = proOrderRepository.getByPurOrdSerWithExp(purSerialNo, purOrder.getExpect_price(), pageIndex*pageSize, pageSize);
            }
        }
        else {
            if (queryType.equals("样品接收")) {
                statusSet += String.valueOf(Status.Order.OFFERED_SAMPLE) + "," + String.valueOf(Status.Order.REQUIRE_SAMPLE);
            } else if (queryType.equals("样品判断")) {
                statusSet += String.valueOf(Status.Order.CONFIRM_SAMPLE) + ",";
            } else if (queryType.equals("发送合同")) {
                statusSet += String.valueOf(Status.Order.ACC_SAMPLE) + "," + String.valueOf(Status.Order.DECLINE_CONTRACT) + ",";
            }
            if(purOrder.getExpect_price() == -1){
                proOrders = proOrderRepository.getByPurOrdSn(purSerialNo, statusSet, pageIndex*pageSize, pageSize);
            }
            else{
                proOrders = proOrderRepository.getByPurOrdSerWithExp(purSerialNo, purOrder.getExpect_price(), statusSet, pageIndex*pageSize, pageSize);
            }
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
    public ArrayList<ProOrderBean> viewProOrder(@RequestParam Map<String, String> param, HttpServletRequest request){
        int pageIndex = getPageIndex(param) - 1;
        int pageSize = AdminOptUtil.getDftPageSize();
        String statusSet;
        if(param.get("queryType").equals("current")){
            statusSet = appendProCurrentType();
        }
        else{
            statusSet = appendProHisType();
        }
        ArrayList<ProOrders> proOrders = proOrderRepository.getByProviderNameAndStatus(SessionUtil.getMobileNo(request), statusSet, pageIndex*pageSize, pageSize);
        return packProOrderBeans(proOrders);
    }

    @RequestMapping("/showPurOrders.do")
    @ResponseBody
    public ArrayList<PurOrderBean> getPurOrders(@RequestParam Map<String, String> param, HttpServletRequest request){
        ArrayList<PurOrders> orders;
        int pageIndex = getPageIndex(param) - 1;
        int pageSize = AdminOptUtil.getDftPageSize();
        if(param.get("queryType").equals("his")){
            String statusSet = appendPurHisType();
            orders = purOrderRepository.getPurOrderByNameAndStatus(SessionUtil.getMobileNo(request), statusSet, pageIndex*pageSize, pageSize);
        }
        else{
            String statusSet = appendPurCurrentType();
            orders = purOrderRepository.getPurOrderByNameAndStatus(SessionUtil.getMobileNo(request), statusSet, pageIndex*pageSize, pageSize);
        }
        ArrayList<PurOrderBean> beans = packPurOrderBeans(orders, request);
        for(PurOrderBean a : beans){
            if(a.getOrderStatusNo() == Status.Order.SIGNED || a.getOrderStatusNo() == Status.Order.DONE){
                int contractSn = contractRepository.getByPurSn(a.getPurSerialNo()).getContract_serial_no();
                a.setAddonUrl(addonsRepository.queryByOrderSerialNo(contractSn).get(0).getAddon_url());
            }
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

    @RequestMapping("/getOfferNum.do")
    @ResponseBody
    public int getOfferNum(@RequestParam Map<String, String> param){
        if(param.get("serialNo") == null){
            return 0;
        }
        int serialNo = Integer.parseInt(param.get("serialNo"));
        return proOrderRepository.getByPurSerialNo(serialNo).size();
    }

    @RequestMapping("/getMessage.do")
    @ResponseBody
    public ArrayList<Message> getMessage(HttpServletRequest request){
        int userType = SessionUtil.getUserType(request);
        String mobileNo = SessionUtil.getMobileNo(request);
        ArrayList<MessageType> messageTypes = messageTypeRepository.getByReceiver(userType);
        ArrayList<Message> messages;
        StringBuilder builder = new StringBuilder();
        for(MessageType a : messageTypes){
            String temp = String.valueOf(a.getType_no()) + ",";
            builder.append(temp);
        }
        messages = (messageRepository.getByPriKyeAndType(mobileNo, userType, builder.toString()));
        return messages;
    }

    @RequestMapping("/messageUpdate.do")
    @ResponseBody
    public void messageUpdate(HttpServletRequest request){
        int userType = SessionUtil.getUserType(request);
        String mobileNo = SessionUtil.getMobileNo(request);
        Message message;
        message = new Message();
        message.setMobile_no(mobileNo);
        message.setUser_type(userType);
        if(userType == Type.User.PURCHASER){
            ArrayList<PurOrders> purOrders;
            ArrayList<ProOrders> proOrders;
            //获取已报价订单
            purOrders = purOrderRepository.getPurOrderByNameAndStatus(mobileNo, Status.Order.OFFERED_PRICE);
            message.setMessage_type_no(5);
            if(purOrders != null) {
                message.setMessage_cnt(purOrders.size());
            }
            else{
                message.setMessage_cnt(0);
            }
            messageRepository.save(message);
            //获取待确认样品
            int cnt = 0;
            String statusSet = String.valueOf(Status.Order.REQUIRE_SAMPLE) + "," +
                    String.valueOf(Status.Order.OFFERED_SAMPLE) + "," +
                    String.valueOf(Status.Order.CONFIRM_SAMPLE);
            purOrders = purOrderRepository.getPurOrderByNameAndStatus(mobileNo, statusSet);
            for(PurOrders a : purOrders){
                proOrders = proOrderRepository.getByPurSerialNo(a.getPurSerialNo());
                for(ProOrders b : proOrders){
                    if(b.getOrder_status() == Status.Order.OFFERED_SAMPLE){
                        cnt++;
                    }
                }
            }
            message.setMessage_type_no(6);
            message.setMessage_cnt(cnt);
            messageRepository.save(message);
            //获取已签合同
            statusSet = String.valueOf(Status.Order.SIGNED);
            purOrders = purOrderRepository.getPurOrderByNameAndStatus(mobileNo, statusSet);
            message.setMessage_type_no(7);
            if(purOrders != null) {
                message.setMessage_cnt(purOrders.size());
            }
            else{
                message.setMessage_cnt(0);
            }
            messageRepository.save(message);
            //获取被拒合同
            purOrders = purOrderRepository.getPurOrderByNameAndStatus(mobileNo, String.valueOf(Status.Order.DECLINE_CONTRACT));
            message.setMessage_type_no(8);
            if(purOrders != null) {
                message.setMessage_cnt(purOrders.size());
            }
            else{
                message.setMessage_cnt(0);
            }
            messageRepository.save(message);
        }
        else if(userType == Type.User.PROVIDER){
            ArrayList<PurOrders> orders = new ArrayList<PurOrders>();
            ArrayList<ProOrders> proOrders;
            Providers provider = providerRepository.getProviderByMobileNo(mobileNo);
            List<String> types = Arrays.asList(provider.getProvide_type().split(","));
            UserAddress address = addressRepository.queryByPrimaryKey(mobileNo, userType);
            //获取未报价订单
            orders.addAll(purOrderRepository.getPurOrderByStatus(Status.Order.UN_REC));
            ArrayList<PurOrders> temp = purOrderRepository.getPurOrderByStatus(Status.Order.OFFERED_PRICE);
            for(PurOrders a : temp){
                if(proOrderRepository.getByPurSalNoAndName(a.getPurSerialNo(), mobileNo) == null){
                    orders.add(a);
                }
            }
            orders = filterByRegionAndType(orders, address, types);
            message.setMessage_type_no(1);
            message.setMessage_cnt(orders.size());
            messageRepository.save(message);
            //获取待寄送样品
            proOrders = proOrderRepository.getByProviderNameAndStatus(mobileNo, String.valueOf(Status.Order.REQUIRE_SAMPLE));
            message.setMessage_type_no(2);
            message.setMessage_cnt(proOrders.size());
            messageRepository.save(message);
            //获取已确认收到样品
            proOrders = proOrderRepository.getByProviderNameAndStatus(mobileNo, String.valueOf(Status.Order.CONFIRM_SAMPLE));
            message.setMessage_type_no(3);
            if(proOrders != null) {
                message.setMessage_cnt(proOrders.size());
            }
            else{
                message.setMessage_cnt(0);
            }
            messageRepository.save(message);
            //获取待签合同
            proOrders = proOrderRepository.getByProviderNameAndStatus(mobileNo, String.valueOf(Status.Order.OFFERED_CONTRACT));
            message.setMessage_type_no(4);
            if(proOrders != null) {
                message.setMessage_cnt(proOrders.size());
            }
            else{
                message.setMessage_cnt(0);
            }
            messageRepository.save(message);
        }
    }

    @RequestMapping("/showSpicStatusPurOrder.do")
    @ResponseBody
    public ArrayList<PurOrderBean> showUnRecOrder(@RequestParam Map<String, String> param, HttpServletRequest request){
        String mobileNo = SessionUtil.getMobileNo(request);
        int userType = SessionUtil.getUserType(request);
        ArrayList<PurOrders> orders = new ArrayList<PurOrders>();
        Providers provider = providerRepository.getProviderByMobileNo(SessionUtil.getMobileNo(request));
        List<String> types = Arrays.asList(provider.getProvide_type().split(","));
        UserAddress address = addressRepository.queryByPrimaryKey(SessionUtil.getMobileNo(request), SessionUtil.getUserType(request));
        if(param.get("queryType").equals("unOffer")) {
            orders.addAll(purOrderRepository.getPurOrderByStatus(Status.Order.UN_REC));
            ArrayList<PurOrders> temp = purOrderRepository.getPurOrderByStatus(Status.Order.OFFERED_PRICE);
            for(PurOrders a : temp){
                if(proOrderRepository.getByPurSalNoAndName(a.getPurSerialNo(), mobileNo) == null){
                    orders.add(a);
                }
            }
        }
        else if(param.get("queryType").equals("sendSample")){//requireSample
            orders = purOrderRepository.getPurOrderByStatus(Status.Order.REQUIRE_SAMPLE);
        }
        else if(param.get("queryType").equals("offered")){
            ArrayList<ProOrders> proOrders = proOrderRepository.getByProviderNameAndStatus(mobileNo, String.valueOf(Status.Order.OFFERED_PRICE));
            for(ProOrders a : proOrders){
                orders.add(purOrderRepository.getPurOrderBySerialNo(a.getPur_serial_no()));
            }
        }
        else if(param.get("queryType").equals("confirmedSample")){
            orders = purOrderRepository.getPurOrderByStatus(Status.Order.CONFIRM_SAMPLE);
        }
        orders = filterByRegionAndType(orders, address, types);
        if(orders == null) {
            return null;
        }
        return packPurOrderBeans(orders, request);
    }

    @RequestMapping("/messageCenter.do")
    @ResponseBody
    public int messageCenter(@RequestParam Map<String, String> param, HttpServletRequest request){
        String queryType = param.get("queryType");
        String mobileNo = SessionUtil.getMobileNo(request);
        String statusSet;
        if(queryType.equals("unsignedContract")){
            statusSet = String.valueOf(Status.Order.OFFERED_CONTRACT);
            return ordersRepository.getByStatusSetAndNameAndType(statusSet, mobileNo, 1).size();
        }
        else if(queryType.equals("confirmedSample")){
            statusSet = String.valueOf(Status.Order.CONFIRM_SAMPLE);
            return ordersRepository.getByStatusSetAndNameAndType(statusSet, mobileNo, 1).size();
        }
        else if(queryType.equals("unOffered")){
            statusSet = String.valueOf(Status.Order.UN_REC);
            return ordersRepository.getByStatusSetAndNameAndType(statusSet, mobileNo, 0).size();
        }
        else if(queryType.equals("requiredSample")){
            statusSet = String.valueOf(Status.Order.REQUIRE_SAMPLE);
            return ordersRepository.getByStatusSetAndNameAndType(statusSet, mobileNo, 0).size();
        }
        else if(queryType.equals("signedContract")){
            statusSet = String.valueOf(Status.Order.SIGNED);
            return ordersRepository.getByStatusSetAndNameAndType(statusSet, mobileNo, 0).size();
        }
        return 0;
    }

    @RequestMapping("/updatePurOrderStatus.do")
    @ResponseBody
    public String updatePurOrderStatus(@RequestParam Map<String, String> orderInfo){
        int orderStatus = Integer.parseInt(orderInfo.get("order_status"));
        int sn = Integer.parseInt(orderInfo.get("pur_serial_no"));
        purOrderRepository.updateOrderStatus(orderStatus, sn);
        if(orderStatus == Status.Order.CANCEL){
            proOrderRepository.delete(proOrderRepository.getByPurSerialNo(sn));
        }
        return "success";
    }

    @RequestMapping("/recOrder.do")
    @ResponseBody
    public String recOrder(@RequestParam Map<String, String> orderInfo){
        int serialNo = Integer.parseInt(orderInfo.get("pur_serial_no"));
        AllOrders order = ordersRepository.getBySerialNo(serialNo);
        PurOrders purOrder = purOrderRepository.getPurOrderBySerialNo(serialNo);
        order.setOrder_status(Status.Order.OFFERED_PRICE);
        order.setOrder_cat(Type.Order.PROVIDE_ORDER);
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
        Date date = new Date();
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        allOrder.setGen_date(sdf.format(date));
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
        PurOrders purOrder = purOrderRepository.getPurOrderBySerialNo(proOrder.getPur_serial_no());
        changeProOrdStatus(proOrder, Status.Order.REQUIRE_SAMPLE);
        changePurOrdStatus(purOrder, Status.Order.REQUIRE_SAMPLE);
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

    @RequestMapping("/getNewSignCnt.do")
    @ResponseBody
    public int getNewSignCnt(HttpServletRequest request){
        ArrayList<PurOrders> orders = purOrderRepository.getPurOrderByNameAndStatus(SessionUtil.getMobileNo(request), Status.Order.SIGNED);
        return orders.size();
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

    @RequestMapping("/queryRefPurOrder.do")
    @ResponseBody
    public ProOrderBean queryRefPurOrder(@RequestParam Map<String, String> param){
        if(param.get("proSerialNo") == null){
            return null;
        }
        int sn = Integer.parseInt(param.get("proSerialNo"));
        String queryType = param.get("queryType");
        ProOrders order = proOrderRepository.getByProSerialNo(sn);
        if(queryType.equals("查看当前需求")) {
            return packProOrderBean(order);
        }
        else if(queryType.equals("寄送样品") && order.getOrder_status()==Status.Order.REQUIRE_SAMPLE) {
            return packProOrderBean(proOrderRepository.getByProSerialNo(sn));
        }
        else if(queryType.equals("查阅合同") && order.getOrder_status()==Status.Order.OFFERED_CONTRACT) {
            return packProOrderBean(proOrderRepository.getByProSerialNo(sn));
        }
        return null;
    }

    @RequestMapping("/accSample.do")
    @ResponseBody
    public String accSample(@RequestParam Map<String, String> param){
        if(param.get("serialNo") == null){
            return null;
        }
        int sn = Integer.parseInt(param.get("serialNo"));
        changeProOrdStatus(proOrderRepository.getByProSerialNo(sn), Status.Order.ACC_SAMPLE);
        return "success";
    }

    @RequestMapping("/decSample.do")
    @ResponseBody
    public String decSample(@RequestParam Map<String, String> param){
        if(param.get("serialNo") == null){
            return null;
        }
        int sn = Integer.parseInt(param.get("serialNo"));
        changeProOrdStatus(proOrderRepository.getByProSerialNo(sn), Status.Order.DEC_SAMPLE);
        return "success";
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
        Date date = new Date();
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        allOrder.setGen_date(sdf.format(date));
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
        order.setExpire_date(param.get("expireDate"));
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

    private ProOrderBean packProOrderBean(ProOrders order){
        ProOrderBean bean = new ProOrderBean();
        PurOrders purOrder = purOrderRepository.getPurOrderBySerialNo(order.getPur_serial_no());
        OrderTypes type = typeRepository.getTypeByNo(purOrder.getTypeNo());
        AllOrders allOrders = ordersRepository.getBySerialNo(order.getPro_serial_no());
        Contract contract = contractRepository.getByProSn(order.getPro_serial_no());
        if(contract != null){
            bean.setContractSn(contract.getContract_serial_no());
        }
        bean.setPurUserName(userRepository.queryUserByPriKey(purOrderRepository.getPurOrderBySerialNo(order.getPur_serial_no()).getPurchaserName(), Type.User.PURCHASER).getUserName());
        bean.setProUserName(userRepository.queryUserByPriKey(order.getProvider_name(), Type.User.PROVIDER).getUserName());
        bean.setPurOrderStatus(Status.orderTranslate(purOrder.getOrderStatus()));
        bean.setProSerialNo(order.getPro_serial_no());
        bean.setOfferPrice("￥" + order.getOffer_price() + "元");
        bean.setProviderMobileNo(order.getProvider_name());
        bean.setPurSerialNo(purOrder.getPurSerialNo());
        bean.setOrderAmount(purOrder.getOrder_amount() + type.getType_unit());
        bean.setOrderType(type.getType_content());
        bean.setOrderTypeNo(type.getType_no());
        bean.setOrderStatus(Status.orderTranslate(order.getOrder_status()));
        bean.setDatetime(allOrders.getGen_date());
        bean.setPurDateTime(ordersRepository.getBySerialNo(purOrder.getPurSerialNo()).getGen_date());
        if(order.getExpress_no() != null){
            bean.setExpressNo(order.getExpress_no());
        }
        else{
            bean.setExpressNo("对方未寄送");
        }
        bean.setPurchaserMobileNo(purOrder.getPurchaserName());
        UserAddress address = addressRepository.queryByPrimaryKey(purOrder.getPurchaserName(), Type.User.PURCHASER);
        if(address != null){
            bean.setPurAddress(address.getProvince() + address.getCity() + address.getDist() + address.getDetail_address());
        }
        else{
            bean.setPurAddress("未提供，请联系采购商！");
        }
        bean.setPageSize(AdminOptUtil.getDftPageSize());

        return bean;
    }

    private ArrayList<ProOrderBean> packProOrderBeans(ArrayList<ProOrders> proOrders){
        ArrayList<ProOrderBean> beans = new ArrayList<ProOrderBean>();
        for(ProOrders order : proOrders){
            ProOrderBean bean = packProOrderBean(order);
            beans.add(bean);
        }
        return beans;
    }

    private PurOrderBean packPurOrderBean(PurOrders a, HttpServletRequest request){
        OrderTypes type = typeRepository.getTypeByNo(a.getTypeNo());
        ProOrders order = proOrderRepository.getByPurSalNoAndName(a.getPurSerialNo(), SessionUtil.getMobileNo(request));
        AllOrders allOrders = ordersRepository.getBySerialNo(a.getPurSerialNo());
        PurOrderBean bean = new PurOrderBean();
        ArrayList<AllAddons> addons = addonsRepository.queryByOrderSerialNo(a.getPurSerialNo());

        bean.setExpireDate(a.getExpire_date());
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
        bean.setDatetime(allOrders.getGen_date());
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

    private String appendPurHisType(){
        return String.valueOf(Status.Order.CANCEL) +  "," +
                String.valueOf(Status.Order.UN_SIGNED) +  "," +
                String.valueOf(Status.Order.DONE) +  "," +
                String.valueOf(Status.Order.SIGNED);
    }

    private String appendPurCurrentType(){
        return String.valueOf(Status.Order.UN_REC) + "," +
                String.valueOf(Status.Order.OFFERED_PRICE) + "," +
                String.valueOf(Status.Order.REQUIRE_SAMPLE) + "," +
                String.valueOf(Status.Order.EXAM_SAMPLE) + "," +
                String.valueOf(Status.Order.DECLINE_CONTRACT) + "," +
                String.valueOf(Status.Order.OFFERED_CONTRACT);
    }

    private String appendProHisType(){
        return String.valueOf(Status.Order.CANCEL) +  "," +
                String.valueOf(Status.Order.UN_SIGNED) +  "," +
                String.valueOf(Status.Order.DONE) +  "," +
                String.valueOf(Status.Order.SIGNED);
    }

    private String appendProCurrentType(){
        return String.valueOf(Status.Order.UN_REC) + "," +
                String.valueOf(Status.Order.OFFERED_PRICE) + "," +
                String.valueOf(Status.Order.REQUIRE_SAMPLE) + "," +
                String.valueOf(Status.Order.OFFERED_SAMPLE) + "," +
                String.valueOf(Status.Order.CONFIRM_SAMPLE) + "," +
                String.valueOf(Status.Order.DECLINE_CONTRACT) + "," +
                String.valueOf(Status.Order.ACC_SAMPLE) + "," +
                String.valueOf(Status.Order.DEC_SAMPLE) + "," +
                String.valueOf(Status.Order.OFFERED_CONTRACT);
    }

    private String appendHisType(){
        return String.valueOf(Status.Order.CANCEL) +  "," +
                String.valueOf(Status.Order.UN_SIGNED) +  "," +
                String.valueOf(Status.Order.DONE) +  "," +
                String.valueOf(Status.Order.SIGNED);
    }

    private String appendCurrentType(){
        return String.valueOf(Status.Order.UN_REC) + "," +
                String.valueOf(Status.Order.OFFERED_PRICE) + "," +
                String.valueOf(Status.Order.REQUIRE_SAMPLE) + "," +
                String.valueOf(Status.Order.OFFERED_SAMPLE) + "," +
                String.valueOf(Status.Order.CONFIRM_SAMPLE) + "," +
                String.valueOf(Status.Order.EXAM_SAMPLE) + "," +
                String.valueOf(Status.Order.DECLINE_CONTRACT) + "," +
                String.valueOf(Status.Order.ACC_SAMPLE) + "," +
                String.valueOf(Status.Order.DEC_SAMPLE) + "," +
                String.valueOf(Status.Order.OFFERED_CONTRACT);
    }

    private void fileUpload(Map<String, String> param, SerialNoGen gen, String mobileNo, int userType){
        try {
            JSONArray array = new JSONArray(param.get("hash"));
            double usedSpaceKB = 0;
            for (int i = 0; i < array.length(); i++) {
                String fileHash = array.getJSONObject(i).getString("hash");
                String fileName = array.getJSONObject(i).getString("name");
                double fileSizeKB = array.getJSONObject(i).getDouble("size");
                usedSpaceKB += fileSizeKB;
                //记录附件上传数据
                AllAddons addon = new AllAddons();
                addon.setOrder_serial_no(gen.getSerial_no());
                addon.setFile_key(fileHash);
                addon.setFile_name(fileName);
                addon.setFile_size(fileSizeKB);
                addon.setUploader_moble_no(mobileNo);
                addon.setAddon_url(ApiKey.Qiniu.baseUrl + fileHash + "?attname=" + fileName);
                addonsRepository.save(addon);
            }
            //记录用户上传空间用量
            userRepository.updateUsedSpace(mobileNo, userType, usedSpaceKB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeProOrdStatus(ProOrders a, int status){
        a.setOrder_status(status);
        AllOrders foo = ordersRepository.getBySerialNo(a.getPro_serial_no());
        foo.setOrder_status(status);
        ordersRepository.save(foo);
        proOrderRepository.save(a);
    }

    private void changePurOrdStatus(PurOrders a, int status){
        a.setOrderStatus(status);
        AllOrders foo = ordersRepository.getBySerialNo(a.getPurSerialNo());
        foo.setOrder_status(status);
        ordersRepository.save(foo);
        purOrderRepository.save(a);
    }

    private ArrayList<PurOrders> filterByRegionAndType(ArrayList<PurOrders> orders, UserAddress address, List<String> types){
        if(orders != null) {
            for (int i = 0; i < orders.size(); i++) {
                boolean flag = false;
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                Date today, expireDate;
                today = new Date();
                try {
                    expireDate = sdf.parse(orders.get(i).getExpire_date());
                    if(today.after(expireDate)){
                        flag = true;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(!flag) {
                    ArrayList<ProOrders> proOrders = proOrderRepository.getByPurSerialNo(orders.get(i).getPurSerialNo());
                    for (ProOrders a : proOrders) {
                        int status = a.getOrder_status();
                        if (status == Status.Order.REQUIRE_SAMPLE || status == Status.Order.OFFERED_SAMPLE || status == Status.Order.CONFIRM_SAMPLE || status == Status.Order.ACC_SAMPLE || status == Status.Order.DEC_SAMPLE || status == Status.Order.DECLINE_CONTRACT ) {
                            flag = true;
                            break;
                        }
                    }
                }
                if(!flag) {
                    ArrayList<FilterDict> filters = filterDictRepository.getByOrderSerialNo(orders.get(i).getPurSerialNo());
                    if (filters.size() > 0) {
                        for (FilterDict a : filters) {
                            if (a.getProvince().equals(address.getProvince())) {
                                if (a.getCity().equals(address.getCity())) {
                                    if (a.getDist().equals(address.getDist())) {
                                        flag = false;
                                        break;
                                    } else if (a.getDist().equals("null")) {
                                        flag = false;
                                    }
                                } else if (a.getCity().equals("null")) {
                                    flag = false;
                                }
                            } else if (a.getProvince().equals("null")) {
                                flag = false;
                            }
                        }
                    } else {
                        flag = false;
                    }
                }
                if (!types.contains(String.valueOf(orders.get(i).getTypeNo())) || flag) {
                    orders.remove(i);
                    i--;
                }
            }
        }
        return orders;
    }
}
