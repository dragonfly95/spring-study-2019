<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    <h2>회원 정보 </h2>
    <button type="button" onclick="location.href='register'">회원 추가 </button>
    <table border="1">
      <tr>
         <th>id</th><th>pw</th><th>name</th><th>reg date</th>
      </tr>
      <c:forEach var="m" items="${memList}">
      <tr>
        <td>${m.id}</td>
        <td>${m.password}</td>
        <td>${m.name}</td>
        <td>${m.regdate}</td>
            </tr>
      
      </c:forEach>
    
    
    </table>
</body>
</html>