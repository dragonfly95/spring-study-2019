<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

    <!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script>

function getList() {
    $.ajax({
        url: "./board",
        data: "",
        success: function (response) {
            var data = response.list;

            var str = '';
            $.each(data, function (indexInArray, valueOfElement) { 
                str +='<tr class="detailno">';
                str +='<td><input type="button" name="btnDelete" value="삭제" data-id="'+ valueOfElement.id +'"/></td>';
                str +='<td data-id='+ valueOfElement.id +' style="cursor:pointer;">'+ valueOfElement.title +'</td>';
                str +='<td>'+ valueOfElement.content +'</td>';
                str +='<td>'+ valueOfElement.regdate +'</td>';
                str +='</tr>';
            });
            $('tbody.aa').html(str);

            $('[name="id"]').val("");
            $('[name="title"]').val("");
            $('[name="content"]').val("");
        }
    });
}


    $(document).ready(function () {

        getList();

        $('#execute').click(function(){  // 목록 불러오기 
            getList();        
        });  // execute 
        


        // 등록
        $('#btnSave1').click(function () { 

            var id = $('[name="id"]').val(); 

            if(id == "") {
                // 등록
                $.ajax({
                    type: "POST",
                    url: "./board",
                    data: JSON.stringify({
                        "title":$('[name="title"]').val(),
                        "content":$('[name="content"]').val()}),
                    dataType: "json",
                    contentType: "application/json",
                    success: function (response) {
                        if (response.message == 'ok') {
                            alert('저장되었습니다.');
                        }
                        getList();
                    }
                });
            } else {
                // 수정 
                $.ajax({
                    type: "PUT",
                    url: "./board2/" + id,
                    data: JSON.stringify({
                        "id": $('[name="id"]').val(),
                        "title":$('[name="title"]').val(),
                        "content":$('[name="content"]').val()}),
                    dataType: "json",
                    contentType: "application/json",
                    success: function (response) {
                        // alert(response.message);
                        if(response.message == 'ok') {
                            alert('저장되었습니다.');
                        }
                        getList();
                    }
                });
            }

        });

        // 1건 불러오기 
        $(document).on('click', '.detailno', function(e){
            var id = e.target.getAttribute('data-id');
            if(id == "") return;
            $.ajax({
                type: "get",
                url: "./board2/"+id,
                dataType: "json",
                success: function (response) {
                    $('[name="id"]').val(response.id);
                    $('[name="title"]').val(response.title);
                    $('[name="content"]').val(response.content);
                }
            });
        });



        // 삭제 
        $(document).on('click','[name="btnDelete"]', function(e) {
            var id = e.target.getAttribute('data-id');
            console.log(id);

            $.ajax({
                type: "delete",
                url: "./board2/"+id,
                dataType: "json",
                success: function (response) {
                    console.log(response);
                    if(response.message == 'ok') {
                        alert('삭제되었습니다.');
                        getList();
                    }
                }
            });
        })
    });

</script>


<table>
    <tr>
        <td valign="top">
            <input type="hidden" name="id" value=""/>
            제목: <br>
            <input type = "text" name="title" value=""><br>
            내용: <br>
            <textarea name="content" id="" cols="40" rows="10"></textarea><br>
            <p style="text-align: center;"/>
            <input type="button" value="저장" id="btnSave1">        
            <input type="button" value="취소"> 
        </td>
        <td valign="top">
            <table border="1">
                <thead>
                    <tr>
                        <td>button</td>
                        <td>제목</td>
                        <td>내용</td>
                        <td>등록일자</td>
                    </tr>
                </thead>
                <tbody class="aa"></tbody>
            </table>
        </td>
    </tr>

</table>

</body>
</html>