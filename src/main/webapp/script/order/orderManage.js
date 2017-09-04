/**
 * Created by zengzy19585 on 2017/9/1.
 */
$(document).ready(function () {
    $("#addNew").click(function () {
        if(!checkInputNull()){
            return;
        }
        var param = {'orderType' : $("#orderType").val()};
        param["typeUnit"] = $("#").val();
        $.ajax({
            type: "POST",
            url: "/order/addOrderType.do",
            data: param,
            success: function () {
                $("#message").html(
                    "<div class=\"alert alert-success alert-dismissable\">\n" +
                    "   <button type=\"button\" class=\"close\" data-dismiss=\"alert\"\n" +
                    "aria-hidden=\"true\">\n" +
                    "    &times;\n" +
                    "   </button>\n" +
                    "添加成功" +
                    "</div>"
                );
            }
        });
    });

    $("#placeOrder").click(function () {
        if(!checkInputNull()){
            return;
        }
        var param = {"type_no" : $("#typeSelect").val()};
        param["orderAmount"] = $("#orderAmount").val();
        param["expect"] = $("#expect").val();
        $.ajax({
            type: "POST",
            url: "/order/placeOrder.do",
            data: param,
            success: function () {
                $("#message").html(
                    "<div class=\"alert alert-success alert-dismissable\">\n" +
                    "   <button type=\"button\" class=\"close\" data-dismiss=\"alert\"\n" +
                    "aria-hidden=\"true\">\n" +
                    "    &times;\n" +
                    "   </button>\n" +
                    "发布成功" +
                    "</div>"
                );
                setTimeout(function () {
                    window.location.href="/order/showPurOrders";
                }, 1000);
            }
        });
    });

    if(window.location.pathname === "/order/placeOrder") {
        $.ajax({
            type: "POST",
            url: "/order/showOrderType.do",
            success: function (data) {
                $.each(data, function (i, item) {
                    $("#typeSelect").append(
                        "<option value = \"" +
                        item.type_no +
                        "\" type-unit='" +
                        item.type_unit +
                        "'>" +
                        item.type_content +
                        "</option>"
                    );
                });
                $("#typeUnit").text($("#typeSelect").find("option:selected").attr("type-unit"));

                $("#typeSelect").change(function () {
                    $("#typeUnit").text($("#typeSelect").find("option:selected").attr("type-unit"));
                });
            }
        });
    }
});