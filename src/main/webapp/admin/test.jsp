<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html xmlns:th="http://www.w3.org/1999/html">
<script src="/diandiancar/js/jquery/1.9.1/jquery.min.js" type="text/javascript"></script>
<script src="/diandiancar/js/jquery/1.9.1/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="/diandiancar/js/additional-methods.js" type="text/javascript"></script>
<head>
    <meta charset="UTF-8">
    <title>test</title>
</head>
<script type="text/javascript">

    $(document).ready(function(){
        $("#btn1").click(function(){
            var url = "/diandiancar/test/json";
            alert(url);
            $('#tes').load(url);
        });

        $("#b").click(function(){
            $.ajax({
                url:"/diandiancar/admin/carCategory/list",
                success:function(result){
                    $("#div1").html(result);
                }});
        });
    });

</script>
<body>
<div class="content">
    <ul id="test"></ul>
    <a id="search_button" href="/diandiancar/admin/carCategory/list">test</a>
    <button id="btn1" type="button">获得外部的内容</button>
</div>

<button id="b">获取其他内容</button>
</body>

</html>