<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>点点租车管理系统</title>
    <link rel="stylesheet" href="/diandiancar/admin/css/layui.css">
</head>

<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">点点租车管理系统</div>
        <!-- 头部区域（可配合layui已有的水平导航） -->

        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <img src="images/1.gif" class="layui-nav-img"> 管理员
                </a>
                <%--<dl class="layui-nav-child">--%>
                    <%--<dd>--%>
                        <%--<a href="">基本资料</a>--%>
                    <%--</dd>--%>
                <%--</dl>--%>
            </li>
            <li class="layui-nav-item">
                <a href="/diandiancar/admin/logout">退出</a>
            </li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree" lay-filter="test">
                <li class="layui-nav-item layui-nav-itemed">
                    <a class="" href="javascript:;">车辆管理</a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="/diandiancar/admin/carInfo/list" target="haha">列表</a>
                        </dd>
                        <dd>
                            <a href="/diandiancar/admin/carInfo/index" target="haha">新增</a>
                        </dd>

                    </dl>
                </li>

                <li class="layui-nav-item">
                    <a href="javascript:;">车辆类型</a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="/diandiancar/admin/carCategory/list" target="haha">列表</a>
                        </dd>
                        <dd>
                            <a href="/diandiancar/admin/carCategory/index" target="haha">新增</a>
                        </dd>
                    </dl>
                </li>

                <li class="layui-nav-item">
                    <a href="javascript:;">租借记录</a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="/diandiancar/admin/book/list" target="haha">预约信息</a>
                        </dd>
                        <dd>
                            <a href="/diandiancar/admin/rent/list" target="haha">租借信息</a>
                        </dd>
                    </dl>
                </li>

                <li class="layui-nav-item">
                    <a href="javascript:;">用户管理</a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="/diandiancar/admin/customer/list" target="haha">列表</a>
                        </dd>
                        <dd>
                            <a href="/diandiancar/admin/customer/freeze-list" target="haha">冻结的用户</a>
                        </dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">维修记录</a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="/diandiancar/admin/repair/toList" target="haha">列表</a>
                        </dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">前台轮播图</a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="/diandiancar/carousel/admin_list" target="haha">列表</a>
                        </dd>
                        <dd>
                            <a href="/diandiancar/carousel/index" target="haha">新增</a>
                        </dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">统计</a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="/diandiancar/admin/statistics/in_out_library" target="haha">出入库统计</a>
                        </dd>
                        <dd>
                            <a href="/diandiancar/admin/statistics/new_cars" target="haha">新增车辆统计</a>
                        </dd>
                        <%--<dd>
                            <a href="/diandiancar/admin/statistics/repair" target="haha">车辆维修统计</a>
                        </dd>--%>

                    </dl>
                </li>
            </ul>
        </div>
    </div>

    <div class="layui-body" style="z-index: 0;">
        <!-- 内容主体区域 -->
        <div style="padding: 15px;">
            <%--<iframe src="product.html" name="right" frameborder="0" width="100%" height="1200"></iframe>--%>
                <iframe src="/diandiancar/images/welcome.jpg" name="haha" frameborder="0" width="100%" height="1200"></iframe>

        </div>
    </div>

    <div class="layui-footer">
        <!-- 底部固定区域 -->
        点点租车
    </div>
</div>

<script type="text/javascript" src="/diandiancar/admin/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/diandiancar/admin/myplugs/js/plugs.js">
</script>
<script type="text/javascript">
    //添加编辑弹出层
    function updatePwd(title, id) {
        $.jq_Panel({
            title: title,
            iframeWidth: 500,
            iframeHeight: 300,
            url: "updatePwd.html"
        });
    }
</script>
<script src="/diandiancar/admin/js/layui.js"></script>
<script>
    //JavaScript代码区域
    layui.use('element', function () {
        var element = layui.element;

    });
</script>
</body>

</html>