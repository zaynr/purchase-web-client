/**
 * Created by zengzy19585 on 2017/9/1.
 */

var param = {};
var picker, orderAmount, expect, moreDetail, selectedType, regionFilter;

$(document).ready(function () {
    if(window.location.pathname === "/order/addOrderType") {
        var orderType = $("#orderType");
        var unitType = $("#unitType");
        var typeCategory = $("#typeCategory");
        var type_picker = $("#type_picker");
        type_picker.cxSelect({
            url: "/order/showOrdTypeGrpByCat.do",
            selects: ['type_category', 'type_content'],
            required: true,
            jsonName: 'name',
            jsonValue: 'typeUnit',
            jsonSub: 'types'
        });
        type_picker.change(function () {
            typeCategory.val($("select.type_category").find("option:selected").text());
            orderType.val($("select.type_content").find("option:selected").text());
            unitType.val($("select.type_content").find("option:selected").attr("value"));
        });
        $("#addNew").click(function () {
            param['orderType'] = orderType.val();
            param["typeUnit"] = unitType.val();
            param["typeCategory"] = typeCategory.val();
            var res = confirm("是否添加这个类型？");
            if(!res){
                return;
            }
            $.ajax({
                type: "POST",
                url: "/order/addOrderType.do",
                data: param,
                success: function (data) {
                    if(data === "success") {
                        successMessage("添加成功");
                    }
                    else{
                        successMessage("更新成功");
                    }
                }
            });
        });
        $("#delete").click(function () {
            param['orderType'] = orderType.val();
            param["typeUnit"] = unitType.val();
            param["typeCategory"] = typeCategory.val();
            var res = confirm("是否删除这个类型？");
            if(!res){
                return;
            }
            $.ajax({
                type: "POST",
                url: "/order/delOrderType.do",
                data: param,
                success: function (data) {
                    if(data === "success") {
                        successMessage("删除成功");
                        window.location.reload();
                    }
                    else{
                        errorMessage("删除失败");
                    }
                }
            });
        });
        $("#update").click(function () {
            param['offerPageSize'] = $("#offerPageSize").val();
            if($("#offerPageSize").val() === ""){
                errorMessage("请输入个数");
                return;
            }
            $.ajax({
                type: "POST",
                url: "/order/updateAdminOption.do",
                data: param,
                success: function () {
                    successMessage("更新成功");
                }
            });
        });
        $.ajax({
            type: "POST",
            url: "/order/getPageSize.do",
            success: function (data) {
                $("#offerPageSize").val(data);
            }
        });
    }

    if(window.location.pathname === "/order/modifyPurOrder") {
        param["serialNo"] = getUrlParam('serialNo');
        pageInit();
        $.ajax({
            type: "POST",
            url: "/order/getCurrentPurOrder.do",
            data: param,
            success: function (data) {
                //init data
                $("#placeOrder").text("更新需求");
                $("#typeSelectText").val(data.typeContent);
                orderAmount.val(data.orderAmount);
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
                selectedType.html(successAlert(data.typeContent));
                if(data.moreDetail !== "未添加详细需求"){
                    moreDetail.val(data.moreDetail);
                }
                if(data.showExpectPrice === 1){
                    $("#showExpect").prop("checked", "true");
                }
                var expect = $("#expect");
                if(data.expectPrice === -1){
                    expect.val("");
                }
                else{
                    expect.val(data.expectPrice);
                }

                $("#placeOrder").click(function() {
                    if(!checkInputNull()){
                        errorMessage("请完整输入！");
                        return;
                    }

                    //init param
                    param["pur_serial_no"] = data.purSerialNo;
                    param["orderAmount"] = orderAmount.val();
                    param["expect"] = expect.val();
                    param["more_detail"] = moreDetail.val();
                    param["showExpect"] = $("#showExpect").prop("checked");
                    param["filter"] = JSON.stringify(getFilterRegion());

                    $.ajax({
                        type: "POST",
                        url: "/order/modifyPurOrder.do",
                        data: param,
                        success: function (data) {
                            if(data === "success") {
                                successMessage("更新成功");
                                setTimeout(function () {
                                    window.location.reload();
                                }, 1000);
                            }
                            else if(data === "no_such_type"){
                                errorMessage("无此类型，请联系管理员添加！");
                            }
                            else{
                                errorMessage("更新失败");
                            }

                        }
                    });
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
            if(selectedType.html() === ""){
                errorMessage("请选择类型！");
                return;
            }
            param["type_no"] = selectedType.children("div").attr("type-no");
            param["orderAmount"] = orderAmount.val();
            param["expect"] = expect.val();
            param["more_detail"] = moreDetail.val();
            param["filter"] = JSON.stringify(getFilterRegion());
            param["showExpect"] = $("#showExpect").prop("checked");
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

function pageInit() {
    var type_picker = $("#type_picker");
    type_picker.cxSelect({
        url: "/order/showOrdTypeGrpByCat.do",
        selects: ['type_category', 'type_content'],
        required: true,
        jsonName: 'name',
        jsonValue: 'typeNo',
        jsonSub: 'types'
    });
    selectedType = $("#selectedType");
    picker = $("#distpicker");
    regionFilter = $("#regionFilter");
    orderAmount = $("#orderAmount");
    expect = $("#expect");
    moreDetail = $("#moreDetail");
    var typeSelectText = $("#typeSelectText");
    type_picker.change(function () {
        var selected = $("select.type_content").find("option:selected");
        typeSelectText.val(selected.text());
        param['type_no'] = selected.attr("value");
        $.ajax({
            type: "POST",
            url: "/order/getTypeUnit.do",
            data: param,
            success: function (data) {
                $("#typeUnit").text(data.type_unit);
            }
        });
        selectedType.html(successAlert(typeSelectText.val()));
        selectedType.children("div").attr("type-no", selected.attr("value"));
    });
    //添加地区过滤
    picker.citypicker({
        simple: true
    });
    $("#addFilter").click(function () {
        var flag = false;
        if(picker.val() === ""){
            flag = true;
        }
        regionFilter.find("div.my-alert").each(function (i, item) {
            if($(item).html() === picker.val()){
                flag = true;
            }
        });
        if(flag){
            return;
        }
        regionFilter.append(
            successAlert(picker.val())
        );
    });
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
                item["size"] = (data.files[index].size);
                item["hash"] = data.response.hash;
                files.push(item);
                param['hash'] = JSON.stringify(files);
            });
        }
    });
    //get autocomplete
    typeSelectText.devbridgeAutocomplete({
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
            selectedType.html(successAlert(suggestion.value));
            selectedType.children("div").attr("type-no", suggestion.data);
        }
    });
}

function getFilterRegion() {
    var filter = [];
    regionFilter.find("div.my-alert").each(function (index, item) {
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
    return filter;
}