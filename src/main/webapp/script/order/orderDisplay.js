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
                        item.contractSerialNo +
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
                        "<td><button id=\"cancelOrder\" type=\"button\" class=\"btn btn-danger\">取消订单</button></td>" +
                        "</tr>"
                    );
                });
                $("button.btn-danger").each(function (i, item) {
                    if($(item).parent().prevAll().first().html() === "撤销"){
                        $(item).remove();
                    }
                    if($(item).text() !== "取消订单"){
                        return;
                    }
                    $(item).on("click", function () {
                        var param = {"pur_serial_no" : $(this).parent().prevAll().last().html()};
                        param["order_status"] = 6;
                        var result = confirm('是否取消需求');
                        if (result) {
                            $.ajax({
                                type: "POST",
                                url: "/order/updatePurOrderStatus.do",
                                data: param,
                                success: function () {
                                    window.location.reload();
                                },
                                error: function () {
                                    alert("取消失败");
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
                        item.contractSerialNo +
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