<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="/diandiancar/admin/css/amazeui.min.css"/>
    <link rel="stylesheet" href="/diandiancar/admin/css/admin.css"/>
    <!-- 引入CSS -->
    <link href="/diandiancar/css/bootstrap.css" type="text/css" rel="stylesheet">
    <link href="/diandiancar/css/style.css" type="text/css" rel="stylesheet">
    <!-- 引入JS -->
    <script src="/diandiancar/js/jquery-3.3.1.min.js"></script>
    <script src="/diandiancar/js/popper.min.js"></script>
    <script src="/diandiancar/js/bootstrap.js"></script>

    <style>
        table tr,td{
            font-size: 15px;
            text-align: center;
        }
    </style>
</head>

<body>
<div class="admin-content-body">
    <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">友情链接</strong>
            <small></small>
        </div>
    </div>

    <hr>

    <div class="am-g">
        <div class="am-u-sm-12 am-u-md-3">

        </div>
        <%--<div class="am-u-sm-12 am-u-md-3">--%>
        <%--<div class="am-input-group am-input-group-sm">--%>
        <%--<input type="text" class="am-form-field" placeholder="请输入链接名称">--%>
        <%--<span class="am-input-group-btn">--%>
        <%--<button class="am-btn am-btn-default" type="button">搜索</button>--%>
        <%--</span>--%>
        <%--</div>--%>
        <%--</div>--%>
    </div>
    <div class="am-g">
        <div class="am-u-sm-12">
            <form class="am-form">
                <table class="am-table am-table-striped am-table-hover table-main table-border">
                    <thead>
                    <tr>
                        <th>租借单号</th>
                        <th>预约订单id</th>
                        <th>用户id</th>
                        <th>租借起始日</th>
                        <th>租借结束日</th>
                        <th>预计租借天数</th>
                        <th>押金支付状态</th>
                        <th>租金支付状态</th>
                        <th>租借状态</th>
                        <th>押金</th>
                        <th>租金</th>
                        <th>创建时间</th>
                        <th class="table-set">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="rentDTO" items="${rentDTOPage.content}">
                        <tr>
                            <td>${rentDTO.id}</td>
                            <td>${rentDTO.bookId}</td>
                            <td>${rentDTO.customerId}</td>
                            <td><fmt:formatDate value="${rentDTO.beginDate  }" pattern="yyyy-MM-dd"/></td>
                            <td><fmt:formatDate value="${rentDTO.endDate  }" pattern="yyyy-MM-dd"/></td>
                            <td>${rentDTO.rentSumDate}(天)</td>
                            <td>${rentDTO.getPayDepositStatusEnum().getMessage()}</td>
                            <td>${rentDTO.getPayRentStatusEnum().getMessage()}</td>
                            <td>${rentDTO.getRentStatusEnum().message}</td>
                            <td>${rentDTO.paymentAmountDeposit}(元)</td>
                            <td>${rentDTO.paymentAmountRent}(元)</td>
                            <td><fmt:formatDate value="${rentDTO.createTime  }" pattern="yyyy-MM-dd"/></td>
                            <td>
                                <c:if test="${rentDTO.getRentStatusEnum().getMessage() == '提交租车信息'
                                && rentDTO.getPayDepositStatusEnum().getMessage() == '等待支付押金'}">
                                    <a href="/diandiancar/admin/rent/cancel?rentId=${rentDTO.id}">取消租车</a>
                                </c:if>
                                <c:if test="${rentDTO.getPayRentStatusEnum().getMessage() == '等待支付租金'
                                && rentDTO.getPayDepositStatusEnum().getMessage() == '支付押金完成'}">
                                    待付租金
                                </c:if>
                                <c:if test="${rentDTO.getRentStatusEnum().getMessage() != '提交租车信息'}">
                                    ${rentDTO.getRentStatusEnum().getMessage()}
                                </c:if>
                                <%--用户支付租金后检查车辆，才能行完结--%>
                                <c:if test="${rentDTO.getRentStatusEnum().getMessage() == '提交租车信息'
                                && rentDTO.getPayRentStatusEnum().getMessage() == '租金支付完成'}">
                                    <a href="/diandiancar/admin/rent/finish?rentId=${rentDTO.id}">完结</a>

                                    <%--这里面应该加个车辆损坏的按钮--%>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>


                <%--分页--%>
                <div class="am-fr">
                    <ul class="am-pagination">
                        <c:if test="${currentPage<=1}">
                            <li class="am-disabled"><a href="#">上一页</a></li>
                        </c:if>
                        <c:if test="${currentPage>1}">
                            <li class="am-active"><a
                                    href="/diandiancar/admin/rent/list?page=${currentPage-1}&size=${size}">上一页</a>
                            </li>
                        </c:if>
                        <c:forEach var="index" begin="1" end="${rentDTOPage.getTotalPages()}">
                            <c:if test="${currentPage == index}">
                                <li class="am-disabled"><a href="">${index}</a></li>
                            </c:if>
                            <c:if test="${currentPage != index}">
                                <li class="am-active"><a
                                        href="/diandiancar/admin/rent/list?page=${index}&size=${size}">${index}</a>
                                </li>
                            </c:if>
                        </c:forEach>
                        <c:if test="${currentPage == rentDTOPage.getTotalPages()}">
                            <li class="am-disabled"><a href="#">下一页</a></li>

                        </c:if>
                        <c:if test="${currentPage < rentDTOPage.getTotalPages()}">
                            <li class="am-active">
                                <a href="/diandiancar/admin/rent/list?page=${currentPage+1}&size=${size}">下一页</a>
                            </li>
                        </c:if>
                    </ul>
                </div>
                <hr>
            </form>
        </div>

    </div>
</div>
<script type="text/javascript" src="/diandiancar/admin/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/diandiancar/admin/myplugs/js/plugs.js"></script>
<script>
    $(function () {
        $(".btnedit").click(function () {


            $.jq_Panel({
                title: "修改链接",
                iframeWidth: 500,
                iframeHeight: 300,
                url: "editLink.html"
            });
        });


    })
</script>

</body>

</html>