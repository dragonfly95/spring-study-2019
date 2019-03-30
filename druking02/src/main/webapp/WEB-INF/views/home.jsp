<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<link rel="icon" type="image/png" href="assets/img/favicon.ico">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<title>Onnuri Church</title>
	
	<meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
	<meta name="viewport" content="width=device-width" />
	
	<link href="resources/assets/css/bootstrap.min.css" rel="stylesheet" />
	<link href="resources/assets/css/animate.min.css" rel="stylesheet" />
	<link href="resources/assets/css/light-bootstrap-dashboard.css" rel="stylesheet" />
	<link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
	<link href='http://fonts.googleapis.com/css?family=Roboto:400,700,300' rel='stylesheet' type='text/css'>
	<link href="resources/assets/css/pe-icon-7-stroke.css" rel="stylesheet" />
	<link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jsgrid/1.5.2/jsgrid.min.css" />
	<link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jsgrid/1.5.2/jsgrid-theme.min.css" />

</head>
<body>
	<div class="wrapper">
		<jsp:include page="common.jsp" flush="false"></jsp:include>
		<div class="main-panel">
			<nav class="navbar navbar-default navbar-fixed">
				<div class="container-fluid">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle" data-toggle="collapse"
							data-target="#navigation-example-2">
							<span class="sr-only">Toggle navigation</span> <span
								class="icon-bar"></span> <span class="icon-bar"></span> <span
								class="icon-bar"></span>
						</button>
						<span class="navbar-brand">사역팀 입력 (입력자 : ${userName} )</span>
					</div>
					<div class="collapse navbar-collapse">
						<ul class="nav navbar-nav navbar-right">
							<li><a href="/j_spring_security_logout"> Log out
							</a></li>
						</ul>
					</div>
				</div>
			</nav>
			<div class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="col-md-12">
							<div class="card">
								<div class="header">
									<h4 class="title">사역팀</h4>
									<p class="category">복수의 인원을 입력할 때는 사람마다 쉼표(,)로 구분해주세요</p>
								</div>
								<div class="content table-responsive table-full-width">
									<div id="jsGrid"></div>
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="card">
								<div class="header">
									<h4 class="title" id="excutor">실행위원</h4>
									<p class="category" id="status">위 테이블에서 사역팀을 선택해주세요.</p>
								</div>
								<textarea id="excutors" class="col-md-12"
									style="margin: 2px; padding: 2px">
								</textarea>
								<div align="right" style="margin: 2px; padding: 2px">
									<button id= "saveBtn" type="button" class="btn btn-primary">save</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
		</div>
	</div>
</body>

<script src="resources/assets/js/jquery-1.10.2.js" type="text/javascript"></script>
<script src="resources/assets/js/bootstrap.min.js" type="text/javascript"></script>
<script src="resources/assets/js/bootstrap-checkbox-radio-switch.js"></script>
<script src="resources/assets/js/chartist.min.js"></script>
<script src="resources/assets/js/bootstrap-notify.js"></script>
<script src="resources/assets/js/light-bootstrap-dashboard.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jsgrid/1.5.2/jsgrid.min.js"></script>

