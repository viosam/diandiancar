<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>修改车辆信息</title>

    <link rel="stylesheet" href="/diandiancar/admin/css/amazeui.min.css">
    <link rel="stylesheet" href="/diandiancar/admin/css/admin.css">
    <link rel="stylesheet" href="/diandiancar/admin/css/app.css">
    <style>
        .admin-main {
            padding-top: 0px;
        }
    </style>

    <%--<!-- 引入CSS -->
    <link href="/diandiancar/css/bootstrap.css" type="text/css" rel="stylesheet">
    <link href="/diandiancar/css/style.css" type="text/css" rel="stylesheet">
    <!-- 引入JS -->
    <script src="/diandiancar/js/jquery-3.3.1.min.js"></script>
    <script src="/diandiancar/js/popper.min.js"></script>
    <script src="/diandiancar/js/bootstrap.js"></script>--%>
</head>
<body>

<div class="am-cf admin-main">
    <!-- content start -->
    <div class="admin-content">
        <div class="admin-content-body">
            <div class="form-row">

                <form class="am-form am-form-horizontal form-group" method="post"
                      action="/diandiancar/admin/carInfo/save" enctype="multipart/form-data" style="padding-top: 30px;">
                    <div class="am-form-group col-lg-8">
                        <label>车辆名称</label>
                        <input width="50px" name="carName" autofocus="autofocus" type="text" placeholder="车辆名称"
                               value="<c:if test='${carInfo.carName!=""}'>${carInfo.carName}</c:if>"/>
                    </div>
                    <div class="am-form-group">
                        <label>可载人数</label>
                        <input name="capacity" type="number" placeholder="可载人数"
                               value="<c:if test='${carInfo.capacity!=""}'>${(carInfo.capacity)}</c:if>"/>
                    </div>
                    <div class="am-form-group">
                        <label>生产商</label>
                        <input name="producer" type="text" placeholder="生产商"
                               value="<c:if test='${carInfo.producer!=""}'>${(carInfo.producer)}</c:if>"/>
                    </div>
                    <div class="am-form-group">
                        <label>行驶公里数</label>
                        <input name="kilometers" type="text" placeholder="行驶公里数"
                               value="<c:if test='${carInfo.kilometers!=""}'>${(carInfo.kilometers)}</c:if>"/>
                    </div>
                    <div class="am-form-group">
                        <label>生产日期</label>
                        <input name="productTime" type="date" placeholder="生产日期"
                               value="<fmt:formatDate value="${carInfo.productTime  }" pattern="yyyy-MM-dd"/>">
                    </div>
                    <div class="am-form-group">
                        <label>日租金</label>
                        <input name="rentPrice" type="text" placeholder="日租金"
                               value="<c:if test='${carInfo.rentPrice!=""}'>${(carInfo.rentPrice)}</c:if>"/>
                    </div>
                    <div class="am-form-group">
                        <label>押金</label>
                        <input name="deposit" type="text" placeholder="押金"
                               value="<c:if test='${carInfo.deposit!=""}'>${(carInfo.deposit)}</c:if>"/>
                    </div>
                    <div class="am-form-group">
                        <label>描述</label>
                        <input name="carDescription" type="text" placeholder="描述"
                               value="<c:if test='${carInfo.carDescription!=""}'>${(carInfo.carDescription)}</c:if>"/>
                    </div>
                    <div class="am-form-group">
                        <label>图片</label>
                        <img height="100" src="<c:if test='${carInfo.carIcon!=""}'>${(carInfo.carIcon)}</c:if>" alt="">
                        <input type="hidden" name="carIcon"
                               value="<c:if test='${carInfo.carIcon!=""}'>${carInfo.carIcon}</c:if>">
                        <input name="carIconFile" type="file"/>
                    </div>
                    <div class="am-form-group">
                        <label>类目</label>
                        <select name="categoryType" class="am-control-nav">
                            <c:forEach var="category" items="${categoryList}">
                                <option value="${category.categoryType}"
                                        <c:if test="${carInfo.categoryType!=''&& carInfo.categoryType == category.categoryType}">
                                            selected
                                        </c:if>
                                >
                                    <c:if test="${category.categoryName!=''}">
                                        ${category.categoryName}
                                    </c:if>

                                </option>

                            </c:forEach>
                        </select>
                    </div>
                    <div class="am-form-group">
                        <input type="hidden" name="carId"
                               value="<c:if test='${carInfo.carId!=""}'>${carInfo.carId}</c:if>">
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