<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>testshop</title>

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


<body backgroun>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="to-top.jsp"></jsp:include>

<div>

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
    <select id="shop" name="shop" class="am-control-nav">
        <option value="">请选择...</option>
    </select>

</div>
<div style="margin-top: 200px"></div>
<div style="margin-top: 50px"></div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>