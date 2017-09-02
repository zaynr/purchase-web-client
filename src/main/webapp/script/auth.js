$(document).ready(function () {

    $.ajax({
        type: "POST",
        url: "/check-auth.do",
        success: function (data) {
            if(data === "unAuth"){
                $("#navbarButton").html(
                    "<a class=\"btn btn-primary\" href=\"/users/user-login\" role=\"button\">登录</a>"
                );
            }
            else if(data === "0"){
                $("#navbarButton").html(
                    "<a class=\"btn btn-primary\" href=\"/\" role=\"button\">管理订单</a>"
                    + "<button id=\"logout\" class=\"btn btn-primary\">注销</button>"
                );
            }
            else if(data === "1"){
                $("#navbarButton").html(
                    "<a class=\"btn btn-primary\" href=\"/\" role=\"button\">下单</a>"
                    + "<button id=\"logout\" class=\"btn btn-primary\">注销</button>"
                );
            }
            else if(data === "2"){
                $("#navbarButton").html(
                    "<a class=\"btn btn-primary\" href=\"/\" role=\"button\">接单</a>"
                    + "<button id=\"logout\" class=\"btn btn-primary\">注销</button>"
                );
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

});