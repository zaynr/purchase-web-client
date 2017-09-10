var map = {};

$(document).ready(function () {
    $.ajax({
        type: "POST",
        url: "/check-auth.do",
        success: function (data) {
            if(data === "unAuth"){
                $("#navbarButton").append(
                    "<a class=\"btn btn-primary\" href=\"/login/user-login\" role=\"button\">登录</a>"
                );
            }
            else{
                $("#navbarButton").append(
                    "<a id=\"accountCenter\" class=\"btn btn-primary\"  href=\"/account/info\">"
                    + "账户中心"
                    + "</a><button id=\"logout\" class=\"btn btn-danger\">"
                    + "注销"
                    + "</button>"
                );
                $.ajax({
                    type: "POST",
                    url: "/order/messageUpdate.do"
                });
                if(data === "0"){
                    addTab("/order/addOrderType", "配置中心");
                    addTab("/order/adminGetAll", "订单管理");
                    // addTab("/order/allContract", "合同管理");
                    addTab("/account/userManage", "用户管理");
                }
                else if(data === "1"){
                    addTab("/order/placeOrder", "发布需求");
                    addTab("/order/showPurOrders", "查看订单");
                    addTabWithBadge("/order/confirmSample", "接收样品", "confirmSample");
                    addTabWithBadge("/order/allContract", "查看合同", "allContract");
                    addTab("/order/allContacts", "查看联系人");

                    function queryPurInfo() {
                        $.ajax({
                            type: "POST",
                            url: "/order/getMessage.do",
                            success: function (data) {
                                $.each(data, function (i, item) {
                                    switch(item.message_type_no){
                                        case 5:
                                            break;
                                        case 6:
                                            if(item.message_cnt === 0){
                                                $("#confirmSample").text("");
                                            }
                                            else{
                                                $("#confirmSample").text(item.message_cnt);
                                            }
                                            break;
                                        case 7:
                                            if(item.message_cnt === 0){
                                                $("#allContract").text("");
                                            }
                                            else{
                                                $("#allContract").text(item.message_cnt);
                                            }
                                            break;
                                    }
                                });
                            }
                        });
                    }

                    queryPurInfo();
                    setInterval(function () {
                        queryPurInfo();
                    }, 3000);
                }
                else if(data === "2"){
                    addTabWithBadge("/order/recOrder", "提供报价", "unOffer");
                    addTabWithBadge("/order/sendSample", "寄送样品", "sendSample");
                    addTabWithBadge("/order/viewProOrder", "查看订单", "viewProOrder");
                    addTabWithBadge("/order/allContract", "查看合同", "allContract");
                    addTab("/order/allContacts", "查看联系人");

                    function queryProInfo() {
                        $.ajax({
                            type: "POST",
                            url: "/order/getMessage.do",
                            success: function (data) {
                                $.each(data, function (i, item) {
                                    switch(item.message_type_no){
                                        case 1:
                                            if(item.message_cnt === 0){
                                                $("#unOffer").text("");
                                            }
                                            else{
                                                $("#unOffer").text(item.message_cnt);
                                            }
                                            break;
                                        case 2:
                                            if(item.message_cnt === 0){
                                                $("#sendSample").text("");
                                            }
                                            else{
                                                $("#sendSample").text(item.message_cnt);
                                            }
                                            break;
                                        case 3:
                                            if(item.message_cnt === 0){
                                                $("#viewProOrder").text("");
                                            }
                                            else{
                                                $("#viewProOrder").text(item.message_cnt);
                                            }
                                            break;
                                        case 4:
                                            if(item.message_cnt === 0){
                                                $("#allContract").text("");
                                            }
                                            else{
                                                $("#allContract").text(item.message_cnt);
                                            }
                                            break;
                                    }
                                });
                            }
                        });
                    }

                    queryProInfo();
                    setInterval(function () {
                        queryProInfo();
                    }, 3000);
                }

                setInterval(function () {
                    $.ajax({
                        type: "POST",
                        url: "/order/messageUpdate.do"
                    });
                }, 10000);

                setInterval(function () {
                    $("#navTabs").find("li").each(function () {
                        if($(this).find("a").attr("href") === window.location.pathname){
                            $(this).addClass("active");
                        }
                    });
                }, 100);
            }

            $("#logout").on("click", function () {
                $.ajax({
                    type: "POST",
                    url: "/logout.do",
                    success: function () {
                        window.location.href="/";
                    }
                });
            });

        }
    });

    function addTab(href, content) {
        $("#navTabs").append(
            "<li><a href=\"" +
            href +
            "\">" +
            content +
            "</a></li>"
        );
    }

    function addTabWithBadge(href, content, badgeId) {
        $("#navTabs").append(
            "<li><a href=\"" +
            href +
            "\">" +
            content +
            "<span id='" +
            badgeId +
            "' class='badge'></span></a></li>"
        );
    }
});

function num(obj){
    obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
    obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字
    obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个, 清除多余的
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
}

function checkInputNull(){
    var flag = true;
    $("input").each(function (i, item) {
        if($.trim($(item).val()).length === 0 && $(item).attr("not-nec") !== "true"){
            errorMessage("请输入必要项目");
            flag = false;
        }
    });
    return flag;
}

function normalMessage(message) {
    $("#message").html(
        "<div class=\"alert alert-info alert-dismissable\">" +
        "<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>" +
        message +
        "</div>"
    );
}

function errorMessage(message) {
    $("#message").html(
        "<div class=\"alert alert-danger alert-dismissable\">" +
        "<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>" +
        message +
        "</div>"
    );
}

function successMessage(message) {
    $("#message").html(
        "<div class=\"alert alert-success alert-dismissable\">" +
        "<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>" +
        message +
        "</div>"
    );
}

function successAlert(message) {
    return "<div class=\"alert alert-success alert-dismissable\">" +
        "<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button><div class='my-alert' >" +
        message +
        "</div></div>";
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r !== null) return unescape(r[2]); return null; //返回参数值
}

// 验证手机号
function isPhoneNo(phone) {
    var pattern = /^1[0-9]{10}$/;
    return pattern.test(phone);
}
