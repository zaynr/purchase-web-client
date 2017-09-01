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
                $("#test").text(data);
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
                $("#test").text(data);
            }
        });
    });
});