<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>관리자 메인</title>
</head>
<body>
<h2>관리자 메인</h2>

<c:if test="${not empty user}">
    <p>${user.userNm} 님 환영합니다.</p>
    <p>역할: ${user.roleList}</p>
</c:if>

<p><a href="<%=request.getContextPath()%>/logout.do">로그아웃</a></p>

</body>
</html>
