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
            <form action="/diandiancar/admin/carCategory/search" method="get">
                <div class="am-input-group am-input-group-sm">
                    <input type="text" name="categoryName" class="am-form-field" placeholder="请输入类型名称">
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
                    <tr>
                        <th>id</th>
                        <th>类型名称</th>
                        <th>类型编号</th>
                        <th>创建时间</th>
                        <th>修改时间</th>
                        <th class="table-set">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="category" items="${carCategoryPage.content}">
                        <tr>
                            <td>${category.categoryId}</td>
                            <td>${category.categoryName}</td>
                            <td>${category.categoryType}</td>
                            <td>${category.createTime}</td>
                            <td>${category.updateTime}</td>
                            <td><a class="btn btn-outline-info" href="/diandiancar/admin/carCategory/index?carCategoryId=${category.categoryId}">修改</a></td>
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
                                        href="/diandiancar/admin/carCategory/list?page=${currentPage-1}&size=${size}">上一页</a>
                                </li>
                            </c:if>
                            <c:forEach var="index" begin="1" end="${carCategoryPage.getTotalPages()}">
                                <c:if test="${currentPage == index}">
                                    <li class="am-disabled"><a href="">${index}</a></li>
                                </c:if>
                                <c:if test="${currentPage != index}">
                                    <li class="am-active"><a
                                            href="/diandiancar/admin/carCategory/list?page=${index}&size=${size}">${index}</a>
                                    </li>
                                </c:if>

                            </c:forEach>
                            <c:if test="${currentPage == carCategoryPage.getTotalPages()}">
                                <li class="am-disabled"><a href="#">下一页</a></li>

                            </c:if>
                            <c:if test="${currentPage < carCategoryPage.getTotalPages()}">
                                <li class="am-active"><a
                                        href="/diandiancar/admin/carCategory/list?page=${currentPage+1}&size=${size}">下一页</a>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                </c:if>
                <%--<#--search的分页按钮-->--%>
                <c:if test="${searchTag !='notSearch'}">
                    <div class="am-fr">
                        <ul class="am-pagination">
                            <c:if test="${currentPage<=1}">
                                <li class="am-disabled"><a href="#">上一页</a></li>
                            </c:if>
                            <c:if test="${currentPage>1}">
                                <li class="am-active"><a
                                        href="/diandiancar/admin/carCategory/search?page=${currentPage-1}&size=${size}&categoryName=${categoryName}">上一页</a>
                                </li>
                            </c:if>
                            <c:forEach var="index" begin="1" end="${carCategoryPage.getTotalPages()}">
                                <c:if test="${currentPage == index}">
                                    <li class="am-disabled"><a href="">${index}</a></li>
                                </c:if>
                                <c:if test="${currentPage != index}">
                                    <li class="am-active"><a
                                            href="/diandiancar/admin/carCategory/search?page=${index}&size=${size}&categoryName=${categoryName}">${index}</a>
                                    </li>
                                </c:if>
                            </c:forEach>
                            <c:if test="${currentPage == carCategoryPage.getTotalPages()}">
                                <li class="am-disabled"><a href="#">下一页</a></li>
                            </c:if>
                            <c:if test="${currentPage < carCategoryPage.getTotalPages()}">
                                <li class="am-active"><a
                                        href="/diandiancar/admin/carCategory/search?page=${currentPage+1}&size=${size}&categoryName=${categoryName}">下一页</a>
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