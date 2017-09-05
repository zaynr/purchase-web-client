/**
 * Created by zengzy19585 on 2017/9/1.
 */
var map = {};

$(document).ready(function () {
    $("th").addClass("NoNewline");

    $("li").each(function (i, item) {
        $(item).click(function () {
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

    if(window.location.pathname === "/order/viewAllOffer"){
        map["serialNo"] = getUrlParam('serialNo');
        queryOfferOrder(map);
    }

    if(window.location.pathname === "/order/confirmSample"){
        querySentSample();
    }

    if(window.location.pathname === "/order/sendSample"){
        queryRequiredSample();
    }

    if(window.location.pathname === "/order/viewProOrder"){
        queryProOrder();
    }
});

function tableItemWrap(content){
    var item = "<td class='NoNewline'>" + content + "</td>";
    return item;
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r !== null) return unescape(r[2]); return null; //返回参数值
}

function queryPurOrder(map) {
    $.ajax({
        type: "POST",
        url: "/order/showPurOrders.do",
        data: map,
        success: function (data) {
            $.each(data, function (i, item) {
                $("#orderTableContent").append(
                    "<tr>" +
                    tableItemWrap(item.purSerialNo) +
                    tableItemWrap(item.expectPrice) +
                    tableItemWrap(item.orderAmount) +
                    tableItemWrap(item.purchaserName) +
                    tableItemWrap(item.providerName) +
                    tableItemWrap(item.typeContent) +
                    tableItemWrap(item.orderStatus) +
                    tableItemWrap("<button my-attr='op' type=\"button\" class=\"btn btn-danger\">取消订单</button>") +
                    "</tr>"
                );
            });
            $("button.btn").each(function (i, item) {
                if($(item).parent().prevAll().first().html() === "撤销" || $(item).parent().prevAll().first().html() === "已完成"){
                    $(item).removeClass("btn-danger");
                    $(item).text("查看详情");
                }
                else if($(item).parent().prevAll().first().html() === "已报价" || $(item).parent().prevAll().first().html() === "待提供样品" || $(item).parent().prevAll().first().html() === "已寄送样品" || $(item).parent().prevAll().first().html() === "已确认样品"){
                    $(item).removeClass("btn-danger");
                    $(item).addClass("btn-info");
                    $(item).text("查看报价");
                }
                else if($(item).parent().prevAll().first().html() === "已签合同"){
                    $(item).removeClass("btn-danger");
                    $(item).addClass("btn-success");
                    $(item).text("查看合同");
                }
                if($(item).attr("my-attr") !== "op"){
                    return;
                }
                $(item).on("click", function () {
                    var param = {"pur_serial_no" : $(this).parent().prevAll().last().html()};
                    var hint;
                    if($(item).text() === "取消订单"){
                        var order_status = 8;
                         hint = "是否取消需求";
                    }
                    else if($(item).text() === "查看报价"){
                        window.location.href="/order/viewAllOffer?serialNo=" + param["pur_serial_no"];
                        return;
                    }
                    else{
                        return;
                    }
                    param["order_status"] = order_status;
                    var result = confirm(hint);
                    if (result) {
                        $.ajax({
                            type: "POST",
                            url: "/order/updatePurOrderStatus.do",
                            data: param,
                            success: function () {
                                window.location.reload();
                            },
                            error: function () {
                                alert("操作失败");
                            }
                        });
                    }
                });
            });
        }
    });
}
function queryUnOfferOrder(map) {
    $.ajax({
        type: "POST",
        url: "/order/showSpicStatusPurOrder.do",
        data: map,
        success: function (data) {
            $.each(data, function (i, item) {
                $("#orderTableContent").append(
                    "<tr>" +
                    tableItemWrap(item.purSerialNo) +
                    tableItemWrap(item.expectPrice) +
                    tableItemWrap(item.orderAmount) +
                    tableItemWrap(item.offeredPrice) +
                    tableItemWrap(item.providerName) +
                    tableItemWrap(item.typeContent) +
                    tableItemWrap(item.orderStatus) +
                    tableItemWrap("<button type=\"button\" class=\"btn btn-success\">提供报价</button>") +
                    "</tr>"
                );
            });
            $("button.btn-success").each(function (i, item) {
                if($(item).parent().prevAll().first().html() === "已报价"){
                    $(item).text("修改报价");
                }
                $(item).on("click", function () {
                    var param = {"pur_serial_no": $(item).parent().prevAll().last().html()};
                    $("#recOrderDetail").modal();
                    $("#confirm").click(function () {
                        if(!checkInputNull()){
                            return;
                        }
                        var postUrl;
                        if($(item).parent().prevAll().first().html() === "待报价"){
                            postUrl = "/order/placeProOrder.do";
                        }
                        else if($(item).parent().prevAll().first().html() === "已报价"){
                            postUrl = "/order/updateProOrderPrice.do";
                        }
                        param["offer_price"] = $("#offerPrice").val();
                        $.ajax({
                            type: "POST",
                            url: postUrl,
                            data: param,
                            success: function () {
                                $.ajax({
                                    type: "POST",
                                    url: "/order/recOrder.do",
                                    data: param,
                                    success: function () {
                                        window.location.reload();
                                    },
                                    error: function () {
                                        alert("接单失败");
                                    }
                                });
                            },
                            error: function () {
                                alert("提供报价失败");
                            }
                        });
                    });
                });
            });
        }
    });
}
function queryOfferOrder(map){
    $.ajax({
        type: "POST",
        url: "/order/getAllOffer.do",
        data: map,
        success: function (data) {
            $.each(data, function (i, item) {
                $("#orderTableContent").append(
                    "<tr>" +
                    tableItemWrap(item.proSerialNo) +
                    tableItemWrap(item.offerPrice) +
                    tableItemWrap(item.providerMobileNo) +
                    tableItemWrap(item.purSerialNo) +
                    tableItemWrap(item.orderAmount) +
                    tableItemWrap(item.orderType) +
                    tableItemWrap(item.expressNo) +
                    tableItemWrap(item.orderStatus) +
                    tableItemWrap("<button type=\"button\" class=\"btn btn-info\">索取样品</button><button type=\"button\" class=\"btn btn-success\">签订合同</button>") +
                    "</tr>"
                );
            });
            $("button.btn").each(function (i, item) {
                if($(item).parent().prevAll().first().html() === "已寄送样品" && $(item).text() === "索取样品"){
                    $(item).removeClass("btn-info");
                    $(item).addClass("btn-primary");
                    $(item).text("确认样品");
                }
                else if($(item).parent().prevAll().first().html() === "已确认样品" && $(item).text() === "索取样品"){
                    $(item).remove();
                }
                $(item).on("click", function () {
                    map["proSerialNo"] = $(item).parent().prevAll().last().html();
                    map["purSerialNo"] = $(item).parent().prevAll().eq(4).html();
                    if($(item).text() === "索取样品"){
                        $.ajax({
                            type: "POST",
                            url: "/order/requestSample.do",
                            data: map,
                            success: function () {
                                normalMessage("索取成功，等待对方寄送");
                                setTimeout(function () {
                                    window.location.reload();
                                }, 1000);
                            },
                            error: function () {
                                errorMessage("索取失败");
                            }
                        });
                    }
                    else if($(item).text() === "确认样品"){
                        window.location.href = "/order/confirmSample"
                    }
                    else if($(item).text() === "签订合同"){
                        $("#confirmContract").modal();
                        $("#confirmDate").click(function () {
                            if(!checkInputNull()){
                                return;
                            }
                            $.ajax({
                                type: "POST",
                                url: "/order/genContract.do",
                                data: map,
                                success: function () {
                                    normalMessage("生成合同成功");
                                    setTimeout(function () {
                                        window.location.reload();
                                    }, 1000);
                                },
                                error: function () {
                                    alert("生成合同失败");
                                }
                            });
                        });
                    }
                });
            });
        }
    });
}
function querySentSample(){
    $.ajax({
        type: "POST",
        url: "/order/querySentSample.do",
        success: function (data) {
            $.each(data, function (i, item) {
                $("#orderTableContent").append(
                    "<tr>" +
                    tableItemWrap(item.purSerialNo) +
                    tableItemWrap(item.proSerialNo) +
                    tableItemWrap(item.offerPrice) +
                    tableItemWrap(item.providerMobileNo) +
                    tableItemWrap(item.expressNo) +
                    tableItemWrap("<button type=\"button\" class=\"btn btn-primary\">确认收到</button>") +
                    "</tr>"
                );
            });
            $("button.btn-primary").each(function (i, item) {
                $(item).on("click", function () {
                    map["proSerialNo"] = $(item).parent().prevAll().eq(3).html();
                    map["purSerialNo"] = $(item).parent().prevAll().last().html();
                    $.ajax({
                        type: "POST",
                        url: "/order/confirmSample.do",
                        data: map,
                        success: function () {
                            normalMessage("确认成功");
                            setTimeout(function () {
                                window.location.reload();
                            }, 1000);
                        },
                        error: function () {
                            errorMessage("确认失败");
                        }
                    });
                });
            });
        }
    });
}
function queryRequiredSample(){
    $.ajax({
        type: "POST",
        url: "/order/queryRequiredSample.do",
        success: function (data) {
            $.each(data, function (i, item) {
                $("#orderTableContent").append(
                    "<tr>" +
                    tableItemWrap(item.purSerialNo) +
                    tableItemWrap(item.proSerialNo) +
                    tableItemWrap(item.purchaserMobileNo) +
                    tableItemWrap(item.purAddress) +
                    "<td><input type=\"text\" class=\"form-control\" id=\"expressNo\" placeholder=\"填写寄送的快递单号\"></td>" +
                    tableItemWrap("<button type=\"button\" class=\"btn btn-primary\">确认寄送</button>") +
                    "</tr>"
                );
            });
            $("button.btn-primary").each(function (i, item) {
                $(item).on("click", function () {
                    if(!checkInputNull()){
                        return;
                    }
                    map["proSerialNo"] = $(item).parent().prevAll().eq(3).html();
                    map["purSerialNo"] = $(item).parent().prevAll().last().html();
                    map["expressNo"] = $(item).parent().prevAll().first().children().val();
                    $.ajax({
                        type: "POST",
                        url: "/order/sendSample.do",
                        data: map,
                        success: function () {
                            normalMessage("确认成功");
                            setTimeout(function () {
                                window.location.reload();
                            }, 1000);
                        },
                        error: function () {
                            errorMessage("确认失败");
                        }
                    });
                });
            });
        }
    });
}
function queryProOrder(){
    $.ajax({
        type: "POST",
        url: "/order/viewProOrder.do",
        success: function (data) {
            $.each(data, function (i, item) {
                $("#orderTableContent").append(
                    "<tr>" +
                    tableItemWrap(item.proSerialNo) +
                    tableItemWrap(item.offerPrice) +
                    tableItemWrap(item.purchaserMobileNo) +
                    tableItemWrap(item.purSerialNo) +
                    tableItemWrap(item.orderAmount) +
                    tableItemWrap(item.orderType) +
                    tableItemWrap(item.orderStatus) +
                    tableItemWrap("<button type=\"button\" class=\"btn btn-info\">查看详情</button>") +
                    "</tr>"
                );
            });
            $("button.btn-info").each(function (i, item) {
                if($(item).parent().prevAll().last().html() === "查看详情"){
                    $(item).removeClass("btn-info");
                    $(item).addClass("btn-success");
                    $(item).text("查看详情");
                }
            });
        }
    });
}

function queryByType(item){
    map["queryType"] = $(item).children().attr("id");
    $("#orderTableContent").html("");
    if(window.location.pathname === "/order/showPurOrders") {
        queryPurOrder(map);
    }
    else if(window.location.pathname === "/order/recOrder"){
        queryUnOfferOrder(map);
    }
}

