<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
						<span class="navbar-brand">사역팀 조회</span>
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
									<p class="category">
										사람 이름으로 검색 : <input type="text" id="searchText"></input>
										<button id="searchBtn">검색</button>
										<button id="clear">초기화</button>
									</p>
								</div>
								<div class="content table-responsive table-full-width">
									<div id="jsGrid"></div>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="card">
								<div class="header">
									<h4 class="title" id="teamMemberId">팀 구성원</h4>
								</div>
								<div id="jsGridPeople"></div>
								<div>&nbsp;&nbsp;&nbsp;</div>
							</div>
						</div>
						<div class="col-md-8">
							<div class="card">
								<div class="header">
									<h4 class="title" id="teamMemberInfoId">인적 정보</h4>
								</div>
								<div id="jsGridChurch"></div>
								<div>&nbsp;&nbsp;&nbsp;</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

<!--   Core JS Files   -->
<script src="resources/assets/js/jquery-1.10.2.js" type="text/javascript"></script>
<script src="resources/assets/js/bootstrap.min.js" type="text/javascript"></script>
<script src="resources/assets/js/bootstrap-checkbox-radio-switch.js"></script>
<script src="resources/assets/js/chartist.min.js"></script>
<script src="resources/assets/js/bootstrap-notify.js"></script>
<script src="resources/assets/js/light-bootstrap-dashboard.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jsgrid/1.5.2/jsgrid.min.js"></script>
<script type="text/javascript" src="//cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jsgrid/1.5.2/jsgrid.min.js"></script>

<script type="text/javascript">
		var myRend = function(value, item) {
			var result = "<td>";
			value = value.replace(/[0-9]/g, '');
			result = result + value + "</td>";
			return result;
		}
    	$(document).ready(function(){
    		var keyword = "";
    		
    		$("#searchBtn").click(function() {
    			keyword = $("#searchText").val();
    			drawMainGrid();
    		});
    		
    		$("#clear").click(function() {
    			$("#searchText").val("");
    			keyword = "";
    			drawMainGrid();
    		});
    	 	
    		drawMainGrid();
    		drawTeamMemberGrid(-1, "", "", "", "");
    		drawDetailGrid('');
    		
    		function drawMainGrid() {
    			$.ajax({
                    type: "GET",
                    url: "getCampusList",
                    success: function(data) {
                        var campusList = data.campusList;	
                        var departmentList = data.departmentList;
                        
                        $("#jsGrid").jsGrid({
                			width : "100%",
                			height : "400px",
                			filtering: true,
                			autoload: true,	
                			sorting : true,
                			paging : true,
                			selecting : true,
                			pageSize: 15,
                	        pageButtonCount: 5,
                			
                			rowClick : function(args) {
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
                				
                				var team1 = args.item.team1;
                				var team2 = args.item.team2;
                				drawTeamMemberGrid(id, campusName, deptName, team1, team2);
                			},
                			controller : {
                				loadData : function(filter) {
                					filter.keyword = keyword;
                					return $.ajax({
                						type: "GET", 
                						url:"get_items_filter",
                						data:filter
                					})
                				},
                			},
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
                				{ name : "elder", align : "center", width : "auto", title : "장로", type : "text", cellRenderer : myRend }, 
                				{ name : "cheif", align : "center", width : "auto", title : "팀장", type : "text", cellRenderer : myRend }, 
                				{ name : "manager", align : "center", width : "auto", title : "총무", type : "text", cellRenderer : myRend }, 
                				{ type: "control", editButton: false, deleteButton: false }
                			]
                		});
                    }
        		});
    		}
    		
    		function drawTeamMemberGrid(teamid, campusName, deptName, team1, team2) {
    			if (team1 != "" && team2 != "") {
	    		  $("#teamMemberId").html("팀 구성원 (" + campusName + " > " +  deptName + " > " + team1 + ">" + team2 + ")");	
    			}
    			
    			if (team1 == "" && team2 == "") {
    				$("#teamMemberId").html("팀 구성원");
    			}
    			
    			if (team1 != "" && team2 == "") {
    				$("#teamMemberId").html("팀 구성원 ("+ campusName + " > " +  deptName + " > " + team1 + ")");
    			}
    			
                  $("#jsGridPeople").jsGrid({
          			width : "100%",
          			height : "400px",
          			autoload: true,
          			selecting : true,
          			controller : {
          				loadData : function(filter) {
          					filter.keyword = keyword;
          					return $.ajax({
          						type: "GET", 
          						url:"teamperson/"+teamid,
          					})
          				},
          			},
          			rowClick : function(args) {
          				name = args.item.name;
          				drawDetailGrid(name);
          			},
          			fields : [ 
          				{ align : "center", width : "auto", name : "name", title : "이름", type : "text", cellRenderer : myRend }, 
          				{ align : "center", width : "auto", name : "kk", title : "역할", type : "text" }, 
          			]
          		});
    		}
    		
    		
    		function drawDetailGrid(name) {
    			 $("#jsGridChurch").jsGrid({
         			width : "100%",
         			height : "400px",
         			autoload: true,	
         			controller : {
         				loadData : function(filter) {
         					return $.ajax({
         						type: "GET", 
         						url:"getChurchPersonByName/"+name,
         					})
         				},
         			},
         			//이름, 생년월일, 공동체, 직분, 전화번호
         			fields : [ 
         				{ align : "center", width : "auto", name : "name", title : "이름",    type : "text" }, 
         				{ align : "center", width : "auto", name : "birth",title : "생년월일", type : "text" }, 
         				{ align : "center", width : "auto", name : "comm", title : "공동체",   type : "text" }, 
         				{ align : "center", width : "auto", name : "kk",   title : "캠퍼스",    type : "text" }, 
         				{ align : "center", width : "auto", name : "tel", title : "전화번호",  type : "text" },
         				{ align : "center", width : "auto", name : "jch", title : "직분",  type : "text" }
         			]
         		});
    		}
    		
    		
    		
        });
	</script>

</html>






