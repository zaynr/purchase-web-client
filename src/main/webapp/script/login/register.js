/**
 * Created by zengzy19585 on 2017/9/3.
 */
$(document).ready(function () {

    $("#register").click(function () {
        if(!checkInputNull()){
            return;
        }
        var provide_type = "";
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
        $("div.alert-content").each(function (i, item) {
            provide_type += $(item).attr("my-attr") + ",";
        });
        if(provide_type === ""){
            $("#message").html(
                "<div class=\"alert alert-info alert-dismissable\">\n" +
                "   <button type=\"button\" class=\"close\" data-dismiss=\"alert\"\n" +
                "aria-hidden=\"true\">\n" +
                "    &times;\n" +
                "   </button>\n" +
                "请选择类型" +
                "</div>"
            );
            return;
        }
        map["provide_type"] = provide_type;

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
                        data: map,
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
            $("#provide_type").html("");
            $("#myModalLabel").text("选择可供应类型");
        }
        else if($("#user_type").val() === '1'){
            $("#myModalLabel").text("选择偏好类型");
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
                "<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>" +
                "<div class='alert-content' my-attr=\"" +
                $("#typeSelect").val() +
                "\">" +
                $("#typeSelect").find("option:selected").text() +
                "</div></div><br>"
            );
        }
    });

    $("#confirm").click(function () {
        $("#provide_type").html($("#provide_type_dialog").html());
        $("#provide_type_dialog").html("");
    });

    $("#addMoreType").on("click", function () {
        $("#type_detail").modal();
    });
});