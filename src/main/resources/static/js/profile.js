
function toggleSubscribe(toUserId, obj) {
    if ($(obj).text() === "언팔로우") {
        $.ajax({
            type: "delete",
            url: "/api/follow/" + toUserId,
            dataType: "text"
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
            dataType: "text"
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
    console.log("ee");

    $.ajax({
        url: `/api/follow/${profileId}/follower`,
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
        url: `/api/follow/${profileId}/following`,
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
		<img src="/profile_imgs/${follow.profileImgUrl}" />
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
            <span>스토리</span> `;
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
	    <div class="post-info">
	        <div class="text">
	            <span>${postInfoDto.text}</span>
            </div>
	        <div class="tag">
	            <span>${postInfoDto.tag}</span>
            </div>
        </div>
        <div class="subscribe__img">
            <span>${postInfoDto.createdate.toLocaleString()}</span>
        </div>
        <div class="comment-info"></div>
	        <div class="comment">
		        <div class="comment_input">
                    <input id="input-comment-post" class="input-comment-post" type="text" placeholder="댓글 달기..." >
                    <button type="submit" class="submit-comment" disabled>게시</button>
                </div>
	        </div>
	    </div>
    </div>`;
    return item;
}
