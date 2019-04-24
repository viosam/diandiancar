<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>出入库统计</title>
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
<script type="text/javascript">
    $(document).ready(function () {

        // if($("#carStatus").is(":checked")){
            /*$.ajax({
                type: "get",
                url: "/diandiancar/customer/check_username",
                data: {"userName": userName},
                success: function (response) {

                }
            });*/

    })
</script>

<body>
<div class="admin-content-body">
    <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">车辆出入库统计：</strong>
            <small></small>
        </div>
        <div style="margin-left: 200px">
            <%--<select name="carStatus" id="carStatus" style="font-size: large">
                <option>请选择</option>
                <option value="4">未归还</option>
                <option value="3">已归还</option>
                <option value="9">在库中</option>

            </select>--%>
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
                        <th>租借单号</th>
                        <th>用户id</th>
                        <th>租借起始日</th>
                        <th>租借结束日</th>
                        <th>租借天数</th>
                        <th>车辆状态</th>
                        <th>订单创建时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="rentDTO" items="${statisticsCarInOutLib.rentDTOPage.content}">
                        <tr>
                            <td>${rentDTO.carId}</td>
                            <td>${rentDTO.id}</td>
                            <td>${rentDTO.customerId}</td>
                            <td><fmt:formatDate value="${rentDTO.beginDate  }" pattern="yyyy-MM-dd"/></td>
                            <td><fmt:formatDate value="${rentDTO.endDate  }" pattern="yyyy-MM-dd"/></td>
                            <td>${rentDTO.rentSumDate}(天)</td>
                            <td>
                                <c:if test="${rentDTO.getRentStatusEnum().getMessage()=='租车完结'}">
                                    已入库
                                </c:if>
                                <c:if test="${rentDTO.getRentStatusEnum().getMessage()!='租车完结'}">
                                    未入库
                                </c:if>
                            </td>
                            <td><fmt:formatDate value="${rentDTO.createTime  }" pattern="yyyy-MM-dd"/></td>

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
                                    <a href="/diandiancar/admin/statistics/in_out_library?page=${currentPage-1}&size=${size}">上一页</a>
                                </c:if>
                                <c:if test="${status!=null}">
                                    <a href="/diandiancar/admin/statistics/in_out_library?page=${currentPage-1}&size=${size}&status=${status}">上一页</a>
                                </c:if>
                            </li>
                        </c:if>
                        <c:forEach var="index" begin="1" end="${statisticsCarInOutLib.rentDTOPage.getTotalPages()}">
                            <c:if test="${currentPage == index}">
                                <li class="am-disabled"><a href="">${index}</a></li>
                            </c:if>
                            <c:if test="${currentPage != index}">
                                <li class="am-active">
                                    <c:if test="${status==null}">
                                        <a href="/diandiancar/admin/statistics/in_out_library?page=${index}&size=${size}">${index}</a>

                                    </c:if>
                                    <c:if test="${status!=null}">
                                        <a href="/diandiancar/admin/statistics/in_out_library?page=${index}&size=${size}&status=${status}">${index}</a>

                                    </c:if>
                                </li>
                            </c:if>
                        </c:forEach>
                        <c:if test="${currentPage == statisticsCarInOutLib.rentDTOPage.getTotalPages()}">
                            <li class="am-disabled">
                                <a href="#">下一页</a>
                            </li>

                        </c:if>
                        <c:if test="${currentPage < statisticsCarInOutLib.rentDTOPage.getTotalPages()}">
                            <li class="am-active">
                                <c:if test="${status==null}">
                                    <a href="/diandiancar/admin/statistics/in_out_library?page=${currentPage+1}&size=${size}">下一页</a>

                                </c:if>
                                <c:if test="${status!=null}">
                                    <a href="/diandiancar/admin/statistics/in_out_library?page=${currentPage+1}&size=${size}&status=${status}">下一页</a>

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

<%--统计的画布--%>
<div style="width:400px;height:400px;">
    <canvas id="myChart"></canvas>
</div>
<script>
    var ctx = document.getElementById("myChart").getContext('2d');

    var data = {
        labels: [
            "已归还", "未归还", "在库中"
        ],
        datasets: [{
            data: [${statisticsCarInOutLib.returned}, ${statisticsCarInOutLib.notReturend}, ${inStocks}],
            backgroundColor: ["lightblue", "#999999", "lightgreen"]
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