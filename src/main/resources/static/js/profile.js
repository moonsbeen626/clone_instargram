function toggleSubscribe(toUserId, obj) {
    if ($(obj).text() === "언팔로우") {
        $.ajax({
            type: "delete",
            url: "/api/follow/" + toUserId,
        }).done(res => {
            $(obj).text("팔로우");
            $(obj).toggleClass("blue");
        }).fail(error => {
            console.log("언팔로우 실패", error);
        });
    } else {
        $.ajax({
            type: "post",
            url: "/api/follow/" + toUserId,
        }).done(res => {
            $(obj).text("언팔로우");
            $(obj).toggleClass("blue");
        }).fail(error => {
            console.log("팔로우 실패", error);
        });
    }
}

function followerInfoModalOpen(profileId) {
    $(".modal-follower").css("display", "flex");

    $.ajax({
        url: `/api/user/${profileId}/follower`,
        dataType: "json"
    }).done(res => {
        res.forEach((follow) => {
            let item = getfollowModalItem(follow);
            $("#followerModalList").append(item);
        });
    }).fail(error => {
        console.log("구독정보 불러오기 오류", error);
    });
}
function followingInfoModalOpen(profileId) {
    $(".modal-following").css("display", "flex");

    $.ajax({
        url: `/api/user/${profileId}/following`,
        dataType: "json"
    }).done(res => {
        res.forEach((follow) => {
            let item = getfollowModalItem(follow);
            $("#followingModalList").append(item);
        });
    }).fail(error => {
        console.log("구독정보 불러오기 오류", error);
    });
}

function getfollowModalItem(follow) {
    let item = `<div class="subscribe__item" id="subscribeModalItem-${follow.id}">
	<div class="subscribe__img">
		<a href="/user/profile?id=${follow.id}" ><img src="/profile_imgs/${follow.profileImgUrl}" onerror="this.src='/img/default_profile.jpg';" /></a>
	</div>
	<div class="subscribe__text">
		<h2>${follow.name}</h2>
	</div>
	<div class="subscribe__btn">`;
    if(!follow.loginUser){
        if(follow.followState){
            item += `<button class="cta-follow blue" onclick="toggleSubscribe(${follow.id}, this)">언팔로우</button>`;
        }else{
            item += `<button class="cta-follow" onclick="toggleSubscribe(${follow.id}, this)">팔로우</button>`;
        }
    }
    item += `
	</div>
</div>`;
    return item;
}

function popup(obj) {
    $(obj).css("display", "flex");
}

function closePopup(obj) {
    $(obj).css("display", "none");
}

// 사용자 정보(회원정보, 로그아웃, 닫기) 모달
function modalInfo() {
    $(".modal-info").css("display", "none");
}

// (6) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달
function modalImage() {
    $(".modal-image").css("display", "none");
}

// (7) 구독자 정보 모달 닫기
function modalClose() {
    $(".modal-follower").css("display", "none");
    location.reload();
}
function modalClose() {
    $(".modal-following").css("display", "none");
    location.reload();
}

//포스트
function postPopup(postId, obj) {
    $(obj).css("display", "flex");

    $.ajax({
        url: "/api/post/" + postId,
        dataType: "json"
    }).done(res => {
        let item = getPostModalInfo(res);
        $("#postInfoModal").append(item);
    }).fail(error => {
        console.log("post 정보 불러오기 오류", error);
    });
}

