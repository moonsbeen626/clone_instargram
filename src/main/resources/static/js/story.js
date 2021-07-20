let principalId = $("#principalId").val();

// (1) 스토리 로드하기
let page = 0;

function storyLoad() {
    $.ajax({
        url: `/api/post/story?page=${page}`,
        dataType: "json"
    }).done(res => {
        res.content.forEach((post) => {
            let postItem = getStoryItem(post);
            $("#feeds").append(postItem);
        });
    }).fail(error => {
        console.log("오류", error);
    });
}

storyLoad();

function getStoryItem(post) {
        let item = `
        <article>
            <header>
                <div class="profile-of-article">
                    <a href="/user/profile?id=${post.user.id}"><img class="img-profile pic" src="/profile_imgs/${post.user.profileImgUrl}" onerror="this.src='/img/default_profile.jpg'""></a>
                    <span class="userID main-id point-span" >${post.user.name}</span>
                </div>
            </header>
            <div class="main-image">
                <img src="/upload/${post.postImgUrl}" class="mainPic">
            </div>
            <div class="icons-react">
                <div class="icons-left">`;
            if(post.likesState) {
                item += `<i class="fas fa-heart active" id="storyLikeIcon-${post.id}" onclick="toggleLike(${post.id})">${post.likesCount}</i>`;
            } else {
                item += `<i class="far fa-heart" id="storyLikeIcon-${post.id}" onclick="toggleLike(${post.id})">${post.likesCount}</i>`;
            }
            item += `
                </div>
            </div>
            <div class="reaction">
                <div class="text">
	                <span>${post.text}</span>
                </div>
	            <div class="tag">
	                <span>${post.tag}</span>
                </div>
                <div class="subscribe__img">
                    <span>${post.createDate.toLocaleString()}</span>
                </div>
            </div>
	        <div class="comment">
		        <div class="comment_input">
                    <input id="input-comment-post-${post.id}" class="input-comment-post" type="text" placeholder="댓글 달기..." >
                    <button type="submit" class="submit-comment" disabled>게시</button>
                </div>
	        </div>
        </article>`;
        return item;
}

// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {
    //console.log("윈도우 scrollTop", $(window).scrollTop());
    //console.log("문서의 높이", $(document).height());
    //console.log("윈도우 높이", $(window).height());

    let checkNum = $(window).scrollTop() - ( $(document).height() - $(window).height() );
    //console.log(checkNum);

    if(checkNum < 1 && checkNum > -1){
        page++;
        storyLoad();
    }
});
//좋아요
function toggleLike(postId) {
    let likeIcon = $(`#storyLikeIcon-${postId}`);

    if (likeIcon.hasClass("far")) { // 좋아요 하겠다
        $.ajax({
            type: "post",
            url: `/api/post/${postId}/likes`,
            dataType: "text"
        }).done(res=>{
            let likeCountStr = $(`#storyLikeIcon-${postId}`).text();
            let likeCount = Number(likeCountStr) + 1;
            $(`#storyLikeIcon-${postId}`).text(likeCount);

            likeIcon.addClass("fas");
            likeIcon.addClass("active");
            likeIcon.removeClass("far");
        }).fail(error=>{
            console.log("오류", error);
        });
    } else { // 좋아요취소 하겠다
        $.ajax({
            type: "delete",
            url: `/api/post/${postId}/unLikes`,
            dataType: "text"
        }).done(res=>{
            let likeCountStr = $(`#storyLikeIcon-${postId}`).text();
            let likeCount = Number(likeCountStr) - 1;
            $(`#storyLikeIcon-${postId}`).text(likeCount);

            likeIcon.removeClass("fas");
            likeIcon.removeClass("active");
            likeIcon.addClass("far");
        }).fail(error=>{
            console.log("오류", error);
        });
    }
}