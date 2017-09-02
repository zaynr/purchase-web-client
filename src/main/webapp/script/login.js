/**
 * Created by zengzy19585 on 2017/7/12.
 */
$(document).ready(function () {
    $("#login").click(function () {
        var encrypt = $.md5($("#password").val());
        var map = {};
        map["password"] = encrypt;
        map["mobile_no"] = $("#mobile_no").val();
        map["user_type"] = $("#user_type").val();
        var param = {userInfo:map};

        $.ajax({
            type: "POST",
            url: "/users/login.do",
            data: param,
            success: function (data) {
                if(data === "log_success"){
                    $("#message").html(
                        "<div class=\"alert alert-success alert-dismissable\">\n" +
                        "   <button type=\"button\" class=\"close\" data-dismiss=\"alert\"\n" +
                        "aria-hidden=\"true\">\n" +
                        "    &times;\n" +
                        "   </button>\n" +
                        "登录成功" +
                        "</div>"
                    );
                    window.location.href="/";
                }
                else{
                    $("#message").html(
                        "<div class=\"alert alert-info alert-dismissable\">\n" +
                        "   <button type=\"button\" class=\"close\" data-dismiss=\"alert\"\n" +
                        "aria-hidden=\"true\">\n" +
                        "    &times;\n" +
                        "   </button>\n" +
                        "密码错误或用户不存在" +
                        "</div>"
                    );
                }
            }
        });
    });


    $("#register").click(function () {
        var encrypt = $.md5($("#password").val());
        var map = {};
        map["password"] = encrypt;
        map["mobile_no"] = $("#mobile_no").val();
        map["user_type"] = $("#user_type").val();
        alert(map["mobile_no"] === "");
        if(map["mobile_no"] === "" || map["password"] === ""){
            $("#message").html(
                "<div class=\"alert alert-info alert-dismissable\">\n" +
                "   <button type=\"button\" class=\"close\" data-dismiss=\"alert\"\n" +
                "aria-hidden=\"true\">\n" +
                "    &times;\n" +
                "   </button>\n" +
                "请输入账号名及密码" +
                "</div>"
            );
            return;
        }
        var param = {userInfo:map};

        $.ajax({
            type: "POST",
            url: "/users/register.do",
            data: param,
            success: function (data) {
                if(data === "reg_success"){
                    $("#message").html(
                        "<div class=\"alert alert-success alert-dismissable\">\n" +
                        "   <button type=\"button\" class=\"close\" data-dismiss=\"alert\"\n" +
                        "aria-hidden=\"true\">\n" +
                        "    &times;\n" +
                        "   </button>\n" +
                        "注册成功" +
                        "</div>"
                    );
                    window.location.href="/";
                }
                else if(data === "already_exist"){
                    $("#message").html(
                        "<div class=\"alert alert-info alert-dismissable\">\n" +
                        "   <button type=\"button\" class=\"close\" data-dismiss=\"alert\"\n" +
                        "aria-hidden=\"true\">\n" +
                        "    &times;\n" +
                        "   </button>\n" +
                        "该手机号已被注册" +
                        "</div>"
                    );
                }
            }
        });
    });

});