<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>翰林家居 | 关于我们</title>
    <!-- 引入CSS -->
    <link href="/diandiancar/css/bootstrap.css" type="text/css" rel="stylesheet">
    <!-- 引入JS -->
    <script src="/diandiancar/js/jquery-3.3.1.min.js"></script>
    <script src="/diandiancar/js/popper.min.js"></script>
    <script src="/diandiancar/js/bootstrap.js"></script>
    <style type="text/css">
        .navli{margin-left: 2rem;}
    </style>
</head>
<body>

<!-- 导航条 -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <a class="navbar-brand" href="index.html" style="margin-left: 3rem;">翰林家居</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav">
            <li class="nav-item navli" >
                <a class="nav-link" href="introduce.html">公司简介</a>
            </li>
            <li class="nav-item dropdown navli">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    家居种类
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="produce1.html">清新风格</a>
                    <a class="dropdown-item" href="produce2.html">古典风格</a>
                    <a class="dropdown-item" href="produce3.html">欧美风格</a>
                </div>
            </li>
            <li class="nav-item navli">
                <a class="nav-link" href="about.jsp">关于我们</a>
            </li>
            <!-- search -->
            <li class="nav-item" style="margin-left: 12rem;">
                <form class="form-inline my-2 my-lg-0">
                    <input class="form-control mr-sm-2" type="search" placeholder="输入想要查找的商品" aria-label="Search">
                    <button class="btn btn-outline-info my-2 my-sm-0" type="submit">Go!</button>
                </form>
            </li>
        </ul>
        <!-- 右侧 -->
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="register.html">注册</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="login.html">登录</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#" data-toggle="modal" data-target="#exampleModal">个人中心</a>
            </li>
        </ul>
    </div>
</nav>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">温馨提醒</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body text-danger mx-5">
                请先进行用户登录!!!
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<!-- 折叠面板 -->
<p class="text-center" style="margin-top: 6rem;">
    <a class="btn btn-primary" data-toggle="collapse" href="#multiCollapseExample1" role="button" aria-expanded="false" aria-controls="multiCollapseExample1">公司品牌</a>
    <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#multiCollapseExample2" aria-expanded="false" aria-controls="multiCollapseExample2">思想理念</button>
    <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#multiCollapseExample3" aria-expanded="false" aria-controls="multiCollapseExample3">联系方式</button>
</p>

<div class="row" style="width: 90%;margin: 0 auto;">
    <div class="col">
        <div class="collapse multi-collapse" id="multiCollapseExample1">
            <div class="card card-body shadow">
                翰林家居是一家专业生产沙发、床、桌子多种风格家居公司。采用欧洲E1级环保板材，涵盖实木床、组合 沙发、时尚书柜、餐桌、柜子、多种样式推拉门、隔断门、折叠门、百叶门、烤漆门等9大类。
            </div>
        </div>
    </div>
    <div class="col">
        <div class="collapse multi-collapse" id="multiCollapseExample2">
            <div class="card card-body shadow">
                【诚信为本】 【以质为本】
                【科技领先】 【服务取胜】
            </div>
        </div>
    </div>
    <div class="col">
        <div class="collapse multi-collapse" id="multiCollapseExample3">
            <div class="card card-body shadow">
                <p>电话：050-XXXXXXX(固话)</p>
                <p>134XXXXXXXX(手机)</p>
                <p>邮箱：xxx@123.com</p>
                <p>地址：广州大学华软软件学院 | 翰林家居</p>
            </div>
        </div>
    </div>
</div>

<!-- pic -->
<div style="height: 2rem;"></div>
<div class="container text-center">
    <img src="image/twopage03.jpg" class="img-thumbnail">
</div>

<%--<div style="height: 600px;"></div>--%>

