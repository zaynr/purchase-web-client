/**
 * Created by zengzy19585 on 2017/9/2.
 */
$(document).ready(function () {
    $.ajax({
        type: "POST",
        url: "/account/getInfo.do",
        success: function (data) {
            $("#userType").text(data.userType);
            $("#mobileNo").val(data.mobileNo);
            $("#userName").val(data.userName)
        }
    });

    $("#commitChange").click(function () {
        var userName = $("#userName").val();
        var mobileNo = $("#mobileNo").val();
    });
});