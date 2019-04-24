<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title></title>

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

                <form class="am-form am-form-horizontal" method="post" action="/diandiancar/admin/carCategory/save"
                      style="padding-top: 30px;">
                    <div class="am-form-group" style="margin-left: auto">
                        <label>车辆类型
                        <input width="50px" name="categoryName" autofocus = "autofocus" type="text" placeholder="车辆类型" value="<c:if test='${category.categoryName!=""}'>${category.categoryName}</c:if>"/>
                        </label>
                    </div>
                    <div class="am-form-group">
                        <label>类型编号
                        <input name="categoryType" type="number" placeholder="类型编号" value="<c:if test='${category.categoryType!=""}'>${category.categoryType}</c:if>"/>
                        </label>
                    </div>
                    <div class="am-form-group">
                        <input type="hidden" name="categoryId" value="<c:if test='${category.categoryId!=""}'>${category.categoryId}</c:if>">
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
