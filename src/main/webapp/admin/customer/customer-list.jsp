<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
        <div class="am-u-sm-12 am-u-md-3">
            <form action="/diandiancar/admin/customer/search" method="get">
                <div class="am-input-group am-input-group-sm">
                    <input type="text" name="nickname" class="am-form-field" placeholder="请输入用户昵称">
                    <span class="am-input-group-btn">
                    <button class="am-btn am-btn-default" type="submit">搜索</button>
                    </span>
                </div>
            </form>
        </div>
    </div>
    <div class="am-g">
        <div class="am-u-sm-12">
            <form class="am-form">
                <table class="am-table am-table-striped am-table-hover table-main table-border">
                    <thead>
                    <tr align="center">
                        <th>id</th>
                        <th>用户名称</th>
                        <th>手机号码</th>
                        <th>用户昵称</th>
                        <th>信用等级</th>
                        <th>驾照号码</th>
                        <th>状态</th>
                        <th>创建时间</th>
                        <th class="table-set">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="customer" items="${customerPage.content}">
                        <tr>
                            <td>${customer.id}</td>
                            <td>${customer.userName}</td>
                            <td>${customer.telNum}</td>
                            <td>${customer.nickname}</td>
                            <td>${customer.creditLevel}</td>
                            <td>${customer.driverLicenseID}</td>
                            <td>${customer.getCustomerFreezedEnum().getMessage()}</td>
                            <td>${customer.createTime}</td>
                            <td>
                                <c:if test="${customer.getCustomerFreezedEnum().message=='已冻结'}">
                                    <a href="/diandiancar/admin/customer/unfreeze?customerId=${customer.getId()}">解冻</a>
                                </c:if>
                                <c:if test="${customer.getCustomerFreezedEnum().message!='已冻结'}">
                                    <a href="/diandiancar/admin/customer/freeze?customerId=${customer.getId()}">冻结</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <%--分页--%>
                <c:if test="${searchTag =='notSearch'}">

                    <div class="am-fr">
                        <ul class="am-pagination">
                            <c:if test="${currentPage<=1}">
                                <li class="am-disabled"><a href="#">上一页</a></li>
                            </c:if>
                            <c:if test="${currentPage>1}">
                                <li class="am-active"><a
                                        href="/diandiancar/admin/customer/list?page=${currentPage-1}&size=${size}">上一页</a>
                                </li>
                            </c:if>
                            <c:forEach var="index" begin="1" end="${customerPage.getTotalPages()}">
                                <c:if test="${currentPage == index}">
                                    <li class="am-disabled"><a href="">${index}</a></li>
                                </c:if>
                                <c:if test="${currentPage != index}">
                                    <li class="am-active"><a
                                            href="/diandiancar/admin/customer/list?page=${index}&size=${size}">${index}</a>
                                    </li>
                                </c:if>

                            </c:forEach>
                            <c:if test="${currentPage == customerPage.getTotalPages()}">
                                <li class="am-disabled"><a href="#">下一页</a></li>

                            </c:if>
                            <c:if test="${currentPage < customerPage.getTotalPages()}">
                                <li class="am-active"><a
                                        href="/diandiancar/admin/customer/list?page=${currentPage+1}&size=${size}">下一页</a>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                </c:if>
                <%--<#--search的分页按钮-->--%>
                <c:if test="${searchTag =='search'}">
                    <div class="am-fr">
                        <ul class="am-pagination">
                            <c:if test="${currentPage<=1}">
                                <li class="am-disabled"><a href="#">上一页</a></li>
                            </c:if>
                            <c:if test="${currentPage>1}">
                                <li class="am-active"><a
                                        href="/diandiancar/admin/customer/search?page=${currentPage-1}&size=${size}&nickname=${nickname}">上一页</a>
                                </li>
                            </c:if>
                            <c:forEach var="index" begin="1" end="${customerPage.getTotalPages()}">
                                <c:if test="${currentPage == index}">
                                    <li class="am-disabled"><a href="">${index}</a></li>
                                </c:if>
                                <c:if test="${currentPage != index}">
                                    <li class="am-active"><a
                                            href="/diandiancar/admin/customer/search?page=${index}&size=${size}&nickname=${nickname}">${index}</a>
                                    </li>
                                </c:if>
                            </c:forEach>
                            <c:if test="${currentPage == customerPage.getTotalPages()}">
                                <li class="am-disabled"><a href="#">下一页</a></li>
                            </c:if>
                            <c:if test="${currentPage < customerPage.getTotalPages()}">
                                <li class="am-active"><a
                                        href="/diandiancar/admin/customer/search?page=${currentPage+1}&size=${size}&nickname=${nickname}">下一页</a>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                </c:if>


                <%--<#--已删除列表的分页按钮-->--%>
                <c:if test="${searchTag =='freezeList'}">
                    <div class="am-fr">
                        <ul class="am-pagination">
                            <c:if test="${currentPage<=1}">
                                <li class="am-disabled"><a href="#">上一页</a></li>
                            </c:if>
                            <c:if test="${currentPage>1}">
                                <li class="am-active"><a
                                        href="/diandiancar/admin/customer/freeze-list?page=${currentPage-1}&size=${size}&nickname=${nickname}">上一页</a>
                                </li>
                            </c:if>
                            <c:forEach var="index" begin="1" end="${customerPage.getTotalPages()}">
                                <c:if test="${currentPage == index}">
                                    <li class="am-disabled"><a href="">${index}</a></li>
                                </c:if>
                                <c:if test="${currentPage != index}">
                                    <li class="am-active"><a
                                            href="/diandiancar/admin/customer/freeze-list?page=${index}&size=${size}&nickname=${nickname}">${index}</a>
                                    </li>
                                </c:if>
                            </c:forEach>
                            <c:if test="${currentPage == customerPage.getTotalPages()}">
                                <li class="am-disabled"><a href="#">下一页</a></li>
                            </c:if>
                            <c:if test="${currentPage < customerPage.getTotalPages()}">
                                <li class="am-active"><a
                                        href="/diandiancar/admin/customer/freeze-list?page=${currentPage+1}&size=${size}&nickname=${nickname}">下一页</a>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                </c:if>

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