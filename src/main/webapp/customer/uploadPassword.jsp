<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>更改密码</title>
    <!-- 引入CSS -->
    <link href="/diandiancar/css/bootstrap.css" type="text/css" rel="stylesheet">
    <link href="/diandiancar/css/style.css" type="text/css" rel="stylesheet">
    <!-- 引入JS -->
    <script src="/diandiancar/js/jquery-3.3.1.min.js"></script>
    <script src="/diandiancar/js/popper.min.js"></script>
    <script src="/diandiancar/js/bootstrap.js"></script>
</head>
<style type="text/css">
    body{background: url("/diandiancar/images/bg.jpg") no-repeat;background-size: 100%,100%;}
    .demo{width: 400px;height: 350px;position: absolute;top: 50%;left: 50%;margin-top: -200px;margin-left: -200px;border: 1px solid gainsboro;color: #f5f5f5;background: #000000;opacity: 0.7;box-shadow: 10px 10px 10px grey;border-radius: 8px;}
    a:hover{color: #87cefa !important;}
</style>
<body>

<jsp:include page="header.jsp"></jsp:include>
<div class="container" style="align-content: center" align="center">
    <!-- 内容主体区域 -->
    <div class="container" style="margin-top: 3rem;">
        <h3>更改密码：</h3>
        <div class="row">
            <div class="col-lg-2"></div>
            <div class="col-lg-8">
                <form action="/diandiancar/customer/identity/updatePassword" method="post" id="myform">
                    <div class="form-group row">
                        <label for="input1" class="col-sm-2 col-form-label">用户名：</label>
                        <div class="col-sm-10">
                            <input type="text" readonly class="form-control-plaintext" id="input1"
                                   value="${customer.userName}">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="input2" class="col-sm-2 col-form-label">原密码：</label>
                        <div class="col-sm-10">
                            <input type="password" name="oldPassword" class="form-control" id="input2">
                        </div>
                    </div>


                    <div class="form-group row">
                        <label for="input2" class="col-sm-2 col-form-label">新密码：</label>
                        <div class="col-sm-10">
                            <input type="password" name="password" class="form-control" id="input3">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="input2" class="col-sm-2 col-form-label">确认密码：</label>
                        <div class="col-sm-10">
                            <input type="password" name="confirmPassword" class="form-control" id="input4">
                        </div>
                    </div>

                    <span id="error" style="color: red"></span>
                    <div class="form-group" style="margin-left: 40%;">
                        <a href="/diandiancar/customer/toPersonalCenter"
                           class="btn btn-info">返回</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="submit" id="submit" class="btn btn-success" value="修改">
                    </div>

                </form>
            </div>
        </div>
    </div>

    <div th:include="customer/footer::footer"></div>
</div>

<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>