<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>车辆信息详情</title>

    <link rel="stylesheet" href="/diandiancar/admin/css/amazeui.min.css">
    <link rel="stylesheet" href="/diandiancar/admin/css/admin.css">
    <link rel="stylesheet" href="/diandiancar/admin/css/app.css">
    <style>
        .admin-main {
            padding-top: 0px;
        }
    </style>

    <!-- 引入CSS -->
    <link href="/diandiancar/css/bootstrap.css" type="text/css" rel="stylesheet">
    <link href="/diandiancar/css/style.css" type="text/css" rel="stylesheet">
    <!-- 引入JS -->
    <script src="/diandiancar/js/jquery-3.3.1.min.js"></script>
    <script src="/diandiancar/js/popper.min.js"></script>
    <script src="/diandiancar/js/bootstrap.js"></script>
</head>
<body>
<div class="container" style="margin-top: 3rem;">
    <h2>车辆详情信息：</h2>
    <div class="row">
        <div class="col-lg-2"></div>
        <div class="col-lg-8">
                <form class="am-form am-form-horizontal form-group" style="padding-top: 30px;">

                    <div class="am-form-group">
                        <label>车辆名称</label>
                        <input width="50px" name="carName" readonly type="text"
                               value="<c:if test='${carInfo.carName!=""}'>${carInfo.carName}</c:if>"/>
                    </div>
                    <div class="am-form-group">
                        <label>可载人数</label>
                        <input name="capacity" type="number" readonly
                               value="<c:if test='${carInfo.capacity!=""}'>${(carInfo.capacity)}</c:if>"/>
                    </div>
                    <div class="am-form-group">
                        <label>生产商</label>
                        <input name="producer" type="text" readonly
                               value="<c:if test='${carInfo.producer!=""}'>${(carInfo.producer)}</c:if>"/>
                    </div>
                    <div class="am-form-group">
                        <label>行驶公里数</label>
                        <input name="kilometers" type="text" readonly
                               value="<c:if test='${carInfo.kilometers!=""}'>${(carInfo.kilometers)}</c:if>"/>
                    </div>
                    <div class="am-form-group">
                        <label>生产日期</label>
                        <input name="productTime" type="date" readonly
                               value="<fmt:formatDate value="${carInfo.productTime  }" pattern="yyyy-MM-dd"/>">
                    </div>
                    <div class="am-form-group">
                        <label>日租金</label>
                        <input name="rentPrice" type="text" readonly
                               value="<c:if test='${carInfo.rentPrice!=""}'>${(carInfo.rentPrice)}</c:if>"/>
                    </div>
                    <div class="am-form-group">
                        <label>押金</label>
                        <input name="deposit" type="text" readonly
                               value="<c:if test='${carInfo.deposit!=""}'>${(carInfo.deposit)}</c:if>"/>
                    </div>
                    <div class="am-form-group">
                        <label>描述</label>
                        <input name="carDescription" type="text" readonly
                               value="<c:if test='${carInfo.carDescription!=""}'>${(carInfo.carDescription)}</c:if>"/>
                    </div>
                    <div class="am-form-group">
                        <label>图片</label>
                        <img height="100" src="<c:if test='${carInfo.carIcon!=""}'>${(carInfo.carIcon)}</c:if>" alt="">
                    </div>

                    <div class="am-form-group">
                        <input type="hidden" name="carId"
                               value="<c:if test='${carInfo.carId!=""}'>${carInfo.carId}</c:if>">
                    </div>
                    <div class="am-form-group">
                        <div class="am-u-sm-9 am-u-sm-push-3">
                            <a class="btn btn-outline-info" href="/diandiancar/admin/carInfo/list" class="btn btn-info">返回</a>
                            <c:if test="${carInfo.getCarStatusEnum().message=='未出租'}">
                                <a class="btn btn-outline-danger" href="/diandiancar/admin/repair/create?carId=${carInfo.carId}" class="btn btn-info">报修</a>

                            </c:if>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>