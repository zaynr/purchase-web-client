/**
 * Created by zengzy19585 on 2017/9/3.
 */
$(document).ready(function () {

    $("#register").click(function () {
        var encrypt = $.md5($("#password").val());
        var map = {};
        map["password"] = encrypt;
        map["mobile_no"] = $("#mobile_no").val();
        map["user_type"] = $("#user_type").val();
        if(map["mobile_no"] === "" || map["password"] === ""){
            $("#message").html(
                "<div class=\"alert alert-info alert-dismissable\">\n" +
                "   <button type=\"button\" class=\"close\" data-dismiss=\"alert\"\n" +
                "aria-hidden=\"true\">\n" +
                "    &times;\n" +
                "   </button>\n" +
                "请输入手机号及密码" +
                "</div>"
            );
            return;
        }

        $.ajax({
            type: "POST",
            url: "/login/register.do",
            data: map,
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
                    $.ajax({
                        type: "POST",
                        url: "/login/login.do",
                        data: param,
                        success: function () {
                            window.location.href="/";
                        }
                    });
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

    $.ajax({
        type: "POST",
        url: "/login/showOrderType.do",
        success: function (data) {
            $.each(data, function (i, item) {
                $("#typeSelect").append(
                    "<option value = \""
                    + item.type_no
                    + "\">"
                    + item.type_content
                    + "</option>"
                );
            });
        }
    });

    $("#user_type").change(function () {
        if($("#user_type").val() === '2'){
            $("#type_detail").modal();
        }
        else{
            $("#provide_type").html("");
        }
    });

    $("#add").click(function () {
        var flag = true;
        $("div.alert-content").each(function (i, item) {
            if($(item).text() === $("#typeSelect").find("option:selected").text()){
                flag = false;
            }
        });
        if(flag) {
            $("#provide_type_dialog").append(
                "<div class=\"alert alert-success alert-dismissable\">\n" +
                "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"\n" +
                "aria-hidden=\"true\">\n" +
                "&times;" +
                "</button>\n<div class='alert-content'>" +
                $("#typeSelect").find("option:selected").text() +
                "</div></div>"
            );
        }
    });

    $("#confirm").click(function () {
        $("#provide_type").html($("#provide_type_dialog").html());
        $("#provide_type").append("<button id=\"addMoreType\" class=\"btn btn-primary\" >添加更多</button>");

        $("#addMoreType").on("click", function () {
            $("#type_detail").modal();
        });
    });

});