<%--
  Created by IntelliJ IDEA.
  User: zaynr
  Date: 2017/9/3
  Time: 20:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>所有合同</title>
    <jsp:include page="../script-sources.jsp"/>
    <script src="../../../script/order/orderDisplay.js"></script>
</head>
<body>
<div class="container">
    <div align="center">
        <br>
        <div id="message"></div>
        <br>
        <ul class="nav nav-pills" role="tablist">
            <li role="presentation" class="active"><a id="current">查看未签合同</a></li>
            <li role="presentation"><a id="his">查看已签合同</a></li>
        </ul>
        <br>
    </div>
    <div class="table-responsive">
        <table class="table table-bordered table-hover" >
            <thead>
            <tr id="tableHead">
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
</body>
</html>
