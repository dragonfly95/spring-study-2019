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
           <script>
               function getOrderNo() { //2019-05-12-123923
                   var d = new Date();
                   var s =
                       leadingZeros(d.getFullYear(), 4) + '-' +
                       leadingZeros(d.getMonth() + 1, 2) + '-' +
                       leadingZeros(d.getDate(), 2) + '-' +
       /**
                       leadingZeros(d.getHours(), 2) + ':' +
                       leadingZeros(d.getMinutes(), 2) + ':' +
                       leadingZeros(d.getSeconds(), 2) + '-' +
       **/
                       getRandomInt(10000,99999);
                   return s;
               }
               function getRandomInt(min, max) {
                   min = Math.ceil(min);
                   max = Math.floor(max);
                   return Math.floor(Math.random() * (max - min + 1)) + min;
               }
               function leadingZeros(n, digits) {
                   var zero = '';
                   n = n.toString();
                   if (n.length < digits) {
                       for (i = 0; i < digits - n.length; i++)
                           zero += '0';
                   }
                   return zero + n;
               }
               var order_no = getOrderNo();
               function remove_order(id){
                   $("#"+id).remove();
               }
               function add_order(){
                   var waiter = $("#waiter").val();
                   var menu_name = $("#menu_name option:selected").val();
                   var message = $("#message").val();
                   var id = getRandomInt(1111,9999);
                   var html = '            <tr id="'+id+'">\n' +
                       '                <td>\n' +
                       '                    <input type="hidden" name="order_no" value="'+order_no+'" />\n' +
                       '                    <input type="text" name="waiter" value="'+waiter+'" class="waiter" />\n' +
                       '                </td>\n' +
                       '                <td>\n' +
                       '                    <input type="text" name="menu_name" value="'+menu_name+'" class="menu_name"/>\n' +
                       '                </td>\n' +
                       '                <td><input type="text" name="message" value="'+message+'" class="message"></td>\n' +
                       '                <td>\n' +
                       '                    <button type="button" onClick="remove_order(\''+id+'\');">Remove Order</button>\n' +
                       '                </td>\n' +
                       '            </tr>\n';
                   $("#order_tbody").append(html);
                   $("#order_table").show();
                   $(".button_order").show();
               }

               // 주문
               $('.button_order').on('click',function(){
                   $.ajax({
                       type: "POST",
                       url: "./orders",
                       data: "",
                       dataType: "json", contentType: 'application/json',
                       success: function (response) {
                       debugger
                       }
                   });
               });
           </script>
           <style>
               .waiter { width: 80px;}
               .menu_name { width: 100px;}
               .message { width: 150px;}
               .cooking_status { width: 90px;}
               #order_table {display: none;}
               .button_order {display: none;}
           </style>
       </head>
       <body>

       <div class="container">
           <div>
               <span> <a href="terminal.do">1. Terminal</a></span>
               <span> <a href="order.do">2. Kitchen</a></span>
               <span> <a href="check_out.html">3. Check Out</a></span>
           </div>

           <h2>Kitchen Page</h2>
           <table class="table">
               <thead>
               <tr>
                   <th>Order No.</th>
                   <th>Waiter</th>
                   <th>Order</th>
                   <th>Message</th>
       <!--            <th>Receipt</th>-->
                   <th>Cooking status</th>
                   <th>Action</th>
               </tr>
               </thead>
               <tbody>
                   <c:forEach items = "${orderlist}" var = "data">
                       <tr id="mycell">
                           <td>${data.order_id}</td>
                           <td>${data.user_name}</td>
                           <td>${data.menu_name}</td>
                           <td>${data.message}</td>
                           <td>${data.cooking_status}</td>
                               <%-- <select id="cooking_status" onChange="if(this.selectedIndex!=0)
                               document.getElementById('mycell').bgColor=this.options[this.selectedIndex].value">

                               <option value="${data.cooking_status}">${data.cooking_status}</option>
                               <option value="">Not Started</option>
                               <option value="E0FFFF">Started</option>
                               <option value="strike">Cancel</option>
                               </select>
                           </td> --%>
                           <td>
                                <button class="">Fire</button>
                                <button class="">Serve</button>

                           </td>
                       </tr>
                   </c:forEach>
                   <script>
                       var cssGreen = {'background-color':'#6DFF6D'};
                   </script>
               </tbody>
           </table>
       </div>
       </body>
       </html>>