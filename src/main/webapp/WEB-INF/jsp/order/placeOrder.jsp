<%--
  Created by IntelliJ IDEA.
  User: zengzy19585
  Date: 2017/9/1
  Time: 19:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <jsp:include page="../script-sources.jsp"/>
    <title>需求下单</title>
</head>
<body>

<div class="container"style="padding: 1% 10%;">
    <form class="form-horizontal" role="form">
        <div class="form-group">
            <label class="col-sm-2 control-label">购买类型: </label>
            <div class="col-sm-10">
                <select id="typeSelect" class="form-control">
                    <option>禁止选择</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">禁用</label>
            <div class="col-sm-10">
                <input class="form-control" id="disabledInput" type="text" placeholder="该输入框禁止输入..." >
            </div>
        </div>

    </form>
</div>

</body>
</html>
