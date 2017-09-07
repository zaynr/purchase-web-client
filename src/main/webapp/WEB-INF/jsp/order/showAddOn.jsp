<%--
  Created by IntelliJ IDEA.
  User: zengzy19585
  Date: 2017/9/6
  Time: 14:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <jsp:include page="../script-sources.jsp"/>
    <script src="../../../script/order/orderDisplay.js"></script>
    <title>查看附件</title>
</head>
<body>
<div class="container">
    <div class="table-responsive">
        <table class="table table-bordered table-hover table-responsive" >
            <thead>
            <tr id="tableHead">
                <th>需求序列号</th>
                <th>附件名</th>
                <th>附件大小</th>
                <th>附件链接</th>
            </tr>
            </thead>
            <tbody id="orderTableContent">

            </tbody>
        </table>
    </div>
</div>
</body>
</html>
