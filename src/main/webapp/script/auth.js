$(document).ready(function () {

    $.ajax({
        type: "POST",
        url: "/check-auth.do",
        success: function (data) {
            if(data === "unAuth"){
                $("#navbarButton").append(
                    "<a class=\"btn btn-primary\" href=\"/users/user-login\" role=\"button\">登录</a>"
                );
            }
            else{
                $("#navbarButton").append(
                    "<button id=\"logout\" class=\"btn btn-primary\">"
                    + "注销"
                    + "</button>"
                );
                if(data === "0"){
                    addTab("/order/addOrderType", "添加订单类型");
                }
                else if(data === "1"){
                    addTab("/order/addOrderType", "发出订单");
                }
                else if(data === "2"){
                    addTab("/order/addOrderType", "查看订单");
                }
            }

            $("#logout").on("click", function () {
                $.ajax({
                    type: "POST",
                    url: "/logout.do",
                    success: function () {
                        window.location.reload();
                    }
                });
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