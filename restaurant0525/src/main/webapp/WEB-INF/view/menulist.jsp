<%@ page language="java" isELIgnored="false" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
 <head>
 <style>
 .modify {
   position: absolute;
   top: 106px;
   left:300px;
 }
 </style>
 </head>
 <body>

  <h1>Menu Management</h1>
  <table class="new">
      <tr>
          <td><input type="text" placeholder="menu_name" name="menu_name" value="" /></td>
          <td><input type="text" placeholder="menu_price" name="menu_price" value="" /></td>
          <td><input type="text" placeholder="cooking_time" name="cooking_time" value="" /></td>
          <td>
            <button class="btn-menu-save">Add Menu</button>
          </td>
      </tr>
  </table>

   <hr/>
   <h2>list menu </h2>
    <ul class="menus-list">
        <c:forEach items="${menulist}" var="demo">
               <li data-id="${demo.menu_id}">${demo.menu_id} / ${demo.menu_name} / ${demo.menu_price} / ${demo.cooking_time} </li>
        </c:forEach>
   </ul>

  <div class="modify">
  <h2>Modification</h2>
  <table>
      <tr>
          <td>
            <input type="hidden" name="menu_id" value=""/>
            <input type="text" placeholder="menu_name" name="menu_name" value="" /></td>
      </tr>
      <tr>
          <td><input type="text" placeholder="menu_price" name="menu_price" value="" /></td>
      </tr>
      <tr>
          <td><input type="text" placeholder="cooking_time" name="cooking_time" value="" /></td>
      </tr>
      <tr>
          <td>
                <button class="btn-menu-save2">Change and save</button>
                <button class="btn-menu-delete">Delete</button>
          </td>
      </tr>
  </table>
  </div>

<script src="https://code.jquery.com/jquery-2.2.4.js"></script>
<script>

$(document).ready(function () {

  $('[name="menu_name"]').val();
  $('[name="menu_price"]').val();
  $('[name="cooking_time"]').val();

  $('.btn-menu-save').on('click', function() {

    var dataObj = {
      menu_name: $('[name="menu_name"]').val(),
      menu_price: $('[name="menu_price"]').val(),
      cooking_time: $('[name="cooking_time"]').val()
      };

      debugger  

    $.ajax({
      type: "POST",
      url: "./menus",
      data: dataObj,
      dataType: "json",  // return
      success: function (response) {
        alert('Registered');
        getmenulist();
      }
    });
  }); // 신규 저장 버튼 이벤트


  // 1건 조회
  $(document).on('click','li', function() {
  // $('li').on('click', function() {
    console.log($(this).data('id'));

    $.ajax({
      url: "./menus/" + $(this).data('id') ,
      // data: "data",  // url?id=1
      dataType: "json",  // return type
      success: function (response) {
        $('.modify [name="menu_id"]').val(response.menu_id);
        $('.modify [name="menu_name"]').val(response.menu_name);
        $('.modify [name="menu_price"]').val(response.menu_price);
        $('.modify [name="cooking_time"]').val(response.cooking_time);
      }
    });
  });

  // 수정
  $('.btn-menu-save2').on('click', function() {

    var obj = {
        "menu_id": $('.modify [name="menu_id"]').val(),
        "menu_name": $('.modify [name="menu_name"]').val(),
        "menu_price": $('.modify [name="menu_price"]').val(),
        "cooking_time": $('.modify [name="cooking_time"]').val()
    };

    $.ajax({
      type: "PUT",
      url: "./menus/" + $('.modify [name="menu_id"]').val(),
      data: JSON.stringify(obj),
      contentType: 'application/json',
      dataType: "json", // return type
      success: function (response) {
        debugger
        alert('Modified');
        getmenulist();
      }
    });
  });


  // 삭제
  $('.btn-menu-delete').on('click', function() {
    $.ajax({
      type: "DELETE",
      url: "./menus/" + $('.modify [name="menu_id"]').val(),
      contentType: 'application/json',
      dataType: "json",
      success: function (response) {
        alert('Deleted');
        getmenulist();
      }
    });
  })

  function getmenulist() {
    var str = '';
    $.ajax({
      url: "./menus",
      dataType: "json",  // return type
      success: function (response) {
        $.each(response, function (indexInArray, valueOfElement) {
            str += '<li data-id="'+ valueOfElement.menu_id + '"> ' + valueOfElement.menu_id + ' /' + valueOfElement.menu_name + ' /' + valueOfElement.menu_price + ' /' + valueOfElement.cooking_time + '</li>';
        });
        $('.menus-list').html(str);
      }
    });

  } // getmenulist


});  // doc REady

</script>
</body>
</html>