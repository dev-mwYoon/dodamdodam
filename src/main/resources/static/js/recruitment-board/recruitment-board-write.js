const $boardTitle = $('.boardTitle'); // 제목
const $boardContent = $('.boardContent'); // 내용
const $recruitmentAddress = $('.address'); // 주소
const $recruitmentAddressDetail = $('.addressDetail'); // 상세주소
const $recruitmentDate = $('#startDate'); // 모집일
const $recruitmentPeopleCount = $('.recruitmentPeopleCount'); // 모집인원
const $recruitmentOpenChatting = $('.recruitmentOpenChatting'); // 오픈채팅 링크

const fileArray = new Array();
let formData = new FormData(); // input 태그 담는 폼

/* 필수항목 체크  */
$(function() {
	$("#submit-btn").validate();
    $.extend( $.validator.messages, {
		required: "필수 항목입니다.",
        url: "URL을 올바로 입력하세요.",
        number: "숫자만 입력하세요."
    });
});

 /* 주소 */
 function searchAddress() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }

            } else {
            }

            document.getElementById("address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detailAddress").focus();
        }
    }).open();
}
   

/* 파일 */
let sel_files = [];  // 전역 변수로 이동
( /* att_zone : 이미지들이 들어갈 위치 id, btn : file tag id */
  imageView = function imageView(att_zone, btn){

    var attZone = document.getElementById(att_zone);
    var btnAtt = document.getElementById(btn)

    // 이미지와 체크 박스를 감싸고 있는 div 속성
    var div_style = 'display:inline-block;position:relative;padding:10px;'
                  + 'width:120px;height:120px;margin:5px;z-index:1;';
    // 미리보기 이미지 속성
    var img_style = 'width:100%;height:100%;z-index:none';
    // 이미지안에 표시되는 체크박스의 속성
    var chk_style = 'width:15px;height:15px;position:absolute;font-size:4px;padding:0px;'
                  + 'right:2px;bottom:3px;z-index:999;color:#fff;cursor:pointer;border-radius:50%;background-color:darkgray;border:none;';
                  
    btnAtt.onchange = function(e){
      var files = e.target.files;
      var fileArr = Array.prototype.slice.call(files)
      for(f of fileArr){
        imageLoader(f);
      }
    }  
    
    /*첨부된 이미지들을 배열에 넣고 미리보기 */
    imageLoader = function(file){
      sel_files.push(file);
      var reader = new FileReader();
      reader.onload = function(ee){
        let img = document.createElement('img')
        img.setAttribute('style', img_style)
        img.src = ee.target.result;
        attZone.appendChild(makeDiv(img, file));
      }
      reader.readAsDataURL(file);
    }
    
    /*첨부된 파일이 있는 경우 checkbox와 함께 attZone에 추가할 div를 만들어 반환 */
    makeDiv = function(img, file){
      var div = document.createElement('div')
      div.setAttribute('style', div_style)
      
      var btn = document.createElement('input')
      btn.setAttribute('type', 'button')
      btn.setAttribute('value', 'x')
      btn.setAttribute('delFile', file.name);
      btn.setAttribute('style', chk_style);
      btn.onclick = function(ev){
        var ele = ev.srcElement;
        var delFile = ele.getAttribute('delFile');
          for(var i=0 ;i<sel_files.length; i++){
              if(delFile== sel_files[i].name){
                  sel_files.splice(i, 1);
              }
          }

          if($('#att_zone').children().length > 5){
              $(".file-button").hide();
              return;
          }

          dt = new DataTransfer();
          for(f in sel_files) {
              var file = sel_files[f];
              dt.items.add(file);
          }
          btnAtt.files = dt.files;
          var p = ele.parentNode;
          attZone.removeChild(p)
      }
        div.appendChild(img)
        div.appendChild(btn)
        return div
    }
  }
)('att_zone', 'btnAtt')

document.getElementById("btnAtt").addEventListener("change", function (e) {
    const files = e.target.files;

    console.log("files");
    console.log(files);

    formData = new FormData();
    for (let i = 0; i < files.length; i++) {
        formData.append("file", files[i]);
    }

    console.log(formData);

    $.ajax({
        url: '/file/upload',
        data: formData,
        method: 'post',
        processData: false,
        contentType: false,
        success: function (result) {
            console.log("success@@@@@@@@@@@@@@@@@@@@");
            if(result){
                console.log(result);
                for (let i = 0; i < result.uuids.length; i++) {
                    let file = new Object();

                    file.filePath = result.paths[i];
                    file.fileUuid = result.uuids[i];
                    file.fileOriginalName = result.fileOriginalNames[i];

                    console.log(file);

                    fileArray.push(file);
                }
            }
        }
    });
});

$('.submit-btn').on('click', function (e) {
    e.preventDefault();
    writeBoard();
});

