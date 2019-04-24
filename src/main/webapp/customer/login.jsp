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
        body {
            background: url("/diandiancar/images/login_bg.jpg") no-repeat;
            background-size: 100%, 100%;
        }

        .demo {
            width: 400px;
            height: 430px;
            position: absolute;
            top: 50%;
            left: 50%;
            margin-top: -200px;
            margin-left: -200px;
            border: 1px solid gainsboro;
            color: #f5f5f5;
            background: #55704b;
            opacity: 0.7;
            box-shadow: 10px 10px 10px grey;
            border-radius: 8px;
        }

        a:hover {
            color: #87cefa !important;
        }
    </style>
</head>


<script type="text/javascript">

    $(document).ready(function () {
        $("#userName").blur(function () {
            // alert("in");
            var userName = $("#userName").val();
            var pass = 0;
            if (userName.length == 0) {
                $("#userNameInfo").text("请输入用户名！");
                $("#login").attr("disabled", 'disabled');
            } else {
                $.ajax({
                    type: "get",
                    url: "/diandiancar/customer/check_username",
                    data: {"userName": userName},
                    success: function (response) {
                        // alert("infun");
                        // alert(response);
                        var userNameInfoHtml = null;
                        if (response == "用户名正确") {
                            pass = 1;
                        }
                        if (pass == 0) {
                            userNameInfoHtml += "用户名输入有误！";
                            $("#login").attr("disabled", 'disabled');
                        } else {
                            $("#login").removeAttr("disabled");
                            userNameInfoHtml += "用户名正确！";

                        }
                        $("#userNameInfo").text(response);
                    }
                });
            }
        });

    })
</script>


<body>
<jsp:include page="header.jsp"></jsp:include>

<div class="container demo">

    <form action="/diandiancar/customer/login" method="post">
        <div style="height: 25px;"></div>
        <p class="font-weight-bold text-center">用户登录</p>
        <div class="form-group row">
            <span class="col-lg-1"></span>
            <label for="userName" class="col-lg-2">账号</label>
            <input type="text" autofocus="autofocus" class="form-control col-lg-7" name="userName" id="userName"
                   aria-describedby="emailHelp" placeholder="请输入账号" required>
        </div>
        <div style="margin-left:100px; margin-top: 0px"><span id="userNameInfo" style="color: lightgreen;"></span></div>
        <div class="form-group row">
            <span class="col-lg-1"></span>
            <label for="password" class="col-lg-2">密码</label>
            <input type="password" class="form-control col-lg-7" required id="password" name="password" placeholder="请输入密码">
        </div>
        <%--<div class="form-check" style="margin-left: 4rem;">--%>
        <%--<input type="checkbox" class="form-check-input" id="exampleCheck1">--%>
        <%--<label class="form-check-label mx-2" for="exampleCheck1">记住我</label>--%>
        <%--</div>--%>
        <br>
        <div class="col-lg-10 mx-auto">
            <button type="submit" id="login" disabled class="btn btn-primary btn-lg btn-block">登录</button>
        </div>
        <p class="text-center" style="margin-top: 1rem;">
            <a href="/diandiancar/customer/register.jsp" style="color: wheat;text-decoration: none;">注册</a> |
            <a href="/diandiancar/customer/index" style="color: wheat;text-decoration: none;">返回主页</a>
        </p>
    </form>
</div>
<div style="margin-top:600px"></div>

<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>