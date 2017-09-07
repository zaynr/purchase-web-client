/**
 * Created by zengzy19585 on 2017/9/7.
 */
var param = {};

$(document).ready(function () {
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
            if(pur_mobile_no.val() !== ""){
                param["userType"] = 1;
                param["mobileNo"] = pur_mobile_no.val();
            }
            else if(pro_mobile_no.val() !== ""){
                param["userType"] = 2;
                param["mobileNo"] = pro_mobile_no.val();
            }
            else if(serial_no.val() !== ""){
                param["serialNo"] = serial_no.val();
            }
            queryOrder(param);
        }
    );
    param['pageIndex'] = 1;
    param["userType"] = "-1";
    param["mobileNo"] = "-1";
    param["serialNo"] = "-1";
    queryOrder(param);
    pager(queryOrder);
});
function queryOrder(param) {
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
        }
    })
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