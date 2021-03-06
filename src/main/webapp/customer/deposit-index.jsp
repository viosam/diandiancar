<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<!--在预约信息表中点击租车后跳转到这个页面，这是租借信息确认的页面，form中name对应BookForm的字段-->
<head>
    <meta charset="UTF-8">
    <title>请交押金</title>

    <!-- 引入CSS -->
    <link href="/diandiancar/css/bootstrap.css" type="text/css" rel="stylesheet">
    <link href="/diandiancar/css/style.css" type="text/css" rel="stylesheet">
    <!-- 引入JS -->
    <script src="/diandiancar/js/jquery-3.3.1.min.js"></script>
    <script src="/diandiancar/js/popper.min.js"></script>
    <script src="/diandiancar/js/bootstrap.js"></script></head>
</head>

<style type="text/css">
    body{background: url("/diandiancar/images/bg.jpg") no-repeat;background-size: 100%,100%;}
    .demo{width: 400px;height: 350px;position: absolute;top: 50%;left: 50%;margin-top: -200px;margin-left: -200px;border: 1px solid gainsboro;color: #f5f5f5;background: #000000;opacity: 0.7;box-shadow: 10px 10px 10px grey;border-radius: 8px;}
    a:hover{color: #87cefa !important;}
</style>
<body>

<jsp:include page="header.jsp"></jsp:include>

<div class="container" style="margin-top: 3rem;">
    <h3>请交押金：</h3>
    <div class="row">
        <div class="col-lg-2"></div>
        <div class="col-lg-8">
            <form action="/diandiancar/customer/rent/to_deposit_pay" method="post" id="myform">

                <div class="form-group row">
                    <label for="input1" class="col-sm-2 col-form-label">租车单号：</label>
                    <div class="col-sm-10">
                        <input type="text" name="id"  readonly class="form-control-plaintext" id="input1" value="${rentDTO.id}">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="input2" class="col-sm-2 col-form-label">车辆名称：</label>
                    <div class="col-sm-10">
                        <input type="text" name="carName"  readonly class="form-control-plaintext" id="input2" value="${carInfo.carName}">
                    </div>
                </div>

                <div class="form-group row">
                    <label for="input3" class="col-sm-2 col-form-label">押金：</label>
                    <div class="col-sm-10">
                        <input type="text" name="paymentAmount" readonly class="form-control" id="input3" value="${rentDTO.paymentAmountDeposit}">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="input4" class="col-sm-2 col-form-label">租借起始日：</label>
                    <div class="col-sm-10">
                        <input name="beginDate" disabled type="date" id="input4" value="<fmt:formatDate value="${rentDTO.beginDate  }" pattern="yyyy-MM-dd"/>" class="form-control" placeholder="租借起始日"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="input5" class="col-sm-2 col-form-label">租借结束日：</label>
                    <div class="col-sm-10">
                        <input name="endDate" disabled type="date" id="input5" value="<fmt:formatDate value="${rentDTO.endDate  }" pattern="yyyy-MM-dd"/>" class="form-control" placeholder="租借结束日"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">是否购买车险：</label>
                    <div class="col-sm-10">
                        ${rentDTO.getCarInsuranceEnum().getMessage()}
                    </div>
                </div>
                <span id="error" style="color: red"></span>
                <div class="form-group" style="margin-left: 40%;">
                    <a href="/diandiancar/customer/rent/toList" class="btn btn-info">返回</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="submit" id="submit" class="btn btn-success" value="确认支付">
                </div>
            </form>
        </div>

    </div>
</div>

<jsp:include page="footer.jsp"></jsp:include>

<%--<script>
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
</script>--%>
<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>