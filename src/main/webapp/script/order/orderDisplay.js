/**
 * Created by zengzy19585 on 2017/9/1.
 */
var map = {};
var prev, next;

$(document).ready(function () {
    prev = $("#prev");
    next = $("#next");
    $("th").addClass("NoNewline");
    $("li[role='presentation']").each(function (i, item) {
        $(item).click(function () {
            $("#cur").html("1");
            map['pageIndex'] = 1;
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
    //his, current

    if(window.location.pathname === "/order/showHisPurOrder"){
        map["queryType"] = "his";
        $("#orderTableContent").html("");
        pager(queryPurOrder);
        queryPurOrder(map);
        checkReload(5);
        checkReload(8);
        checkReload(7);
    }

    if(window.location.pathname === "/order/showPurOrders"){
        map["queryType"] = "current";
        $("#orderTableContent").html("");
        pager(queryPurOrder);
        queryPurOrder(map);
        checkReload(5);
        checkReload(8);
        checkReload(7);
    }

    if(window.location.pathname === "/order/viewHisProOrder"){
        map["queryType"] = "his";
        $("#orderTableContent").html("");
        pager(queryProOrder);
        queryProOrder(map);
    }

    if(window.location.pathname === "/order/viewProOrder"){
        map["queryType"] = "current";
        $("#orderTableContent").html("");
        pager(queryProOrder);
        queryProOrder(map);
    }

    if(window.location.pathname === "/order/showAddOn"){
        map["serialNo"] = getUrlParam('serialNo');
        queryPurOrderAddOn(map);
        //获取上传 token，记录上传数据
        $.ajax({
            type: "POST",
            url: "/order/getUploadToken.do",
            success: function (data) {
                var putAddOn = $("#putAddOn");
                var files = [];
                putAddOn.fileinput({
                    uploadUrl: "http://up-z2.qiniu.com",
                    browseClass: "btn btn-primary btn-block",
                    dropZoneEnabled: true,
                    uploadExtraData: {"token" : data},
                    language: "zh",
                    showCaption: false,
                    allowedFileExtensions: ["jpg", "png", "jpeg", "gif", "txt", "doc", "docx", "xls", "xlsx", "pdf"],
                    hideThumbnailContent: true,
                    maxFileCount: 4,
                    maxFileSize: 20480
                }).on("fileuploaded", function(event, data, previewId, index) {
                    var item = {};
                    item["name"] = data.filenames[index];
                    item["size"] = (data.files[index].size / 1024).toFixed(4);
                    item["hash"] = data.response.hash;
                    files.push(item);
                    map['hash'] = JSON.stringify(files);
                    $("#addNew").off("click").on("click", function () {
                        $.ajax({
                            type: "POST",
                            url: "/order/newAddon.do",
                            data: map,
                            success: function (data) {
                                if(data === "success"){
                                    successMessage("更新附件成功");
                                    setTimeout(function () {
                                        window.location.reload();
                                    }, 1000);
                                }
                                else{
                                    errorMessage("更新附件失败");
                                }
                            }
                        });
                    });
                });
            }
        });
    }

    if(window.location.pathname === "/order/confirmSample"){
        querySentSample();
    }

    if(window.location.pathname === "/order/sendSample"){
        queryRequiredSample();
        checkReload(2);
    }

    if(window.location.pathname === "/order/allContacts"){
        map['pageIndex'] = 1;
        pager(queryAllContacts);
        queryAllContacts(map);
    }

    if(window.location.pathname === "/order/viewContract"){
        map['pageIndex'] = 1;
        pager(queryViewContract);
        queryViewContract(map);
    }

//ajax
    function queryPurOrder(map) {
        $.ajax({
            type: "POST",
            url: "/order/showPurOrders.do",
            data: map,
            success: function (data) {
                $.each(data, function (i, item) {
                    if(item.expectPrice === -1){
                        item.expectPrice = "用户未设置";
                    }
                    else{
                        item.expectPrice = item.expectPrice + "元";
                    }
                    var temp = item.datetime.split(":");
                    item.datetime = temp[0] + ":" + temp[1];
                    var btn = "<button my-attr='op' type=\"button\" class=\"btn btn-danger\">取消需求</button><button my-attr='op' type=\"button\" class=\"btn btn-warning\">修改需求</button>";
                    if(item.orderStatusNo === 9 || item.orderStatusNo === 10){
                        btn = "<a target='_blank' class='btn btn-success' href='"+item.addonUrl+"'>下载合同</a>"
                    }
                    else if(item.orderStatusNo === 5){
                        btn = "<a class='btn btn-success' href='/order/viewContract?purSerialNo="+item.purSerialNo+"&queryType=his'>查看合同</a>"
                    }
                    $("#orderTableContent").append(
                        "<tr>" +
                        tableItemWrap(item.purSerialNo) +
                        tableItemWrap(item.typeContent) +
                        tableItemWrap(item.expectPrice) +
                        tableItemWrap(item.orderAmount + item.typeUnit) +
                        tableItemWrap(item.datetime) +
                        tableItemWrap(item.expireDate) +
                        tableItemWrap(item.providerName) +
                        tableItemWrap(item.orderStatus) +
                        tableItemWrap(btn) +
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
                    if($(item).parent().prevAll().first().html() === "撤销"){
                        $(item).remove();
                    }
                    else if($(item).parent().prevAll().first().html() === "已报价" || $(item).parent().prevAll().first().html() === "待寄送样品" || $(item).parent().prevAll().first().html() === "检验样品"){
                        rmModify(item);
                        $(item).removeClass("btn-danger");
                        $(item).addClass("btn-info");
                        $(item).text("查看报价");
                    }
                    else if($(item).parent().prevAll().first().html() === "合同被拒绝"){
                        if($(item).text() === "修改需求"){
                            $(item).removeClass("btn-warning");
                            $(item).addClass("btn-info");
                            $(item).text("查看报价");
                        }
                    }
                    else if($(item).parent().prevAll().first().html() === "已发送合同"){
                        rmModify(item);
                        $(item).removeClass("btn-danger");
                        $(item).addClass("btn-success");
                        $(item).text("更改合同");
                    }
                    $(item).on("click", function () {
                        if($(item).attr("my-attr") !== "op"){
                            return;
                        }
                        var param = {"pur_serial_no" : $(this).parent().prevAll().last().html()};
                        var hint;
                        if($(item).text() === "取消需求"){
                            var order_status = 8;
                            hint = "是否取消需求";
                        }
                        else if($(item).text() === "查看报价"){
                            window.location.href="/order/viewAllOffer?serialNo=" + param["pur_serial_no"];
                            return;
                        }
                        else if($(item).text() === "修改需求"){
                            window.location.href="/order/modifyPurOrder?serialNo=" + param["pur_serial_no"];
                            return;
                        }
                        else if($(item).text() === "更改合同"){
                            showContract(param);
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
    function showContract(param) {
        $.ajax({
            type: "POST",
            url: "/order/getContractSn.do",
            data: param,
            success: function (data) {
                window.location.href = "/order/showAddOn?serialNo=" + data;
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
                    if(item.moreDetail !== null) {
                        if (item.moreDetail.length > 10) {
                            item.moreDetail =
                                "<button detail='" +
                                item.moreDetail +
                                "' class='btn btn-info showMore'>查看详情</button>";
                        }
                    }
                    else{
                        item.moreDetail = "";
                    }
                    if(item.showExpectPrice === 1){
                        item.expectPrice = "采购方未展示";
                    }
                    else{
                        if(item.expectPrice === -1){
                            item.expectPrice = "用户未设置";
                        }
                        else{
                            item.expectPrice = item.expectPrice + "元";
                        }
                    }
                    if(item.offeredPrice === -1){
                        item.offeredPrice = "未报价";
                    }
                    else{
                        item.offeredPrice += " 元";
                    }
                    var temp = item.datetime.split(":");
                    item.datetime = temp[0] + ":" + temp[1];
                    $("#orderTableContent").append(
                        "<tr>" +
                        tableItemWrap(item.purSerialNo) +
                        tableItemWrap(item.expectPrice) +
                        tableItemWrap(item.orderAmount + item.typeUnit) +
                        tableItemWrap(item.moreDetail) +
                        tableItemWrap("<a class='btn btn-info' href='showAddOn?serialNo=" + item.purSerialNo + "'>" + item.addonNum + "</a>") +
                        tableItemWrap(item.offeredPrice) +
                        tableItemWrap(item.providerName) +
                        tableItemWrap(item.typeContent) +
                        tableItemWrap(item.orderStatus) +
                        tableItemWrap(item.datetime) +
                        tableItemWrap(item.expireDate) +
                        tableItemWrap("<button type=\"button\" class=\"btn btn-success\">提供报价</button>") +
                        "</tr>"
                    );
                });
                $("button.showMore").each(function (i, item) {
                    $(item).click(function () {
                        $("#details").modal();
                        $("#detailContent").html($(this).attr("detail"));
                    });
                });
                $("button.btn-success").each(function (i, item) {
                    if($(item).parent().prevAll().eq(5).html() !== "未报价"){
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
                            if($(item).parent().prevAll().eq(5).html() === "未报价"){
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
                var foo = data[0];
                $("#detail").html("<h3>需求序列号："+foo.purSerialNo+"；需求类型："+foo.orderType+"；需求数量："+foo.orderAmount+"</h3>");
                var tabName = $("li.active").children().html();
                $.each(data, function (i, item) {
                    var btn = "";
                    if(tabName === "查看所有报价"){
                        btn="<button type=\"button\" class=\"btn btn-info\">索取样品</button><button type=\"button\" class=\"btn btn-success\">签订合同</button><button type=\"button\" class=\"btn btn-info\" disabled>收到样品</button><button type=\"button\" class=\"btn btn-success\" disabled>接受样品</button><button type=\"button\" class=\"btn btn-danger\" disabled>拒绝样品</button><button class='btn btn-info' disabled>确认完成</button>";
                        if(item.orderStatus === "待寄送样品" || item.orderStatus === "样品不合格"){
                            btn="<button type=\"button\" class=\"btn btn-info\" disabled>索取样品</button><button type=\"button\" class=\"btn btn-success\" disabled>签订合同</button><button type=\"button\" class=\"btn btn-info\" disabled>收到样品</button><button type=\"button\" class=\"btn btn-success\" disabled>接受样品</button><button type=\"button\" class=\"btn btn-danger\" disabled>拒绝样品</button><button class='btn btn-info' disabled>确认完成</button>";
                        }
                        else if(item.orderStatus === "已寄送样品"){
                            btn="<button type=\"button\" class=\"btn btn-info\" disabled>索取样品</button><button type=\"button\" class=\"btn btn-success\" disabled>签订合同</button><button type=\"button\" class=\"btn btn-info\">收到样品</button><button type=\"button\" class=\"btn btn-success\" disabled>接受样品</button><button type=\"button\" class=\"btn btn-danger\" disabled>拒绝样品</button><button class='btn btn-info' disabled>确认完成</button>";
                        }
                        else if(item.orderStatus === "已确认样品"){
                            btn="<button type=\"button\" class=\"btn btn-info\" disabled>索取样品</button><button type=\"button\" class=\"btn btn-success\" disabled>签订合同</button><button type=\"button\" class=\"btn btn-info\" disabled>收到样品</button><button type=\"button\" class=\"btn btn-success\">接受样品</button><button type=\"button\" class=\"btn btn-danger\">拒绝样品</button><button class='btn btn-info' disabled>确认完成</button>";
                        }
                        else if(item.orderStatus === "样品合格"){
                            btn="<button type=\"button\" class=\"btn btn-info\" disabled>索取样品</button><button type=\"button\" class=\"btn btn-success\">签订合同</button><button type=\"button\" class=\"btn btn-info\" disabled>收到样品</button><button type=\"button\" class=\"btn btn-success\" disabled>接受样品</button><button type=\"button\" class=\"btn btn-danger\" disabled>拒绝样品</button><button class='btn btn-info' disabled>确认完成</button>";
                        }
                        else if(item.orderStatus === "已签合同"){
                            btn="<button type=\"button\" class=\"btn btn-info\" disabled>索取样品</button><button type=\"button\" class=\"btn btn-success\" disabled>签订合同</button><button type=\"button\" class=\"btn btn-info\" disabled>收到样品</button><button type=\"button\" class=\"btn btn-success\" disabled>接受样品</button><button type=\"button\" class=\"btn btn-danger\" disabled>拒绝样品</button><button class='btn btn-info'>确认完成</button>";
                        }
                        else if(item.orderStatus === "合同被拒绝"){
                            btn="<button type=\"button\" class=\"btn btn-info\" disabled>索取样品</button><button type=\"button\" class=\"btn btn-success\">签订合同</button><button type=\"button\" class=\"btn btn-info\" disabled>收到样品</button><button type=\"button\" class=\"btn btn-success\" disabled>接受样品</button><button type=\"button\" class=\"btn btn-danger\" disabled>拒绝样品</button><button class='btn btn-info' disabled>确认完成</button>";
                        }
                    }
                    else if(tabName === "样品接收"){
                        if(item.orderStatus === "待寄送样品"){
                            btn = "<button type=\"button\" class=\"btn btn-info\" disabled>收到样品</button>";
                        }
                        else {
                            btn = "<button type=\"button\" class=\"btn btn-info\">收到样品</button>";
                        }
                    }
                    else if(tabName === "样品判断"){
                        btn="<button type=\"button\" class=\"btn btn-success\">接受样品</button><button type=\"button\" class=\"btn btn-danger\">拒绝样品</button>";
                    }
                    else if(tabName === "发送合同"){
                        btn="<button type=\"button\" class=\"btn btn-success\">签订合同</button><button class='btn btn-info'>确认完成</button>";
                    }
                    $("#orderTableContent").append(
                        "<tr>" +
                        tableItemWrap(item.offerPrice) +
                        tableItemWrap(item.proUserName) +
                        tableItemWrap(item.providerMobileNo) +
                        tableItemWrap(item.expressNo) +
                        tableItemWrap(item.orderStatus) +
                        tableItemWrap(btn) +
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
                $("td").find("button.btn").each(function (i, item) {
                    var statusName = $(item).parent().prevAll().first().html();
                    if(statusName === "合同被拒绝" && $(item).text() === "签订合同"){
                        $(item).text("重发合同");
                    }
                    else if(statusName === "合同被拒绝" && $(item).text() !== "签订合同"){
                        $(item).remove();
                    }
                    $(item).on("click", function () {
                        map['cur'] = $("#cur").html();
                        var index;
                        if(tabName === "查看所有报价"){
                            index = parseInt(i/5);
                        }
                        else{
                            index = parseInt(i/2);
                        }
                        if($(item).text() === "索取样品"){
                            map["proSerialNo"] = data[index].proSerialNo;
                            map["purSerialNo"] = data[index].purSerialNo;
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
                        else if($(item).text() === "收到样品"){
                            map["proSerialNo"] = data[index].proSerialNo;
                            map["purSerialNo"] = data[index].purSerialNo;
                            $.ajax({
                                type: "POST",
                                url: "/order/confirmSample.do",
                                data: map,
                                success: function () {
                                    successMessage("确认成功");
                                    setTimeout(function () {
                                        window.location.reload();
                                    }, 1000);
                                },
                                error: function () {
                                    errorMessage("确认失败");
                                }
                            });
                        }
                        else if($(item).text() === "接受样品"){
                            map["serialNo"] = data[index].proSerialNo;
                            $.ajax({
                                type: "POST",
                                url: "/order/accSample.do",
                                data: map,
                                success: function () {
                                    successMessage("确认成功");
                                    setTimeout(function () {
                                        window.location.reload();
                                    }, 1000);
                                },
                                error: function () {
                                    errorMessage("确认失败");
                                }
                            });
                        }
                        else if($(item).text() === "拒绝样品"){
                            map["serialNo"] = data[index].proSerialNo;
                            $.ajax({
                                type: "POST",
                                url: "/order/decSample.do",
                                data: map,
                                success: function () {
                                    successMessage("拒绝成功");
                                    setTimeout(function () {
                                        window.location.reload();
                                    }, 1000);
                                },
                                error: function () {
                                    errorMessage("拒绝失败");
                                }
                            });
                        }
                        else if($(item).text() === "签订合同" || $(item).text() === "重发合同"){
                            map["proSerialNo"] = data[index].proSerialNo;
                            map["purSerialNo"] = data[index].purSerialNo;
                            $("#confirmContract").modal();
                            $("#confirm").click(function () {
                                $.ajax({
                                    type: "POST",
                                    url: "/order/genContract.do",
                                    data: map,
                                    success: function (data) {
                                        if(data === "success") {
                                            successMessage("生成合同成功");
                                            setTimeout(function () {
                                                window.location.href = "/order/showPurOrders";
                                            }, 1000);
                                        }
                                        else{
                                            alert("生成合同失败");
                                        }
                                    },
                                    error: function () {
                                        alert("生成合同失败");
                                    }
                                });
                            });
                        }
                        else if($(item).text() === "确认完成") {
                            map["contractSn"] = data[index].contractSn;
                            $.ajax({
                                type: "POST",
                                url: "/order/finishOrder.do",
                                data: map,
                                success: function (data) {
                                    if(data === "success") {
                                        successMessage("确认成功！");
                                        setTimeout(function () {
                                            window.location.href = "/";
                                        }, 1000);
                                    }
                                    else{
                                        errorMessage("确认失败");
                                    }
                                },
                                error: function () {
                                    errorMessage("确认失败");
                                }
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
        map['proSerialNo'] = getUrlParam('proSerialNo');
        $.ajax({
            type: "POST",
            url: "/order/queryRequiredSample.do",
            success: function (data) {
                $.each(data, function (i, item) {
                    item.datetime = item.datetime.split(" ")[0];
                    $("#orderTableContent").append(
                        "<tr>" +
                        tableItemWrap(item.purSerialNo) +
                        tableItemWrap(item.proSerialNo) +
                        tableItemWrap(item.purchaserMobileNo) +
                        tableItemWrap(item.orderType) +
                        tableItemWrap(item.datetime) +
                        tableItemWrap(item.purAddress) +
                        "<td><input type=\"text\" class=\"form-control\" id=\"expressNo\" placeholder=\"填写寄送的快递单号\"></td>" +
                        tableItemWrap("<button type=\"button\" class=\"btn btn-primary\">确认寄送</button>") +
                        "</tr>"
                    );
                });
                $("button.btn-primary").each(function (i, item) {
                    $(item).on("click", function () {
                        if($.trim($(item).parent().prevAll().first().children().val()).length === 0){
                            errorMessage("请输入订单号！");
                            return;
                        }
                        map["proSerialNo"] = $(item).parent().prevAll().eq(5).html();
                        map["purSerialNo"] = $(item).parent().prevAll().last().html();
                        map["expressNo"] = $(item).parent().prevAll().first().children().val();
                        $.ajax({
                            type: "POST",
                            url: "/order/sendSample.do",
                            data: map,
                            success: function () {
                                normalMessage("确认成功");
                                setTimeout(function () {
                                    window.location.href = '/order/viewProOrder';
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
    function queryProOrder(map){
        $.ajax({
            type: "POST",
            url: "/order/viewProOrder.do",
            data: map,
            success: function (data) {
                $.each(data, function (i, item) {
                    var btn = "<a href='/order/purOrderDetail?proSerialNo="+item.proSerialNo+"&queryType=current' class='btn btn-warning'>报价操作</a>";
                    // if(item.orderStatus === "待寄送样品"){
                    //     btn += "<a href='/order/sendSample?proSerialNo="+item.proSerialNo+"' class='btn btn-success'>寄送样品</a>";
                    // }
                    // else if(item.orderStatus === "已发送合同"){
                    //     btn += "<a href='/order/viewContract?proSerialNo="+item.proSerialNo+"&queryType=current' class='btn btn-success'>查看合同</a>";
                    // }
                    // else if(item.orderStatus === "已签合同"){
                    //     btn += "<a href='/order/viewContract?proSerialNo="+item.proSerialNo+"&queryType=his' class='btn btn-success'>查看合同</a>";
                    // }
                    btn += "<button type=\"button\" class=\"btn btn-info\">查看详情</button>";
                    var temp = item.datetime.split(":");
                    item.datetime = temp[0]+":"+temp[1];
                    $("#orderTableContent").append(
                        "<tr>" +
                        tableItemWrap(item.proSerialNo) +
                        tableItemWrap(item.offerPrice) +
                        tableItemWrap(item.purUserName) +
                        tableItemWrap(item.purchaserMobileNo) +
                        tableItemWrap(item.orderAmount) +
                        tableItemWrap(item.orderType) +
                        tableItemWrap(item.datetime) +
                        tableItemWrap(item.orderStatus) +
                        tableItemWrap(btn) +
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
                    $(item).on("click", function () {
                        if($(item).text() === "查看详情"){
                            $("#purOrderDetail").modal();
                            $("#purMobileNo").val(data[i].purchaserMobileNo);
                            $("#orderType").val(data[i].orderType);
                            $("#orderAmount").val(data[i].orderAmount);
                            $("#placeTime").val(data[i].purDateTime.split(":")[0]+":"+data[i].datetime.split(":")[1]);
                        }
                    });
                });
            }
        });
    }
    function queryPurOrderAddOn(){
        $.ajax({
            type: "POST",
            url: "/order/showAddOn.do",
            data: map,
            success: function (data) {
                if(data.length === 0){
                    $("#addonAttch").remove();
                    return;
                }
                if(data[0].userType === 1 || data[0].userType === 0) {
                    $("#tableHead").append("<th>操作</th>");
                    if(data[0].privilege === 0){
                        $("#addonAttch").remove();
                    }
                    $.each(data, function (i, item) {
                        $("#orderTableContent").append(
                            "<tr>" +
                            tableItemWrap(item.orderSerialNo) +
                            tableItemWrap(item.fileName) +
                            tableItemWrap(item.fileSize) +
                            tableItemWrap("<a class='btn btn-info' target='_blank' href='" + item.addonUrl + "'>点击下载</a>") +
                            tableItemWrap("<button class='btn btn-danger' file-key='" + item.fileKey + "' addon-sn='" + item.addonSerialNo + "'>删除此附件</button>") +
                            "</tr>"
                        );
                    });
                    $("td").find("button.btn-danger").on("click", function () {
                        var param = {};
                        param['fileKey'] = $(this).attr("file-key");
                        param['addonSerial'] = $(this).attr("addon-sn");
                        var result = confirm("是否删除");
                        if(!result){
                            return;
                        }
                        $.ajax({
                            type: "POST",
                            url: "/order/deleteAddon.do",
                            data: param,
                            success: function (data) {
                                if(data === 'success'){
                                    $(this).parent().parent().remove();
                                    successMessage("删除成功");
                                    setTimeout(function () {
                                        window.location.reload();
                                    }, 1000);
                                }
                                else{
                                    errorMessage("删除失败");
                                }
                            }
                        });
                    });
                }
                else if(data[0].userType === 2) {
                    $("#addonAttch").remove();
                    $.each(data, function (i, item) {
                        $("#orderTableContent").append(
                            "<tr>" +
                            tableItemWrap(item.addonSerialNo) +
                            tableItemWrap(item.fileName) +
                            tableItemWrap(item.fileSize + " MB") +
                            tableItemWrap("<a class='btn btn-info' target='_blank' href='" + item.addonUrl + "'>点击下载</a>") +
                            "</tr>"
                        );
                    });
                }
            }
        });
    }
    function fileUpload(map){
        //获取上传 token，记录上传数据
        $.ajax({
            type: "POST",
            url: "/order/getUploadToken.do",
            success: function (data) {
                var putAddOn = $("#putAddOn");
                var files = [];
                putAddOn.fileinput({
                    uploadUrl: "http://up-z2.qiniu.com",
                    browseClass: "btn btn-primary btn-block",
                    uploadExtraData: {"token" : data},
                    language: "zh",
                    showCaption: false,
                    allowedFileExtensions: ["jpg", "png", "jpeg", "gif", "txt", "doc", "docx", "xls", "xlsx", "pdf"],
                    maxFileCount: 4,
                    maxFileSize: 20480
                }).on("fileuploaded", function(event, data, previewId, index) {
                    var item = {};
                    item["name"] = data.filenames[index];
                    item["size"] = (data.files[index].size / 1024).toFixed(4);
                    item["hash"] = data.response.hash;
                    files.push(item);
                    map['hash'] = JSON.stringify(files);
                });
            }
        });
    }
    function queryViewContract(map) {
        map['proSerialNo'] = getUrlParam('proSerialNo');
        map['queryType'] = getUrlParam('queryType');
        map['purSerialNo'] = getUrlParam('purSerialNo');
        $.ajax({
            type: "POST",
            url: "/order/getAllContract.do",
            data: map,
            success: function (data) {
                $.each(data, function (i, item) {
                    var url = "<a class='btn btn-info' target='_blank' href='" + item.addonUrl + "'>下载合同</a>";
                    var btn = '';
                    if(item.userType === 1){
                        $("#tableHead").html("<th>合同序号</th><th>需求序号</th><th>合同下载</th><th>操作</th>");
                    }
                    else if(item.userType === 2){
                        $("#tableHead").html("<th>合同序号</th><th>报价序号</th><th>合同下载</th><th>操作</th>");
                    }
                    if(map['queryType'] !== 'his'){
                        if (item.userType === 1) {
                            btn = "<button class='btn btn-info'>更改合同</button>";
                        }
                        else {
                            btn = "<button class='btn btn-success'>接受合同</button><button class='btn btn-danger'>拒绝合同</button>";
                        }
                    }
                    else{
                        if (item.userType === 1 && item.status === 0) {
                            btn = "<button class='btn btn-info'>确认完成</button>";
                        }
                        else{
                            btn = "订单已完成";
                        }
                    }
                    if(item.userType === 1){
                        $("#orderTableContent").append(
                            "<tr>" +
                            tableItemWrap(item.contractSn) +
                            tableItemWrap(item.purOrdSn) +
                            tableItemWrap(url) +
                            tableItemWrap(btn) +
                            "</tr>"
                        );
                    }
                    else if(item.userType === 2){
                        $("#orderTableContent").append(
                            "<tr>" +
                            tableItemWrap(item.contractSn) +
                            tableItemWrap(item.proOrdSn) +
                            tableItemWrap(url) +
                            tableItemWrap(btn) +
                            "</tr>"
                        );
                    }
                    else{
                        $("#orderTableContent").append(
                            "<tr>" +
                            tableItemWrap(item.contractSn) +
                            tableItemWrap(item.purOrdSn) +
                            tableItemWrap(item.proOrdSn) +
                            tableItemWrap(url) +
                            tableItemWrap(btn) +
                            "</tr>"
                        );
                    }
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
                        if($(item).text() === "接受合同") {
                            map["contractSn"] = $(item).parent().prevAll().last().html();
                            $.ajax({
                                type: "POST",
                                url: "/order/acceptContract.do",
                                data: map,
                                success: function (data) {
                                    if(data === "success") {
                                        successMessage("接受成功");
                                        setTimeout(function () {
                                            window.location.href = '/order/viewProOrder';
                                        }, 1000);
                                    }
                                    else{
                                        errorMessage("接受失败");
                                    }
                                },
                                error: function () {
                                    errorMessage("接受失败");
                                }
                            });
                        }
                        else if($(item).text() === "拒绝合同") {
                            map["contractSn"] = $(item).parent().prevAll().last().html();
                            $.ajax({
                                type: "POST",
                                url: "/order/declineContract.do",
                                data: map,
                                success: function (data) {
                                    if(data === "success") {
                                        successMessage("拒绝成功");
                                        setTimeout(function () {
                                            window.location.href = '/order/viewProOrder';
                                        }, 1000);
                                    }
                                    else{
                                        errorMessage("拒绝失败");
                                    }
                                },
                                error: function () {
                                    errorMessage("拒绝失败");
                                }
                            });
                        }
                        else if($(item).text() === "更改合同") {
                            map["pur_serial_no"] = $(item).parent().prevAll().eq(1).html();
                            showContract(map);
                        }
                        else if($(item).text() === "确认完成") {
                            map["contractSn"] = $(item).parent().prevAll().last().html();
                            $.ajax({
                                type: "POST",
                                url: "/order/finishOrder.do",
                                data: map,
                                success: function (data) {
                                    if(data === "success") {
                                        successMessage("确认成功！");
                                        setTimeout(function () {
                                            window.location.href = "/";
                                        }, 1000);
                                    }
                                    else{
                                        errorMessage("确认失败");
                                    }
                                },
                                error: function () {
                                    errorMessage("确认失败");
                                }
                            });
                        }
                    });
                });
            }
        });
    }
    function queryAllContract(map) {
        map['proSerialNo'] = getUrlParam('proSerialNo');
        $.ajax({
            type: "POST",
            url: "/order/getAllContract.do",
            data: map,
            success: function (data) {
                $.each(data, function (i, item) {
                    var url = "<a class='btn btn-info' target='_blank' href='" + item.addonUrl + "'>下载合同</a>";
                    var btn = '';
                    if(item.userType === 1){
                        $("#tableHead").html("<th>合同序号</th><th>需求序号</th><th>合同下载</th><th>操作</th>");
                    }
                    else if(item.userType === 2){
                        $("#tableHead").html("<th>合同序号</th><th>报价序号</th><th>合同下载</th><th>操作</th>");
                    }
                    if(map['queryType'] !== 'his'){
                        if (item.userType === 1) {
                            btn = "<button class='btn btn-info'>更改合同</button>";
                        }
                        else {
                            btn = "<button class='btn btn-success'>接受合同</button><button class='btn btn-danger'>拒绝合同</button>";
                        }
                    }
                    else{
                        if (item.userType === 1 && item.status === 0) {
                            btn = "<button class='btn btn-info'>确认完成</button>";
                        }
                        else{
                            btn = "订单已完成";
                        }
                    }
                    if(item.userType === 1){
                        $("#orderTableContent").append(
                            "<tr>" +
                            tableItemWrap(item.contractSn) +
                            tableItemWrap(item.purOrdSn) +
                            tableItemWrap(url) +
                            tableItemWrap(btn) +
                            "</tr>"
                        );
                    }
                    else if(item.userType === 2){
                        $("#orderTableContent").append(
                            "<tr>" +
                            tableItemWrap(item.contractSn) +
                            tableItemWrap(item.proOrdSn) +
                            tableItemWrap(url) +
                            tableItemWrap(btn) +
                            "</tr>"
                        );
                    }
                    else{
                        $("#orderTableContent").append(
                            "<tr>" +
                            tableItemWrap(item.contractSn) +
                            tableItemWrap(item.purOrdSn) +
                            tableItemWrap(item.proOrdSn) +
                            tableItemWrap(url) +
                            tableItemWrap(btn) +
                            "</tr>"
                        );
                    }
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
                        if($(item).text() === "接受合同") {
                            map["contractSn"] = $(item).parent().prevAll().last().html();
                            $.ajax({
                                type: "POST",
                                url: "/order/acceptContract.do",
                                data: map,
                                success: function (data) {
                                    if(data === "success") {
                                        successMessage("接受成功");
                                        setTimeout(function () {
                                            window.location.reload();
                                        }, 1000);
                                    }
                                    else{
                                        errorMessage("接受失败");
                                    }
                                },
                                error: function () {
                                    errorMessage("接受失败");
                                }
                            });
                        }
                        else if($(item).text() === "拒绝合同") {
                            map["contractSn"] = $(item).parent().prevAll().last().html();
                            $.ajax({
                                type: "POST",
                                url: "/order/declineContract.do",
                                data: map,
                                success: function (data) {
                                    if(data === "success") {
                                        successMessage("拒绝成功");
                                        setTimeout(function () {
                                            window.location.reload();
                                        }, 1000);
                                    }
                                    else{
                                        errorMessage("拒绝失败");
                                    }
                                },
                                error: function () {
                                    errorMessage("拒绝失败");
                                }
                            });
                        }
                        else if($(item).text() === "更改合同") {
                            map["pur_serial_no"] = $(item).parent().prevAll().eq(1).html();
                            showContract(map);
                        }
                        else if($(item).text() === "确认完成") {
                            map["contractSn"] = $(item).parent().prevAll().last().html();
                            $.ajax({
                                type: "POST",
                                url: "/order/finishOrder.do",
                                data: map,
                                success: function (data) {
                                    if(data === "success") {
                                        successMessage("确认成功！");
                                        setTimeout(function () {
                                            window.location.href = "/";
                                        }, 1000);
                                    }
                                    else{
                                        errorMessage("确认失败");
                                    }
                                },
                                error: function () {
                                    errorMessage("确认失败");
                                }
                            });
                        }
                    });
                });
            }
        });
    }
    function queryAllContacts(map){
        $.ajax({
            type: "POST",
            url: "/order/getContact.do",
            data: map,
            success: function (data) {
                $.each(data, function (i, item) {
                    if(item.userType === 1) {
                        $("#tableHead").html("<th>供应商联系方式</th><th>合作次数</th><th>操作</th>");
                        $("#orderTableContent").append(
                            "<tr>" +
                            tableItemWrap(item.provider_mobile_no) +
                            tableItemWrap(item.coop_count + " 次") +
                            tableItemWrap("<button id='detail' class='btn btn-info'>查看详情</button>") +
                            "</tr>"
                        );
                    }
                    else if(item.userType === 2){
                        $("#tableHead").html("<th>采购商联系方式</th><th>合作次数</th><th>操作</th>");
                        $("#orderTableContent").append(
                            "<tr>" +
                            tableItemWrap(item.provider_mobile_no) +
                            tableItemWrap(item.coop_count + " 次") +
                            tableItemWrap("<button id='detail' class='btn btn-info'>查看详情</button>") +
                            "</tr>"
                        );
                    }
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
                $("button.btn-info").each(function (i, item) {
                    $(item).off("click").on("click", function () {
                        var param = {};
                        param['mobileNo'] = $(item).parent().prevAll().last().html();
                        $.ajax({
                            type: "POST",
                            url: "/order/getContactDetail.do",
                            data: param,
                            success: function (data) {
                                $("#contactDetail").html("");
                                $.each(data, function (i, item) {
                                    var href = "<a class=\"btn btn-success\" id=\"download\" target=\"_blank\" href=\"" + item.addonUrl + "\">点击下载合同</a>"
                                    var temp = item.datetime.split(":");
                                    item.datetime = temp[0] + ":" + temp[1];
                                    $("#contactDetail").append(
                                        "<tr>" +
                                        tableItemWrap(item.contractSn) +
                                        tableItemWrap(item.orderType) +
                                        tableItemWrap(item.orderAmount) +
                                        tableItemWrap(item.orderPrice) +
                                        tableItemWrap(item.datetime) +
                                        tableItemWrap(href) +
                                        "</tr>"
                                    );
                                    $("#purOrderDetail").modal();
                                });
                            }
                        })
                    })
                });
            }
        });
    }
    function checkReload(index) {
        var current = 0;
        $.ajax({
            type: "POST",
            url: "/order/getMessage.do",
            success: function (data) {
                $.each(data, function (i, item) {
                    if(item.message_type_no === index){
                        current = item.message_cnt;
                    }
                });
            }
        });
        setInterval(function () {
            $.ajax({
                type: "POST",
                url: "/order/getMessage.do",
                success: function (data) {
                    $.each(data, function (i, item) {
                        if(item.message_type_no === index){
                            if(current !== item.message_cnt){
                                window.location.reload();
                            }
                        }
                    });
                }
            });
        }, 5000);
    }
    function queryRefPurOrder(map) {
        map['proSerialNo'] = getUrlParam('proSerialNo');
        var tabName = $("li.active").children().html();
        $.ajax({
            type: "POST",
            url: "/order/queryRefPurOrder.do",
            data: map,
            success: function (data) {
                var foo = data;
                var input = '';
                var btn = '';
                if(tabName === '查看当前需求'){
                    $("#detail").html("<h3>需求类型："+foo.orderType+"；需求数量："+foo.orderAmount+"；需求状态："+foo.purOrderStatus+"</h3><br><h4>采购商地址："+foo.purAddress+"</h4>");
                    if(foo.orderStatus === '待寄送样品'){
                        btn = '<button class="btn btn-info">寄送样品</button><button class="btn btn-success" disabled>接受合同</button><button class="btn btn-danger" disabled>拒绝合同</button>';
                        input = "<input type=\"text\" class=\"form-control\" id=\"expressNo\" placeholder=\"填写寄送的快递单号\">";
                    }
                    else if(foo.orderStatus === '已发送合同'){
                        btn = '<button class="btn btn-info" disabled>寄送样品</button><button class="btn btn-success" >接受合同</button><button class="btn btn-danger" >拒绝合同</button>';
                        input = "<input type=\"text\" class=\"form-control\" id=\"expressNo\" placeholder=\"填写寄送的快递单号\" readonly>";
                    }
                    else{
                        btn = '<button class="btn btn-info" disabled>寄送样品</button><button class="btn btn-success" disabled>接受合同</button><button class="btn btn-danger" disabled>拒绝合同</button>';
                        input = "<input type=\"text\" class=\"form-control\" id=\"expressNo\" placeholder=\"填写寄送的快递单号\" readonly>";
                    }
                    $("#orderTableContent").append(
                        "<tr>" +
                        tableItemWrap(foo.proUserName) +
                        tableItemWrap(foo.purchaserMobileNo) +
                        tableItemWrap(foo.purDateTime) +
                        tableItemWrap(foo.orderStatus) +
                        tableItemWrap(input) +
                        tableItemWrap(btn) +
                        "</tr>"
                    );
                }
                else if(tabName === '寄送样品' && data !== ""){
                    btn = '<button class="btn btn-info">寄送样品</button>';
                    input = "<input type=\"text\" class=\"form-control\" id=\"expressNo\" placeholder=\"填写寄送的快递单号\" readonly>";
                    $("#orderTableContent").append(
                        "<tr>" +
                        tableItemWrap(foo.proUserName) +
                        tableItemWrap(foo.purchaserMobileNo) +
                        tableItemWrap(foo.purDateTime) +
                        tableItemWrap(foo.orderStatus) +
                        tableItemWrap(input) +
                        tableItemWrap(btn) +
                        "</tr>"
                    );
                }
                else if(tabName === '查阅合同' && data !== ""){
                    btn = '<button class="btn btn-success" >接受合同</button><button class="btn btn-danger" >拒绝合同</button>';
                    input = "<input type=\"text\" class=\"form-control\" id=\"expressNo\" placeholder=\"填写寄送的快递单号\" readonly>";
                    $("#orderTableContent").append(
                        "<tr>" +
                        tableItemWrap(foo.proUserName) +
                        tableItemWrap(foo.purchaserMobileNo) +
                        tableItemWrap(foo.purDateTime) +
                        tableItemWrap(foo.orderStatus) +
                        tableItemWrap(input) +
                        tableItemWrap(btn) +
                        "</tr>"
                    );
                }
                var expNo = $("td").find("input");
                if(foo.expressNo !== '对方未寄送') {
                    expNo.val(foo.expressNo);
                }
                $("td").find("button").each(function (i, item) {
                    $(item).off("click").on("click", function () {
                        var itemName = $(item).html();
                        if (itemName === "寄送样品") {
                            if ($.trim(expNo.val()).length === 0) {
                                errorMessage("请输入快递单号！");
                                return;
                            }
                            map["proSerialNo"] = foo.proSerialNo;
                            map["purSerialNo"] = foo.purSerialNo;
                            map["expressNo"] = expNo.val();
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
                        }
                        else if (itemName === "接受合同") {
                            map["contractSn"] = foo.contractSn;
                            $.ajax({
                                type: "POST",
                                url: "/order/acceptContract.do",
                                data: map,
                                success: function (data) {
                                    if (data === "success") {
                                        successMessage("接受成功");
                                        setTimeout(function () {
                                            window.location.reload();
                                        }, 1000);
                                    }
                                    else {
                                        errorMessage("接受失败");
                                    }
                                },
                                error: function () {
                                    errorMessage("接受失败");
                                }
                            });
                        }
                        else if (itemName === "拒绝合同") {
                            map["contractSn"] = foo.contractSn;
                            $.ajax({
                                type: "POST",
                                url: "/order/declineContract.do",
                                data: map,
                                success: function (data) {
                                    if (data === "success") {
                                        successMessage("拒绝成功");
                                        setTimeout(function () {
                                            window.location.reload();
                                        }, 1000);
                                    }
                                    else {
                                        errorMessage("拒绝失败");
                                    }
                                },
                                error: function () {
                                    errorMessage("拒绝失败");
                                }
                            });
                        }
                    });
                })
            }
        });
    }

//simple logic
    function queryByType(item){
        map["queryType"] = $(item).children().attr("id");
        $("#orderTableContent").html("");
        if(window.location.pathname === "/order/showPurOrders") {
            pager(queryPurOrder);
            queryPurOrder(map);
            checkReload(5);
            checkReload(8);
            checkReload(7);
        }
        else if(window.location.pathname === "/order/recOrder"){
            pager(queryUnOfferOrder);
            queryUnOfferOrder(map);
            checkReload(1);
        }
        else if(window.location.pathname === "/order/allContract"){
            pager(queryAllContract);
            queryAllContract(map);
            checkReload(7);
            checkReload(4);
        }
        else if(window.location.pathname === "/order/viewProOrder"){
            pager(queryProOrder);
            queryProOrder(map);
        }
        else if(window.location.pathname === "/order/viewAllOffer"){
            fileUpload(map);
            map["serialNo"] = getUrlParam('serialNo');
            map["queryType"] = $(item).children().html();
            pager(queryOfferOrder);
            queryOfferOrder(map);

            var current;
            $.ajax({
                url: "/order/getOfferNum.do",
                type: "POST",
                data: map,
                success: function (data) {
                    current = data;
                }
            });
            setInterval(function () {
                $.ajax({
                    url: "/order/getOfferNum.do",
                    type: "POST",
                    data: map,
                    success: function (data) {
                        if(current !== data){
                            window.location.reload();
                        }
                    }
                });
            }, 1000);
            checkReload(8);
        }
        else if(window.location.pathname === "/order/purOrderDetail"){
            map["serialNo"] = getUrlParam('serialNo');
            map["queryType"] = $(item).children().html();
            queryRefPurOrder(map);
            checkReload(2);
            checkReload(3);
            checkReload(4);
        }
    }
    function rmModify(item) {
        if($(item).html() === "修改需求"){
            $(item).remove();
        }
    }
});
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
        map['pageIndex'] = pageNo;
        cur.html(pageNo);
        func(map);
    });
    $("li.current").off("click").on("click", function () {
        $("#orderTableContent").html("");
        map['pageIndex'] = cur.html();
        func(map);
    });
    $("li.next").off("click").on("click", function () {
        if($(this).hasClass("disabled")){
            return;
        }
        $("#orderTableContent").html("");
        var pageNo = parseInt(cur.html(), 10) + 1;
        map['pageIndex'] = pageNo;
        cur.html(pageNo);
        func(map);
    });
}
