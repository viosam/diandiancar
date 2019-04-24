<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>统计</title>

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
<div class="container" style="margin-top: 3rem;">
    <h3>统计：</h3>
    <div class="row">
        <div class="col-lg-2"></div>
        <div class="col-lg-8">
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">
                    <label>类型</label>
                    <select name="categoryType" class="am-control-nav">
                        <c:forEach var="category" items="${categoryList}">
                            <option value="1">
                                测试

                            </option>

                        </c:forEach>
                    </select>
                </label>
            </div>
        </div>
    </div>

</div>
</div>
</body>
</html>
