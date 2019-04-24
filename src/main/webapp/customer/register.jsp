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
            color: #f3f5e1;
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
            // alert(userName.length);
            if (userName.length==0) {
                $("#userNameInfo").text("请输入用户名！");
                // alert("if");
            } else {
                // alert("else");
                $.ajax({
                    type: "get",
                    url: "/diandiancar/customer/check_username",
                    data: {"userName": userName},
                    success: function (response) {
                        // alert("infun");
                        // alert(response);
                        var userNameHtml = null;
                        if (response == "用户名正确") {
                            pass = 1;
                        }
                        if (pass == 0) {
                            $("#register").removeAttr("disabled");
                            userNameHtml += "该用户名可用！";
                        } else {
                            $("#register").attr("disabled", 'disabled');
                            userNameHtml += "该用户名已注册！";
                        }
                        $("#userNameInfo").text(userNameHtml);
                    }
                });
            }
            $("#confirmPassword").blur(function () {
                // alert("in");
                var passwordHtml = null;
                var password = $("#password").val();
                var confirmPassword = $("#confirmPassword").val();

                if (password.trim() == confirmPassword.trim()) {
                    $("#login").removeAttr("disabled");
                } else {
                    $("#login").attr("disabled", 'disabled');
                    passwordHtml += "两次密码不一致！"
                }
                $("#passwordInfo").text(passwordHtml);

            });

        });

    })
</script>

<body>
<jsp:include page="header.jsp"></jsp:include>

<div class="container demo">

    <form action="/diandiancar/customer/register" method="post">
        <div style="height: 25px;"></div>
        <p class="font-weight-bold text-center">用户注册</p><br/>
        <div class="form-group row">
            <span class="col-lg-1"></span>
            <label for="userName" class="col-lg-2">账号</label>
            <input type="text" autofocus="autofocus" class="form-control col-lg-7" name="userName" id="userName"
                   aria-describedby="emailHelp" placeholder="请输入账号" required>
        </div>
        <div style="margin-left:100px; margin-top: 0px">
            <span id="userNameInfo" style="color: lightgreen;"></span>
        </div>
        <div class="form-group row">
            <span class="col-lg-1"></span>
            <label for="password" class="col-lg-2">密码</label>
            <input type="password" class="form-control col-lg-7" required name="password" id="password" placeholder="请输入密码">
        </div>
        <div class="form-group row">
            <span class="col-lg-1"></span>
            <label for="confirmPassword" class="col-lg-2">确认密码</label>
            <input type="password" class="form-control col-lg-7" required name="confirmPassword" id="confirmPassword"
                   placeholder="请确认密码">
        </div>
        <div style="margin-left:100px; margin-top: 0px">
            <span id="passwordInfo" style="color: lightcoral;"></span>
        </div>

        <div class="form-group row">
            <span class="col-lg-1"></span>
            <label for="telNum" class="col-lg-2">手机号码</label>
            <input type="number" class="form-control col-lg-7" name="telNum" required id="telNum" placeholder="请输入手机号码">
        </div>
        <div class="col-lg-10 mx-auto">
            <button type="submit" disabled class="btn btn-danger btn-lg btn-block" required id="register">注册</button>
        </div>
        <p class="text-left mx-5 small" style="margin-top: 1rem;">
            <a href="/diandiancar/customer/login.jsp" style="color: wheat;text-decoration: none;">已有账号，进行登录</a> |
            <a href="/diandiancar/customer/index/customer_index" style="color: wheat;text-decoration: none;">返回主页</a>
        </p>
    </form>


</div>
<div style="margin-top:600px"></div>
<jsp:include page="footer.jsp"></jsp:include>


</body>
</html>