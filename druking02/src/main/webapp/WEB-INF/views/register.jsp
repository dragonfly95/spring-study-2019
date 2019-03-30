<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
  <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Sign Up Form</title>
        <link href='http://fonts.googleapis.com/css?family=Nunito:400,300' rel='stylesheet' type='text/css'>
        
        <style>
			*, *:before, *:after {
				-moz-box-sizing: border-box;
				-webkit-box-sizing: border-box;
				box-sizing: border-box;
			}
			
			body {
				font-family: 'Nunito', sans-serif;
				color: #384047;
			}
			
			.ldk {
				max-width: 300px;
				margin: 10px auto;
				padding: 10px 20px;
				border-radius: 8px;
				margin-bottom: 30px;
				border: none;
			}
			
			#formstyle {
				max-width: 300px;
				margin: 10px auto;
				padding: 10px 20px;
				background: #f4f7f8;
				border-radius: 8px;
			}
			
			h1 {
				margin: 0 0 30px 0;
				text-align: center;
			}
			
			input[type="text"], input[type="password"], input[type="date"], input[type="datetime"],
				input[type="email"], input[type="number"], input[type="search"], input[type="tel"],
				input[type="time"], input[type="url"], textarea, select {
				background: rgba(255, 255, 255, 0.1);
				border: none;
				font-size: 16px;
				height: auto;
				margin: 0;
				outline: 0;
				padding: 15px;
				width: 100%;
				background-color: #e8eeef;
				color: #8a97a0;
				box-shadow: 0 1px 0 rgba(0, 0, 0, 0.03) inset;
				margin-bottom: 30px;
			}
			
			input[type="radio"], input[type="checkbox"] {
				margin: 0 4px 8px 0;
			}
			
			select {
				padding: 6px;
				height: 32px;
				border-radius: 2px;
			}
			
			button {
				padding: 19px 39px 18px 39px;
				color: #FFF;
				background-color: #4bc970;
				font-size: 18px;
				text-align: center;
				font-style: normal;
				border-radius: 5px;
				width: 100%;
				border: 1px solid #3ac162;
				border-width: 1px 1px 3px;
				box-shadow: 0 -1px 0 rgba(255, 255, 255, 0.1) inset;
				margin-bottom: 10px;
			}
			
			fieldset {
				margin-bottom: 30px;
				border: none;
			}
			
			legend {
				font-size: 1.4em;
				margin-bottom: 10px;
			}
			
			label {
				display: block;
				margin-bottom: 8px;
			}
			
			label.light {
				font-weight: 300;
				display: inline;
			}
			
			.number {
				background-color: #5fcf80;
				color: #fff;
				height: 30px;
				width: 30px;
				display: inline-block;
				font-size: 0.8em;
				margin-right: 4px;
				line-height: 30px;
				text-align: center;
				text-shadow: 0 1px 0 rgba(255, 255, 255, 0.2);
				border-radius: 100%;
			}
			
			@media screen and (min-width: 480px) {
				#formstyle {
					max-width: 480px;
				}
			}
		</style>
    </head>
    <body>
      <div id="formstyle">
      
        <h1>사역 관리 입력자 가입</h1>
        
        <fieldset>
          <label for="id">ID</label>
          <input type="text" id="user_id" name="user_id">
          
          <label for="name">이름</label>
          <input type="text" id="user_name" name="user_name">
          
          <label for="birth">생년월일(예:19800101)</label>
          <input type="number" id="user_birth" name="user_birth">
          
          <label for="password">Password</label>
          <input type="password" id="user_password" name="user_password">
          
          <button id= "saveBtn" >Sign Up</button>
          <button id= "cancleBtn" >Cancel</button>
        </fieldset>
      </div>
      
      
    </body>
      <script src="resources/assets/js/jquery-1.10.2.js" type="text/javascript"></script>
      <script type="text/javascript">

		$(document).ready(function() {
			$("#cancleBtn").click(function() {
				location.href='login';
			});
			
			
			$("#saveBtn").click(function() {
				var user_id = $("#user_id").val();
				var user_name = $("#user_name").val();
				var user_birth = $("#user_birth").val();
				var password = $("#user_password").val();
				
				if (user_id == "" || user_name == "" ||user_birth == ""||password == "" ) {
				     alert("모든 입력란을 채워주세요.");
				}
				else {
					console.log("hi");
					var param = {
						user_id : user_id,
						user_name : user_name, 
						user_birth : user_birth,
						password : password
					};
					
					console.log(JSON.stringify(param));
					
					$.ajax({
			            type: "POST",
			            url: "user/registerUser",
			            data: param, 
			            success: function(ddd) {
			            	if (ddd == "NO_CHURCH_PERSON") {
				            	alert("전산실에 문의하여 주세요. ");	
			            	}
			            	else if (ddd == "OK") {
			            		alert("가입되었습니다. 로그인해주세요.")
			            		location.href='login';
			            	}
			            }
					});
				}
			});
		});
</script>
      
      
</html>
