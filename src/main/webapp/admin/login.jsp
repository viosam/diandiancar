<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>

    <!-- 引入CSS -->
    <link href="/diandiancar/css/bootstrap.css" type="text/css" rel="stylesheet">
    <link href="/diandiancar/css/style.css" type="text/css" rel="stylesheet">
    <!-- 引入JS -->
    <script src="/diandiancar/js/jquery-3.3.1.min.js"></script>
    <script src="/diandiancar/js/popper.min.js"></script>
    <script src="/diandiancar/js/bootstrap.js"></script>

    <style type="text/css">
        body{background: url("/diandiancar/images/admin_login_bg.jpg") no-repeat;background-size: 100%,100%;}
        .demo{width: 400px;height: 350px;position: absolute;top: 50%;left: 50%;margin-top: -200px;margin-left: -200px;border: 1px solid gainsboro;color: #f5f5f5;background: #000000;opacity: 0.7;box-shadow: 10px 10px 10px grey;border-radius: 8px;}
        a:hover{color: #87cefa !important;}
    </style>
</head>


<script type="text/javascript">
    <%--若已经登录，则不会再进到这个页面--%>
    // $(document).ready(function () {
    //     $.ajax({
    //         type: "get",
    //         url: "/diandiancar/customer/checkLogin",
    //         data: {},
    //         dataType: "json",
    //         success: function (response) {
    //
    //         }
    //     });
    //
    // })

</script>


<body>

<div class="container demo">

    <form action="/diandiancar/admin/login" method="post">
        <div style="height: 25px;"></div>
        <p class="font-weight-bold text-center">管理员登录</p>
        <div class="form-group row">
            <span class="col-lg-1"></span>
            <label for="userName" class="col-lg-2">账号</label>
            <input type="text" autofocus = "autofocus" class="form-control col-lg-7" name="adminName" id="userName" aria-describedby="emailHelp" placeholder="请输入账号">
        </div>
        <div class="form-group row">
            <span class="col-lg-1"></span>
            <label for="password" class="col-lg-2">密码</label>
            <input type="password" class="form-control col-lg-7" id="password" name="password" placeholder="请输入密码">
        </div>
        <br>
        <div class="col-lg-10 mx-auto">
            <button type="submit" class="btn btn-primary btn-lg btn-block">登录</button>
        </div>
    </form>
</div>
<div style="margin-top:700px"></div>

<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>