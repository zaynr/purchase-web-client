<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="script-sources.jsp"/>
        <script src="../../script/login/login.js"></script>
        <script src="../../script/login/jQuery-MD5.js"></script>
        <title>简单交易 Simple Trade</title>
    </head>
    <body>
        <div class="jumbotron" style="padding: 5% 20%;">
            <h1>欢迎来到简单交易网</h1>
        </div>

        <div class="container">
            <!-- Example row of columns -->
            <div class="row">
                <div class="col-md-4">
                    <h2>可提供报价</h2>
                    <p>订单详情：</p>
                </div>
                <div class="col-md-4">
                    <h2>未提供样品</h2>
                    <p>订单详情：</p>
                </div>
                <div class="col-md-4">
                    <h2>已签订的合同</h2>
                    <p>合同</p>
                    <div>
                        <a class="btn btn-default" href="#" role="button">查看详情 &raquo;</a>
                    </div>
                </div>
            </div>

            <hr>

            <footer>
                <p>&copy; 2017 Company, Inc.</p>
            </footer>
        </div> <!-- /container -->
    </body>
</html>
