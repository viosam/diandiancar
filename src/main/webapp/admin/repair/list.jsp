<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>维修管理</title>
    <link rel="stylesheet" href="/diandiancar/admin/css/amazeui.min.css"/>
    <link rel="stylesheet" href="/diandiancar/admin/css/admin.css"/>

    <!-- 引入CSS -->
    <link href="/diandiancar/css/bootstrap.css" type="text/css" rel="stylesheet">
    <link href="/diandiancar/css/style.css" type="text/css" rel="stylesheet">
    <!-- 引入JS -->
    <script src="/diandiancar/js/jquery-3.3.1.min.js"></script>
    <script src="/diandiancar/js/popper.min.js"></script>
    <script src="/diandiancar/js/bootstrap.js"></script>
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
        <div class="am-u-sm-12 am-u-md-3">
        </div>
    </div>
    <div class="am-g">
        <div class="am-u-sm-12">
            <form class="am-form">
                <table class="am-table am-table-striped am-table-hover table-main table-border">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>报修车id</th>
                        <th>描述</th>
                        <th>维修部位</th>
                        <th>维修状态</th>
                        <th>图片</th>
                        <th>创建时间</th>
                        <%--<th class="table-set">操作</th>--%>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="repairDTO" items="${repairDTOPage.content}">
                        <tr>
                            <td>${repairDTO.id}</td>
                            <td>${repairDTO.carId}</td>
                            <td>${repairDTO.description}</td>
                            <td>${repairDTO.repairPart}</td>
                            <td>${repairDTO.getRepairStatusEnum().getMessage()}</td>
                            <td><img height="60" width="60" style="padding: 5px;" src="${repairDTO.icons}" alt="">

                            <td><fmt:formatDate value="${repairDTO.createTime  }" pattern="yyyy-MM-dd"/></td>
                            <%--<td>--%>
                                <%--<c:if test="${repairDTO.getRepairStatusEnum().getMessage() == '提交维修信息'}">--%>
                                    <%--<a class="btn btn-outline-info btn-sm" href="/diandiancar/admin/repair/approved_or_fail?repairId=${repairDTO.id}&tag=1">通过</a>--%>
                                    <%--<a class="btn btn-outline-danger btn-sm" href="/diandiancar/admin/repair/approved_or_fail?repairId=${repairDTO.id}&tag=0">取消</a>--%>
                                <%--</c:if>--%>

                                <%--<c:if test="${repairDTO.getRepairStatusEnum().getMessage() != '提交维修信息'}">--%>
                                    <%--<c:if test="${repairDTO.getRepairStatusEnum().getMessage() == '维修完结'}">--%>
                                        <%--<a class="am-disabled">维修完结</a>--%>
                                    <%--</c:if>--%>
                                <%--</c:if>--%>
                            <%--</td>--%>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </form>

                <%--分页--%>
                <div class="am-fr">
                    <ul class="am-pagination">
                        <c:if test="${currentPage<=1}">
                            <li class="am-disabled"><a href="#">上一页</a></li>
                        </c:if>
                        <c:if test="${currentPage>1}">
                            <li class="am-active"><a
                                    href="/diandiancar/admin/repair/toList?page=${currentPage-1}&size=${size}">上一页</a>
                            </li>
                        </c:if>
                        <c:forEach var="index" begin="1" end="${repairDTOPage.getTotalPages()}">
                            <c:if test="${currentPage == index}">
                                <li class="am-disabled"><a href="">${index}</a></li>
                            </c:if>
                            <c:if test="${currentPage != index}">
                                <li class="am-active"><a
                                        href="/diandiancar/admin/repair/toList?page=${index}&size=${size}">${index}</a>
                                </li>
                            </c:if>
                        </c:forEach>
                        <c:if test="${currentPage == repairDTOPage.getTotalPages()}">
                            <li class="am-disabled"><a href="#">下一页</a></li>

                        </c:if>
                        <c:if test="${currentPage < repairDTOPage.getTotalPages()}">
                            <li class="am-active">
                                <a href="/diandiancar/admin/repair/toList?page=${currentPage+1}&size=${size}">下一页</a>
                            </li>
                        </c:if>
                    </ul>
                </div>

                </ul>
        </div>
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