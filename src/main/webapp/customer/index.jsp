<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>点点租车首页</title>

    <!-- 引入CSS -->
    <link href="/diandiancar/css/bootstrap.css" type="text/css" rel="stylesheet">
    <link href="/diandiancar/css/style.css" type="text/css" rel="stylesheet">
    <link href="/diandiancar/css/index.css" rel="stylesheet" type="text/css">

    <!-- 引入JS -->
    <script src="/diandiancar/js/jquery-3.3.1.min.js"></script>
    <script src="/diandiancar/js/popper.min.js"></script>
    <script src="/diandiancar/js/bootstrap.js"></script>


</head>
<script>

    $(document).ready(function () {

        $.ajax({
            type: "get",
            url: "/diandiancar/carousel/list",
            data: {},
            dataType: "json",
            success: function (response) {
                var carouselList = response.data;
                var carouselHtml = "";
                var carouselbottomHtml = "";

                $.each(carouselList, function (i, carousel) {

                    if (i == 0) {
                        // 轮播图底部的横杆
                        carouselbottomHtml+='<li data-target="#carouselExampleCaptions" data-slide-to="0" class="active"></li>';
                        //轮播图
                        carouselHtml += '<div class="carousel-item active"><a href="/diandiancar/customer/carInfo/detail?carId='
                            +carousel.carId +'"><img src="'+ carousel.picUrl
                            + '" class="d-block w-100"> <div class="carousel-caption d-none d-md-block"> <p style="color: black">'
                            + carousel.description + '</p> </div> </div>';
                    } else {
                        // 轮播图底部的横杆
                        carouselbottomHtml+='<li data-target="#carouselExampleCaptions" data-slide-to="0"></li>';
                        //轮播图
                        carouselHtml += '<div class="carousel-item"><img src="'
                            + carousel.picUrl + '" class="d-block w-100"> <div class="carousel-caption d-none d-md-block"> <p style="color: black"  >'
                            + carousel.description + '</p> </div> </div>';
                    }
                });

                $("#carouselList").append(carouselHtml);
                $("#carouselbottom").append(carouselbottomHtml);
            }
        });

        //$(".carousel-item img").css("style","height: "+temp+"px");
        $(".carousel-item img").css("style", "height: 300px");
        // $("p").css("background-color", "yellow");

    })
    $("p").css("background-color", "yellow");
</script>
<body style="background-color: #f5f5f5;">

<jsp:include page="header.jsp"></jsp:include>
<%--首页轮播--%>
<%--<div class="container" style="margin-top: 10px;">--%>
    <div class="bd-example">
        <div id="carouselExampleCaptions" class="carousel slide" data-ride="carousel">
            <ol class="carousel-indicators" id="carouselbottom">

            </ol>
            <div class="carousel-inner" id="carouselList">

                <%--轮播图内容--%>

            </div>
            <a class="carousel-control-prev" href="#carouselExampleCaptions" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="carousel-control-next" href="#carouselExampleCaptions" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>
    </div>
</div>
<script>
    $('.carousel').carousel({
        interval: 2000
    })
</script>

<%--</div>--%>





<!-- 简介 -->
<div class="twopage">

    <div class="left">
        <img src="/diandiancar/images/introduct.jpg" width="480" height="300">
    </div>

    <div class="right">
        <article>
            <header>
                <h1 style="font-size: 24px;">点点租车简介</h1>
                <br>
            </header>
            <section class="article">
                点点租车--中国汽车租赁行业头部企业，在全国300多座城市开设4000多个服务网点，
                拥有200多种车型，7万多台租赁车辆。提供自驾租车、商务用车、个性化定制用车方案。
                全国任意网点支持异地取、还车，不限里程，拥有24小时客服、24小时救援、24小时门店，
                让您可以随时随地享受无忧的用车服务！
            </section>
        </article>
    </div>
    <div style="clear: both;"></div>
    <br><br><br><br>
    <div class="shadow"></div>
</div>


<!-- 专业方向 -->
<div style="width: 100%;height:60px;background-color: #f8f8f8"></div>
<div class="threepage">

    <p class="title">专注服务、安全、出行</p>

    <div class="content">

        <div class="modeldiv">
            <div class="pic">
                <img src="/diandiancar/images/introduct01.jpg" width="350" height="234">
            </div>
            <div class="introduce">
                <p class="p1"></p>
                <br>
                <p class="p2">
                    借助专业系统/解决车队管理难题
                </p>
            </div>
        </div>

        <div class="modeldiv">
            <div class="pic">
                <img src="/diandiancar/images/introduct02.jpg" width="350" height="234">
            </div>
            <div class="introduce">
                <p class="p1"></p>
                <br>
                <p class="p2">
                    企业贴身定制租车服务，专业高效
                </p>
            </div>
        </div>

        <div class="modeldiv">
            <div class="pic">
                <img src="/diandiancar/images/introduct03.jpg" width="350" height="234">
            </div>
            <div class="introduce">
                <p class="p1"></p>
                <br>
                <p class="p2">
                    200多座城市全天候私人管家式高品质接送服务题
                </p>
            </div>
        </div>

    </div>
</div>

<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>