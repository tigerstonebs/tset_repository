$(document).ready(function(){
	
	var token;
	if(document.cookie.includes("accesstoken")) {
		token = document.cookie.split('token=')[1];	
	}
	
	$.ajax({
		beforeSend: function(xhr){
			xhr.setRequestHeader('accesstoken', token);
        },
        url: "/post"
    }).then(function(data) {
		console.log(data[1].content);
    	$.each(data[1].content, function(index, e) {
    		$('#posts').append(
    				'<div class="card mb-4"> <div class="card-body"> <h2 class="card-title">' + e.title 
    				+ '</h2> <p class="card-text">' + e.content 
    				+ '</p> <a href="/post/detail/' + e.id 
    				+ '" class="btn btn-primary">Read More &rarr;</a> </div> ' 
    				+ '<div class="card-footer text-muted"> Posted on ' + e.createdAt.split('T')[0]
    				+ ' by ' + e.user.username + getFollowInfo(e.user.id, data[0], data[2])
    				+ '<br> <div id="viewed2' + e.id + '">' + getPostViewCount(e.id)
    				+ '</div> </div> </div>');

    	});
    	
    	$('#posts').append(
                '<div id="addlistbtnarea">' 
                +'<div class="btns">'
                +'<button id="addlistbtn" onclick="javascript:moreList(this);" value="0" class="btn btn-primary btn-block">더보기</button>'
                +'</div>'
                +'</div><br>'
    	);
    	
    	if(data[1].last == true){
            // 더 불러올 포스트 목록이 없는 경우 버튼을 disabled 지정하면 불투명해지면서 클릭불가능해짐 (부트스트랩)
            $('#addlistbtn').attr("class","btn btn-primary btn-block disabled");
            $('#addlistbtn').text("더 불러올 목록이 없습니다");
    	}
    }, function(err) {
    	console.log(err.responseJSON);
    });
	
	
	
	$.ajax({
		beforeSend: function(xhr){
			xhr.setRequestHeader('accesstoken', token);
        },
        url: "/post/feed"
    }).then(function(data) {
    	$.each(data[1], function(index, e) {
    		$('#myfeed').append(
    				'<div class="card mb-4"> <div class="card-body"> <h2 class="card-title">' + e.title 
    				+ '</h2> <p class="card-text">' + e.content 
    				+ '</p> <a href="/post/detail/' + e.id 
    				+ '" class="btn btn-primary">Read More &rarr;</a> </div> ' 
    				+ '<div class="card-footer text-muted"> Posted on ' + e.createdAt.split('T')[0]
    				+ ' by ' + e.user.username + getFollowInfo(e.user.id, data[0], data[2])
    				+ '<br> <div id="viewed' + e.id + '">' + getPostViewCountInFeed(e.id)
    				+ '</div> </div>');
    	});
    }, function(err) {
    	console.log(err.responseJSON);
    });
	
	$('#save_post_btn').click(function(){
		var title = $('#create_title_text').val();
		var content = $('#create_content_text').val();
		
		console.log(title);
		console.log(content);
		
		var param = {
			title: title,
			content: content
		}
		
		$.ajax({
			beforeSend: function(xhr){
				xhr.setRequestHeader('accesstoken', token);
	        },
	        url: "/post",
	        method: "POST",
	        dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(param)
	    }).then(function(data) {
	    	window.location.href = '/';
	    }, function(err) {
	    	alert(err.responseJSON);
	    });
	});
	
	$('#header_logout_btn').click(function(){
		document.cookie = "accesstoken=; expires=Thu, 01 Jan 1970 00:00:01 GMT;";
		window.location.href = '/logout';
	});
	
	$('body').on('click', '.follow', function($event) {
		console.log($(event.target).html());
		console.log($(this).attr('value'));
		var userId = $(this).attr('value');
		
		var param = {
			followee_id: userId
		}
		
		$.ajax({
			beforeSend: function(xhr){
				xhr.setRequestHeader('accesstoken', token);
	        },
	        url: "/follow",
	        method: "POST",
	        dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(param)
	    }).then(function(data) {
	    	window.location.reload();
	    }, function(err) {
	    	alert(err.responseJSON);
	    });
	});
	
	$('body').on('click', '.unfollow', function() {
		console.log("unfollow clicked!!!");
		console.log($(this).attr('value'));
		var userId = $(this).attr('value');
		
		var param = {
			followee_id: userId
		}
		
		$.ajax({
			beforeSend: function(xhr){
				xhr.setRequestHeader('accesstoken', token);
	        },
	        url: "/follow",
	        method: "DELETE",
	        dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(param)
	    }).then(function(data) {
	    	window.location.reload();
	    }, function(err) {
	    	alert(err.responseJSON);
	    });
	});
});

function moreList(btn){
    console.log("moreList에서 받은 매개변수 : "+btn);
    console.log("moreList 매개변수로 들어온 (버튼객체)의 value = 페이징 "+btn.value);
    $.ajax({
        url : "/getmorelist",
        type : "get",
        cache : false,
        dataType: 'json',
        data : {numberOfRequests : btn.value },
        success : function(data){
            console.log(data);
            console.log(data[1].last);
            
            $.each(data[1].content, function(index, e) {
            	console.log("title = "+e.title);
        		$('#posts').append(
        				'<div class="card mb-4"> <div class="card-body"> <h2 class="card-title">' + e.title 
        				+ '</h2> <p class="card-text">' + e.content 
        				+ '</p> <a href="/post/detail/' + e.id 
        				+ '" class="btn btn-primary">Read More &rarr;</a> </div> ' 
        				+ '<div class="card-footer text-muted"> Posted on ' + e.createdAt.split('T')[0]
        				+ ' by ' + e.user.username + getFollowInfo(e.user.id, data[0], data[2])
        				+ '<br> <div id="viewed2' + e.id + '">' + getPostViewCount(e.id)
        				+ '</div> </div>');
        	});
        
            var pagingNum = data[1].number;
            $('#addlistbtnarea').remove();
            $('#posts').append(
            		'<div id="addlistbtnarea">' 
                    +'<div class="btns">'
                    +'<button id="addlistbtn" onclick="javascript:moreList(this);" value="'+pagingNum+'" class="btn btn-primary btn-block">더보기</button>'
                    +'</div>'
                    +'</div><br>'
            );
            
            if(data[1].last == true){
                $('#addlistbtn').attr("class","btn btn-primary btn-block disabled");
                $('#addlistbtn').text("더 불러올 목록이 없습니다");
            }
        }, 
        error : function(){
           alert('ajax 통신 실패');
        }
   
    });
}

function getFollowInfo(user, owner, followee) {
	if(user == owner) {
		return '';
	} else {
		if(followee.indexOf(user) == -1){
			return ' <span class="follow" value="' + user + '" style="color:blue; cursor: pointer;"> Follow </span>';
		} else {
			return ' <span class="unfollow" value="' + user + '" style="color:blue; cursor: pointer;"> Unfollow </span>';
		}
	}
}

function getPostViewCountInFeed(postId){
	var count = 0;
	$.ajax({
        url: "/post/count/" + postId
    }).then(function(data) {
       count = data;
       $('#viewed'+postId).text("조회수 : " + count);
    }, function(err) {
    	console.log(err.responseJSON);
    });
}

function getPostViewCount(postId){
	var count = 0;
	$.ajax({
        url: "/post/count/" + postId
    }).then(function(data) {
       count = data;
       $('#viewed2'+postId).text("조회수 : " + count);
    }, function(err) {
    	console.log(err.responseJSON);
    });
	return "조회수 : 조회수를 불러올 수 없습니다.";
}
