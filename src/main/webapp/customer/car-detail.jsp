<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>footer</title>
    <!-- 引入CSS -->
    <link href="/diandiancar/css/bootstrap.css" type="text/css" rel="stylesheet">
    <link href="/diandiancar/css/style.css" type="text/css" rel="stylesheet">
    <!-- 引入JS -->
    <script src="/diandiancar/js/jquery-3.3.1.min.js"></script>
    <script src="/diandiancar/js/popper.min.js"></script>
    <script src="/diandiancar/js/bootstrap.js"></script>

    <style type="text/css">
        .box1 {
            margin-top: 15px;
            font-size: 16px;
            padding: 3px;
            text-decoration: none;
        }
    </style>
</head>
<style type="text/css">
    body{background: url("/diandiancar/images/bg.jpg") no-repeat;background-size: 100%,100%;}
    .demo{width: 400px;height: 350px;position: absolute;top: 50%;left: 50%;margin-top: -200px;margin-left: -200px;border: 1px solid gainsboro;color: #f5f5f5;background: #000000;opacity: 0.7;box-shadow: 10px 10px 10px grey;border-radius: 8px;}
    a:hover{color: #87cefa !important;}
</style>
<body>
<jsp:include page="header.jsp"></jsp:include>

<div class="container" style="margin-top: 5rem;">
    <div class="row" style="margin: auto 20px;">
        <div class="col-lg-2"></div>
        <div>

        </div>
        <div class="col-lg-4">
            <div style="margin-top: 15px;">
                <img src="${carInfo.carIcon}" width="300px" height="350px">
            </div>
        </div>
        <div class="col-lg-6" style="margin-top: 10px;">
            <div class="box1" id="carName">车辆名称：${carInfo.carName}</div>
            <div class="box1" id="capacity">可载人数：${carInfo.capacity}</div>
            <div class="box1" id="kilometers">公里数：${carInfo.kilometers}&nbsp;&nbsp;公里</div>
            <div class="box1" id="producer">生产商: ${carInfo.producer}</div>
            <div class="box1" id="carDescription">描述：${carInfo.carDescription}</div>
            <div class="box1" id="rentPrice">租金：${carInfo.rentPrice}(元/天)</div>
            <div class="box1"><span style="color: #892E65">可预约!</span></div>
            <div>
                <a href="/diandiancar/customer/book/index?carId=${carInfo.carId}" class="btn btn-info btn-sm" style="margin-left: 250px">我要预约</a>
            </div>
        </div>

    </div>
</div>

<%--<script>--%>
    <%--function book(carId) {--%>
        <%--// alert("in" + carId);--%>

        <%--$.ajax({--%>
            <%--type: "get",--%>
            <%--url: "/diandiancar/customer/book/index",--%>
            <%--data: {"carId": carId},--%>
            <%--success: function (response) {--%>
                <%--alert("success");--%>

            <%--},--%>
            <%--error: function (response) {--%>
                <%--alert("error");--%>

            <%--}--%>

        <%--})--%>
    <%--}--%>

<%--</script>--%>

<div style="margin-top: 200px"></div>


<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>