/**
 * Created by zengzy19585 on 2017/9/1.
 */
$(document).ready(function () {
    $("#addNew").click(function () {
        var param = {'orderType' : $("#orderType").val()};
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
});