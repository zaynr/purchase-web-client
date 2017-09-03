/**
 * Created by zengzy19585 on 2017/9/2.
 */
$(document).ready(function () {
    var oldPwd;
    $.ajax({
        type: "POST",
        url: "/account/getInfo.do",
        success: function (data) {
            $("#userType").text(data.userType);
            $("#mobileNo").val(data.mobileNo);
            $("#userName").val(data.userName);
            oldPwd = data.pwd;
            if(data.userType === "供应商"){
                $("#provideType").html("<h1>供应类型：</h1>");
                $.each(data.provideType, function (i, item) {
                    $("#provideType").append(
                        "<div class='alert alert-success' my-attr='" +
                        item.type_no + "'>\n" +
                        item.type_content +
                        "</div>"
                    );
                });
            }
            else if(data.userType === "采购商"){
                $("#provideType").html("<h1>偏好类型：</h1>");
                $.each(data.provideType, function (i, item) {
                    if(i < 5) {
                        $("#provideType").append(
                            "<div class='alert alert-success' my-attr='" +
                            item.type_no + "'>\n" +
                            item.type_content +
                            "</div>"
                        );
                    }
                });
            }
            else{
                $("#provideType").html("<h1>管理账号：</h1>");
                $("#provideType").append(
                    "<h3><a href='/account/purchaser'>采购商管理</a></h3>" +
                    "<h3><a href='/account/provider'>供应商管理</a></h3>"
                );
            }
        }
    });

    $("#commitChange").click(function () {
        var map = {};
        var encrypt = $.md5($("#oldPwd").val());
        if($("#oldPwd").val() !== "") {
            if (encrypt !== oldPwd) {
                errorMessage("密码不正确");
                return;
            }
            if ($("#newPwd").val() === "") {
                errorMessage("请输入新密码");
                return;
            }
            if ($("#newPwd").val() !== $("#rptPwd").val()) {
                errorMessage("两次输入密码不同");
                return;
            }
            map["password"] = $.md5($("#newPwd").val());
        }
        else{
            map["password"] = oldPwd;
        }
        map["userName"] = $("#userName").val();
        map["mobileNo"] = $("#mobileNo").val();
        $.ajax({
            type: "POST",
            url: "/account/updateInfo.do",
            data: map,
            success: function (data) {
                if(data === "success") {
                    normalMessage("更新成功");
                    setTimeout(function () {
                        window.location.reload();
                    }, 1000);
                }
                else{
                    errorMessage("手机号已被注册");
                }
            },
            error: function () {
                errorMessage("更新失败");
            }
        });
    });
});