<script type="text/javascript">
	var myRend = function(value, item) {
		var result = "<td>";
		var arrs = value.split(',');
		for ( var i in arrs) {
			var item = arrs[i];
			result = result + item + "<br>";
		}
		result = result + "</td>";
		return result;
	}

	$(document).ready(function() {
		var teamName = "";
		var id = -10;
		$("#excutors").val('');
		$("#excutors").prop('disabled', true);
		$("#saveBtn").prop('disabled', true);
		
		$("#saveBtn").click(function() {
			if (id == -10) {
				alert("팀을 선택해주세요.");
			}
			else {
				var text = $('#excutors').val();
				var param = {
					excutors : text,
					team2 : teamName,
					id : id
				};
				
				$.ajax({
		            type: "POST",
		            url: "save_excutors",
		            data: param, 
		            success: function(data) {
		            	alert("저장되었습니다.");	
		            }
				});
			}
		});

		var excutors = [ {
			"excutor" : ""
		} ];
		
		function drawInputJsGrid(campusList, departmentList) {
			//console.log(JSON.stringify(campusList));
			$("#jsGrid").jsGrid({
    			width : "100%",
    			height : "400px",

    			autoload: true,	
    			inserting : true,
    			editing : true,
    			sorting : true,
    			paging : true,
    			selecting : true,
    			pageSize: 15,
    	        pageButtonCount: 5,
    			
    			rowClick : function(args) {
    				$("#excutors").prop('disabled', false);
    				$("#saveBtn").prop('disabled', false);
    				
    				teamName = args.item.team2;
    				id = args.item.id;
    				
    				var campusName = '';
    				for (var i in campusList) {
    					if (campusList[i].id == args.item.campus) {
    						campusName = campusList[i].name; 
    					}
    				}
    				
    				var deptName = '';
    				for (var i in departmentList) {
    					if (departmentList[i].id == args.item.department) {
    						deptName = departmentList[i].name; 
    					}
    				}
    				
    				$("#excutor").text("실행위원 (" + campusName + "-" + deptName + "-" + args.item.team1 + "-" + args.item.team2 + ")")
    				$("#status").html("복수의 인원을 입력할 때는 사람마다 쉼표(,)로 구분해주세요  <br>이름 뒤에 생년월일을 입력하실 수 있습니다. YYYYMMDD 예)홍길동19701130");
    				
    				if (id != null) {
    					$.ajax({
        					url:"excutors/"+id,
        					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        					type: 'GET',
        					success: function(data) {
        						var content = "";
        						for (var i in data) {
        							content = content + data[i].name + ",";
        						}
        						$('#excutors').height(((data.length / 25)+1) * 30);
        						$('#excutors').val(content);
        			
        					}, 
        					error: function(jqXHR, textStatus, error) {
        						alert("error" + JSON.stringify(textStatus) + " " + JSON.stringify(error));
        						
        					}
        				});
    				}
    				else {
    					$('#excutors').val("");
    				}
    			},
    			controller : {
    				loadData : function(filter) {
    					return $.ajax({
    						type: "GET", 
    						url:"get_items",
    					})
    				},
    				insertItem : function(item) {
    					return $.ajax({
    			            type: "POST",
    			            url: "insert_item",
    			            data: item
    			        });
    				},
    				updateItem : function(item) {
    					return $.ajax({
    			            type: "POST",
    			            url: "update_item",
    			            data: item
    			        });
    				},
    				deleteItem : function(item) {
    					return $.ajax({
    			            type: "POST",
    			            url: "delete_item",
    			            data: item
    			        });
    				},
    			},
    			confirmDeleting : true,
    			deleteConfirm : "삭제하시겠습니까?",
    			fields : [ 
    			{ 
    			  align : "center", width : "auto", name : "campus", title : "캠퍼스", type : "select",
    			  items : campusList, valueField : "id", textField : "name", validate : "required"
    			}, 
    			{ 
    			  align : "center", width : "auto", name : "department", title : "본부", type : "select", 
    			  items : departmentList, valueField : "id", textField : "name", validate : "required"
    			}, 
    			{ align : "center", width : "auto", name : "team1", title : "본부사역팀", type : "text" }, 
    			{ align : "center", width : "auto", name : "team2", title : "사역팀", type : "text" }, 
    			{ align : "center", width : "auto", name : "pastor", title : "교역자", type : "text", cellRenderer : myRend }, 
    			{ align : "center", width : "auto", name : "elder", title : "장로", type : "text", cellRenderer : myRend }, 
    			{ align : "center", width : "auto", name : "cheif", title : "팀장", type : "text", cellRenderer : myRend }, 
    			{ align : "center", width : "auto", name : "manager", title : "총무", type : "text", cellRenderer : myRend },
    			{ align : "center", width : "auto", type : "control" } 
    			]
    		});
		}
		
		$.ajax({
            type: "GET",
            url: "getCampusList",
            success: function(data) {
                var campusList = data.campusList;	
                var departmentList = data.departmentList;
                
                drawInputJsGrid(campusList, departmentList);
            }
		});
	});
</script>
</html>
