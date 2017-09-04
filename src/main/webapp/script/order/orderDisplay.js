/**
 * Created by zengzy19585 on 2017/9/1.
 */
$(document).ready(function () {
    if(window.location.pathname === "/order/showPurOrders") {
        $.ajax({
            type: "POST",
            url: "/order/showPurOrders.do",
            success: function (data) {
                $.each(data, function (i, item) {
                    $("#purTableContent").append(
                        "<tr>\n" +
                        "<td>" +
                        item.purSerialNo +
                        "</td>" +
                        "<td>" +
                        item.expectPrice +
                        "</td>" +
                        "<td>" +
                        item.orderAmount +
                        "</td>" +
                        "<td>" +
                        item.purchaserName +
                        "</td>" +
                        "<td>" +
                        item.providerName +
                        "</td>" +
                        "<td>" +
                        item.typeContent +
                        "</td>" +
                        "<td>" +
                        item.orderStatus +
                        "</td>" +
                        "<td><button my-attr='op' id=\"cancelOrder\" type=\"button\" class=\"btn btn-danger\">取消订单</button></td>" +
                        "</tr>"
                    );
                });
                $("button.btn").each(function (i, item) {
                    if($(item).parent().prevAll().first().html() === "撤销"){
                        $(item).removeClass("btn-danger");
                    }
                    if($(item).parent().prevAll().first().html() === "已报价"){
                        $(item).removeClass("btn-danger");
                        $(item).addClass("btn-info");
                        $(item).text("查看全部报价");
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
                        else if($(item).text() === "查看全部报价"){
                            window.location.href="/order/viewAllOffer";
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

    if(window.location.pathname === "/order/recOrder") {
        $.ajax({
            type: "POST",
            url: "/order/showUnRecOrder.do",
            success: function (data) {
                $.each(data, function (i, item) {
                    $("#purTableContent").append(
                        "<tr>\n" +
                        "<td>" +
                        item.purSerialNo +
                        "</td>" +
                        "<td>" +
                        item.expectPrice +
                        "</td>" +
                        "<td>" +
                        item.orderAmount +
                        "</td>" +
                        "<td>" +
                        item.purchaserName +
                        "</td>" +
                        "<td>" +
                        item.providerName +
                        "</td>" +
                        "<td>" +
                        item.typeContent +
                        "</td>" +
                        "<td>" +
                        item.orderStatus +
                        "</td>" +
                        "<td><button id=\"cancelOrder\" type=\"button\" class=\"btn btn-success\">提供报价</button></td>" +
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
});