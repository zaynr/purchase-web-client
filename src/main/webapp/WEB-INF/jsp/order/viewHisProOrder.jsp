<%--
  Created by IntelliJ IDEA.
  User: zengzy19585
  Date: 2017/9/5
  Time: 12:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <jsp:include page="../script-sources.jsp"/>
    <script src="../../../script/order/orderDisplay.js"></script>
    <title>查看历史需求</title>
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
                <th>报价</th>
                <th>采购商用户名</th>
                <th>采购商手机号</th>
                <th>需求数量</th>
                <th>需求类型</th>
                <th>报价时间</th>
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
<div class="modal fade" id="purOrderDetail" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">查看需求详情</h4>
            </div>
            <form class="bs-example bs-example-form" role="form">
                <br>
                <br>
                <div class="input-group">
                    <span class="input-group-addon">采购商手机号</span>
                    <input type="text" class="form-control" id="purMobileNo" disabled>
                </div>
                <br>
                <div class="input-group">
                    <span class="input-group-addon">采购类型</span>
                    <input type="text" class="form-control" id="orderType" disabled>
                </div>
                <br>
                <div class="input-group">
                    <span class="input-group-addon">采购数量</span>
                    <input type="text" class="form-control" id="orderAmount" disabled>
                </div>
                <br>
                <div class="input-group">
                    <span class="input-group-addon">采购时间</span>
                    <input type="text" class="form-control" id="placeTime" disabled>
                </div>
                <br>
            </form>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>
</html>
