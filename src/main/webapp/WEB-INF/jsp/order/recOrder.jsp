<%--
  Created by IntelliJ IDEA.
  User: zaynr
  Date: 2017/9/2
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../script-sources.jsp"/>
    <title>等待报价</title>
    <script src="../../../script/orderDisplay.js"></script>
    <script>
        function num(obj){
            obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
            obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字
            obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个, 清除多余的
            obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
            obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
        }
    </script>
</head>
<body>
    <div class="container"style="padding: 1% 10%;">
        <table class="table table-bordered table-hover table-responsive" >
            <thead>
            <tr>
                <th>需求序列号</th>
                <th>合同序列号</th>
                <th>采购商手机号</th>
                <th>供应商手机号</th>
                <th>需求类型</th>
                <th>需求状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="purTableContent">

            </tbody>
        </table>
    </div>
    <div class="modal fade" id="recOrderDetail" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">接单详情</h4>
                </div>
                <form class="bs-example bs-example-form" role="form">
                    <div class="input-group">
                        <span class="input-group-addon">单位报价：</span>
                        <input class="form-control" id="offerPrice" placeholder="输入单位报价" onkeyup="num(this)">
                        <span class="input-group-addon">元</span>
                    </div>
                </form>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" id="confirm" class="btn btn-primary">确认价格</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
</body>
</html>