function getPostModalInfo(postInfoDto) {
    let item = `
    <div class="subscribe-header">
            <a href="/user/profile?id=${postInfoDto.postUploader.id}"><img class="post-img-profile pic" src="/profile_imgs/${postInfoDto.postUploader.profileImgUrl}" onerror="this.src='/img/default_profile.jpg'""></a>  
            <span>${postInfoDto.postUploader.name}</span> `;
    item += `<button class="exit" onclick="modalClose()"><i class="fas fa-times"></i></button>`
    if(postInfoDto.uploader) {
        item += `<button class="edit" onclick="location.href='/post/update/${postInfoDto.id}'"><i class="far fa-edit"></i></button>`
    }
    item += `
    </div>
    <div class="post-box">
	    <div class="subscribe__img">
		    <img src="/upload/${postInfoDto.postImgUrl}" />
	    </div>
	    <div class="post-div">
	    <div class="post-info">
	        <div class="text"> `;
            if(postInfoDto.likeState) {
                item += `<i class="fas fa-heart active" id="storyLikeIcon" onclick="toggleLike(${postInfoDto.id})">${postInfoDto.likesCount}</i>`;
            } else {
                item += `<i class="far fa-heart" id="storyLikeIcon" onclick="toggleLike(${postInfoDto.id})">${postInfoDto.likesCount}</i>`;
            }
            item += `
            </div>
	        <div class="text">
	            <span>${postInfoDto.text}</span>
            </div>
	        <div class="tag">`;
                    let arr = postInfoDto.tag.split(',');

                    for(let i = 0; i < arr.length; i++) {
                        item += `<span class="tag-span" onclick="location.href='/post/search?tag=${arr[i]}'">#${arr[i]} </span>`;
                    }
                    item += `
            </div>
        </div>
        <div class="subscribe__img">
            <span>${postInfoDto.createdate.toLocaleString()}</span>
        </div>
        <div class="comment-section" >
                <ul class="comments" id="storyCommentList-${postInfoDto.id}">`;
                    postInfoDto.commentList.forEach((comment)=>{
                    item += `<li id="storyCommentItem-${comment.id}">
                               <span><span class="point-span userID">${comment.user.name}</span>${comment.text}</span>`;
                                if(principalId == comment.user.id) {
                                    item += `<button onclick="deleteComment(${comment.id})" class="delete-comment-btn">
                                                <i class="fas fa-times"></i>
                                            </button>`;
                                }
                    item += `</li>`});
                item += `
                </ul>
            </div>
            </div>
            <div class="comment_input">
                    <input id="storyCommentInput-${postInfoDto.id}" class="input-comment-post" type="text" placeholder="댓글 달기..." >
                    <button type="button" class="submit-comment" onClick="addComment(${postInfoDto.id})">게시</button>
            </div>
        </div>
    </div>`;
    return item;
}
function toggleLike(postId) {
    let likeIcon = $("#storyLikeIcon");

    if (likeIcon.hasClass("far")) { // 좋아요 하겠다
        $.ajax({
            type: "post",
            url: `/api/post/${postId}/likes`,
            dataType: "text"
        }).done(res=>{
            let likeCountStr = $("#storyLikeIcon").text();
            let likeCount = Number(likeCountStr) + 1;
            $("#storyLikeIcon").text(likeCount);

            likeIcon.addClass("fas");
            likeIcon.addClass("active");
            likeIcon.removeClass("far");
        }).fail(error=>{
            console.log("오류", error);
        });
    } else { // 좋아요취소 하겠다
        $.ajax({
            type: "delete",
            url: `/api/post/${postId}/likes`,
            dataType: "text"
        }).done(res=>{
            let likeCountStr = $("#storyLikeIcon").text();
            let likeCount = Number(likeCountStr) - 1;
            $("#storyLikeIcon").text(likeCount);

            likeIcon.removeClass("fas");
            likeIcon.removeClass("active");
            likeIcon.addClass("far");
        }).fail(error=>{
            console.log("오류", error);
        });
    }
}

//댓글 추가
function addComment(postId) {
    let commentInput = $(`#storyCommentInput-${postId}`);
    let commentList = $(`#storyCommentList-${postId}`);

    let data = {
        postId: postId,
        text: commentInput.val()
    }

    if (data.text === "") {
        alert("댓글을 작성해주세요!");
        return;
    }

    $.ajax({
        type: "post",
        url: "/api/comment",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        dataType: "json"
    }).done(res=>{
        console.log("성공", res);
        let comment = res;
        let content = `
		    <li id="storyCommentItem-${comment.id}">
                 <span><span class="point-span userID">${comment.user.name}</span>${comment.text}</span>
                 <button onclick="deleteComment(${comment.id})" class="delete-comment-btn">
                    <i class="fas fa-times"></i>
                 </button>
            </li>`;
        commentList.append(content);
    }).fail(error=>{
        console.log("오류", error);
        alert(error.responseText);
    });

    commentInput.val(""); // 인풋 필드를 깨끗하게 비워준다.
}

function deleteComment(commentId) {
    $.ajax({
        type: "delete",
        url: `/api/comment/${commentId}`
    }).done(res=>{
        console.log("성공", res);
        $(`#storyCommentItem-${commentId}`).remove();
    }).fail(error=>{
        console.log("오류", error);
    });
}
