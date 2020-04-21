var main = {
    init : function(){
        var _this=this;//main을 변수로 담음
        $('#btn-save').on('click', function(){//버튼을 누르면
            _this.save();//main.save를 호출
        });
        $('#btn-update').on('click',function(){
            _this.update();
        });
        $('#btn-delete').on('click',function(){
            _this.delete();
        });
    },
    save : function(){
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url : '/api/v1/posts',
            dataType : 'json',
            contentType:'application/json; charset=utf-8',
            data:JSON.stringify(data)//제목, 작성자, 내용이 담긴 data 변수

        }).done(function(){
            alert('글이 등록되었습니다.')
            window.location.href='/';//성공시 메인페이지로
        }).fail(function (error){
            alert(JSON.stringify(error));//실패시 알림
        });
    },
    update : function(){
        var data = {
                    title: $('#title').val(),
                    content: $('#content').val()
        };

        var id = $('#id').val();
        $.ajax({
            type: 'PUT',
            url : '/api/v1/posts/'+id,//posts 뒤에 슬래쉬 필수!!!
            dataType : 'json',
            contentType:'application/json; charset=utf-8',
            data:JSON.stringify(data)
        }).done(function(){
                      alert('글이 수정되었습니다.')
                      window.location.href='/';//성공시 메인페이지로
                  }).fail(function (error){
                      alert(JSON.stringify(error));//실패시 알림
                  });
    },
    delete : function(){
        var id = $('#id').val();
        $.ajax({
            type: 'DELETE',
            url : '/api/v1/posts/'+id,//posts 뒤에 슬래쉬 필수!!!
            dataType : 'json',
            contentType:'application/json; charset=utf-8',
        }).done(function(){
                      alert('글이 삭제되었습니다.')
                      window.location.href='/';//성공시 메인페이지로
                  }).fail(function (error){
                      alert(JSON.stringify(error));//실패시 알림
                  });
    }
};

main.init();