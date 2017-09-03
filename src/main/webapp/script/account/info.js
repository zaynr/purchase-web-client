/**
 * Created by zengzy19585 on 2017/9/2.
 */
$(document).ready(function () {
    $.ajax({
        type: "POST",
        url: "account/getInfo.do",
        success: function (data) {

        }
    });

    $("#commitChange").click(function () {
        var userName = $("#userName").val();
        var mobileNo = $("#mobileNo").val();
    });
});