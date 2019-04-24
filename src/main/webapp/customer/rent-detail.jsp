<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<!--车辆预约的主页,确认表单之后才能完成预约-->
<head>
    <meta charset="UTF-8">
    <title>租借单详情</title>

    <!-- 引入CSS -->
    <link href="/diandiancar/css/bootstrap.css" type="text/css" rel="stylesheet">
    <link href="/diandiancar/css/style.css" type="text/css" rel="stylesheet">
    <!-- 引入JS -->
    <script src="/diandiancar/js/jquery-3.3.1.min.js"></script>
    <script src="/diandiancar/js/popper.min.js"></script>
    <script src="/diandiancar/js/bootstrap.js"></script>
</head>

<style type="text/css">
    body {
        background: url("/diandiancar/images/bg.jpg") no-repeat;
        background-size: 100%, 100%;
    }

    .demo {
        width: 400px;
        height: 350px;
        position: absolute;
        top: 50%;
        left: 50%;
        margin-top: -200px;
        margin-left: -200px;
        border: 1px solid gainsboro;
        color: #f5f5f5;
        background: #000000;
        opacity: 0.7;
        box-shadow: 10px 10px 10px grey;
        border-radius: 8px;
    }

    a:hover {
        color: #87cefa !important;
    }
</style>
</head>

<body>

<jsp:include page="/customer/header.jsp"></jsp:include>


