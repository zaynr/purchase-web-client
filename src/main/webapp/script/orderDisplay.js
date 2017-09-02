/**
 * Created by zengzy19585 on 2017/9/1.
 */
$(document).ready(function () {
    $.ajax({
        type: "POST",
        url: "/order/showPurOrders.do",
        success: function (data) {
            $.each(data, function (i, item) {
                $("#tableContent").append(
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
                    "</tr>"
                );
            });
        }
    });
});