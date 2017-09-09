<%--
  Created by IntelliJ IDEA.
  User: zengzy19585
  Date: 2017/9/4
  Time: 10:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <jsp:include page="../script-sources.jsp"/>
    <%--file-upload--%>
    <link href="../../../css/fileinput.min.css" rel="stylesheet">
    <script type="text/javascript" src="../../../script/plugins/sortable.min.js"></script>
    <script type="text/javascript" src="../../../script/fileinput.min.js"></script>
    <script type="text/javascript" src="../../../script/locales/zh.js"></script>
    <script type="text/javascript" src="../../../script/themes/fa/theme.min.js"></script>
    <%--user-custom--%>
    <script src="../../../script/order/orderDisplay.js"></script>
    <title>查看报价</title>
</head>
<body>
<div class="container">
    <br>
    <div id="message"></div>
    <br>
    <div class="table-responsive">
        <table class="table table-bordered table-hover" >
            <thead>
            <tr>
                <th>报价序列号</th>
                <th>供应商报价</th>
                <th>供应商手机号</th>
                <th>需求序列号</th>
                <th>需求数量</th>
                <th>需求类型</th>
                <th>快递单号</th>
                <th>报价状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="orderTableContent">

            </tbody>
        </table>
    </div>
    <div align="center">
        <ul class="pagination" style="max-width: 300px; margin: auto;">
            <li class="previous"><a id="prev">&larr; 上一页</a></li>
            <li class="current"><a id="cur">1</a></li>
            <li class="next"><a id="next">下一页 &rarr;</a></li>
        </ul>
    </div>
</div>
<div class="modal fade" id="confirmContract" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">上传合同</h4>
            </div>
            <form class="bs-example bs-example-form" role="form">
                <input id="putAddOn" name="file" type="file" multiple class="file-loading">
            </form>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" id="confirm" class="btn btn-primary">确认</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>
</html>
