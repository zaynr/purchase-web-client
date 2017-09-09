/**
 * Created by zengzy19585 on 2017/9/7.
 */
var param = {};

$(document).ready(function () {

    if(window.location.pathname === "/order/adminGetAll") {
        var pur_mobile_no = $("#pur_mobile_no");
        var pro_mobile_no = $("#pro_mobile_no");
        var serial_no = $("#serial_no");
        var query = $("#query");
        query.off("click").on("click",
            function () {
                $("#cur").html("1");
                param['pageIndex'] = 1;
                param["userType"] = "-1";
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
                else if (serial_no.val() !== "") {
                    param["serialNo"] = serial_no.val();
                }
                $("li[role='presentation']").each(function (i, item) {
                    if($(item).attr("class") === "active"){
                        param["queryType"] = $(item).children().attr("id");
                        queryAllOrder(param);
                    }
                });
            }
        );
        $("th").addClass("NoNewline");
        $("li[role='presentation']").each(function (i, item) {
            $(item).click(function () {
                $("#cur").html("1");
                param['pageIndex'] = 1;
                $("li").each(function (i, item) {
                    $(item).removeClass("active");
                });
                $(item).addClass("active");
                queryByType(item);
            });
            if($(item).attr("class") === "active"){
                queryByType(item);
            }
        });

        //simple logic
        function queryByType(item){
            param["queryType"] = $(item).children().attr("id");
            $("#orderTableContent").html("");
            param['pageIndex'] = 1;
            param["userType"] = "-1";
            param["mobileNo"] = "-1";
            param["serialNo"] = "-1";
            pager(queryAllOrder);
            queryAllOrder(param);
        }
    }

    if(window.location.pathname === "/order/modifyProOrder") {
        param["serialNo"] = getUrlParam("serialNo");
        queryProOrder(param);
    }
});
//ajax
function queryAllOrder(param) {
    $.ajax({
        type: "POST",
        url: "/order/getAllOrder.do",
        data: param,
        success: function (data) {
            $.each(data, function (i, item) {
                $("#orderTableContent").append(
                    "<tr>" +
                    tableItemWrap(item.serialNo) +
                    tableItemWrap(item.orderType) +
                    tableItemWrap(item.orderStatus) +
                    tableItemWrap("<button type=\"button\" class=\"btn btn-info\">查看详情</button><button type=\"button\" class=\"btn btn-danger\">删除订单</button>") +
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
            $("button.btn").each(function (i, item) {
                $(item).on("click", function () {
                    if($(item).text() === "查看详情"){
                        if($(item).parent().prevAll().eq(1).html() === "采购需求") {
                            window.location.href = "/order/modifyPurOrder?serialNo=" + $(item).parent().prevAll().last().html();
                        }
                        else{
                            window.location.href = "/order/modifyProOrder?serialNo=" + $(item).parent().prevAll().last().html();
                        }
                    }
                    else if($(item).text() === "删除订单"){
                        var res = confirm("确认删除此订单？");
                        if(!res){
                            return;
                        }
                        param["serialNo"] = $(item).parent().prevAll().last().html();
                        param["orderType"] = $(item).parent().prevAll().eq(1).html();
                        $.ajax({
                            type: "POST",
                            url: "/order/adminDeleteOrder.do",
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
    })
}
function queryProOrder(param) {
    $.ajax({
        type: "POST",
        url: "/order/getProOrder.do",
        data: param,
        success: function (data) {
            var pur_serial_no = $("#pur_serial_no");
            var pro_mobile_no = $("#pro_mobile_no");
            var offer_price = $("#offer_price");
            var express_no = $("#express_no");
            var proSn = data.pro_serial_no;

            pur_serial_no.val(data.pur_serial_no);
            pro_mobile_no.val(data.provider_name);
            offer_price.val(data.offer_price);
            express_no.val(data.express_no);
            param["pur_serial_no"] = "-1";
            param["pro_mobile_no"] = "-1";
            param["offer_price"] = "-1";
            param["express_no"] = "-1";

            $("#update").off("click").on("click", function () {
                param["proSerialNo"] = proSn;
                param["pur_serial_no"] = pur_serial_no.val();
                param["pro_mobile_no"] = pro_mobile_no.val();
                param["offer_price"] = offer_price.val();
                param["express_no"] = express_no.val();
                modifyProOrder(param);
            });
        }
    });
}
function modifyProOrder(param) {
    $.ajax({
        type: "POST",
        url: "/order/adminMdyProOrder.do",
        data: param,
        success: function () {
            successMessage("更新成功");
            setTimeout(function () {
                window.location.reload();
            }, 1000);
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