<%@ page language="java" isELIgnored="false" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

hello-modify

<ul>
  <c:forEach items="${list}" var="person">
  <li data-id="${person.id}">${person.firstName}</li>
  </c:forEach>
</ul>