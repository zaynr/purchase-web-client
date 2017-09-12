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
    <script src="../../../script/order/orderDisplay.js"></script>
    <title>等待报价</title>
</head>
<body>
<div class="container">
    <div align="center">
        <br>
        <ul class="nav nav-pills" role="tablist">
            <li role="presentation" class="active"><a id="unOffer">未报价订单</a></li>
            <li role="presentation"><a id="offered">已报价订单</a></li>
        </ul>
        <br>
    </div>
    <div class="table-responsive">
        <table class="table table-bordered table-hover table-responsive" >
            <thead>
            <tr>
                <th>需求序列号</th>
                <th>期望价格</th>
                <th>需求数量</th>
                <th>详细要求</th>
                <th>附件</th>
                <th>个人报价</th>
                <th>已报价人次</th>
                <th>需求类型</th>
                <th>需求状态</th>
                <th>发布时间</th>
                <th>截止日期</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="orderTableContent">

            </tbody>
        </table>
    </div>
</div>
<div class="modal fade" id="details" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">详细说明</h4>
            </div>
            <div class="modal-body">
                <p class="text-justify" id="detailContent"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
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
            <br>
            <div id="message"></div>
            <br>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>
</html>
