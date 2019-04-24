<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>维修列表</title>
    <!-- 引入CSS -->
    <link href="/diandiancar/css/bootstrap.css" type="text/css" rel="stylesheet">
    <link href="/diandiancar/css/style.css" type="text/css" rel="stylesheet">
    <!-- 引入JS -->
    <script src="/diandiancar/js/jquery-3.3.1.min.js"></script>
    <script src="/diandiancar/js/popper.min.js"></script>
    <script src="/diandiancar/js/bootstrap.js"></script>
</head>
<style type="text/css">
    body{background: url("/diandiancar/images/bg.jpg") no-repeat;background-size: 100%,100%;}
    .demo{width: 400px;height: 350px;position: absolute;top: 50%;left: 50%;margin-top: -200px;margin-left: -200px;border: 1px solid gainsboro;color: #f5f5f5;background: #000000;opacity: 0.7;box-shadow: 10px 10px 10px grey;border-radius: 8px;}
    a:hover{color: #87cefa !important;}
</style>
<script type="text/javascript">

    //将时间戳转成正常格式
    /* function getMyDate(str) {
         var oDate = new Date(str),
             oYear = oDate.getFullYear(),
             oMonth = oDate.getMonth() + 1,
             oDay = oDate.getDate(),
             oHour = oDate.getHours(),
             oMin = oDate.getMinutes(),
             oSen = oDate.getSeconds(),
             oTime = oYear + '-' + getzf(oMonth) + '-' + getzf(oDay) + ' ' + getzf(oHour) + ':' + getzf(oMin) + ':' + getzf(oSen);//最后拼接时间
         return oTime;
     };

     //补0操作
     function getzf(num) {
         if (parseInt(num) < 10) {
             num = '0' + num;
         }
         return num;
     };*/
</script>

<style>
    #mytable tr td a {
        text-decoration: none;
    }
</style>
<body>

<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="to-top.jsp"></jsp:include>


<div style="margin-top: 70px;">
    <div id="rentList" class="tab-content" style="margin: auto 90px; width: 70%;">

        <div style="margin: auto 20px;">
            <h3>我的报修：</h3>
            <div class="align-centerr" style="margin-top: 20px;">
                <table class="table table-striped" id="mytable">
                    <tr>
                        <th scope="col">维修单号</th>
                        <th scope="col">故障车id</th>
                        <th scope="col">维修部位</th>
                        <th scope="col">描述</th>
                        <th scope="col">维修状态</th>
                        <th scope="col">图片</th>
                        <th scope="col">创建时间</th>
                    </tr>
                    <c:forEach items="${repairDTOList}" var="repairDTO">
                        <tr>
                            <td>${repairDTO.id}</td>
                            <td>${repairDTO.carId}</td>
                            <td>${repairDTO.repairPart}</td>
                            <td>${repairDTO.description}</td>
                            <td>
                                <c:if test="${repairDTO.getRepairStatusEnum().getMessage()=='提交维修信息'}">
                                    审核中
                                </c:if>
                                <c:if test="${repairDTO.getRepairStatusEnum().getMessage()=='维修中'}">
                                    维修中
                                </c:if>
                                <c:if test="${repairDTO.getRepairStatusEnum().getMessage()=='维修完结'}">
                                    维修完结
                                </c:if>
                                <c:if test="${repairDTO.getRepairStatusEnum().getMessage()=='维修失败'}">
                                    维修失败
                                </c:if>
                            </td>
                            <td><img src="${repairDTO.icons}" height="60" width="60" style="padding: 5px;" ></td>
                            <td><fmt:formatDate value="${repairDTO.createTime  }" pattern="yyyy-MM-dd"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>

    </div>
</div>

<div style="margin-top: 300px"></div>

<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>