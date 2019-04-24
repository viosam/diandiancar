<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>轮播图列表</title>
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
        <div class="am-u-sm-12 am-u-md-6">
            <div class="am-btn-toolbar">
                <div class="am-btn-group am-btn-group-xs">
                    <button type="button" class="am-btn am-btn-default"><span class="am-icon-plus"></span> <a href="/diandiancar/carousel/index">新增</a></button>
                </div>
            </div>
        </div>
        <div class="am-u-sm-12 am-u-md-3">

        </div>
    </div>
    <div class="am-g">
        <div class="am-u-sm-12">
            <form class="am-form">
                <table class="am-table am-table-striped am-table-hover table-main table-border">
                    <thead>
                    <tr>
                        <%--<th class="table-check"><input type="checkbox"></th>--%>
                        <th>id</th>
                        <th>车辆id</th>
                        <th width="30">图片</th>
                        <th>描述</th>
                        <th>创建时间</th>
                        <th>修改时间</th>
                        <th class="table-set" colspan="2">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="carousel" items="${carouselList}">
                    <tr align="center">
                        <%--<td><input type="checkbox"></td>--%>
                        <td>${carousel.id}</td>
                        <td>${carousel.carId}</td>
                        <td ><img height="60" width="60" src="${carousel.picUrl}" alt=""></td>
                        <td>${carousel.description}</td>
                        <td>${carousel.createTime}</td>
                        <td>${carousel.updateTime}</td>
                        <td><a class="btn btn-outline-info btn-lg"
                               href="/diandiancar/carousel/index?id=${carousel.id}">修改</a></td>
                        <td>

                        <c:if test="${carousel.getCarouselDeleteEnum().message=='未删除'}">
                            <a class="btn btn-outline-danger btn-lg"
                               href="/diandiancar/carousel/delete?id=${carousel.id}">删除</a></td>
                        </c:if>
                        <c:if test="${carousel.getCarouselDeleteEnum().message!='未删除'}">
                        ${carousel.getCarouselDeleteEnum().message}
                        </c:if>

                    </tr>
                    </c:forEach>
                    </tbody>
                </table>
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