<%--&lt;%&ndash;首页轮播&ndash;%&gt;--%>
<%--<div class="container" style="margin: 0 100px">--%>
<%--<div class="bd-example">--%>
    <%--<div id="carouselExampleCaptions" class="carousel slide" data-ride="carousel">--%>
        <%--<ol class="carousel-indicators">--%>
            <%--<li data-target="#carouselExampleCaptions" data-slide-to="0" class="active"></li>--%>
            <%--<li data-target="#carouselExampleCaptions" data-slide-to="1"></li>--%>
            <%--<li data-target="#carouselExampleCaptions" data-slide-to="2"></li>--%>
        <%--</ol>--%>
        <%--<div class="carousel-inner">--%>
            <%--<div class="carousel-item active">--%>
                <%--<img src="http://code.z01.com/img/2016instbg_01.jpg" class="d-block w-100" alt="...">--%>
                <%--<div class="carousel-caption d-none d-md-block">--%>
                    <%--<h5>First slide label</h5>--%>
                    <%--<p>Nulla vitae elit libero, a pharetra augue mollis interdum.</p>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="carousel-item">--%>
                <%--<img src="http://code.z01.com/img/2016instbg_02.jpg" class="d-block w-100" alt="...">--%>
                <%--<div class="carousel-caption d-none d-md-block">--%>
                    <%--<h5>Second slide label</h5>--%>
                    <%--<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="carousel-item">--%>
                <%--<img src="http://code.z01.com/img/2016instbg_03.jpg" class="d-block w-100" alt="...">--%>
                <%--<div class="carousel-caption d-none d-md-block">--%>
                    <%--<h5>Third slide label</h5>--%>
                    <%--<p>Praesent commodo cursus magna, vel scelerisque nisl consectetur.</p>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<a class="carousel-control-prev" href="#carouselExampleCaptions" role="button" data-slide="prev">--%>
            <%--<span class="carousel-control-prev-icon" aria-hidden="true"></span>--%>
            <%--<span class="sr-only">Previous</span>--%>
        <%--</a>--%>
        <%--<a class="carousel-control-next" href="#carouselExampleCaptions" role="button" data-slide="next">--%>
            <%--<span class="carousel-control-next-icon" aria-hidden="true"></span>--%>
            <%--<span class="sr-only">Next</span>--%>
        <%--</a>--%>
    <%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--<script>--%>
    <%--$('.carousel').carousel({--%>
        <%--interval: 2000--%>
    <%--})--%>
<%--</script>--%>
<%--&lt;%&ndash;tabs&ndash;%&gt;--%>
<%--<ul class="nav nav-tabs" id="myTab" role="tablist">--%>
    <%--<li class="nav-item">--%>
        <%--<a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">Home</a>--%>
    <%--</li>--%>

    <%--<li class="nav-item">--%>
        <%--<a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">Profile</a>--%>
    <%--</li>--%>
    <%--<li class="nav-item">--%>
        <%--<a class="nav-link" id="contact-tab" data-toggle="tab" href="#contact" role="tab" aria-controls="contact" aria-selected="false">Contact</a>--%>
    <%--</li>--%>

<%--</ul>--%>
<%--<div class="tab-content" id="myTabContent">--%>
    <%--<div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">1...</div>--%>

    <%--<div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">..2.</div>--%>
    <%--<div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="contact-tab">..3.</div>--%>

<%--</div>--%>
<%--<script>--%>
    <%--$('#myTab a').on('click', function (e) {--%>
        <%--e.preventDefault()--%>
        <%--$(this).tab('show')--%>
    <%--})--%>
<%--</script>--%>



车辆名称：
开始时间：
结束时间：
总天数：
预约金额：
状态：
支付状态：
<div style="margin:0 10px">
    <div id="rentList" class="tab-content" style="margin:0 90px">

        <label></label>
        <label></label>
        <label></label>
        <label></label>
        <label></label>
        <label></label>
        <label></label>

    </div>
    <div id="rentList2" class="tab-content" style="margin:0 90px">
        ssssssssssssssssssssssssssss
    </div>
</div>

<!-- footer -->
<footer>
    <div class="bg-dark" style="height: 50px;">
        <p class="small text-center text-secondary" style="padding-top: 15px;">翰林家居 | 广州大学华软软件学院版权@2018-10-15</p>
    </div>
</footer>

</body>
</html>