<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>汽车列表</title>

    <!-- 引入CSS -->
    <link href="/diandiancar/css/bootstrap.css" type="text/css" rel="stylesheet">
    <link href="/diandiancar/css/style.css" type="text/css" rel="stylesheet">
    <!-- 引入JS -->
    <script src="/diandiancar/js/jquery-3.3.1.min.js"></script>
    <script src="/diandiancar/js/popper.min.js"></script>
    <script src="/diandiancar/js/bootstrap.js"></script>



    <style type="text/css">
        body{background: url("/diandiancar/images/bg.jpg");}
    </style>
</head>

<style>
    #carInfos div div a {
        text-decoration: none;
    }
</style>
<script type="text/javascript">
    $(document).ready(function () {

        var hasCarName = "<%=request.getAttribute("hasCarName") %>";
        if (hasCarName == "notCarName") {
            $.ajax({
                type: "get",
                url: "/diandiancar/customer/carInfo/list",
                data: {},
                dataType: "json",
                success: function (response) {
                    var code = response.code;
                    var message = response.msg;
                    var carVOList = response.data;
                    var categoryHtml = "";
                    var carInfoHtml = "";
                    var length = carVOList.length;
                    if (length > 0) {

                        //response:接口返回的list数据,i迭代的次数
                        //第一层迭代车辆类型：carVOList这个列表存放的数据是根据车辆类型归类的
                        $.each(carVOList, function (i, carVO) {
                            var carCategoryName = carVO.carCategoryName;
                            var carCategoryType = carVO.carCategoryType;
                            //车辆类型的标签列表

                            if (i == 0) {
                                categoryHtml += '<li class="nav-item"><a class="nav-link active" id="aaa' + carCategoryType + '" data-toggle="tab" href="#' + "a" + carCategoryType + '" role="tab" aria-controls="' + "a" + carCategoryType + '" aria-selected="true">' + carCategoryName + '</a></li>';
                            } else {
                                categoryHtml += '<li class="nav-item"><a class="nav-link" id="aaa' + carCategoryType + '" data-toggle="tab" href="#' + "a" + carCategoryType + '" role="tab" aria-controls="' + "a" + carCategoryType + '" aria-selected="false">' + carCategoryName + '</a></li>';
                            }
                            // alert("carCategoryName:" + carCategoryName + ",carCategoryType:" + carCategoryType);
                            //主要作用是根据category标签的id，拼凑在车辆信息列表的div（点击category标签可以跳转到对应车辆信息列表）
                            if (i == 0) {
                                carInfoHtml += '<div class="tab-pane fade show active" id="' + "a" + carCategoryType + '" role="tabpanel" aria-labelledby="aaa' + carCategoryType + '">';
                            } else {
                                carInfoHtml += '<div class="tab-pane fade" id="' + "a" + carCategoryType + '" role="tabpanel" aria-labelledby="aaa' + carCategoryType + '">';
                            }
                            var carInfoVOList = carVO.carInfoVOList;
                            //这层迭代：该类型下的车辆信息列表
                            $.each(carInfoVOList, function (i, carInfoVO) {
                                var carId = carInfoVO.carId;
                                var carName = carInfoVO.carName;
                                var capacity = carInfoVO.capacity;
                                var kilometers = carInfoVO.kilometers;
                                var producer = carInfoVO.producer;
                                var rentPrice = carInfoVO.rentPrice;
                                var productTime = carInfoVO.productTime;
                                var carIcon = carInfoVO.carIcon;
                                var carDescription = carInfoVO.carDescription;

                                carInfoHtml += '<div class="card" style="width: 20rem;"><a href="/diandiancar/customer/carInfo/detail?carId=' + carId + '">' +
                                    '<img class="card-img-top" src="'+carIcon+'" height="250"><div class="card-body"><p class="card-text" style="color: rgb(188, 143, 143);">'
                                    + carName + '</p><h5 class="card-title font-weight-light" style="color: rgb(239, 156, 62);">租金：'
                                    + rentPrice + '元/日</h5></div></div>';

                            });
                            //最后补完外层标签
                            carInfoHtml += '</div>';
                        });
                        //添加到对应的位置
                        $("#carCategorys").append(categoryHtml);
                        $("#carInfos").append(carInfoHtml);
                    } else {
                        carInfoHtml += "<div style='margin-top: 50px'></div><dic style='color: black;margin-left: 100px;margin-top: 1200px'><h2>查无数据</h2></div>";
                        $("#searchError").append(carInfoHtml);
                    }

                    console.log(categoryHtml);
                    console.log(carInfoHtml);
                }
            });
        } else {
            var carName = "<%=request.getAttribute("carName") %>";
            $.ajax({
                type: "get",
                url: "/diandiancar/customer/carInfo/list",
                data: {"carName": carName},
                dataType: "json",
                success: function (response) {
                    var code = response.code;
                    var message = response.msg;
                    var carVOList = response.data;
                    var categoryHtml = "";
                    var carInfoHtml = "";
                    var length = carVOList.length;
                    // alert("carVOList.size:"+length);
                    if (length > 0) {
                        //response:接口返回的list数据,i迭代的次数
                        //第一层迭代车辆类型：carVOList这个列表存放的数据是根据车辆类型归类的
                        $.each(carVOList, function (i, carVO) {
                            var carCategoryName = carVO.carCategoryName;
                            var carCategoryType = carVO.carCategoryType;
                            //车辆类型的标签列表
                            if (i == 0) {
                                categoryHtml += '<li class="nav-item"><a class="nav-link active" id="aaa' + carCategoryType + '" data-toggle="tab" href="#' + "a" + carCategoryType + '" role="tab" aria-controls="' + "a" + carCategoryType + '" aria-selected="true">' + carCategoryName + '</a></li>';
                            } else {
                                categoryHtml += '<li class="nav-item"><a class="nav-link" id="aaa' + carCategoryType + '" data-toggle="tab" href="#' + "a" + carCategoryType + '" role="tab" aria-controls="' + "a" + carCategoryType + '" aria-selected="false">' + carCategoryName + '</a></li>';
                            }
                            // alert("carCategoryName:" + carCategoryName + ",carCategoryType:" + carCategoryType);
                            //主要作用是根据category标签的id，拼凑在车辆信息列表的div（点击category标签可以跳转到对应车辆信息列表）
                            if (i == 0) {
                                carInfoHtml += '<div class="tab-pane fade show active" id="' + "a" + carCategoryType + '" role="tabpanel" aria-labelledby="aaa' + carCategoryType + '">';
                            } else {
                                carInfoHtml += '<div class="tab-pane fade" id="' + "a" + carCategoryType + '" role="tabpanel" aria-labelledby="aaa' + carCategoryType + '">';
                            }
                            var carInfoVOList = carVO.carInfoVOList;
                            //这层迭代：该类型下的车辆信息列表
                            $.each(carInfoVOList, function (i, carInfoVO) {
                                var carId = carInfoVO.carId;
                                var carName = carInfoVO.carName;
                                var capacity = carInfoVO.capacity;
                                var kilometers = carInfoVO.kilometers;
                                var producer = carInfoVO.producer;
                                var rentPrice = carInfoVO.rentPrice;
                                var productTime = carInfoVO.productTime;
                                var carIcon = carInfoVO.carIcon;
                                var carDescription = carInfoVO.carDescription;

                                carInfoHtml += '<div class="card" style="width: 20rem;"><a href="/diandiancar/customer/carInfo/detail?carId=' + carId + '">' +
                                    '<img class="card-img-top" src="'+carIcon+'" height="250"><div class="card-body"><p class="card-text" style="color: rgb(188, 143, 143);">'
                                    + carName + '</p><h5 class="card-title font-weight-light" style="color: rgb(239, 156, 62);">租金：'
                                    + rentPrice + '元/日</h5></div></div>';

                            });
                            //最后补完外层标签
                            carInfoHtml += '</div>';
                        });
                        //添加到对应的位置
                        $("#carCategorys").append(categoryHtml);
                        $("#carInfos").append(carInfoHtml);
                    } else {
                        carInfoHtml += "<div style='margin-top: 50px'></div><dic style='color: black;margin-left: 100px;margin-top: 1200px'><h2>查无数据</h2></div>";
                        $("#searchError").append(carInfoHtml);
                    }

                    console.log(categoryHtml);
                    console.log(carInfoHtml);
                }
            });
        }

    })

</script>


<body backgroun>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="to-top.jsp"></jsp:include>

<div>

    <%--tabs--%>
    <%--这个位置放的是车辆类型--%>
    <ul class="nav nav-tabs" id="carCategorys" role="tablist" style="background-color: #e3f2fd">
    </ul>
    <%--这个位置放的是该类型下的车辆列表--%>
    <div class="container card-columns" style="margin-top: 2rem;">
        <div class="tab-content" id="carInfos">

        </div>

    </div>
    <div id="searchError" style="margin-left: 45rem;margin-bottom: 450px"></div>
</div>
<script>
    $('#carCategorys li a').click(function (e) {
        e.preventDefault();
        $(this).tab('show');
    })

</script>
<div style="margin-top: 200px"></div>
<div style="margin-top: 50px"></div>
<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>