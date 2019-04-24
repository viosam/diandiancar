<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>租借列表</title>

    <!-- 引入CSS -->
    <link href="/diandiancar/css/bootstrap.css" type="text/css" rel="stylesheet">
    <link href="/diandiancar/css/style.css" type="text/css" rel="stylesheet">
    <!-- 引入JS -->
    <script src="/diandiancar/js/jquery-3.3.1.min.js"></script>
    <script src="/diandiancar/js/popper.min.js"></script>
    <script src="/diandiancar/js/bootstrap.js"></script>
</head>
<style type="text/css">
    body{background: url("/diandiancar/images/bg.jpg");}
</style>
<body>

<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="to-top.jsp"></jsp:include>

<div style="margin-top: 70px;"></div>
<div id="rentList" class="tab-content" style="margin: auto 90px; width: 80%;">
    <div style="margin: auto 20px;">
        <h3>我的租借：</h3>
        <div class="align-centerr" style="margin-top: 20px;">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">租车单号</th>
                    <th scope="col">预约订单号</th>
                    <th scope="col">开始时间</th>
                    <th scope="col">结束时间</th>
                    <th scope="col">总租用时间</th>
                    <th scope="col">押金</th>
                    <th scope="col">实付金额</th>
                    <th scope="col">车险</th>
                    <th scope="col">租车状态</th>
                    <th scope="col">押金支付状态</th>
                    <th scope="col">租金支付状态</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${rentDTOList}" var="rentDTO">
                    <tr>
                        <td><a href="/diandiancar/customer/rent/detail?rentId=${rentDTO.id}" >${rentDTO.id}</a></td>
                        <td>${rentDTO.bookId}</td>
                            <%--<td>${rentDTO.beginDate}</td>--%>
                            <%--<td>${rentDTO.endDate}</td>--%>
                        <td><fmt:formatDate value="${rentDTO.beginDate  }" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${rentDTO.endDate  }" pattern="yyyy-MM-dd"/></td>

                        <td>${rentDTO.rentSumDate}天</td>
                        <td>${rentDTO.paymentAmountDeposit}天</td>
                        <td>${rentDTO.paymentAmountRent}（元）</td>
                        <td>
                            <c:if test="${rentDTO.getCarInsuranceEnum().getMessage()=='无车险'}">
                                ${rentDTO.getCarInsuranceEnum().getMessage()}
                            </c:if>
                            <c:if test="${rentDTO.getCarInsuranceEnum().getMessage()=='有车险'}">
                                ${rentDTO.getCarInsuranceEnum().getMessage()}
                            </c:if>
                        </td>

                        <td>
                            <c:if test="${rentDTO.getRentStatusEnum().getMessage() == '提交租车信息'
                            && rentDTO.getPayDepositStatusEnum().getMessage() == '等待支付押金'}">
                                <a href="/diandiancar/customer/rent/cancel?rentId=${rentDTO.id}" class="btn btn-info">取消租车</a>
                            </c:if>
                            <c:if test="${rentDTO.getRentStatusEnum().getMessage() == '提交租车信息'
                            && rentDTO.getPayDepositStatusEnum().getMessage() != '等待支付押金'}">
                                ${rentDTO.getRentStatusEnum().getMessage()}
                            </c:if>
                            <c:if test="${rentDTO.getRentStatusEnum().getMessage() == '取消预约'}">
                                <a style="color: red">已取消</a>
                            </c:if>
                            <c:if test="${rentDTO.getRentStatusEnum().getMessage() == '租车完结'}">
                                已完结
                            </c:if>
                        </td>
                        <td>
                                <%--是否交押金--%>
                            <c:if test="${rentDTO.getPayDepositStatusEnum().getMessage() == '等待支付押金'}">
                                <a href="/diandiancar/customer/rent/deposit_index?rentId=${rentDTO.id}" class="btn btn-info">去支付</a>
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
                        </td>
                        <td>

                                <%--是否交租金--%>
                            <c:if test="${rentDTO.getPayRentStatusEnum().getMessage() == '等待支付租金'}">
                                <c:if test="${rentDTO.getPayDepositStatusEnum().getMessage() == '支付押金完成'&&
                                rentDTO.getRentStatusEnum().getMessage() == '提交租车信息'}">
                                    <a href="/diandiancar/customer/rent/rent_index?rentId=${rentDTO.id}" class="btn btn-info">去支付</a>
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
                        </td>

                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>

    </div>
</div>
</div>
<div style="margin-top: 300px"></div>
<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>