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
    </div>
    <div class="am-g">
        <div class="am-u-sm-12">
            <form class="am-form">
                <table class="am-table am-table-striped am-table-hover table-main table-border">
                    <thead>
                    <tr>
                        <th>预约单号</th>
                        <th>预定车ID</th>
                        <th>用户id</th>
                        <th>预定起始日</th>
                        <th>预定结束日</th>
                        <th>总预定时间</th>
                        <th>支付状态</th>
                        <th>预约状态</th>
                        <th>定金</th>
                        <th>创建时间</th>
                        <th class="table-set">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="bookDTO" items="${bookDTOPage.content}">
                        <tr>
                            <td>${bookDTO.id}</td>
                            <td>${bookDTO.carID}</td>
                            <td>${bookDTO.customerID}</td>
                            <td><fmt:formatDate value="${bookDTO.bookBeginDate  }" pattern="yyyy-MM-dd"/></td>
                            <td><fmt:formatDate value="${bookDTO.bookEndDate  }" pattern="yyyy-MM-dd"/></td>
                            <td>${bookDTO.bookSumDate}(天)</td>
                            <td>${bookDTO.getPayBookStatusEnum().getMessage()}</td>
                            <td>${bookDTO.getBookStatusEnum().getMessage()}</td>
                            <td>${bookDTO.earnestMoney}</td>
                            <td><fmt:formatDate value="${bookDTO.createTime  }" pattern="yyyy-MM-dd"/></td>
                            <td>
                                <c:if test="${bookDTO.getBookStatusEnum().getMessage() == '提交预约'}">

                                    <a class="btn btn-outline-danger btn-sm" href="/diandiancar/admin/book/cancel?bookId=${bookDTO.id}">取消预约</a>
                                </c:if>
                                <c:if test="${bookDTO.getBookStatusEnum().getMessage() != '提交预约'}">
                                    ${bookDTO.getBookStatusEnum().getMessage()}
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </form>

                <%--分页--%>
                <div class="am-fr">
                    <ul class="am-pagination">
                        <c:if test="${currentPage<=1}">
                            <li class="am-disabled"><a href="#">上一页</a></li>
                        </c:if>
                        <c:if test="${currentPage>1}">
                            <li class="am-active"><a
                                    href="/diandiancar/admin/book/list?page=${currentPage-1}&size=${size}">上一页</a>
                            </li>
                        </c:if>
                        <c:forEach var="index" begin="1" end="${bookDTOPage.getTotalPages()}">
                            <c:if test="${currentPage == index}">
                                <li class="am-disabled"><a href="">${index}</a></li>
                            </c:if>
                            <c:if test="${currentPage != index}">
                                <li class="am-active"><a
                                        href="/diandiancar/admin/book/list?page=${index}&size=${size}">${index}</a>
                                </li>
                            </c:if>
                        </c:forEach>
                        <c:if test="${currentPage == bookDTOPage.getTotalPages()}">
                            <li class="am-disabled"><a href="#">下一页</a></li>

                        </c:if>
                        <c:if test="${currentPage < bookDTOPage.getTotalPages()}">
                            <li class="am-active">
                                <a href="/diandiancar/admin/book/list?page=${currentPage+1}&size=${size}">下一页</a>
                            </li>
                        </c:if>
                    </ul>
                </div>

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