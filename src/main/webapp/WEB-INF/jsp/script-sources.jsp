<%--
  Created by IntelliJ IDEA.
  User: zengzy19585
  Date: 2017/9/1
  Time: 11:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <%--icon--%>
    <link rel="icon" type="image/png" sizes="300*300" href="../../img/icon.png">

    <link href="../../css/bootstrap.min.css" rel="stylesheet">
    <link href="../../css/jumbotron.css" rel="stylesheet">

    <script type="text/javascript" src="../../script/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="../../bower_components/bootstrap-3.3.7/dist/js/bootstrap.js"></script>
    <script type="text/javascript" src="../../script/auth.js"></script>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">简单交易 Simple Trade</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul id="navTabs" class="nav navbar-nav">

            </ul>
            <div id="navbarButton" class="navbar-form navbar-right">

            </div>
        </div><!--/.navbar-collapse -->
    </div>
</nav>
</body>
</html>