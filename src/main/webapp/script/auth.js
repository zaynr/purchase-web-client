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
                if(data === "0"){
                    addTab("/order/addOrderType", "添加订单类型");
                    addTab("/order/allOrder", "订单管理");
                    addTab("/order/allContract", "合同管理");
                    addTab("/account/purchaser", "采购商管理");
                    addTab("/order/provider", "供应商管理");
                }
                else if(data === "1"){
                    addTab("/order/placeOrder", "发布需求");
                    addTab("/order/showPurOrders", "查看订单");
                    addTab("/order/addOrderType", "查看合同");
                    addTab("/order/addOrderType", "查看联系人");
                }
                else if(data === "2"){
                    $("#navTabs").append(
                        "<li><a href=\"" +
                        "/order/recOrder" +
                        "\">" +
                        "等待报价" +
                        "<span id='unOffer' class='badge'></span></a></li>"
                    );

                    addTab("/order/addOrderType", "查看订单");
                    addTab("/order/addOrderType", "查看合同");
                    addTab("/order/addOrderType", "查看联系人");
                }
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

            $("li").each(function () {
                if($(this).find("a").attr("href") === window.location.pathname){
                    $(this).addClass("active");
                }
            });

            var map = {};
            map["queryType"] = "unOffer";
            $.ajax({
                type: "POST",
                url: "/order/showUnRecOrder.do",
                data: map,
                success: function (data) {
                    if(data.length === 0){
                        $("#unOffer").text("");
                    }
                    else{
                        $("#unOffer").text(data.length);
                    }
                }
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
        if($.trim($(item).val()).length === 0){
            errorMessage("请全部输入");
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