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
    <%--city-pick--%>
    <link href="../../../css/city-picker.css" rel="stylesheet">
    <script type="text/javascript" src="../../../script/citypick/city-picker.data.min.js"></script>
    <script type="text/javascript" src="../../../script/citypick/city-picker.min.js"></script>
    <%--auto-complete--%>
    <script type="text/javascript" src="../../../script/jquery.autocomplete.min.js"></script>
    <%--file-upload--%>
    <link href="../../../css/fileinput.css" rel="stylesheet">
    <script type="text/javascript" src="../../../script/plugins/sortable.min.js"></script>
    <script type="text/javascript" src="../../../script/fileinput.min.js"></script>
    <script type="text/javascript" src="../../../script/locales/zh.js"></script>
    <%--datetime-picker--%>
    <script type="text/javascript" src="../../../bower_components/moment/min/moment-with-locales.min.js"></script>
    <script type="text/javascript" src="../../../bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>
    <link rel="stylesheet" href="../../../bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css" />
    <%--my-custom--%>
    <script src="../../../script/order/orderManage.js"></script>
    <script src="../../../script/citypick/jquery.cxselect.min.js"></script>
</head>
<body>

<div class="container">
    <form class="form-horizontal" role="form">
        <br>
        <div id="type_picker">
            <select class="type_category form-control"></select>
            <select class="type_content form-control"></select>
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon">购买类型</span>
            <input id="typeSelectText" class="form-control">
        </div>
        <br>
        <div id="selectedType" type-no align="center"></div>
        <br>
        <div class="input-group">
            <span class="input-group-addon">订购数量</span>
            <input type="number" id="orderAmount" class="form-control" onkeyup="num(this)" placeholder="需求数量">
            <span id="typeUnit" class="input-group-addon">单位</span>
        </div>
        <br>
        <div class="checkbox">
            <label>
                <input type="checkbox" id="showExpect">不展示期望报价
            </label>
        </div>
        <div class="input-group">
            <span class="input-group-addon">期望报价</span>
            <input id="expect" not-nec="true" class="form-control" onkeyup="num(this)" placeholder="供应商将会看到">
            <span class="input-group-addon">元</span>
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon">截止时间</span>
            <input type="text" id="datetimePicker" class="form-control">
            <span class="input-group-addon glyphicon glyphicon-calendar"></span>
        </div>
        <br>
        <div class="docs-methods" align="center">
            <form class="form-inline">
                <div>
                    <div class="form-group">
                        <div style="position: relative;">
                            <input id="distpicker" not-nec="true" class="form-control" readonly type="text">
                        </div>
                        <br>
                        <p><strong>注意，若添加了特定区域，将只会向处在该地区的供应商推送需求！</strong></p>
                    </div>
                </div>
            </form>
        </div>
        <br>
        <div id="regionFilter">

        </div>
        <br>
        <div align="center">
            <button class="btn btn-danger" id="addFilter">添加特定地区</button>
        </div>
        <br>
        <div class="input-group" style="width:100%;max-width:400px; margin:auto">
            <textarea id="moreDetail" class="form-control" placeholder="详细需求(可选，不超过500字)"></textarea>
            <br>
            <br>
            <br>
            <br>
            添加附件（可选）<input id="putAddOn" not-nec="true" name="file" type="file" multiple class="file-loading">
            <br>
        </div>
    </form>
    <br>
    <div id="modify" align="center"></div>
    <div id="message"></div>
    <br>
    <div align="center">
        <button id="placeOrder" class="btn btn-primary">发布需求</button>
    </div>
    <br>
</div>

</body>
</html>
