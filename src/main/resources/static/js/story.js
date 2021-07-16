function toggleLike(imageId) {
    let likeIcon = $(`#storyLikeIcon-${imageId}`);

    if (likeIcon.hasClass("far")) { // 좋아요 하겠다

        $.ajax({
            type: "post",
            url: `/api/post/${imageId}/likes`,
            dataType: "json"
        }).done(res=>{

            let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
            let likeCount = Number(likeCountStr) + 1;
            $(`#storyLikeCount-${imageId}`).text(likeCount);

            likeIcon.addClass("fas");
            likeIcon.addClass("active");
            likeIcon.removeClass("far");
        }).fail(error=>{
            console.log("오류", error);
        });



    } else { // 좋아요취소 하겠다

        $.ajax({
            type: "delete",
            url: `/api/post/${imageId}/likes`,
            dataType: "json"
        }).done(res=>{

            let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
            let likeCount = Number(likeCountStr) - 1;
            $(`#storyLikeCount-${imageId}`).text(likeCount);

            likeIcon.removeClass("fas");
            likeIcon.removeClass("active");
            likeIcon.addClass("far");
        }).fail(error=>{
            console.log("오류", error);
        });
    }
}