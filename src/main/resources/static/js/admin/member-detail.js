
const $detailResults = $("body");
const $memberTable = $("#membertable");

$memberTable.on("click", "tr", function(e) {
    console.log("클릭 이벤트 들어옴");
    console.log($(e.target));
    showModal($(this).find(".numbers").text());
});

function showModal(memberId) {
    $('.modal-whole').css('display', 'flex');
    $('.modal').css('display', 'flex');

    // memberId를 사용하여 서버에 AJAX 요청
    $.ajax({
        url: `/admins/member/detail/${memberId}`, // memberId를 URL에 포함시킴
        type: 'get',
        dataType: 'json',
        success: function(data) {
            console.log("success들어옴");
            showDetail(data); // 응답 받은 멤버 정보를 모달 내부에 출력
            console.log(data);
        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
        }
    });
}

function showDetail(member) {
    let status = member.memberDTO.memberStatus;
    if(status == "NORMAL"){
        status = "일반 회원";
    } else if(status == "WITHDRAWAL"){
        status = "탈퇴 회원";
    } else {
        status = "일반 회원";
    }
    var text = "";
    console.log("showdetail");
    text += `
        <div class="modal-whole">
            <div class="modal" style="display:block;">
                <div class="modal-header">
                    <h2 class="modal-title">회원 정보</h2>
                    <span class="close" onclick="closeModal()">&times;</span>
                </div>
                <div class="modal-body">
                    <div class="member-profile">
                        <div class="profile-image">
                        <img src="${member.memberGrade.gradeTitle}">
                        </div>
                        <div class="member-info">
                            <div class="info-row">
                                <div class="info-label">회원번호:</div>
                                <div class="info-value">${member.memberDTO.id}</div>
                            </div>
                            <div class="info-row">
                                <div class="info-label">이름:</div>
                                <div class="info-value">${member.memberDTO.memberName}</div>
                            </div>
                            <div class="info-row">
                                <div class="info-label">아이디:</div>
                                <div class="info-value">${member.memberDTO.memberId}</div>
                            </div>
                            <div class="info-row">
                                <div class="info-label">핸드폰번호:</div>
                                <div class="info-value">${member.memberDTO.memberPhone}</div>
                            </div>
                            <div class="info-row">
                                <div class="info-label">이메일:</div>
                                <div class="info-value">${member.memberDTO.memberEmail}</div>
                            </div>
                            <div class="info-row">
                                <div class="info-label">회원유형:</div>
                                <div class="info-value">${status}</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `;
    $detailResults.append(text);
}
