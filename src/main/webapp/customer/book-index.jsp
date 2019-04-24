<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<!--车辆预约的主页,确认表单之后才能完成预约-->
<head>
    <meta charset="UTF-8">
    <title>预约</title>

    <!-- 引入CSS -->
    <link href="/diandiancar/css/bootstrap.css" type="text/css" rel="stylesheet">
    <link href="/diandiancar/css/style.css" type="text/css" rel="stylesheet">
    <!-- 引入JS -->
    <script src="/diandiancar/js/jquery-3.3.1.min.js"></script>
    <script src="/diandiancar/js/popper.min.js"></script>
    <script src="/diandiancar/js/bootstrap.js"></script>
</head>
</head>
<style type="text/css">
    body{background: url("/diandiancar/images/bg.jpg") no-repeat;background-size: 100%,100%;}
    .demo{width: 400px;height: 350px;position: absolute;top: 50%;left: 50%;margin-top: -200px;margin-left: -200px;border: 1px solid gainsboro;color: #f5f5f5;background: #000000;opacity: 0.7;box-shadow: 10px 10px 10px grey;border-radius: 8px;}
    a:hover{color: #87cefa !important;}
</style>

<script type="text/javascript">
    $(function () {
        $("#province").change(function (){
            $("#city option:not(:first)").remove();
            var provinceNum = $(this).val();
            if(province != ""){
                var url = "/diandiancar/customer/shop/city";
                var args = {"provinceNum":provinceNum,"time":new Date()};
                $.get(url,args,function (data) {
                    if (data.length > 0) {
                        for(var i=0;i<data.length;i++){
                            var city = data[i].city;
                            $("#city").append("<option value='"+city+"'>"+city+"</option>");
                        }
                    }
                });
            }
        })

    });
    $(function () {
        $("#city").change(function (){

            //清除之前的内容
            $("#shop option:not(:first)").remove();

            var city = $(this).val();
            if(city != ""){
                var url = "/diandiancar/customer/shop/shop";
                var args = {"city":city,"time":new Date()};
                $.get(url,args,function (data) {
                    if (data.length > 0) {
                        for(var i=0;i<data.length;i++){
                            var shop = data[i].shop;
                            $("#shop").append("<option value='"+shop+"'>"+shop+"</option>");
                        }
                    }
                });
            }
        })

    })

</script>
<body>

<jsp:include page="header.jsp"></jsp:include>


<div class="container" style="margin-top: 3rem;">
    <h3>确认预约：</h3>
    <div class="row">
        <div class="col-lg-2"></div>
        <div class="col-lg-8">
            <form action="/diandiancar/customer/book/create" method="post" id="myform">
                <input name="carID" type="hidden" value="${carInfo.carId}">
                省份：
                <select id="province">
                    <option value="">请选择...</option>
                    <c:forEach items="${provinceList}" var="province">
                        <option value="${province.provinceNum}">${province.province}</option>
                    </c:forEach>
                </select>
                城市：
                <select id="city" name="city" class="am-control-nav">
                    <option value="">请选择...</option>
                </select>
                门店：
                <select id="shop" name="fromShop" class="am-control-nav">
                    <option value="">请选择...</option>
                </select>
                <div class="form-group row">
                    <label for="input1" class="col-sm-2 col-form-label">车辆名称：</label>
                    <div class="col-sm-10">
                        <input type="text" readonly class="form-control-plaintext" id="input1"
                               value="${carInfo.carName}">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="input2" class="col-sm-2 col-form-label">可载人数：</label>
                    <div class="col-sm-10">
                        <input type="text" readonly class="form-control" id="input2" value="${carInfo.capacity}">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="input3" class="col-sm-2 col-form-label">发动机号：</label>
                    <div class="col-sm-10">
                        <input type="text" readonly class="form-control" id="input3" value="${carInfo.producer}">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="input4" class="col-sm-2 col-form-label">生产商：</label>
                    <div class="col-sm-10">
                        <input type="text" readonly class="form-control" id="input4" value="${carInfo.capacity}">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="input5" class="col-sm-2 col-form-label">描述：</label>
                    <div class="col-sm-10">
                        <input type="text" readonly class="form-control" id="input5" value="${carInfo.carDescription}">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="input6" class="col-sm-2 col-form-label">租金：</label>
                    <div class="col-sm-10">
                        <input type="text" readonly class="form-control" id="input6" value="${carInfo.rentPrice}(元/天)">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="input7" class="col-sm-2 col-form-label">预定起始日：</label>
                    <div class="col-sm-10">
                        <input name="bookBeginDate" type="date" id="input7" class="form-control" placeholder="预定起始日"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="input8" class="col-sm-2 col-form-label">预定结束日：</label>
                    <div class="col-sm-10">
                        <input name="bookEndDate" type="date" id="input8" class="form-control" placeholder="预定结束日"/>
                    </div>
                </div>
                <div class="form-group" align="center">
                    <div class="col-sm-10">
                        <input type="radio" name="carInsurance" checked value="1">购买<a>车险</a>
                        <input type="radio" name="carInsurance" value="0">无需<a>车险</a>
                    </div>
                </div>
                <br/>
                <span id="error" style="color: red"></span>
                <div class="form-group" style="margin-left: 40%;">
                    <a href="/diandiancar/customer/carInfo/detail?carId=${carInfo.carId}" class="btn btn-info">返回</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="submit" id="submit" class="btn btn-success" disabled value="确认" disabled>
                </div>
            </form>
        </div>

    </div>
</div>

<jsp:include page="footer.jsp"></jsp:include>

<script>
    //开始时间不得大于结束时间
    $('#input8,#input7').blur(function () {
        var start = $('#input7').val();
        var end = $('#input8').val();
        if (start > end) {
            $("#error").html("预定开始时间不得大于预定结束时间");
            $("#myform #submit").attr("disabled", 'disabled');
        } else {
            $("#error").html("");
            $("#myform #submit").removeAttr("disabled");
        }
    });
</script>
</body>
</html>