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

    if(window.location.pathname === "/order/viewAllOffer"){
        fileUpload(map);
        map["serialNo"] = getUrlParam('serialNo');
        pager(queryOfferOrder);
        queryOfferOrder(map);
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
                    var temp = data.filenames[index].split(".");
                    item["name"] = data.filenames[index];
                    item["size"] = (data.files[index].size / Math.pow(1024, 2)).toFixed(2);
                    item["extension"] = temp[temp.length - 1];
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
    }

    if(window.location.pathname === "/order/viewProOrder"){
        queryProOrder();
    }

    if(window.location.pathname === "/order/allContacts"){
        map['pageIndex'] = 1;
        pager(queryAllContacts);
        queryAllContacts(map);
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
                    $("#orderTableContent").append(
                        "<tr>" +
                        tableItemWrap(item.purSerialNo) +
                        tableItemWrap(item.expectPrice) +
                        tableItemWrap(item.orderAmount + item.typeUnit) +
                        tableItemWrap(item.purchaserName) +
                        tableItemWrap(item.providerName) +
                        tableItemWrap(item.typeContent) +
                        tableItemWrap(item.orderStatus) +
                        tableItemWrap("<button my-attr='op' type=\"button\" class=\"btn btn-danger\">取消需求</button><button my-attr='op' type=\"button\" class=\"btn btn-warning\">修改需求</button>") +
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
                    if($(item).parent().prevAll().first().html() === "撤销" || $(item).parent().prevAll().first().html() === "已完成"){
                        rmModify(item);
                        $(item).removeClass("btn-danger");
                        $(item).text("查看详情");
                    }
                    else if($(item).parent().prevAll().first().html() === "已报价" || $(item).parent().prevAll().first().html() === "待寄送样品" || $(item).parent().prevAll().first().html() === "已寄送样品" || $(item).parent().prevAll().first().html() === "已确认样品"){
                        rmModify(item);
                        $(item).removeClass("btn-danger");
                        $(item).addClass("btn-info");
                        $(item).text("查看报价");
                    }
                    else if($(item).parent().prevAll().first().html() === "已签合同" || $(item).parent().prevAll().first().html() === "已发送合同"){
                        rmModify(item);
                        $(item).removeClass("btn-danger");
                        $(item).addClass("btn-success");
                        $(item).text("查看合同");
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
                        else if($(item).text() === "查看合同"){
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
                    if($(item).parent().prevAll().first().html() === "已寄送样品" && $(item).text() === "索取样品"){
                        $(item).removeClass("btn-info");
                        $(item).addClass("btn-primary");
                        $(item).text("确认样品");
                    }
                    else if($(item).parent().prevAll().first().html() === "已发送合同" && $(item).text() === "索取样品"){
                        $(item).remove();
                    }
                    else if($(item).parent().prevAll().first().html() === "待寄送样品" && $(item).text() === "索取样品"){
                        $(item).remove();
                    }
                    else if($(item).parent().prevAll().first().html() === "已确认样品" && $(item).text() === "索取样品"){
                        $(item).remove();
                    }
                    $(item).on("click", function () {
                        map['cur'] = $("#cur").html();
                        if($(item).text() === "索取样品"){
                            map["proSerialNo"] = $(item).parent().prevAll().last().html();
                            map["purSerialNo"] = $(item).parent().prevAll().eq(4).html();
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
                            map["proSerialNo"] = $(item).parent().prevAll().last().html();
                            map["purSerialNo"] = $(item).parent().prevAll().eq(4).html();
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
                        if($.trim($(item).parent().prevAll().first().children().val()).length === 0){
                            errorMessage("请输入订单号！");
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
                    $.each(data, function (i, item) {
                        $("#orderTableContent").append(
                            "<tr>" +
                            tableItemWrap(item.orderSerialNo) +
                            tableItemWrap(item.fileName) +
                            tableItemWrap(item.fileSize + " MB") +
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
                    var temp = data.filenames[index].split(".");
                    item["name"] = data.filenames[index];
                    item["size"] = (data.files[index].size / Math.pow(1024, 2)).toFixed(2);
                    item["extension"] = temp[temp.length - 1];
                    item["hash"] = data.response.hash;
                    files.push(item);
                    map['hash'] = JSON.stringify(files);
                });
            }
        });
    }
    function queryAllContract(map) {
        $.ajax({
            type: "POST",
            url: "/order/getAllContract.do",
            data: map,
            success: function (data) {
                $.each(data, function (i, item) {
                    var url = "<a class='btn btn-info' target='_blank' href='" + item.addonUrl + "'>下载合同</a>";
                    var btn = '';
                    if(map['queryType'] !== 'his'){
                        if (item.userType === 1) {
                            btn = "<button class='btn btn-info'>更改合同</button>";
                        }
                        else {
                            btn = "<button class='btn btn-success'>接受合同</button><button class='btn btn-danger'>拒绝合同</button>";
                        }
                    }
                    $("#orderTableContent").append(
                        "<tr>" +
                        tableItemWrap(item.contractSn) +
                        tableItemWrap(item.purOrdSn) +
                        tableItemWrap(item.proOrdSn) +
                        tableItemWrap(url) +
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
                            var res = confirm("注意，若拒绝合同，本次报价即结束，建议与采购商重新协商并修改合同！");
                            if(!res){
                                return;
                            }
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
                            map["pur_serial_no"] = $(item).parent().prevAll().eq(2).html();
                            showContract(map);
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
                    $("#orderTableContent").append(
                        "<tr>" +
                        tableItemWrap(item.purchaser_mobile_no) +
                        tableItemWrap(item.provider_mobile_no) +
                        tableItemWrap(item.coop_count + " 次") +
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
        });
    }

//simple logic
    function queryByType(item){
        map["queryType"] = $(item).children().attr("id");
        $("#orderTableContent").html("");
        if(window.location.pathname === "/order/showPurOrders") {
            pager(queryPurOrder);
            queryPurOrder(map);
        }
        else if(window.location.pathname === "/order/recOrder"){
            pager(queryUnOfferOrder);
            queryUnOfferOrder(map);
        }
        else if(window.location.pathname === "/order/allContract"){
            pager(queryAllContract);
            queryAllContract(map);
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