<div class="container" style="margin-top: 3rem;">
    <h3>租借单详情：</h3>
    <div class="row">
        <div class="col-lg-2"></div>
        <div class="col-lg-8">
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">尊敬的用户：</label>

            </div>
            <div class="form-group row">
                <label for="input1" class="col-sm-2 col-form-label">租借单号：</label>
                <div class="col-sm-10">
                    <input type="text" readonly class="form-control-plaintext" id="input1" value="${rentDTO.id}">
                </div>
            </div>
            <div class="form-group row">
                <label for="input2" class="col-sm-2 col-form-label">预约单号：</label>
                <div class="col-sm-10">
                    <input type="text" readonly class="form-control-plaintext" id="input2" value="${rentDTO.bookId}">
                </div>
            </div>
            <div class="form-group row">
                <label for="input3" class="col-sm-2 col-form-label">预定开始日：</label>
                <div class="col-sm-10">
                    <input type="text" readonly class="form-control-plaintext" id="input3"
                           value="<fmt:formatDate value="${rentDTO.beginDate}" pattern="yyyy-MM-dd"/>">
                </div>
            </div>
            <div class="form-group row">
                <label for="input4" class="col-sm-2 col-form-label">预定结束日：</label>
                <div class="col-sm-10">
                    <input type="text" readonly class="form-control-plaintext" id="input4"
                           value="<fmt:formatDate value="${rentDTO.endDate}" pattern="yyyy-MM-dd"/>">
                </div>
            </div>
            <div class="form-group row">
                <label for="input5" class="col-sm-2 col-form-label">租用天数：</label>
                <div class="col-sm-10">
                    <input type="text" readonly class="form-control-plaintext" id="input5"
                           value="${rentDTO.rentSumDate}/天">
                </div>
            </div>
            <div class="form-group row">
                <label for="input6" class="col-sm-2 col-form-label">押金：</label>
                <div class="col-sm-5">
                    <input type="text" readonly class="form-control-plaintext" id="input6"
                           value="${rentDTO.paymentAmountDeposit}">
                </div>
                <div class="col-sm-5">
                    <%--是否交押金--%>
                    <c:if test="${rentDTO.getPayDepositStatusEnum().getMessage() == '等待支付押金'}">
                        <a href="/diandiancar/customer/rent/deposit_index?rentId=${rentDTO.id}">去支付</a>
                    </c:if>
                    <c:if test="${rentDTO.getPayDepositStatusEnum().getMessage() == '支付押金完成'}">
                        ${rentDTO.getPayDepositStatusEnum().getMessage()}
                    </c:if>
                    <c:if test="${rentDTO.getPayDepositStatusEnum().getMessage() == '押金退款中'}">
                        ${rentDTO.getPayDepositStatusEnum().getMessage()}
                    </c:if>
                    <c:if test="${rentDTO.getPayDepositStatusEnum().getMessage() == '押金退款成功'}">
                        ${rentDTO.getPayDepositStatusEnum().getMessage()}
                    </c:if>
                    <c:if test="${rentDTO.getPayDepositStatusEnum().getMessage() == '押金退款失败'}">
                        <a style="color: red">${rentDTO.getPayDepositStatusEnum().getMessage()}</a>
                    </c:if>
                    <c:if test="${rentDTO.getPayDepositStatusEnum().getMessage() == '押金支付失败'}">
                        <a style="color: red">${rentDTO.getPayDepositStatusEnum().getMessage()}</a>
                    </c:if>
                </div>

            </div>
            <div class="form-group row">
                <label for="input7" class="col-sm-2 col-form-label">租金：</label>
                <div class="col-sm-5">
                    <input type="text" readonly class="form-control-plaintext" id="input7"
                           value="${rentDTO.paymentAmountDeposit}">
                </div>
                <div class="col-sm-5">
                    <%--是否交租金--%>
                    <c:if test="${rentDTO.getPayRentStatusEnum().getMessage() == '等待支付租金'}">
                        <c:if test="${rentDTO.getPayDepositStatusEnum().getMessage() == '支付押金完成'&&
                                rentDTO.getRentStatusEnum().getMessage() == '提交租车信息'}">
                            <a href="/diandiancar/customer/rent/rent_index?rentId=${rentDTO.id}">去支付</a>
                        </c:if>
                        <c:if test="${rentDTO.getPayDepositStatusEnum().getMessage() == '支付押金完成'&&
                                rentDTO.getRentStatusEnum().getMessage() != '提交租车信息'}">
                            <c:if test="${rentDTO.getRentStatusEnum().getMessage() == '租车完结'}">
                                已付租金
                            </c:if>
                            <c:if test="${rentDTO.getRentStatusEnum().getMessage() == '取消预约'}">
                                取消支付
                            </c:if>

                        </c:if>
                        <c:if test="${rentDTO.getPayDepositStatusEnum().getMessage() != '支付押金完成'}">
                            ${rentDTO.getPayRentStatusEnum().getMessage()}
                        </c:if>
                    </c:if>
                    <c:if test="${rentDTO.getPayRentStatusEnum().getMessage() == '租金支付完成'}">
                        ${rentDTO.getPayRentStatusEnum().getMessage()}
                    </c:if>
                    <c:if test="${rentDTO.getPayRentStatusEnum().getMessage() == '租金退款中'}">
                        ${rentDTO.getPayRentStatusEnum().getMessage()}
                    </c:if>
                    <c:if test="${rentDTO.getPayRentStatusEnum().getMessage() == '租金退款成功'}">
                        ${rentDTO.getPayRentStatusEnum().getMessage()}
                    </c:if>
                    <c:if test="${rentDTO.getPayRentStatusEnum().getMessage() == '租金退款失败'}">
                        ${rentDTO.getPayRentStatusEnum().getMessage()}
                    </c:if>
                    <c:if test="${rentDTO.getPayRentStatusEnum().getMessage() == '支付失败'}">
                        ${rentDTO.getPayRentStatusEnum().getMessage()}
                    </c:if>
                </div>

            </div>

            <div class="form-group row">
                <label for="fromShop" class="col-sm-2 col-form-label">租借门店：</label>
                <div class="col-sm-10">
                    <input type="text" readonly class="form-control-plaintext" id="fromShop"
                           value="${rentDTO.fromShop}">
                </div>
            </div>
            <c:if test="${rentDTO.getPayRentStatusEnum().getMessage()=='租金支付完成'}">
                <div class="form-group row">
                    <label for="toShop" class="col-sm-2 col-form-label">归还门店：</label>
                    <div class="col-sm-10">
                        <input type="text" readonly class="form-control-plaintext" id="toShop"
                               value="${rentDTO.toShop}">
                    </div>
                </div>
            </c:if>
            <span id="error" style="color: red"></span>
            <div class="form-group" style="margin-left: 40%;">
                <a href="/diandiancar/customer/rent/toList" class="btn btn-info">返回</a>
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