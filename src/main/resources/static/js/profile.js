// (1) 유저 프로파일 페이지 구독하기, 구독취소
function toggleSubscribe(fromUserId, toUserId, obj) {
    if ($(obj).text() === "언팔로우") {
        $.ajax({
            type: "delete",
            url: "/api/follow/" + fromUserId + "/" + toUserId,
            dataType: "json"
        }).done(res => {
            let item = getSubscribeState(toUserId);
            $("#subscribeModalList").append(item);
        }).fail(error => {
            console.log("구독취소실패", error);
        });
    } else {
        $.ajax({
            type: "post",
            url: "/api/follow/" + fromUserId + "/" + toUserId,
            dataType: "json"
        }).done(res => {
            let item = getSubscribeState(u);
            $("#subscribeModalList").append(item);
        }).fail(error => {
            console.log("구독하기실패", error);
        });
    }
}

// (2) 구독자 정보  모달 보기
function subscribeInfoModalOpen(pageUserId) {
    $(".modal-subscribe").css("display", "flex");

    $.ajax({
        url: `/api/user/${pageUserId}/subscribe`,
        dataType: "json"
    }).done(res => {
        console.log(res.data);

        res.data.forEach((u)=>{
            let item = getSubscribeState(u);
            $("#subscribeModalList").append(item);
        });
    }).fail(error => {
        console.log("구독정보 불러오기 오류", error);
    });
}

function getSubscribeState(u) {
    let item = ``;

    if(u.subscribeState){ // 구독한 상태
        item += `<button class="cta blue" onclick="toggleSubscribe(${u.id}, this)">구독취소</button>`;
    }else{ // 구독안한 상태
            item += `<button class="cta" onclick="toggleSubscribe(${u.id}, this)">구독하기</button>`;
    }
    item += `
	</div>
</div>`;

    console.log(item);
    return item;
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
