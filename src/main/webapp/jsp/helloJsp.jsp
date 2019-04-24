<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN""http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" href="/diandiancar/css/test.css">
</head>
<body>
  helloJsp
<%--<img src="/diandiancar/images/1.jpg" width="100%" height="100%">--%>
  
<hr>
  <a href="/diandiancar/admin/test.html">${hello}</a>
<input placeholder="<c:if test='${hello=="lin"}'>tesssst</c:if>">
<% if (request.getAttribute("hello") == "lin") { %>
<p>今天是周末 --- </p>
<% } else { %>
<p>今天不是周末</p>
<% } %>
<c:if test="${num>=0}">
    ${num}numnum
</c:if>




</body>
</html>