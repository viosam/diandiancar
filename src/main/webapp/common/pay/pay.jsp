<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<!--车辆预约的主页,确认表单之后才能完成预约-->
<head>
    <meta charset="UTF-8">
    <title>支付订金</title>

    <!-- 引入CSS -->
    <link href="/diandiancar/css/bootstrap.css" type="text/css" rel="stylesheet">
    <link href="/diandiancar/css/style.css" type="text/css" rel="stylesheet">
    <!-- 引入JS -->
    <script src="/diandiancar/js/jquery-3.3.1.min.js"></script>
    <script src="/diandiancar/js/popper.min.js"></script>
    <script src="/diandiancar/js/bootstrap.js"></script>
</head>
</head>

<body>

<jsp:include page="../../customer/header.jsp"></jsp:include>


<div class="container" style="margin-top: 3rem;">
    <h3>请付款：</h3>
    <div class="row">
        <div class="col-lg-2"></div>
        <div class="col-lg-8">
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">尊敬的用户：</label>

                </div>
                <div class="form-group row">
                    <label for="input2" class="col-sm-2 col-form-label">请支付：</label>
                    <div class="col-sm-10">
                        <input type="text" readonly class="form-control-plaintext" id="input2" value="${money}/元">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="input3" class="col-sm-2 col-form-label">订单号：</label>
                    <div class="col-sm-10">
                        <input type="text" readonly class="form-control-plaintext" id="input3" value="${orderId}">
                    </div>
                </div>
                <br/>
                <div class="form-group row">
                    <img style="margin-left:300px;" src="/diandiancar/images/pay.jpg">
                </div>
<br/>
                <span id="error" style="color: red"></span>
                <div class="form-group" style="margin-left: 40%;">
                    <a href="${forBackUrl}" class="btn btn-info">返回</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="${toUrl}" class="btn btn-success">确认支付</a>
                </div>
        </div>

    </div>
</div>

<jsp:include page="../../customer/footer.jsp"></jsp:include>

<script>
    //开始时间不得大于结束时间
    $('#input8,#input7').blur(function () {
       var start = $('#input7').val();
       var end = $('#input8').val();
       if(start > end){
           $("#error").html("预定开始时间不得大于预定结束时间");
           $("#myform #submit").attr("disabled",'disabled');
       }else {
           $("#error").html("");
           $("#myform #submit").removeAttr("disabled");
       }
    });
</script>
</body>
</html>