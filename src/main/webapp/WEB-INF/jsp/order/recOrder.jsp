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
    <script src="../../../script/order/orderDisplay.js"></script>
</head>
<body>
<div class="table-responsive">
    <div class="container"style="padding: 1% 10%;">
        <table class="table table-bordered table-hover table-responsive" >
            <thead>
            <tr>
                <th>需求序列号</th>
                <th>期望价格</th>
                <th>需求数量</th>
                <th>采购商手机号</th>
                <th>已报价人次</th>
                <th>需求类型</th>
                <th>需求状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="purTableContent">

            </tbody>
        </table>
    </div>
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
