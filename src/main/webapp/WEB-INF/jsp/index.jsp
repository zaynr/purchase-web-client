<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="script-sources.jsp"/>
        <script src="../../script/login.js"></script>
        <script src="../../script/jQuery-MD5.js"></script>
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
                    <p>
                        <a href="#">订单条数 <span class="badge">42</span></a>
                    </p>
                </div>
                <div class="col-md-4">
                    <h2>未提供样品</h2>
                    <p>订单详情：</p>
                    <p>
                        <a href="#">订单条数 <span class="badge">42</span></a>
                    </p>
                </div>
                <div class="col-md-4">
                    <h2>已签订的合同</h2>
                    <p>在这里可以看到订单信息 </p>
                    <p><a class="btn btn-default" href="#" role="button">查看详情 &raquo;</a></p>
                </div>
            </div>

            <hr>

            <footer>
                <p>&copy; 2017 Company, Inc.</p>
            </footer>
        </div> <!-- /container -->
    </body>
</html>
