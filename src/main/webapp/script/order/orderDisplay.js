/**
 * Created by zengzy19585 on 2017/9/1.
 */
$(document).ready(function () {
    var map = {};

    function queryPurOrder(map) {
        $.ajax({
            type: "POST",
            url: "/order/showPurOrders.do",
            data: map,
            success: function (data) {
                $.each(data, function (i, item) {
                    $("#orderTableContent").append(
                        "<tr>" +
                        tableItemWrap(item.purSerialNo) +
                        tableItemWrap(item.expectPrice) +
                        tableItemWrap(item.orderAmount) +
                        tableItemWrap(item.purchaserName) +
                        tableItemWrap(item.providerName) +
                        tableItemWrap(item.typeContent) +
                        tableItemWrap(item.orderStatus) +
                        tableItemWrap("<button my-attr='op' type=\"button\" class=\"btn btn-danger\">取消订单</button>") +
                        "</tr>"
                    );
                });
                $("button.btn").each(function (i, item) {
                    if($(item).parent().prevAll().first().html() === "撤销" || $(item).parent().prevAll().first().html() === "已完成"){
                        $(item).removeClass("btn-danger");
                        $(item).text("查看详情");
                    }
                    else if($(item).parent().prevAll().first().html() === "已报价"){
                        $(item).removeClass("btn-danger");
                        $(item).addClass("btn-info");
                        $(item).text("查看报价");
                    }
                    else if($(item).parent().prevAll().first().html() === "已提供样品"){
                        $(item).removeClass("btn-danger");
                        $(item).addClass("btn-info");
                        $(item).text("查看样品");
                    }
                    else if($(item).parent().prevAll().first().html() === "已签"){
                        $(item).removeClass("btn-danger");
                        $(item).addClass("btn-success");
                        $(item).text("查看合同");
                    }
                    if($(item).attr("my-attr") !== "op"){
                        return;
                    }
                    $(item).on("click", function () {
                        var param = {"pur_serial_no" : $(this).parent().prevAll().last().html()};
                        if($(item).text() === "取消订单"){
                            var order_status = 6;
                            var hint = "是否取消需求";
                        }
                        else if($(item).text() === "查看报价"){
                            window.location.href="/order/viewAllOffer?serialNo=" + param["pur_serial_no"];
                            return;
                        }
                        else{
                            return;
                        }
                        param["order_status"] = order_status;
                        var result = confirm(hint);
                        if (result) {
                            $.ajax({
                                type: "POST",
                                url: "/order/updatePurOrderStatus.do",
                                data: param,
                                success: function () {
                                    window.location.reload();
                                },
                                error: function () {
                                    alert("操作失败");
                                }
                            });
                        }
                    });
                });
            }
        });
    }
    function queryUnOfferOrder(map) {
        $.ajax({
            type: "POST",
            url: "/order/showUnRecOrder.do",
            data: map,
            success: function (data) {
                $.each(data, function (i, item) {
                    $("#orderTableContent").append(
                        "<tr>" +
                        tableItemWrap(item.purSerialNo) +
                        tableItemWrap(item.expectPrice) +
                        tableItemWrap(item.orderAmount) +
                        tableItemWrap(item.purchaserName) +
                        tableItemWrap(item.providerName) +
                        tableItemWrap(item.typeContent) +
                        tableItemWrap(item.orderStatus) +
                        tableItemWrap("<button type=\"button\" class=\"btn btn-success\">提供报价</button>") +
                        "</tr>"
                    );
                });
                $("button.btn-success").each(function (i, item) {
                    if($(item).parent().prevAll().first().html() === "已报价"){
                        $(item).text("修改报价");
                    }
                    $(item).on("click", function () {
                        var param = {"pur_serial_no": $(item).parent().prevAll().last().html()};
                        $("#recOrderDetail").modal();
                        $("#confirm").click(function () {
                            if(!checkInputNull()){
                                return;
                            }
                            var postUrl;
                            if($(item).parent().prevAll().first().html() === "待接"){
                                postUrl = "/order/placeProOrder.do";
                            }
                            else if($(item).parent().prevAll().first().html() === "已报价"){
                                postUrl = "/order/updateProOrderPrice.do";
                            }
                            param["offer_price"] = $("#offerPrice").val();
                            $.ajax({
                                type: "POST",
                                url: postUrl,
                                data: param,
                                success: function () {
                                    $.ajax({
                                        type: "POST",
                                        url: "/order/recOrder.do",
                                        data: param,
                                        success: function () {
                                            window.location.reload();
                                        },
                                        error: function () {
                                            alert("接单失败");
                                        }
                                    });
                                },
                                error: function () {
                                    alert("提供报价失败");
                                }
                            });
                        });
                    });
                });
            }
        });
    }
    function queryOfferOrder(map){
        $.ajax({
            type: "POST",
            url: "/order/showAllOffer.do",
            data: map,
            success: function (data) {
                $.each(data, function (i, item) {
                    $("#orderTableContent").append(
                        "<tr>" +
                        tableItemWrap(item.orderStatus) +
                        tableItemWrap("<button type=\"button\" class=\"btn btn-info\">索取样品</button>") +
                        "</tr>"
                    );
                });
                $("button.btn-info").each(function (i, item) {
                    if($(item).parent().parent().prevAll().first().html() === "已提供样品"){
                        $(item).text("查看样品");
                    }
                    $(item).on("click", function () {
                        var postUrl;
                        if($(item).text() === "索取样品"){
                            postUrl = "/order/requestSample.do"
                        }
                        else if($(item).text() === "查看样品"){
                            window.location.href = "/order/viewSample"
                        }
                    });
                });
            }
        });
    }
    function queryByType(item){
        map["queryType"] = $(item).children().attr("id");
        $("#orderTableContent").html("");
        if(window.location.pathname === "/order/showPurOrders") {
            queryPurOrder(map);
        }
        else if(window.location.pathname === "/order/showPurOrders"){
            queryUnOfferOrder(map);
        }
    }

    $("li").each(function (i, item) {
        $(item).click(function () {
            $("li").each(function (i, item) {
                $(item).removeClass("active");
            });
            $(item).addClass("active");
            queryByType(item);
        });
        if($(item).attr("class") === "active"){
            queryByType(item);
        }
    });

});

function tableItemWrap(content){
    var item = "<td>" + content + "</td>";
    return item;
}

