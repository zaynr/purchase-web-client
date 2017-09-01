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
    <jsp:include page="script-sources.jsp"/>
    <title>需求下单</title>
</head>
<body>

<div class="container">
    <form class="form-horizontal" role="form">
        <div class="form-group">
            <label class="col-sm-2 control-label">聚焦</label>
            <div class="col-sm-10">
                <input class="form-control" id="focusedInput" type="text" value="该输入框获得焦点...">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">禁用</label>
            <div class="col-sm-10">
                <input class="form-control" id="disabledInput" type="text" placeholder="该输入框禁止输入..." disabled>
            </div>
        </div>
        <fieldset disabled>
            <div class="form-group">
                <label for="disabledTextInput" class="col-sm-2 control-label">禁用输入（Fieldset disabled）</label>
                <div class="col-sm-10">
                    <input type="text" id="disabledTextInput" class="form-control" placeholder="禁止输入">
                </div>
            </div>
            <div class="form-group">
                <label for="disabledSelect" class="col-sm-2 control-label">禁用选择菜单（Fieldset disabled）</label>
                <div class="col-sm-10">
                    <select id="disabledSelect" class="form-control">
                        <option>禁止选择</option>
                    </select>
                </div>
            </div>
        </fieldset>
        <div class="form-group has-success">
            <label class="col-sm-2 control-label" for="inputSuccess">输入成功</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="inputSuccess">
            </div>
        </div>
        <div class="form-group has-warning">
            <label class="col-sm-2 control-label" for="inputWarning">输入警告</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="inputWarning">
            </div>
        </div>
        <div class="form-group has-error">
            <label class="col-sm-2 control-label" for="inputError">输入错误</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="inputError">
            </div>
        </div>
    </form>
</div>

</body>
</html>
