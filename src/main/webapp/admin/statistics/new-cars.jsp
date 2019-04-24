<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>新增车辆统计</title>
    <link rel="stylesheet" href="/diandiancar/admin/css/amazeui.min.css"/>
    <link rel="stylesheet" href="/diandiancar/admin/css/admin.css"/>
    <!-- 引入CSS -->
    <link href="/diandiancar/css/bootstrap.css" type="text/css" rel="stylesheet">
    <link href="/diandiancar/css/style.css" type="text/css" rel="stylesheet">
    <!-- 引入JS -->
    <script src="/diandiancar/js/jquery-3.3.1.min.js"></script>
    <script src="/diandiancar/js/popper.min.js"></script>
    <script src="/diandiancar/js/bootstrap.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.3/Chart.min.js"></script>

    <style>
        table tr, td {
            font-size: 15px;
            text-align: center;
        }
    </style>
</head>
<%--<script type="text/javascript">
    $(document).ready(function () {

        $("#carStatus").on('select', function () {
// alert("in");


            alert($("#carStatus").val());
            $.ajax({
                type: "get",
                url: "/diandiancar/customer/check_username",
                data: {"userName": userName},
                success: function (response) {

                }
            });
        })
</script>--%>

<body>
<div class="admin-content-body">
    <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">新增车辆统计：</strong>
            <small></small>
        </div>
        <div style="margin-left: 200px">
            <%--<select name="month" id="carStatus" style="font-size: large">--%>
                <%--<option>请选择</option>--%>
                <%--<option value="1">一月</option>--%>
                <%--<option value="2">二月</option>--%>
                <%--<option value="3">三月</option>--%>
                <%--<option value="4">四月</option>--%>
                <%--<option value="5">五月</option>--%>
                <%--<option value="6">六月</option>--%>
                <%--<option value="7">七月</option>--%>
                <%--<option value="8">八月</option>--%>
                <%--<option value="9">九月</option>--%>
                <%--<option value="10">十月</option>--%>
                <%--<option value="11">十一月</option>--%>
                <%--<option value="12">十二月</option>--%>
            <%--</select>--%>
        </div>
        <br/>
    </div>

    <hr>

    <div class="am-g">
        <div class="am-u-sm-12 am-u-md-3">

        </div>

    </div>
    <div class="am-g">
        <div class="am-u-sm-12">
            <form class="am-form">
                <table class="am-table am-table-striped am-table-hover table-main table-border">
                    <thead>
                    <tr>
                        <th>车辆id</th>
                        <th>车辆名</th>
                        <th>车辆类型</th>
                        <th>车辆状态</th>
                        <th>创建时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="carInfo" items="${carInfoPage.content}">
                        <tr>
                            <td>${carInfo.carId}</td>
                            <td>${carInfo.carName}</td>
                            <td>${carInfo.categoryType}</td>
                            <td>${carInfo.getCarStatusEnum().getMessage()}</td>
                            <td><fmt:formatDate value="${carInfo.createTime}" pattern="yyyy-MM-dd"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>


                <%--分页--%>
                <div class="am-fr">
                    <ul class="am-pagination">
                        <c:if test="${currentPage<=1}">
                            <li class="am-disabled"><a href="#">上一页</a></li>
                        </c:if>
                        <c:if test="${currentPage>1}">
                            <li class="am-active">
                                <c:if test="${status==null}">
                                    <a href="/diandiancar/admin/statistics/new_cars?page=${currentPage-1}&size=${size}">上一页</a>
                                </c:if>
                                <c:if test="${status!=null}">
                                    <a href="/diandiancar/admin/statistics/new_cars?page=${currentPage-1}&size=${size}&month=${month}">上一页</a>
                                </c:if>
                            </li>
                        </c:if>
                        <c:forEach var="index" begin="1" end="${carInfoPage.getTotalPages()}">
                            <c:if test="${currentPage == index}">
                                <li class="am-disabled"><a href="">${index}</a></li>
                            </c:if>
                            <c:if test="${currentPage != index}">
                                <li class="am-active">
                                    <c:if test="${status==null}">
                                        <a href="/diandiancar/admin/statistics/new_cars?page=${index}&size=${size}">${index}</a>

                                    </c:if>
                                    <c:if test="${status!=null}">
                                        <a href="/diandiancar/admin/statistics/new_cars?page=${index}&size=${size}&month=${month}">${index}</a>

                                    </c:if>
                                </li>
                            </c:if>
                        </c:forEach>
                        <c:if test="${currentPage == carInfoPage.getTotalPages()}">
                            <li class="am-disabled">
                                <a href="#">下一页</a>
                            </li>

                        </c:if>
                        <c:if test="${currentPage < carInfoPage.getTotalPages()}">
                            <li class="am-active">
                                <c:if test="${status==null}">
                                    <a href="/diandiancar/admin/statistics/new_cars?page=${currentPage+1}&size=${size}">下一页</a>

                                </c:if>
                                <c:if test="${status!=null}">
                                    <a href="/diandiancar/admin/statistics/new_cars?page=${currentPage+1}&size=${size}&month=${month}">下一页</a>

                                </c:if>
                            </li>
                        </c:if>
                    </ul>
                </div>
                <hr>
            </form>
        </div>

    </div>
</div>

统计的画布
<div style="width:400px;height:400px;">
    <canvas id="myChart"></canvas>
</div>

<script>
    var ctx = document.getElementById("myChart").getContext('2d');

    var data = {
        labels: [
            "一月", "二月", "三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"
        ],
        datasets: [{
            data: [${month0},${month1},${month2},${month3},${month4},${month5},${month6},${month7},${month8},${month9},${month10},${month11}],
            backgroundColor: ["lightblue", "#999999", "lightgreen","red","bule","yellow","#998848","#667777","#556666","#445577","#330044","#a1fd70"]
        }],


    }
    var myLineChart = new Chart(ctx, {
        type: 'doughnut',
        data: data,
    });


</script>


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

</div>
</body>

</html>