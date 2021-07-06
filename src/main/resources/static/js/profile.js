// (1) 유저 프로파일 페이지 구독하기, 구독취소
function toggleSubscribe(fromUserId, toUserId, obj) {
    if ($(obj).text() === "언팔로우") {
        $.ajax({
            type: "delete",
            url: "/api/follow/" + fromUserId + "/" + toUserId,
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
            url: "/api/follow/" + fromUserId + "/" + toUserId,
            dataType: "text"
        }).done(res => {
            $(obj).text("언팔로우");
            $(obj).toggleClass("blue");
        }).fail(error => {
            console.log("팔로우 실패", error);
        });
    }
}
// (4) 사용자 정보 메뉴 열기 닫기
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
    $(".modal-subscribe").css("display", "none");
    location.reload();
}
