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
                $("#provideType").append("<h1>供应类型：</h1>");
                $.each(data.provideType, function (i, item) {
                    $("#provideType").append(
                        "<div class='alert alert-success' my-attr='" +
                        item.type_no + "'>\n" +
                        item.type_content +
                        "</div>"
                    );
                });
            }
            else{
                $("#provideType").append("<h1>偏好类型：</h1>");
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
        }
    });

    $("#commitChange").click(function () {
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
        }
        var map = {};
        if($.md5($("#oldPwd").val()) === ""){
            map["password"] = oldPwd;
        }
        else{
            map["password"] = encrypt;
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

    function normalMessage(message) {
        $("#message").html(
            "<div class=\"alert alert-info alert-dismissable\">" +
            "<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>" +
            message +
            "</div>"
        );
    }

    function errorMessage(message) {
        $("#message").html(
            "<div class=\"alert alert-danger alert-dismissable\">" +
            "<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>" +
            message +
            "</div>"
        );
    }
});