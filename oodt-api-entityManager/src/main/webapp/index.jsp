<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Document</title>
</head>
<body>
  

  <input type="file" name="file1" value="">
  <script src="https://code.jquery.com/jquery-3.1.0.js"></script>
  <script>
  $(document).ready(function () {
    
    $('[name="file1"]').on('change', function(){
      var formData = new FormData();
      // file1 은 받을 변수명으로 바꿔주세요.
      formData.append('file1', $(this)[0].files[0]);

      $.ajax({
        type: "post",
        enctype: 'multipart/form-data',
        url: "./uploadFile",
        data: formData,
        processData: false,
        contentType: false,
        timeout: 600000,
        success: function (response) {
          console.log(response);
        },
        error: function(error) {
          console.log(error);
        }
      });

    })
  });
  </script>
</body>
</html>