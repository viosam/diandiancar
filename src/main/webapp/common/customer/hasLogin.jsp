<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>您已登录了</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
<body>
<div class="container">
    <div class="row clearfix" style="margin-top: 200px">
        <div class="col-md-12 column">
            <div class="alert alert-dismissable alert-success">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4>
                    您已登录了，退出请点<strong><a href="${logout}"class="alert-link">这里</a></strong>
                </h4> <strong>${msg}</strong><a href="${url}"class="alert-link">3s后自动跳转到首页</a>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    setTimeout('location.href="${url}"',3000);
</script>
</html>