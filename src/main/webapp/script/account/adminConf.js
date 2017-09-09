/**
 * Created by zengzy19585 on 2017/9/8.
 */
var param = {};

$(document).ready(function () {
    if(window.location.pathname === "/account/userManage") {
        var pur_mobile_no = $("#pur_mobile_no");
        var pro_mobile_no = $("#pro_mobile_no");
        var query = $("#query");
        query.off("click").on("click",
            function () {
                $("#cur").html("1");
                param['pageIndex'] = 1;
                param["mobileNo"] = "-1";
                param["serialNo"] = "-1";
                $("#orderTableContent").html("");
                if (pur_mobile_no.val() !== "") {
                    param["userType"] = 1;
                    param["mobileNo"] = pur_mobile_no.val();
                }
                else if (pro_mobile_no.val() !== "") {
                    param["userType"] = 2;
                    param["mobileNo"] = pro_mobile_no.val();
                }
                queryAllUser(param);
            }
        );
        param['pageIndex'] = 1;
        param["userType"] = "-1";
        param["mobileNo"] = "-1";
        queryAllUser(param);
        pager(queryAllUser);
    }
});
//ajax
function queryAllUser(param) {
    $.ajax({
        type: "POST",
        url: "/account/queryAllUser.do",
        data: param,
        success: function (data) {
            $.each(data, function (i, item) {
                if(item.userType === 1){
                    item.userType = "采购商";
                }
                else if(item.userType === 2){
                    item.userType = "供应商";
                }
                item.spaceUsed += " Mb";
                $("#orderTableContent").append(
                    "<tr>" +
                    tableItemWrap(item.mobileNo) +
                    tableItemWrap(item.userType) +
                    tableItemWrap(item.spaceUsed) +
                    tableItemWrap("<button type=\"button\" class=\"btn btn-info\">修改用户</button><button type=\"button\" class=\"btn btn-danger\">删除用户</button>") +
                    "</tr>"
                );
                if($("#cur").html() === '1'){
                    $("li.previous").addClass("disabled");
                }
                else{
                    $("li.previous").removeClass("disabled");
                }
                if(data[0].pageSize > data.length){
                    $("li.next").addClass("disabled");
                }
                else{
                    $("li.next").removeClass("disabled");
                }
            });
            $("td").find("button.btn-info").each(function (i, item) {
                $(item).off("click").on("click", function () {
                    if($(item).text() === "修改用户"){
                        if(data[i].userType === "采购商") {
                            window.location.href = "/account/adminModify?userType=1&mobileNo="+data[i].mobileNo+"&pwd="+data[i].pwd;
                        }
                        else if(data[i].userType === "供应商"){
                            window.location.href = "/account/adminModify?userType=2&mobileNo="+data[i].mobileNo+"&pwd="+data[i].pwd;
                        }
                    }
                });
            });
            $("td").find("button.btn-danger").each(function (i, item) {
                $(item).off("click").on("click", function () {
                    if($(item).text() === "删除用户"){
                        var res = confirm("确认删除此用户？");
                        if(!res){
                            return;
                        }
                        param["userType"] = data[i].userType;
                        param["mobileNo"] = data[i].mobileNo;
                        $.ajax({
                            type: "POST",
                            url: "/account/adminDeleteUser.do",
                            data: param,
                            success: function (data) {
                                if(data === "success"){
                                    successMessage("删除成功");
                                }
                                else{
                                    errorMessage("删除失败");
                                }
                            }
                        });
                    }
                });
            });
        }
    });
}

//template
function tableItemWrap(content){
    var item = "<td class='NoNewline'>" + content + "</td>";
    return item;
}

//分页
function pager(func){
    var cur = $("#cur");
    $("li.previous").off("click").on("click", function () {
        if($(this).hasClass("disabled")){
            return;
        }
        $("#orderTableContent").html("");
        var pageNo = parseInt(cur.html(), 10) - 1;
        param['pageIndex'] = pageNo;
        cur.html(pageNo);
        func(param);
    });
    $("li.current").off("click").on("click", function () {
        $("#orderTableContent").html("");
        param['pageIndex'] = cur.html();
        func(param);
    });
    $("li.next").off("click").on("click", function () {
        if($(this).hasClass("disabled")){
            return;
        }
        $("#orderTableContent").html("");
        var pageNo = parseInt(cur.html(), 10) + 1;
        param['pageIndex'] = pageNo;
        cur.html(pageNo);
        func(param);
    });
}