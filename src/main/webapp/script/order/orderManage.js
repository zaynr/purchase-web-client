/**
 * Created by zengzy19585 on 2017/9/1.
 */

var param = {};
var picker;

$(document).ready(function () {
    $("#addNew").click(function () {
        if(!checkInputNull()){
            return;
        }
        param['orderType'] = $("#orderType").val();
        param["typeUnit"] = $("#unitType").val();
        $.ajax({
            type: "POST",
            url: "/order/addOrderType.do",
            data: param,
            success: function () {
                successMessage("添加成功");
            }
        });
    });

    $("#showExpect").change(function () {
        checkShowExp();
    });

    if(window.location.pathname === "/order/modifyPurOrder") {
        param["serialNo"] = getUrlParam('serialNo');
        pageInit();
        $.ajax({
            type: "POST",
            url: "/order/getCurrentPurOrder.do",
            data: param,
            success: function (data) {
                //init data
                $("#typeSelectText").val(data.typeContent);
                $("#orderAmount").val(data.orderAmount);
                $("#expect").val(data.expectPrice);
                $("#modify").html("<p>" + data.addonNum + "</p><a class='btn btn-info' target='_blank' href='showAddOn?serialNo=" + data.purSerialNo + "'>管理附件</a>");
                $(data.filters).each(function (i, item) {
                    var content = "";
                    if(item.province !== "null"){
                        content += item.province;
                    }
                    if(item.city !== "null"){
                        content += "/" + item.city;
                    }
                    if(item.dist !== "null"){
                        content += "/" + item.dist;
                    }
                    $("#regionFilter").append(successAlert(content));
                });
                $("#typeUnit").text(data.typeUnit);

                //init param
                param["type_no"] = data.typeNo;
                param["pur_serial_no"] = data.purSerialNo;


                $("#moreDetail").val(data.moreDetail);
                $.ajax({
                    type: "POST",
                    url: "/order/modifyPurOrder.do",
                    success: function (data) {

                    }
                });
            }
        });
    }

    if(window.location.pathname === "/order/placeOrder") {
        pageInit();
        $("#placeOrder").click(function () {
            if (!checkInputNull()) {
                return;
            }
            param["orderAmount"] = $("#orderAmount").val();
            param["expect"] = $("#expect").val();
            param["more_detail"] = $("#moreDetail").val();
            var filter = [];
            $("div.my-alert").each(function (index, item) {
                var map = {};
                var i = 0;
                var foo = $(item).html().split("/");
                foo.forEach(function (t) {
                    switch (i){
                        case 0:
                            map['province'] = t;
                            break;
                        case 1:
                            map['city'] = t;
                            break;
                        case 2:
                            map['dist'] = t;
                            break;
                    }
                    i++;
                });
                switch (i){
                    case 0:
                        map['province'] = 'null';
                    case 1:
                        map['city'] = 'null';
                    case 2:
                        map['dist'] = 'null';
                }
                filter.push(map);
            });
            param["filter"] = JSON.stringify(filter);
            $.ajax({
                type: "POST",
                url: "/order/placeOrder.do",
                data: param,
                success: function (data) {
                    if(data === "success") {
                        successMessage("发布成功");
                        setTimeout(function () {
                            window.location.href = "/order/showPurOrders";
                        }, 1000);
                    }
                    else if(data === "no_such_type"){
                        errorMessage("无此类型，请联系管理员添加！");
                    }
                    else{
                        errorMessage("发布失败");
                    }
                }
            });
        });
    }
});

function checkShowExp() {
    var exp = $("#expect");
    if($("#showExpect").prop('checked')){
        exp.prop("disabled", "true");
        exp.attr("not-nec", "true");
        exp.val("");
    }
    else{
        exp.removeAttr("disabled");
        exp.removeAttr("not-nec");
    }
}

function pageInit() {
    checkShowExp();
    picker = $("#distpicker");
    picker.citypicker({
        simple: true
    });
    $("#addFilter").click(function () {
        var flag = false;
        $("div.my-alert").each(function (i, item) {
            if($(item).html() === picker.val()){
                flag = true;
            }
        });
        if(flag){
            return;
        }
        $("#regionFilter").append(
            successAlert(picker.val())
        );
    });
    //get token
    $.ajax({
        type: "POST",
        url: "/order/getUploadToken.do",
        success: function (data) {
            var putAddOn = $("#putAddOn");
            var files = [];
            putAddOn.fileinput({
                uploadUrl: "http://up-z2.qiniu.com",
                browseClass: "btn btn-primary btn-block",
                theme: "fa",
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
                param['hash'] = JSON.stringify(files);
            });
        }
    });
    //get autocomplete
    $("#typeSelectText").devbridgeAutocomplete({
        serviceUrl: '/order/showOrderType.do',
        showNoSuggestionNotice: true,
        paramName: 'typeContent',
        transformResult: function (data) {
            return {
                suggestions: $.map(JSON.parse(data), function (dataItem) {
                    return {value: dataItem.type_content, data: dataItem.type_no, unit: dataItem.type_unit};
                })
            };
        },
        onSelect: function (suggestion) {
            $("#typeUnit").text(suggestion.unit);
            param["type_no"] = suggestion.data;
        }
    });
}