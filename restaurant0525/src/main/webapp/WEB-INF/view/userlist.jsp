<%@ page language="java" isELIgnored="false" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>user list</title>
</head>
<body>

<h2>사용자 등록</h2>
<table>
  <tr>
    <td><input type="text" placeholder="user_id" name="user_id" value=""/></td>
    <td><input type="text" placeholder="name" name="name" value=""/></td>
    <td><input type="text" placeholder="login" name="login" value=""/></td>
    <td><input type="text" placeholder="password" name="password" value=""/></td>
    <td><input type="text" placeholder="email" name="email" value=""/></td>
    <td><input type="text" placeholder="user_type" name="user_type" value=""/></td>
    <td>
      <button class="btn-user-save">신규 저장</button>
    </td>
  </tr>
</table>
<hr/>
<h2>user list</h2>
  <ul class="users-list">
   <c:forEach items="${userlist}" var="demo">
   <li data-id="${demo.user_id}">${demo.name} / ${demo.login} / ${demo.email} / ${demo.user_type} </li>
   </c:forEach>
  </ul>




<script src="https://code.jquery.com/jquery-2.2.4.js"></script>
<script>
$(document).ready(function () {
  
  // 사용자 신규 저장 
  $('.btn-user-save').on('click', function() {
    debugger;
  });  // 사용자 신규 저장

  function getUserList() {

    var str = '';
    $.ajax({
      url: "./users",
      dataType: "json",
      success: function (response) {
        $.each(response, function (indexInArray, valueOfElement) { 
          str += '<li data-id="'+ valueOfElement.user_id + '"> ' + valueOfElement.name + ' /' + valueOfElement.login + ' /' + valueOfElement.email +' / ' + valueOfElement.user_type +'/ </li>'; 
        });
        $('.users-list').html(str);
      }
    });
    
  }
});
</script>
</body>
</html>