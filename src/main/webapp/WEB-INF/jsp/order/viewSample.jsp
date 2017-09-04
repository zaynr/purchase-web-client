<%--
  Created by IntelliJ IDEA.
  User: zengzy19585
  Date: 2017/9/4
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <jsp:include page="../script-sources.jsp"/>
    <script src="../../../script/order/orderDisplay.js"></script>
    <title>查看样品</title>
</head>
<body>
<div class="container"style="padding: 1% 10%;">
    <div align="center" style="padding: 1% 10%;">
        <div class="btn-group" >
            <br>
            <button id="confirm" type="button" class="btn btn-primary btn-lg">确认订单</button>
            <br>
        </div>
    </div>
    <br>
    <div id="message"></div>
</div>
</body>
</html>
