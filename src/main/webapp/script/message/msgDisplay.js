/**
 * Created by zengzy19585 on 2017/9/10.
 */
$(document).ready(function () {
    if(window.location.pathname === "/"){
        function queryMsg() {
            $.ajax({
                type: "POST",
                url: "/order/getMessage.do",
                success: function (data) {
                    var row1 = $("#row1");
                    var row2 = $("#row2");
                    var row3 = $("#row3");
                    row1.html("");
                    row2.html("");
                    row3.html("");
                    $.each(data, function (i, item) {
                        switch(item.message_type_no){
                            case 1:
                                row1.append("<h3>未报价订单</h3><p>" + item.message_cnt + " 条</p>");
                                break;
                            case 2:
                                row2.append("<h3>待寄送样品</h3><p>" + item.message_cnt + " 条</p>");
                                break;
                            case 3:
                                row2.append("<h3>已确认收到样品</h3><p>" + item.message_cnt + " 条</p>");
                                break;
                            case 4:
                                row3.append("<h3>待确认合同</h3><p>" + item.message_cnt + " 条</p>");
                                break;
                            case 5:
                                row1.append("<h3>已被报价需求</h3><p>" + item.message_cnt + " 条</p>");
                                break;
                            case 6:
                                row2.append("<h3>待确认样品</h3><p>" + item.message_cnt + " 条</p>");
                                break;
                            case 7:
                                row3.append("<h3>供应商已确认合同</h3><p>" + item.message_cnt + " 条</p>");
                                break;
                        }
                    });
                }
            });
        }
        queryMsg();
        setInterval(function () {
            queryMsg();
        }, 3000);
    }
});