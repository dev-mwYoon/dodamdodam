const $listResults = $("#membertable tbody");
let page = 0;

listService = (function() {
    function list(page, callback) {
        $.ajax({
            url: '/admins/member/list-content',
            type: 'get',
            data: { page: page }, // 수정: page를 객체 형태로 전달
            success: function(list) {
                if (callback) {
                    console.log("들어옴");
                    callback(list);
                }
            }
        });
    }

    return {
        list: list
    };
})();

getList(page);


$(".pages-wrapper").on("click", ".page", function(e) {
    e.preventDefault();
    console.log("page 들어옴");
    $listResults.empty();
    const targetPage = $(this).text();

    if ($(this).hasClass("arrow-left")) {
        if (page > 0) {
            page--;
        }
    } else if ($(this).hasClass("arrow-right")) {
        page++;
    } else {
        page = parseInt(targetPage) - 1;
    }
    getList(page);
});


function displayPagination(totalPages) {
    const $pagination = $(".pages-wrapper");
    $pagination.empty();

    const maxDisplayedPages = 10; // 한 번에 표시할 페이지 수
    const startPage = Math.floor(page / maxDisplayedPages) * maxDisplayedPages; // 시작 페이지 번호

    if (page > 0) {
        $pagination.append("<div class='arrow-left page'></div>");
    }

    for (let i = startPage; i < startPage + maxDisplayedPages && i < totalPages; i++) {
        if (i === page) {
            $pagination.append("<div class='page active'>" + (i + 1) + "</div>");
        } else {
            $pagination.append("<div class='page'>" + (i + 1) + "</div>");
        }
    }

    if (page < totalPages - 1) {
        $pagination.append("<div class='arrow-right page'></div>");
    }
}


function listText(list) {
    console.log("list text 들어옴");
    let memberDTOS = list.content;
    $(memberDTOS).each((i, member) => {
        let status = member.memberDTO.memberStatus;
        if(status == "NORMAL"){
            status = "일반 회원";
        } else if(status == "WITHDRAWAL"){
            status = "탈퇴 회원";
        } else {
            status = "일반 회원";
        }
        console.log("text 들어옴");
        var text = "";
        text += `
            <tr>
                <td>
                    <!-- 체크박스 -->
                    <div class="checkbox-wrapper-21">
                        <label class="control control--checkbox">
                            <input type="checkbox" id="select-all" class="substituted select-member" style="display: none;" />
                            <div class="control__indicator"></div>
                        </label>
                    </div>
                    <!-- 체크박스 -->
                </td>
                <td class="numbers">${member.id}</td>
                <td>${member.memberName}</td>
                <td>${member.memberPhone}</td>
                <td>${member.memberEmail}</td>
                <td>${member.memberStatus}</td>
                <!--  <td>{point.pointStatus}</td> -->
                <!-- <td>2000.01.01 21:05:04</td>-->
                <!-- <td><button class="show-detail" onclick="showModal()">상세보기</button></td> -->
            </tr>
        `;
        $listResults.append(text);
    });
}

function getList(page) {
    console.log("getList 들어옴");
    listService.list(page, function(list) { // 수정: page를 인자로 전달
        listText(list);
        displayPagination(list.totalPages);
    });
}
/*
$listResults.on("click", "tr", function(e) {
    console.log("클릭 이벤트 들어옴");
    console.log($(e.target));
    if (!$(e.target).hasClass("select-member")) {
        showModal();
    } else {
        closeModal();
    }
});


function showModal(){
    $('.modal-whole').css('display', 'flex');
}

function closeModal(){
    $('.modal-whole').css('display', 'none');
}*/




/*항목 삭제*/
$(document).ready(function() {
    // 삭제 버튼 클릭 시
    $('.delete-button').click(function() {
        var selectedItems = [];
        // 체크된 항목의 ID를 배열에 추가
        $('input.substituted.select-member:checked').each(function() {
            var memberId = $(this).closest('tr').find('.numbers').text();
            selectedItems.push(parseInt(memberId));
        });

        // 선택된 항목이 없는 경우 경고창을 표시하고 함수를 종료
        if (selectedItems.length === 0) {
            alert('탈퇴할 회원을 선택해주세요.');
            return;
        }
        $('#delete-modal').show(); //삭제 모달창 열기
        $('#confirm-btn').click(function() { //모달창의 확인 버튼 눌렀을 경우 데이터 변경
            $.ajax({
                url: '/admins/member/withdraw',
                type: 'PATCH',
                contentType: 'application/json',
                data: JSON.stringify(selectedItems),
                success: function (response) {
                    // alert(response); // 서버로부터의 응답 메시지를 알림으로 표시(모달로 바꾸기)
                    location.reload() //변경완료 후 새로고침
                },
                error: function (xhr, status, error) {
                    alert('오류가 발생했습니다. 다시 시도해주세요.');
                    console.log(error);
                }
            });
        });
    });
});