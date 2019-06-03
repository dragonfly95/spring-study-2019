<%@ page language="java" isELIgnored="false" contentType="text/html;charset=utf-8"
                pageEncoding="utf-8" %>
       <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
       <html lang="en">
       <head>
           <title>Order Page</title>
           <meta charset="utf-8">
           <meta name="viewport" content="width=device-width, initial-scale=1">
           <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
           <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
           <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
           <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

       </head>
       <body>

       <div class="container">
           <div>
               <span> <a href="terminal.do">1. Terminal</a></span>
               <span> <a href="order.do">2. Kitchen</a></span>
               <span> <a href="check_out.html">3. Check Out</a></span>
           </div>

           <h2>Terminal Page</h2>

           <table class="new">
            <tr>
                <td>
                    <select name="menu_id">
                        <option value="">메뉴</option>
                    <c:forEach items = "${menulist}" var = "data">
                        <option value="${data.menu_id}">${data.menu_name}</option>
                    </c:forEach>
                    </select>
                </td>
                <td><input type="text" placeholder="menu_price" name="menu_price" value="" /></td>
                <td><input type="text" placeholder="cooking_time" name="cooking_time" value="" /></td>
                <td><input type="text" placeholder="order_message" name="message" value="" /></td>
                <td>
                    <button class="btn-menu-save">Add Menu</button>
                </td>
            </tr>
           </table>

        <hr/>
            
           <table class="table">
               <thead>
               <tr>
                   <th>Order detail No.</th>
                   <th>Waiter</th>
                   <th>Order</th>
                   <th>Message</th>
       <!--            <th>Receipt</th>-->
               </tr>
               </thead>
               <tbody>
                   <c:forEach items = "${terminalList}" var = "data">
                       <tr id="mycell">
                           <td>${data.order_detail_id}</td>
                           <td>${data.user_name}</td>
                           <td>${data.menu_name}</td>
                           <td>${data.message}</td>
                       </tr>
                   </c:forEach>
                   <script>
                       var cssGreen = {'background-color':'#6DFF6D'};
                   </script>
               </tbody>
           </table>
        <hr/>
        <table>
            <tr>
                <td><input type="text" placeholder="user_name" name="user_name" value=""></td>
                <td><input type="text" placeholder="user_id" name="user_id" value=""></td>
                <td><input type="text" placeholder="total_cost" name="total_cost" value=""></td>
                <td><input type="button" name="btn-order" value="주문 확인"></td>
            </tr>
        </table>
           
           <script src="https://code.jquery.com/jquery-2.2.4.js"></script>
           <script>

var orders = [];

           $(document).ready(function () {

             $('[name="order_detail_id"]').val();
             $('[name="user_name"]').val();
             $('[name="menu_name"]').val();
             $('[name="order_message"]').val();

$('[name="user_name"]').val('홍낄똥'); $('[name="menu_name"]').val('스테이크'); $('[name="order_message"]').val('ㄷㄷㄷ');


            // 메뉴선택시 가격, 조리시간 불러오기 
             $('[name="menu_id"]').on('change', function() {

                $.ajax({
                    url: "./menus/" + $('[name="menu_id"]').val(),
                    dataType: "json",
                    success: function (response) {
                        $('[name="menu_price"]').val(response.menu_price);
                        $('[name="cooking_time"]').val(response.cooking_time);
                        $('[name="message"]').val('');
                        $('[name="message"]').focus();
                    }
                });  // ajax 
             });  // 메뉴선택시 가격, 조리시간 불러오기 


            $('.btn-menu-save').on('click', function() {

                orders.push({
                "order_detail_id": 0,
                "order_id": 0,
                "menu_id": $('[name="menu_id"]').val(),
                "menu_name": $('[name="menu_id"] option:selected').text(),  //
                "menu_price": $('[name="menu_price"]').val(),               //
                "cooking_time": $('[name="cooking_time"]').val(),           //
                "message": $('[name="message"]').val()
                });

                var total_cost = 0;
                var str = '';
                $.each(orders, function (indexInArray, valueOfElement) { 
                    // Order detail No.	Waiter	Order	Message
                    str += '<tr>';
                    str += '  <td>'+ valueOfElement.menu_id + '</td>';
                    str += '  <td>'+ valueOfElement.menu_name + '</td>';
                    str += '  <td>'+ valueOfElement.menu_price + '</td>';
                    str += '  <td>'+ valueOfElement.cooking_time + '</td>';
                    str += '  <td>'+ valueOfElement.message + '</td>';
                    str += '</tr>';

                    total_cost += parseFloat( valueOfElement.menu_price );
                });
                $('.table > tbody').html(str);

                $('[name="total_cost"]').val(total_cost);
                $('[name="user_id"]').val(1);
                $('[name="user_name"]').val('홍길동');

            });

             // 주문 확인 
             $('[name="btn-order"]').on('click', function() {

               if(orders.length == 0) {
                   alert('메뉴를 주문해주세요.');
                   return;
               }
               var dataObj = {
                    order_id: 0,
                    user_id: $('[name="user_id"]').val(),
                    total_cost: $('[name="total_cost"]').val(),
                    cooking_status: 'Not started',
                    order_detail: orders.map(function(order) {
                                            return {menu_id: order.menu_id, message: order.message}
                                            })
                 };

// var dataObj1 = {
//     "order_id":0,
//     "user_id":"1",
//     "total_cost":"17.5",
//     "cooking_status":"Not started",
//     "order_detail":[
//         {"order_detail_id":0,"order_id":0,"menu_id":"57","message":""}]
// };

               $.ajax({
                 type: "POST",
                 url: "./terminals",
                 data: JSON.stringify(dataObj),
                 dataType: "json",  // return response
                 contentType: 'application/json', // request
                 success: function (response) {
                   alert('Added');
                 },
                 error: function() {
                     console.log('error');
                 }
               });
               
           
             }); // 신규 저장 버튼 이벤트


           }); // doc ready 
            </script>
       </div>
       </body>
       </html>>