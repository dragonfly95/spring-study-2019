<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>  

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>사용자 등록</title>
</head>
<body>
   <form:form modelAttribute="member">
   <table>
     <tr>
        <td>아이디</td>
        <td>
          <form:input path="id" cssStyle="width: 280px"/>
        </td>
     </tr>
     <tr>
        <td>비번</td>
        <td>
          <form:password path="password" cssStyle="width: 280px"/>
        </td>
     </tr>

     <tr>
        <td>비번 확인</td>
        <td>
          <input type="password" id="confirm" name="confirm" style="width: 280px" />
        </td>
     </tr>
     <tr>
        <td>이름</td>
        <td>
          <form:input path="name" cssStyle="width: 280px"/>
        </td>
     </tr>
   
   </table>
     <div>
       <input type="submit" value="등록">
     </div>
   
   
   </form:form>
</body>
</html>