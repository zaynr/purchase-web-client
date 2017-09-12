<%--
  Created by IntelliJ IDEA.
  User: zengzy19585
  Date: 2017/9/12
  Time: 19:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>报价详情</title>
    <jsp:include page="../script-sources.jsp"/>
    <script src="../../../script/order/orderDisplay.js"></script>
</head>
<body>

<div class="container">
    <br>
    <div id="message"></div>
    <br>
    <div id="detail"></div>
    <br>
    <div align="center">
        <br>
        <ul class="nav nav-pills" role="tablist">
            <li role="presentation" class="active"><a>查看当前需求</a></li>
            <li role="presentation"><a>寄送样品</a></li>
            <li role="presentation"><a>查阅合同</a></li>
        </ul>
        <br>
    </div>
    <div class="table-responsive">
        <table class="table table-bordered table-hover" >
            <thead>
            <tr id="tableHead">
                <th>采购商用户名</th><th>采购商手机号</th><th>报价时间</th><th>报价状态</th><th>快递单号</th><th>操作</th>
            </tr>
            </thead>
            <tbody id="orderTableContent">

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
