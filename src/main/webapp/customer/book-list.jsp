<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>预约列表</title>
    <!-- 引入CSS -->
    <link href="/diandiancar/css/bootstrap.css" type="text/css" rel="stylesheet">
    <link href="/diandiancar/css/style.css" type="text/css" rel="stylesheet">
    <!-- 引入JS -->
    <script src="/diandiancar/js/jquery-3.3.1.min.js"></script>
    <script src="/diandiancar/js/popper.min.js"></script>
    <script src="/diandiancar/js/bootstrap.js"></script>
</head>

<script type="text/javascript">

    //将时间戳转成正常格式
    /* function getMyDate(str) {
         var oDate = new Date(str),
             oYear = oDate.getFullYear(),
             oMonth = oDate.getMonth() + 1,
             oDay = oDate.getDate(),
             oHour = oDate.getHours(),
             oMin = oDate.getMinutes(),
             oSen = oDate.getSeconds(),
             oTime = oYear + '-' + getzf(oMonth) + '-' + getzf(oDay) + ' ' + getzf(oHour) + ':' + getzf(oMin) + ':' + getzf(oSen);//最后拼接时间
         return oTime;
     };

     //补0操作
     function getzf(num) {
         if (parseInt(num) < 10) {
             num = '0' + num;
         }
         return num;
     };*/
</script>


<style>

    #mytable tr td a {
        text-decoration: none;
    }
</style>

<style type="text/css">
    body{background: url("/diandiancar/images/bg.jpg");}
</style>
<body>

<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="to-top.jsp"></jsp:include>


<div style="margin-top: 70px;">
    <div id="rentList" class="tab-content" style="margin: auto 90px; width: 70%;">

        <div style="margin: auto 20px;">
            <h3>我的预约：</h3>
            <div class="align-centerr" style="margin-top: 20px;">
                <table class="table table-striped" id="mytable">
                    <tr>
                        <th scope="col">预约单号</th>
                        <th scope="col">预定开始日</th>
                        <th scope="col">预定结束日</th>
                        <th scope="col">预计租用天数</th>
                        <th scope="col">定金</th>
                        <th scope="col">预约状态</th>
                        <th scope="col">支付状态</th>
                    </tr>
                    <c:forEach items="${bookDTOList}" var="bookDTO">
                        <tr>
                            <td><a href="/diandiancar/customer/book/detail?bookId=${bookDTO.id}"> ${bookDTO.id}</a></td>
                                <%--<td>${bookDTO.bookBeginDate}</td>--%>
                                <%--<td>${bookDTO.bookEndDate}</td>--%>
                            <td><fmt:formatDate value="${bookDTO.bookBeginDate  }" pattern="yyyy-MM-dd"/></td>
                            <td><fmt:formatDate value="${bookDTO.bookEndDate  }" pattern="yyyy-MM-dd"/></td>
                            <td>${bookDTO.bookSumDate}天</td>
                            <td>${bookDTO.earnestMoney}(元/天)</td>
                            <td>
                                <c:if test="${bookDTO.getBookStatusEnum().getMessage() == '提交预约'}">
                                    <a href="/diandiancar/customer/book/cancel?bookId=${bookDTO.id}" class="btn btn-info">取消预约</a>
                                </c:if>
                                <c:if test="${bookDTO.getBookStatusEnum().getMessage() != '提交预约'}">
                                    ${bookDTO.getBookStatusEnum().getMessage()}
                                </c:if>
                            </td>
                            <c:if test="${bookDTO.getBookStatusEnum().getMessage() == '已取消'}">
                                <td>已取消</td>
                            </c:if>

                            <c:if test="${bookDTO.getBookStatusEnum().getMessage() != '已取消'}">

                                <td>
                                    <c:if test="${bookDTO.getPayBookStatusEnum().getMessage() == '等待支付预约金'}">
                                        <a href="/diandiancar/customer/book/toPay?id=${bookDTO.id}&carID=${bookDTO.carID}" class="btn btn-info">去支付</a>
                                    </c:if>
                                    <c:if test="${bookDTO.getPayBookStatusEnum().getMessage() == '预约金支付完成'}">
                                        <a href="/diandiancar/customer/book/cancel?bookId=${bookDTO.id}" class="btn btn-info">我要退款</a>
                                    </c:if>
                                    <c:if test="${bookDTO.getPayBookStatusEnum().getMessage() != '等待支付预约金'&&
                                bookDTO.getPayBookStatusEnum().getMessage() != '预约金支付完成'}">
                                        ${bookDTO.getPayBookStatusEnum().getMessage()}
                                    </c:if>
                                </td>
                            </c:if>

                        </tr>
                    </c:forEach>

                </table>
            </div>
        </div>

    </div>
</div>

<div style="margin-top: 300px"></div>

<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>