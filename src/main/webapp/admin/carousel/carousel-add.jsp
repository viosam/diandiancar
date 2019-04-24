<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加轮播图</title>

    <link rel="stylesheet" href="/diandiancar/admin/css/amazeui.min.css">
    <link rel="stylesheet" href="/diandiancar/admin/css/admin.css">
    <link rel="stylesheet" href="/diandiancar/admin/css/app.css">
    <style>
        .admin-main {
            padding-top: 0px;
        }
    </style>
</head>
<body>

<div class="am-cf admin-main">
    <!-- content start -->
    <div class="admin-content">
        <div class="admin-content-body">
            <div class="am-g">

                <form class="am-form am-form-horizontal" method="post" action="/diandiancar/carousel/save" enctype="multipart/form-data" style="padding-top: 30px;">

                    <div class="am-form-group">
                        <label>车辆id</label>
                        <input width="50px" name="carId" type="text" autofocus = "autofocus" placeholder="车辆id"
                               value="<c:if test='${carousel.carId!=""}'>${carousel.carId}</c:if>"/>
                    </div>

                    <div class="am-form-group">
                        <label>描述</label>
                        <input width="50px" name="description" type="text" placeholder="描述"
                               value="<c:if test='${carousel.description!=""}'>${carousel.description}</c:if>"/>
                    </div>

                    <div class="am-form-group">
                        <label>图片</label>
                        <img height="100" src="<c:if test='${carousel.picUrl!=""}'>${(carousel.picUrl)}</c:if>" alt="">
                        <input type="hidden" name="picUrl" value="<c:if test='${carousel.picUrl!=""}'>${carousel.picUrl}</c:if>">
                        <input name="carouselPic" type="file"/>
                    </div>

            <div class="am-form-group">
                <input type="hidden" name="id" value="<c:if test='${carousel.id!=""}'>${carousel.id}</c:if>">
            </div>
            <div class="am-form-group">
                <div class="am-u-sm-9 am-u-sm-push-3">
                    <button type="submit" class="am-btn am-btn-success">提交</button>
                </div>
            </div>
            </form>
            </div>
    </div>
</div>
</div>
</body>
</html>