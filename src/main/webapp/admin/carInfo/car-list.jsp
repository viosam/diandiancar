<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>车辆列表</title>
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
        table tr, td {
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

    <div class="am-g" >
        <div class="am-u-sm-12 am-u-md-6">
            <div class="am-btn-toolbar">
                <div class="am-btn-group am-btn-group-xs">
                    <button type="button" class="am-btn am-btn-default"><span class="am-icon-plus"></span>
                        <a href="/diandiancar/admin/carInfo/index">新增</a></button>
                </div>
            </div>
        </div>
        <div class="am-u-sm-12 am-u-md-3">

        </div>
        <div class="am-u-sm-12 am-u-md-3" style="margin-right: 200px">
            <form action="/diandiancar/admin/carInfo/search" method="get">
                <div class="am-input-group am-input-group-sm">
                    <input type="text" name="carName" class="am-form-field" placeholder="请输入车辆名称">
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
                <div class="table-responsive">
                    <table class="table-striped table-border">
                        <thead>
                        <tr>
                            <%--<th class="table-check"><input type="checkbox"></th>--%>
                            <th width="100">id</th>
                            <th width="30">图片</th>
                            <th width="100">名称</th>
                            <th width="100" width="50px">车辆类型</th>
                            <th width="100">可载人数</th>
                            <th width="100">公里数</th>
                            <th width="100">生产商</th>
                            <th width="100">租借金额</th>
                            <th width="100">生产日期</th>
                            <th width="50px">车辆状态</th>
                            <th width="100">描述</th>
                            <th width="100">创建时间</th>
                            <th width="100">修改时间</th>
                            <th class="table-set" colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="carInfo" items="${carInfoPage.content}">
                            <tr align="center">
                                    <%--<td><input type="checkbox"></td>--%>
                                <td><a href="/diandiancar/admin/carInfo/detail?carId=${carInfo.carId}"> ${carInfo.carId}</a></td>
                                <td><img height="60" width="60" style="padding: 5px;" src="${carInfo.carIcon}" alt="">
                                </td>
                                <td>${carInfo.carName}</td>
                                <td>${carInfo.categoryType}</td>
                                <td>${carInfo.capacity}</td>
                                <td>${carInfo.kilometers }</td>
                                <td>${carInfo.producer}</td>
                                <td>${carInfo.rentPrice}元/天</td>
                                <td><fmt:formatDate value="${carInfo.productTime  }" pattern="yyyy-MM-dd"/></td>
                                <td>${carInfo.getCarStatusEnum().message}</td>
                                <td>${carInfo.carDescription}</td>
                                <td><fmt:formatDate value="${carInfo.createTime  }" pattern="yyyy-MM-dd"/></td>
                                <td><fmt:formatDate value="${carInfo.updateTime  }" pattern="yyyy-MM-dd"/></td>
                                <td><a class="btn btn-outline-info btn-sm"
                                       href="/diandiancar/admin/carInfo/index?carId=${carInfo.carId}">修改</a></td>
                                <td>

                                    <c:if test="${carInfo.getCarDeleteEnum().message=='未删除'}">
                                    <a class="btn btn-sm btn-outline-danger"
                                       href="/diandiancar/admin/carInfo/delete?carId=${carInfo.carId}">删除</a></td>
                                </c:if>&nbsp;&nbsp;
                                <c:if test="${carInfo.getCarDeleteEnum().message!='未删除'}">
                                    ${carInfo.getCarDeleteEnum().message}
                                </c:if>

                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div style="margin-right: 200px">
                    <%--分页--%>
                    <c:if test="${searchTag =='notSearch'}">

                        <div class="am-fr">
                            <ul class="am-pagination">
                                <c:if test="${currentPage<=1}">
                                    <li class="am-disabled"><a href="#">上一页</a></li>
                                </c:if>
                                <c:if test="${currentPage>1}">
                                    <li class="am-active"><a
                                            href="/diandiancar/admin/carInfo/list?page=${currentPage-1}&size=${size}">上一页</a>
                                    </li>
                                </c:if>
                                <c:forEach var="index" begin="1" end="${carInfoPage.getTotalPages()}">
                                    <c:if test="${currentPage == index}">
                                        <li class="am-disabled"><a href="">${index}</a></li>
                                    </c:if>
                                    <c:if test="${currentPage != index}">
                                        <li class="am-active"><a
                                                href="/diandiancar/admin/carInfo/list?page=${index}&size=${size}">${index}</a>
                                        </li>
                                    </c:if>

                                </c:forEach>
                                <c:if test="${currentPage == carInfoPage.getTotalPages()}">
                                    <li class="am-disabled"><a href="#">下一页</a></li>

                                </c:if>
                                <c:if test="${currentPage < carInfoPage.getTotalPages()}">
                                    <li class="am-active"><a
                                            href="/diandiancar/admin/carInfo/list?page=${currentPage+1}&size=${size}">下一页</a>
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
                                            href="/diandiancar/admin/carInfo/search?page=${currentPage-1}&size=${size}&carName=${carName}">上一页</a>
                                    </li>
                                </c:if>
                                <c:forEach var="index" begin="1" end="${carInfoPage.getTotalPages()}">
                                    <c:if test="${currentPage == index}">
                                        <li class="am-disabled"><a href="">${index}</a></li>
                                    </c:if>
                                    <c:if test="${currentPage != index}">
                                        <li class="am-active"><a
                                                href="/diandiancar/admin/carInfo/search?page=${index}&size=${size}&carName=${carName}">${index}</a>
                                        </li>
                                    </c:if>
                                </c:forEach>
                                <c:if test="${currentPage == carInfoPage.getTotalPages()}">
                                    <li class="am-disabled"><a href="#">下一页</a></li>
                                </c:if>
                                <c:if test="${currentPage < carInfoPage.getTotalPages()}">
                                    <li class="am-active"><a
                                            href="/diandiancar/admin/carInfo/search?page=${currentPage+1}&size=${size}&carName=${carName}">下一页</a>
                                    </li>
                                </c:if>
                            </ul>
                        </div>
                    </c:if>

                    <hr>
                </div>
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