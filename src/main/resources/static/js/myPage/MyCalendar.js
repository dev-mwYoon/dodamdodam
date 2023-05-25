/* mypage-profie.html */

const MyCalendar = tui.Calendar;
const container = document.getElementById('calendar');
const options = {
  defaultView: 'month',
  isReadOnly: true,

  gridSelection: {
    enableDblClick: false,
    enableClick: false,
  },
  month: {
    dayNames: ['일', '월', '화', '수', '목', '금', '토'],
    isAlways6Weeks: false,
    visibleEventCount: 4,
  },
  timezone: {
    zones: [
      {
        timezoneName: 'Asia/Seoul',
        displayLabel: 'Seoul',
      }
    ],
  },

  calendars: [
    {
      id: '1',
      name: '과학',
      backgroundColor: '#2e51ef',
    },
    {
      id: '2',
      name: '스포츠',
      backgroundColor: '#9f8673',
    },
    {
      id: '3',
      name: '전통',
      backgroundColor: '#705f53',
    },
    {
      id: '4',
      name: '박물관',
      backgroundColor: '#74aaf0',
    },
    {
      id: '5',
      name: '농촌',
      backgroundColor: '#90949c',
    },
    {
      id: '6',
      name: '미술관',
      backgroundColor: '#9b72e7',
    },
    {
      id: '7',
      name: '공방',
      backgroundColor: '#f0bb9d',
    },
    {
      id: '8',
      name: '기타',
      backgroundColor: '#7a87f5',
    }
  ],
};


const calendar = new MyCalendar(container, options);

let currentDate = calendar.getDate();

$(".year").text(currentDate.getFullYear()+ "년");
$(".month").text(currentDate.getMonth() + 1 + "월");

//이전 월 클릭
$("#calender-prev").click(() => {
  currentDate = calendar.getDate();

  let prevDate = new Date(currentDate.getFullYear(), currentDate.getMonth() - 1, 1);
  let prevYear = prevDate.getFullYear();
  let prevMonthIndex = prevDate.getMonth();

  $(".year").text(prevYear+ "년");
  $(".month").text(prevMonthIndex + 1 + "월");
  
  calendar.prev();
});

//다음 월 클릭
$("#calender-next").click(() => {
  currentDate = calendar.getDate();
  
  let nextDate = new Date(currentDate.getFullYear(), currentDate.getMonth() + 1, 1);
  let nextMonthIndex = nextDate.getMonth();
  let nextYear = nextDate.getFullYear();

  $(".year").text(nextYear+ "년");
  $(".month").text(nextMonthIndex + 1 + "월");
  
  calendar.next();
})

$("#today").click(() => {
  $(".year").text(year+ "년");
  $(".month").text(month + "월");

  calendar.today();
});


/* 쿼리 */

/* 전체 일정을 가져오는 쿼리 : 아래 createEvents로 모든 날을 등록해야함
  그래야 달력에 현재 내가 체험학습을 등록한 일정이 점으로 표현됨

  해당 날짜에 대한 날짜 쿼리 : ajax로 해당 날짜를 누를때마다 쿼리가 나가서 해당 월의 정보를 다 가져오기
  그리고 prev 버튼이나 next 버튼을 누르면 ajax로 다시 컨트롤러를 타야함
   */

/*  */
/* 
    달력 이벤트를 담는 법 json으로 받으면 됨
    대신 모든 정보는 string으로 받아야함
    달력에 점을 넣기 위한 코드
    하루에 하나씩만 넣어줘도 됨
    id는 무조거 달라야함
    *무조건 있어야함
*/
// calendar.createEvents([
//   {
//     // id: 'event3',
//     // calendarId: 'cal1',
//     // title: 'Lunch appointment',
//     start: '2023-05-20',
//     end: '2023-05-20'
//     // start: '2023-05-06T12:00:00',
//     // end: '2023-05-06T13:00:00',
//   },
//   {
//     // id: 'event2',
//     // calendarId: 'cal1',
//     // title: 'Lunch appointment',
//     start: '2023-05-05T12:00:00',
//     end: '2023-05-05T13:00:00',
//   }
// ]);



