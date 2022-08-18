function imageChoose(obj) {
    let f = obj.files[0];

    if (!f.type.match("image.*")) {
        alert("이미지를 등록해야 합니다.");
        return;
    }

    let reader = new FileReader();
    reader.onload = (e) => {
        $("#imageUploadPreview").attr("src", e.target.result);
    }
    reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
}
