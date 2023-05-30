const $listResults = $("#banner-table tbody");
const PAGE_AMOUNT = 10;
const SEARCH_URL = "/parentsYard/list/show";
const searchVariable = {
    memberPhone: null,
    memberName: null
};

let page = 0;


function getList() {
    console.log("ajax 들어옴");
    let url = "/admins/banner/list/" + page;
    // 검색 조건이 비어있을 때에만 searchVariable 객체를 전송.
    if (searchVariable.memberPhone || searchVariable.memberName) {
        $.ajax({
            url: url,
            data: searchVariable,
            success: function(data) {
                console.log(data);
                if (data.content.length > 0) {
                    showList(data);
                    displayPagination(data.totalPages);
                } else { //검색 결과가 없을 때
                    $listResults.append("<tr><td colspan='7'>검색 결과가 없습니다.</td></tr>");
                }
            }
        });
    } else {
        // 검색 조건이 없을 때
        $listResults.empty(); // 이전 검색 결과를 지우기 위해 빈 내용으로 초기화
        $.ajax({
            url: url,
            success: function(data) {
                console.log(data);
                showList(data);
                displayPagination(data.totalPages);

                if (data.content.length === 0) {
                    $listResults.append("<tr><td colspan='7'>검색 결과가 없습니다.</td></tr>");
                }
            }
        });
    }

}


// 검색 기능 구현
$(".search__searchbox__button").on("click", function (e) {
    e.preventDefault();
    $listResults.empty();
    console.log("검색 들어옴");
    let $search = $("#searchbox").val(); //input 입력값
    searchVariable.memberPhone = $search;
    searchVariable.memberName = $search;

    page = 0; // 페이지 번호를 0으로 초기화
    getList(); // 전체 목록 가져오기
});

// 페이지 클릭
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

    getList();
});


// 페이지 출력
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

//   배너 목록
function showList(data) {
    console.log("showList 들어옴");
    let banners = data.content;
    banners.forEach(banner => {
        console.log("text 들어옴");
        var text = "";
        text += `
       <tr>
        <td>
          <div class="checkbox-wrapper-21">
            <label class="control control--checkbox">
              <input type="checkbox" id="select-all" class="substituted select-member" style="display: none;" />
              <div class="control__indicator"></div>
            </label>
          </div>
        </td>
        <td class="numbers" onclick="redirectToDetail(${banner.id})">${banner.id}</td>
        <td onclick="redirectToDetail(${banner.id})">${banner.memberDTO.memberName}</td>
        <td onclick="redirectToDetail(${banner.id})">${banner.memberDTO.memberPhone}</td>
        <td onclick="redirectToDetail(${banner.id})">${formatDate(new Date(banner.bannerRegisterDate))}</td>
        <td onclick="redirectToDetail(${banner.id})">${formatDate(new Date(banner.createdDate))}</td>
        <td onclick="redirectToDetail(${banner.id})">${banner.bannerStatus}</td>
      </tr>
    `;
        $listResults.append(text);

    });
}

getList();


// 상세 페이지로 이동하는 함수
function redirectToDetail(id) {
    window.location.href = "/admins/banner/detail/" + id;
}



/*
/!*항목 삭제*!/
$(document).ready(function() {
    // 삭제 버튼 클릭 시
    $('.delete-button').click(function() {
        var selectedItems = [];
        // 체크된 항목의 ID를 배열에 추가
        $('input.substituted.select-member:checked').each(function() {
            var inquiryId = $(this).closest('tr').find('.numbers').text();
            selectedItems.push(parseInt(inquiryId));
        });

        // 선택된 항목이 없는 경우 경고창을 표시하고 함수를 종료
        if (selectedItems.length === 0) {
            alert('삭제할 항목을 선택해주세요.');
            return;
        }
        $('#delete-modal').show(); //삭제 모달창 열기
        $('#confirm-btn').click(function() {//모달창의 확인 버튼 눌렀을 경우 데이터 삭제
            $.ajax({
                url: '/admins/banner/delete',
                type: 'DELETE',
                contentType: 'application/json',
                data: JSON.stringify(selectedItems),
                success: function (response) {
                    // alert(response); // 서버로부터의 응답 메시지를 알림으로 표시(모달로 바꾸기)
                    location.reload() //삭제완료 후 새로고침
                },
                error: function (xhr, status, error) {
                    alert('오류가 발생했습니다. 다시 시도해주세요.');
                    console.log(error);
                }
            });
        });
    });
});*/



/*상태 변경 */
$(document).ready(function() {
    // 수락 버튼 클릭 시
    $('.delete-button').click(function() {
        var selectedItems = [];
        // 체크된 항목의 ID를 배열에 추가
        $('input.substituted.select-member:checked').each(function() {
            var bannerId = $(this).closest('tr').find('.numbers').text();
            console.log(bannerId);
            selectedItems.push(parseInt(bannerId));
        });

        // 선택된 항목이 없는 경우 경고창을 표시하고 함수를 종료
        if (selectedItems.length === 0) {
            // alert('삭제할 항목을 선택해주세요.');
            return;
        }
        $('#delete-modal').show(); //삭제 모달창 열기
        $('#confirm-btn').click(function() {//모달창의 확인 버튼 눌렀을 경우 데이터 삭제
            $.ajax({
                url: '/admins/banner/complete',
                type: 'PATCH',
                contentType: 'application/json',
                data: JSON.stringify(selectedItems),
                success: function (response) {
                    console.log("바꿈");
                    // alert(response); // 서버로부터의 응답 메시지를 알림으로 표시(모달로 바꾸기)
                    location.reload() //삭제완료 후 새로고침
                },
                error: function (xhr, status, error) {
                    // alert('오류가 발생했습니다. 다시 시도해주세요.');
                    console.log(error);
                }
            });
        });
    });
});