// ajax로 그날 참여한 모집 가져오기
const getDate = () => {
  console.log("getDate 들어옴@@@@");
  $.ajax({
    type: 'GET',
    url: `/mypage/get-recruitment-dates`,
    success: function (result) {
      console.log(result);
      /* caledar에 일정 있는 날 점 찍기*/
      for (var i = 0; i < result.length; i++){
        calendar.createEvents([
          {
            start: result[i],
            end: result[i]
          }
        ]);
      }
    },
    error: function (error) {
      console.log('Error fetching data:', error);
    }
  });
};
$(document).ready(function () {
  console.log("여기로 들어왔어!!!!!!!!!!");
  getDate();
});


  /* 상세 일정을 띄워주는 코드 */
  $(document).ready(function() {
    let monthText = $('.month').text().trim();
      let monthMatch = monthText.match(/(\d+)월/);
      let month = monthMatch ? parseInt(monthMatch[1]) : null;

    /* document.ready를 무조건 해야 이 클래스를 로드할 수 있음 */
    $('.toastui-calendar-daygrid-cell').on('click', function() {
      let day = $(this).text();
      let passText = '';
      let now = new Date();
      let iconBackground = '';
      let eventStart = '';
      let eventEnd = '';

      // 클릭한 날짜의 시작 시간과 끝 시간 구하기
      // start: 시작시간 00:00:00 
      // end : 끝 시간 23:59:59
      let start = new Date(Date.UTC(2023, month - 1, parseInt(day), 0, 0, 0)); // 0시 0분 0초
      let end = new Date(Date.UTC(2023, month - 1, parseInt(day), 23, 59, 59)); // 23시 59분 59초
      // 받아오는 정보
      let eventTitle = "놀러가요"
      let eventBody = "놀러가면 재밌어요"
      let eventCategory = "농촌"
      let location = '서울시 강동구'
      let category = '박물관';

      eventStart = new Date('2023-05-04T16:00:00+09:00');
      eventEnd = new Date('2023-05-04T17:00:00+09:00')

      let kstOffset = -540; // UTC+9
      start.setMinutes(start.getMinutes() + kstOffset); // 시작 시간을 한국 시간대로 변경
      end.setMinutes(end.getMinutes() + kstOffset); // 끝 시간을 한국 시간대로 변경


      // start와 end를 db에 넘기고 이 사이 정보를 받아온 다음
      // 그걸 반복문 돌려서 아래의 코드를 실행
      console.log('start : ' + start)
      console.log('end : ' + end)
      console.log('now :' + now)
      /* 날짜 한국어로 변경하는 코드 */
      let startFormat = eventStart.toLocaleString('ko-KR', {
        month: 'long',
        day: 'numeric',
        hour: 'numeric',
        minute: 'numeric'
      });

      let endFormat = eventEnd.toLocaleString('ko-KR', {
        month: 'long',
        day: 'numeric',
        hour: 'numeric',
        minute: 'numeric'
      });



//  날짜 마우스 오버, 마우스 아웃 이벤트
      let $temp;
      let check = false;
      DOMTokenList.prototype.filter = Array.prototype.filter;
      $(container).on({
        "mouseover": function () {
          check = this.classList.contains('clicked');
          $(this).css("border-radius", "50%");
          $(this).css("background-color", "#135de6");
          $(this).css("display", "inline-block");
          $(this).css("width", "100%");
          $(this).css("height", "100%");
          // $(this).css("line-height", "35px");
          $(this).css("color", "white");
        }, "mouseout": function () {
          if (check) {
            return;
          }
          $(this).css("border-radius", "100%");
          $(this).css("background-color", "#fff");
          $(this).css("color", "rgb(51, 51, 51)");
        }
      }, ".toastui-calendar-weekday-grid-date");

//  날짜 클릭 시 이벤트
      $(container).on("click", ".toastui-calendar-weekday-grid-date", function () {
        $(this).addClass("clicked");
        check = true;
        /* 김세윤 추가 */
        let fullDate = "";
        // fullDate <- 내가 누른 날 가져오기 ex)20230522
        fullDate += removeHangleYear() + removeHangleMonth() + plusZeroToLenghtOne($(this).text());
        console.log(fullDate);

        // ajax로 그날 참여한 모집 가져오기
        const sendData = () => {
          console.log("sendData 들어옴@@@@");
          let date = fullDate;
          $.ajax({
            type: 'GET',
            url: `/mypage/get-recruitment/${date}`,
            // data: $('#reply-form').serialize(),
            success: function (result) {
              console.log(result);
              uploadBoard(result);

              // $('#reply-count').text(result);
              // $(".comment-list-wrapper").empty();
              // nextPage = 0;
              // fetchData();
            },
            error: function (error) {
              console.log('Error fetching data:', error);
            }
          });
        };
        sendData();

        /* 김세윤 추가 */

        if ($temp) {
          $temp.removeClass("clicked");
          $temp.css("border-radius", "100%");
          $temp.css("background-color", "#fff");
          $temp.css("color", "rgb(51, 51, 51)");
        }
        $temp = $(this);
        $(this).css("border-radius", "50%");
        $(this).css("background-color", "#135de6");
        $(this).css("display", "inline-block");
        $(this).css("width", "100%");
        $(this).css("height", "100%");
        // $(this).css("line-height", "35px");
        $(this).css("color", "white");
      });

      /* 클릭시마다 월 바뀌기 */
      $('#calender-next').on('click', function () {
        check = true;
        if ($temp) {
          $temp.css("border-radius", "100%");
          $temp.css("background-color", "#fff");
          $temp.css("color", "rgb(51, 51, 51)");
        }
        $('.lecture-list-month').html($('.month').text());
      })

      $('#calender-prev').on('click', function () {
        check = true;
        if ($temp) {
          $temp.css("border-radius", "100%");
          $temp.css("background-color", "#fff");
          $temp.css("color", "rgb(51, 51, 51)");
        }
        $('.lecture-list-month').html($('.month').text());
      })

// '1~9' 한글자인 텍스트에 0 붙여주기
    function plusZeroToLenghtOne(e) {
      var number = e;

      if (e.length == 1) {
        return "0" + number;
      } else {
        return number;
      }
    }

// '1~12월' 텍스트에서 월 지우기
      function removeHangleMonth() {
        var monthElement = $('.month');
        var monthText = monthElement.text();
        var monthNumber = monthText.replace(/[^0-9]/g, ''); // 숫자만 남기기

        if (monthNumber.length == 1) {
          return "0" + monthNumber;
        } else {
          return monthNumber;
        }
      }

// '2023년' 텍스트에서 년 지우기
      function removeHangleYear() {
        var yearElement = $('.year');
        var yearText = yearElement.text();
        var yearNumber = yearText.replace(/[^0-9]/g, ''); // 숫자만 남기기

        return yearNumber;
      }

      function uploadBoard(result) {
        // $(".lecture-list").empty();
        let eventAll = "";

        if (result.length == 0){
          eventAll += `
            <div class="lecture">
                <div class="lecture-wrap">
                   <div style="width: 100%; height: 100px; text-align: center; padding: 35px; font-size: 18px; font-family: inherit;">참가한 일정이 없습니다.</div>
                </div>
            </div>
          `;
        }

        for (var i = 0; i < result.length; i++) {
          console.log("들어옴" + i);
          if (result[i].recruitmentFileDTOS.length != 0){
            globalThis.filePath = '/file/display?fileName=' + result[i].recruitmentFileDTOS[0].filePath + '/t_' + result[i].recruitmentFileDTOS[0].fileUuid + '_' + result[i].recruitmentFileDTOS[0].fileOriginalName;
          }
          eventAll +=
              `
      <!-- 왼쪽 컨텐츠 한개 -->
      <div class="lecture">
        <div class="lecture-wrap">
        <span class="lecture-image visible">
        `;
          if (result[i].recruitmentFileDTOS.length != 0) {
            eventAll += `
            <img src="${filePath}" alt="/images/free-board/basic-image.png">
                      `;
          } else {
            eventAll += `
            <img src="/images/free-board/basic-image.png" alt="">
                      `;
          }
          eventAll += `
        </span>
        
        <div class="lecture-wrapper">
          <div class="lecture-content">
            <p class="lecture-type">
              <span><i class="reward" style='background: #006633;'></i>참여</span>
            </p>
            <p class="lecture-title">
              ${result[i].boardTitle}
            </p>
            <p class="lecture-info">
              모집일 : ${result[i].recruitmentDate}
              <br> 
              모집 현황 : ${result[i].recruitmentDTOS.length} / ${result[i].recruitmentPeopleCount} 명
              <br />장소 : ${result[i].recruitmentAddress} <span>${result[i].recruitmentAddressDetail}</span>
              <div><strong><span onclick="window.open('${result[i].recruitmentOpenChatting}')">오픈 채팅방 참여하기</span></strong></div>
            </p>
            <p class="lecture-number">
              
            </p>
          </div>
        </div>
      </div>
<!-- 한개 끝 -->
      `
        }
        $(".lecture-list").html(eventAll);
      }
    })})



