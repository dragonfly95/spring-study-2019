<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<html>
<%@ include file="/common/header.jsp"%>
<body>
  <div id="wrapper">
  <%@ include file="/common/usermenu.jsp"%>
    <form:form modelAttribute="user" method="post" id="join-form">
      <div id="page-wrapper">
        <div class="row">
          <div class="col-lg-12">
            <h1 class="page-header">회원가입</h1>
          </div>
        </div>
        <div class="row">
          <div class="col-md-1">
            <label>아이디</label>
          </div>
          <div class="col-md-5">
            <form:input path="username" cssClass="form-control input-sm" />
          </div>
        </div>
        
        <div class="row top-buffer">
          <div class="col-md-1">
            <label>패스워드</label>
          </div>
          <div class="col-md-5">
            <form:input path="password" cssClass="form-control input-sm" type="password" />
          </div>
        </div>
        
        <div class="row top-buffer">
          <div class="col-md-1">
            <label>이름</label>
          </div>
          <div class="col-md-5">
            <form:input path="name" cssClass="form-control input-sm" />
          </div>
        </div>
        
        <div class="row top-buffer">
          <div class="col-md-1">
            <label>모바일</label>
          </div>
          <div class="col-md-5">
            <form:input path="mobile" cssClass="form-control input-sm" />
          </div>
        </div>
               
        <div class="row top-buffer">
          <div class="col-md-1">
            <label>이메일</label>
          </div>
          <div class="col-md-5">
            <form:input path="mobile" cssClass="form-control input-sm"/>
          </div>
        </div>        
        <br>
        <div class="row top-buffer">
          <div class="col-md-12">
            <div class="pull-center">
              <button type="submit" class="btn btn-primary btn-sm btn-block">Save</button>             
            </div>
          </div>
        </div>
      </div>
    </form:form>
  </div>
  <%@ include file="/common/scripts.jsp"%>

  <script>
      var options = {
        url : '/join',
        type : 'post',
        dataType : 'json',
        clearForm : false,
        success : function(data) {
          if (data.resultCode == '100') {
            alert('Saved');
            location.href = '/user/product/list';
          } else {
            alert('저장실패:' + data.message);
          }

        }
      };
      $('#join-form').ajaxForm(options);
     
    </script>
</body>
</html>
