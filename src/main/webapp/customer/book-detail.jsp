<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<!--车辆预约的主页,确认表单之后才能完成预约-->
<head>
    <meta charset="UTF-8">
    <title>预约单详情</title>

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
<style type="text/css">
    body{background: url("/diandiancar/images/bg.jpg") no-repeat;background-size: 100%,100%;}
    .demo{width: 400px;height: 350px;position: absolute;top: 50%;left: 50%;margin-top: -200px;margin-left: -200px;border: 1px solid gainsboro;color: #f5f5f5;background: #000000;opacity: 0.7;box-shadow: 10px 10px 10px grey;border-radius: 8px;}
    a:hover{color: #87cefa !important;}
</style>

<jsp:include page="/customer/header.jsp"></jsp:include>


<div class="container" style="margin-top: 3rem;">
    <h3>预约单详情：</h3>
    <div class="row">
        <div class="col-lg-2"></div>
        <div class="col-lg-8">
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">尊敬的用户：</label>

            </div>
            <div class="form-group row">
                <label for="input2" class="col-sm-2 col-form-label">预约单号：</label>
                <div class="col-sm-10">
                    <input type="text" readonly class="form-control-plaintext" id="input2" value="${bookDTO.id}">
                </div>
            </div>
            <div class="form-group row">
                <label for="input3" class="col-sm-2 col-form-label">预定开始日：</label>
                <div class="col-sm-10">
                    <input type="text" readonly class="form-control-plaintext" id="input3"
                           value="<fmt:formatDate value="${bookDTO.bookBeginDate  }" pattern="yyyy-MM-dd"/>">
                </div>
            </div>
            <div class="form-group row">
                <label for="input4" class="col-sm-2 col-form-label">预定结束日：</label>
                <div class="col-sm-10">
                    <input type="text" readonly class="form-control-plaintext" id="input4"
                           value="<fmt:formatDate value="${bookDTO.bookEndDate  }" pattern="yyyy-MM-dd"/>">
                </div>
            </div>
            <div class="form-group row">
                <label for="input5" class="col-sm-2 col-form-label">预计租用天数：</label>
                <div class="col-sm-10">
                    <input type="text" readonly class="form-control-plaintext" id="input5"
                           value="${bookDTO.bookSumDate}">
                </div>
            </div>
            <div class="form-group row">
                <label for="input6" class="col-sm-2 col-form-label">定金：</label>
                <div class="col-sm-5">
                    <input type="text" readonly class="form-control-plaintext" id="input6"
                           value="${bookDTO.earnestMoney}">
                </div>
                <div class="col-sm-5">
                    <c:if test="${bookDTO.getBookStatusEnum().getMessage() != '已取消'}">
                        <c:if test="${bookDTO.getPayBookStatusEnum().getMessage()=='预约金支付完成'}">
                            ${bookDTO.getPayBookStatusEnum().getMessage()}
                        </c:if>
                        <c:if test="${bookDTO.getPayBookStatusEnum().getMessage()=='预约金退款中'}">
                            ${bookDTO.getPayBookStatusEnum().getMessage()}
                        </c:if>
                        <c:if test="${bookDTO.getPayBookStatusEnum().getMessage()=='预约金退款成功'}">
                            ${bookDTO.getPayBookStatusEnum().getMessage()}
                        </c:if>
                        <c:if test="${bookDTO.getPayBookStatusEnum().getMessage()=='预约金退款失败'}">
                            ${bookDTO.getPayBookStatusEnum().getMessage()}
                        </c:if>
                        <c:if test="${bookDTO.getPayBookStatusEnum().getMessage()=='支付失败'}">
                            ${bookDTO.getPayBookStatusEnum().getMessage()}
                        </c:if>
                    </c:if>

                </div>

            </div>
            <div class="form-group row">
                <label for="input7" class="col-sm-2 col-form-label">预约状态：</label>
                <div class="col-sm-10">
                    <input type="text" readonly class="form-control-plaintext" id="input7"
                           value="${bookDTO.getBookStatusEnum().getMessage()}">
                </div>
            </div>
            <div class="form-group row"><label for="input7" class="col-sm-2 col-form-label">租借门店：</label>
                <div class="col-sm-10"> <input type="text" readonly class="form-control-plaintext" value="${bookDTO.fromShop}"></div>
            <span id="error" style="color: red"></span>
            <div class="form-group" style="margin-left: 40%;">
                <a href="/diandiancar/customer/book/toList" class="btn btn-info">返回</a>

                <c:if test="${bookDTO.getBookStatusEnum().getMessage() != '已取消'}">

                        <c:if test="${bookDTO.getPayBookStatusEnum().getMessage() == '等待支付预约金'}">
                            <a href="/diandiancar/customer/book/toPay?id=${bookDTO.id}&carID=${bookDTO.carID}"
                               class="btn btn-warning">去支付</a>
                        </c:if>
                        <c:if test="${bookDTO.getPayBookStatusEnum().getMessage() == '预约金支付完成'}">
                            <a href="/diandiancar/customer/book/cancel?bookId=${bookDTO.id}" class="btn btn-warning">我要退款</a>
                        </c:if>
                </c:if>
            </div>
        </div>

    </div>
</div>

<jsp:include page="/customer/footer.jsp"></jsp:include>

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