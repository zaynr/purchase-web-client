/**
 * Created by zengzy19585 on 2017/9/1.
 */

var param = {};

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
                $("#message").html(
                    "<div class=\"alert alert-success alert-dismissable\">\n" +
                    "   <button type=\"button\" class=\"close\" data-dismiss=\"alert\"\n" +
                    "aria-hidden=\"true\">\n" +
                    "    &times;\n" +
                    "   </button>\n" +
                    "添加成功" +
                    "</div>"
                );
            }
        });
    });

    $("#showExpect").change(function () {
        checkShowExp();
    });

    if(window.location.pathname === "/order/placeOrder") {
        checkShowExp();
        $.ajax({
            type: "POST",
            url: "/order/getUploadToken.do",
            data: param,
            success: function (data) {
                var uptoken = data;
                var fileIns;
                $("#putAddOn").fileinput({
                    uploadUrl: "http://up-z2.qiniu.com",
                    theme: "fa",
                    uploadExtraData: {"token" : uptoken},
                    language: "zh",
                    allowedFileExtensions: ["jpg", "png", "jpeg", "gif", "txt", "doc", "docx", "xls", "xlsx", "pdf"],
                    hideThumbnailContent: true,
                    maxFileSize: 20480
                }).on("fileuploaded", function(event, data, previewId, index) {
                    param["hash"] = data.response.hash
                });

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

                        $("#placeOrder").click(function () {
                            if (!checkInputNull()) {
                                return;
                            }
                            param["orderAmount"] = $("#orderAmount").val();
                            param["expect"] = $("#expect").val();
                            param["type_no"] = suggestion.data;
                            param["more_detail"] = $("#moreDetail").val();
                            param["addon_url"] = $("#putAddOn").val();
                            $.ajax({
                                type: "POST",
                                url: "/order/placeOrder.do",
                                data: param,
                                success: function () {
                                    successMessage("发布成功");
                                    setTimeout(function () {
                                        window.location.href = "/order/showPurOrders";
                                    }, 1000);
                                }
                            });
                        });
                    }
                });
            }
        });
    }
});

function checkShowExp() {
    var exp = $("#expect");
    if($("#showExpect").prop('checked')){
        exp.prop("disabled", "true");
        exp.attr("not-nec", "true");
    }
    else{
        exp.removeAttr("disabled");
        exp.removeAttr("not-nec");
    }
}