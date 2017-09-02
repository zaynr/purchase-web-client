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
                        item.orderStatus +
                        "</td>" +
                        "<td>" +
                        item.typeContent +
                        "</td>" +
                        "<td><button id=\"cancelOrder\" type=\"button\" class=\"btn btn-danger\">取消订单</button></td>" +
                        "</tr>"
                    );
                    // $("#cancelOrder").on("click", function () {
                    //     var param = {"pur_serial_no" : $(this).parent().prevAll().last().html()};
                    //     var result = confirm('是否取消需求');
                    //     if(result) {
                    //         $.ajax({
                    //             type: "POST",
                    //             url: "/order/cancelPurOrders.do",
                    //             data: param,
                    //             success: function () {
                    //                 $(this).parent().parent().remove();
                    //             },
                    //             error: function () {
                    //                 alert("取消失败");
                    //             }
                    //         });
                    //     }
                    // });
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
                        item.orderStatus +
                        "</td>" +
                        "<td>" +
                        item.typeContent +
                        "</td>" +
                        "<td><button id=\"cancelOrder\" type=\"button\" class=\"btn btn-success\">提供报价</button></td>" +
                        "</tr>"
                    );
                    // $("#cancelOrder").on("click", function () {
                    //     var param = {"pur_serial_no" : $(this).parent().prevAll().last().html()};
                    //     var result = confirm('是否取消需求');
                    //     if(result) {
                    //         $.ajax({
                    //             type: "POST",
                    //             url: "/order/cancelPurOrders.do",
                    //             data: param,
                    //             success: function () {
                    //                 $(this).parent().parent().remove();
                    //             },
                    //             error: function () {
                    //                 alert("取消失败");
                    //             }
                    //         });
                    //     }
                    // });
                });
            }
        });
    }
});