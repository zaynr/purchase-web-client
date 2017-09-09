/**
 * Created by zengzy19585 on 2017/7/12.
 */
$(document).ready(function () {
    $("#login").click(function () {
        if(!checkInputNull()){
            return;
        }
        // if(window.location.pathname === "/login/user-login") {
        //     if (isPhoneNo($.trim($('#mobile_no').val())) === false) {
        //         errorMessage("手机号非法！");
        //         $('#mobile_no').focus();
        //         return;
        //     }
        // }
        var encrypt = $.md5($("#password").val());
        var map = {};
        map["password"] = encrypt;
        map["mobile_no"] = $("#mobile_no").val();
        map["user_type"] = $("#user_type").val();

        $.ajax({
            type: "POST",
            url: "/login/login.do",
            data: map,
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
});