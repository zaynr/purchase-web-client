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
                    alert("登录成功");
                }
                else{
                    alert(data.toString());
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
        var param = {userInfo:map};

        $.ajax({
            type: "POST",
            url: "/users/register.do",
            data: param,
            success: function (data) {
                if(data === "reg_success"){
                    alert("注册成功");
                }
                else{
                    alert(data.toString());
                }
            }
        });
    });

});