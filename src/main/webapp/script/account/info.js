/**
 * Created by zengzy19585 on 2017/9/2.
 */
var param = {};
var oldPwd;

$(document).ready(function () {
    var type_picker = $("#type_picker");
    type_picker.cxSelect({
        url: "/order/showOrdTypeGrpByCat.do",
        selects: ['type_category', 'type_content'],
        required: true,
        jsonName: 'name',
        jsonValue: 'typeUnit',
        jsonSub: 'types'
    });

    if(window.location.pathname === "/account/info") {
        getAccountInfo(param);
    }

    if(window.location.pathname === "/account/adminModify") {
        debugger;
        param["uType"] = getUrlParam("userType");
        param["mno"] = getUrlParam("mobileNo");
        param["pwd"] = getUrlParam("pwd");
        getAccountInfo(param);
    }

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
            param["password"] = $.md5($("#newPwd").val());
        }
        else{
            param["password"] = oldPwd;
        }
        param["userName"] = $("#userName").val();
        param["mobileNo"] = $("#mobileNo").val();
        var dist = $("#distpicker").val().split("/");
        param["province"] = dist[0];
        param["city"] = dist[1];
        param["dist"] = dist[2];
        param["detail_address"] = $("#detailAddress").val();
        updateAccountInfo(param);
    });
});

//ajax
function getAccountInfo(param){
    $.ajax({
        type: "POST",
        url: "/account/getInfo.do",
        data: param,
        success: function (data) {
            $("#userType").text(data.userType);
            $("#mobileNo").val(data.mobileNo);
            $("#userName").val(data.userName);
            $("#distpicker").citypicker({
                simple: true,
                province: data.address.province,
                city: data.address.city,
                district: data.address.dist
            });
            $("#detailAddress").val(data.address.detail_address);

            oldPwd = data.pwd;
            if(data.userType === "供应商" || data.userType === "管理员编辑：供应商"){
                $("#provideType").html("<h1>供应类型</h1><br>");
                $.each(data.provideType, function (i, item) {
                    addType(item);
                });
            }
            else if(data.userType === "采购商" || data.userType === "管理员编辑：采购商"){
                $("#provideType").html("<h1>偏好类型</h1><br>");
                $.each(data.provideType, function (i, item) {
                    if(i < 5) {
                        addType(item);
                    }
                });
            }
            else{
                $("#provideType").html("<h1>管理账号</h1>");
                $("#provideType").append(
                    "<h3><a href='/account/purchaser'>采购商管理</a></h3>" +
                    "<h3><a href='/account/provider'>供应商管理</a></h3>"
                );
            }
        }
    });

    $("#addMoreType").on("click", function () {
        $("#type_detail").modal();
    });

    $("#confirm").click(function () {
        $("#provideType").append($("#provide_type_dialog").html());
        $("#provide_type_dialog").html("");
    });

    $("#add").click(function () {
        var flag = true;
        $("div.alert-content").each(function (i, item) {
            if($(item).text() === $("select.type_content").find("option:selected").text()){
                flag = false;
            }
        });
        if(flag) {
            $("#provide_type_dialog").append(
                "<div class=\"alert alert-success alert-dismissable\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button><div class='alert-content' my-attr=\"" +
                $("select.type_content").find("option:selected").attr("value") +
                "\">" +
                $("select.type_content").find("option:selected").text() +
                "</div></div>"
            );
        }
    });
}
function updateAccountInfo(param){
    var foo = "";
    $("div.alert-content").each(function (i, item) {
        foo += $(item).attr("my-attr") + ",";
    });
    if(foo === ""){
        errorMessage("请选择类型");
        return;
    }
    param["provide_type"] = foo;
    $.ajax({
        type: "POST",
        url: "/account/updateInfo.do",
        data: param,
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
}

//template
function addType(item){
    $("#provideType").append(
        "<div class=\"alert alert-success alert-dismissable\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button><div class='alert-content' my-attr=\"" +
        item.type_no +
        "\">" +
        item.type_content +
        "</div></div>"
    );
}