function writeBoard(){

    console.log(fileArray);
    console.log($boardTitle.val());
    console.log($boardContent.val());
    console.log($recruitmentAddress.val());
    console.log($recruitmentAddressDetail.val());
    console.log($recruitmentDate.val());
    console.log($recruitmentPeopleCount.val());
    console.log($recruitmentOpenChatting.val());

    let recruitmentBoardDTO = {
        boardTitle : $boardTitle.val(),
        boardContent : $boardContent.val(),
        recruitmentAddress : $recruitmentAddress.val(),
        recruitmentAddressDetail : $recruitmentAddressDetail.val(),
        recruitmentDate : $recruitmentDate.val(),
        recruitmentPeopleCount : $recruitmentPeopleCount.val() * 1.0,
        recruitmentOpenChatting : $recruitmentOpenChatting.val(),
        recruitmentFileDTOS : fileArray
    };
    console.log("recruitmentBoardDTO");
    console.log(recruitmentBoardDTO);

    $.ajax({
        url: '/recruitment/write',
        data: JSON.stringify(recruitmentBoardDTO),
        method: 'post',
        processData: false,
        contentType: 'application/json',
        success: function() {
            console.log("recruitment/write ajax 성공");
            location.href='/recruitment/list';
        }
    });
}


    
    // /* 정규식 */
    // // 인원수
    // const $peopleCount = $(".people-count");
    // const $peopleCountWarning = $(".people-count-error");
    
    // $peopleCount.on("blur", function(){
    //     var countPattern =  /^[0-9]+$/;  //인원수 정규식
    //     if (!countPattern.test($peopleCount.val())) {
    //         $peopleCountWarning.text("숫자만 입력해 주세요.");
    //         $peopleCountWarning.css("display", "block");
    //         $peopleCount.css("border-color", "#e52929");
    //         // $(this).find('.mb').attr('class', 'mb0');
    //         checkAll[0] = false;
    
    //     } else {
    //         $peopleCountWarning.css("display", "none");
    //         $peopleCount.css("border-color", "#e0e0e0");
    //         checkAll[0] = true;
    //     }
    // });
    
    //     // 가격
    // const $recruitmentPrice = $(".recruitment-price");
    // const $priceWarning = $(".price-error");
    
    
    // $recruitmentPrice.on("blur", function(){
    //     var countPattern =  /^[0-9]+$/;  //인원수 정규식
    //     if (!countPattern.test($recruitmentPrice.val())) {
    //         $priceWarning.text("숫자만 입력해 주세요.");
    //         $priceWarning.css("display", "block");
    //         $recruitmentPrice.css("border-color", "#e52929");
    //         checkAll[0] = false;
    
    //     } else {
    //         $priceWarning.css("display", "none");
    //         $recruitmentPrice.css("border-color", "#e0e0e0");
    //         checkAll[0] = true;
    //     }
    // });

    //     // 오픈채팅 비밀번호
    // const $openKakaoPassword = $(".open-kakao-password");
    // const $openKakaoPasswordWarning = $(".open-password-error");
    
    
    // $openKakaoPassword.on("blur", function(){
    //     var countPattern =  /^[0-9]+$/;  //인원수 정규식
    //     if (!countPattern.test($openKakaoPassword.val())) {
    //         $openKakaoPasswordWarning.text("숫자만 입력해 주세요.");
    //         $openKakaoPasswordWarning.css("display", "block");
    //         $openKakaoPassword.css("border-color", "#e52929");
    //         checkAll[0] = false;
    
    //     } else {
    //         $openKakaoPasswordWarning.css("display", "none");
    //         $openKakaoPassword.css("border-color", "#e0e0e0");
    //         checkAll[0] = true;
    //     }
    // });

    
    //     // 오픈채팅
    // const $openKakao = $(".open-kakao");
    // const $openKakaoWarning = $(".open-kakao-error");
    
    // $openKakao.on("blur", function(){
    //     var koreanPattern = /[^A-Za-z0-9_\`\~\!\@\#\$\%\^\&\*\(\)\-\=\+\\\{\}\[\]\'\"\;\:\<\,\>\.\?\/\s]/gm;   //한글 정규식
    //     if (!koreanPattern.test($openKakao.val())) {
    //         // console.log($openKakao.val());
    //         $openKakaoWarning.text("한글은 입력 불가능합니다.");
    //         $openKakaoWarning.css("display", "block");
    //         $openKakao.css("border-color", "#e52929");
    //         // $(this).find('.mb').attr('class', 'mb0');
    //         checkAll[0] = false;
        
    //     } else {
    //         console.log($openKakao.val());
    //         $openKakaoWarning.css("display", "none");
    //         $openKakao.css("border-color", "#e0e0e0");
    //         checkAll[0] = true;
    //     }
    //     });


        // /* 필수사항 체크 */
        // const $submitBtn = $(".submit-btn") //등록 버튼
        // // 모든 정규식(약관 제외)
        // let checkAll = [false, false, false, false, false, false, false, false, false];


    