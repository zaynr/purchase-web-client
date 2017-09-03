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
                    + "</a>"
                    +
                    "<button id=\"logout\" class=\"btn btn-danger\">"
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
                    addTab("/order/addOrderType", "查看联系人");
                }
                else if(data === "2"){
                    addTab("/order/recOrder", "等待报价");
                    addTab("/order/addOrderType", "查看订单");
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
        }
    });

    function addTab(href, content) {
        $("#navTabs").append(
            "<li><a href=\""
            + href
            + "\">"
            + content
            + "</a></li>"
        );
    }

});