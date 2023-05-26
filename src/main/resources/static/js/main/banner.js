// HTMLCollection.prototype.forEach = Array.prototype.forEach;
// const banners = document.querySelector("div.main_banner_all"); // 배너들을 감싼 총 div
// const imageDiv = document.querySelectorAll("div.main_banner_all div.main_banner"); // 각각의 이미지가 담기는 배너 리스트
// // const lastImageDiv = document.createElement("div"); // 마지막 이미지를 담을 div 생성
// // const firstImageDiv = document.createElement("div"); // 첫번째 이미지를 담을 div 생성
// const next = document.querySelector(".main_banner_right_button");
// // 다음 버튼
// const prev = document.querySelector(".main_banner_left_button "); // 이전 버튼
// const buttons = document.querySelectorAll(".main_banner_button button"); // 버튼 2개 모두의 리스트
// const $bannerActive = $(".main_banner_current_bar"); // 움직이는 배너 바
// const bannerPercentage = (100 / (imageDiv.length - 2)); // 배너 바 매번 증가해야하는 수치
// // const lastImageDiv = document.querySelector("div.main_banner_all div.main_banner[0]");
// // const firstImageDiv = document.querySelector("div.main_banner_all div.main_banner[parseInt(imageDiv.length)]");
// const $mainColor = $(".banner_container");
// const $currentPage = $('.current_page');
//
//
// let checkArrow = false;
// let count = 1; // 초기 값 배너 순서?
// let auto = setInterval(autoSlide, 2000); // 2초마다 자동 슬라이드
// let temp = buttons[0]; // 현재 버튼은 display none 상태
//
// HTMLCollection.prototype.forEach = Array.prototype.forEach;
// HTMLCollection.prototype.map = Array.prototype.map;
//
//
//
// // buttons.forEach(button => {
// //     button.addEventListener("click", () => {
// //         clearInterval(auto);
// //         count = parseInt(button.innerHTML);
// //         changeButtonStyle();
// //         banners.style.transition = "transform 0.3s";
// //         banners.style.transform = `translate(${-580 * count}px)`;
// //         auto = setInterval(autoSlide, 2000);
// //     });
// // });
// // imageDiv.forEach((div, i) => div.style.backgroundImage = `url(/images/main/00${i+1}.jpg)`); // 배너 리스트 안에 각 배너들에 이미지를 style을 줌.
// imageDiv.forEach((div, i) => div.style.backgroundImage = `url(/static/images/main/00${i+1}.jpg)`); // 배너 리스트 안에 각 배너들에 이미지를 style을 줌.
// // imageDiv[0].style.backgroundImage = `url(/images/main/00${imageDiv.length}.jpg)`;
// // imageDiv[parseInt(${imageDiv.length})].style.backgroundImage = `url(/images/main/00${imageDiv.length}.jpg)`;
//
// imageDiv.forEach((div, i) => console.log(div, i));
//
// // banners.appendChild(lastImageDiv); // 총 배너의 자식인 배너 div(lastImageDiv) 하나 추가
// // lastImageDiv.style.backgroundImage = `url(/images/main/001.jpg)`;
//
//
// // banners.insertBefore(firstImageDiv, document.querySelector("div.banners div.banner"));
// // firstImageDiv.style.backgroundImage = `url(/images/main/00${imageDiv.length}.jpg)`
// // console.log(firstImageDiv);
// // console.log(lastImageDiv);
//
// banners.style.transform = `translate(-580px)`;
//
//
// function changeButtonStyle() {
//     // if(temp){
//     //     temp.style.background = "white";
//     //     temp.style.color = "black";
//     // }
//     // buttons[count - 1].style.background = "black";
//     // buttons[count - 1].style.color = "white";
//     temp = buttons[count - 1];
//     $bannerActive.css("width", `${bannerPercentage * count}%`); /* 배너 바 현재 퍼센트 */
//     $mainColor.css("background-color", `var(--banner${count}-color)`); // 메인 컬러 변경
//     $currentPage.text(count);
// }
//
// function autoSlide(){
//     banners.style.transition = "transform 0.3s"; // 0.3초동안 이동하는 애니메이션 효과
//     banners.style.transform = `translate(${-580 * ++count}px)`;
//
//     if(count == 4) { /* 6->7로 넘어가게 되면*/
//         count = 1; /* count는 다시 1 */
//         setTimeout(function(){
//             banners.style.transition = "transform 0s"; /* 같은 이미지 이동 시 애니메이션 끔. */
//             banners.style.transform = `translate(-580px)`; /* 이동 하는 배너 위치 */
//         }, 300);
//     }
//
//     changeButtonStyle();
//
// }
//
// /* ==================================== */
//
// prev.addEventListener("click", function(){
//     if(checkArrow){return;}
//     checkArrow = true;
//     clearInterval(auto);
//     banners.style.transition = "transform 0.3s";
//     banners.style.transform = `translate(${-580 * --count}px)`;
//     if(count == 0) {
//         count = 3;
//         setTimeout(function(){
//             banners.style.transition = "transform 0s";
//             banners.style.transform = `translate(${-580 * (imageDiv.length)}px)`;
//         }, 300);
//     }
//     changeButtonStyle();
//     auto = setInterval(autoSlide, 2000);
//     setTimeout(()=>{checkArrow = false}, 300);
// });
//
// next.addEventListener("click", function(){
//     if(checkArrow){return;}
//     checkArrow = true;
//     clearInterval(auto);
//     banners.style.transition = "transform 0.3s";
//     banners.style.transform = `translate(${-580 * ++count}px)`;
//     if(count == 4) {
//         count = 1;
//         setTimeout(function(){
//             banners.style.transition = "transform 0s";
//             banners.style.transform = `translate(-580px)`;
//         }, 300);
//     }
//     changeButtonStyle();
//     auto = setInterval(autoSlide, 2000);
//     setTimeout(()=>{checkArrow = false}, 300);
// });
//
//
//
//
//
// /* 판매 배너 */
// const sellBanner = document.querySelector('.sell-swiper-container .swiper-wrapper');
// const sellImageDiv = document.querySelectorAll('.swiper-wrapper div .sell-swiper-image');
// const sellPrev = document.querySelector('.sell-prev');
// const sellNext = document.querySelector('.sell-next');
//
//
// let sellCheckArrow = false;
// let sellCount = 0;
//
// sellImageDiv.forEach((div, i) => {
//   div.style.backgroundImage = `url(/static/images/main/sell-00${i + 1}.jpg)`;
// });
//
// const clonedsellBanners = document.querySelectorAll('.sell-swiper-container .swiper-wrapper > div').values();
//
// // for (const clonedBanner of clonedsellBanners) {
// //   sellBanner.appendChild(clonedBanner.cloneNode(true));
// // }
// const clonedsellBanner1 = $('.sell-swiper1').clone()[0];
// sellBanner.appendChild(clonedsellBanner1);
//
// const clonedsellBanner2 = $('.sell-swiper2').clone()[0];
// sellBanner.appendChild(clonedsellBanner2);
//
// const clonedsellBanner3 = $('.sell-swiper3').clone()[0];
// sellBanner.appendChild(clonedsellBanner3);
//
// const clonedsellBanner4 = $('.sell-swiper4').clone()[0];
// sellBanner.appendChild(clonedsellBanner4);
//
//
//
//
// const clonedsellBanner5 = $('.sell-swiper5').clone()[0];
// sellBanner.insertBefore(clonedsellBanner5, document.querySelector('div.swiper-wrapper .sell-swiper1'));
//
// sellBanner.style.transform = `translate(-326px)`;
//
// sellPrev.addEventListener('click', function() {
//   if (sellCheckArrow) {
//     return;
//   }
//   sellCheckArrow = true;
//   sellCount--;
//   console.log("prev: " + sellCount);
//   if (sellCount < 0) {
//     sellBanner.style.transition = 'transform 0s';
//     sellBanner.style.transform = `translateX(${-324 * (sellImageDiv.length + 1)}px)`;
//     setTimeout(() => {
//       sellCount = sellImageDiv.length;
//       sellBanner.style.transition = 'transform 0.3s';
//       sellBanner.style.transform = `translateX(${-324 * sellCount}px)`;
//       sellCheckArrow = false;
//     }, 10);
//   } else {
//     sellBanner.style.transition = 'transform 0.3s';
//     sellBanner.style.transform = `translateX(${-324 * sellCount}px)`;
//     setTimeout(() => {
//       sellCheckArrow = false;
//     }, 300);
//   }
// });
//
// sellNext.addEventListener('click', function() {
//   if (sellCheckArrow) {
//     return;
//   }
//   sellCheckArrow = true;
//   sellCount++;
//   console.log("next: " + sellCount);
//   sellBanner.style.transition = 'transform 0.3s';
//   sellBanner.style.transform = `translateX(${-324 * sellCount}px)`;
//   if (sellCount === sellImageDiv.length + 1) {
//     sellCount = 1;
//     setTimeout(function() {
//       sellBanner.style.transition = 'transform 0s';
//       sellBanner.style.transform = 'translateX(-324px)';
//     }, 300);
//   }
//   setTimeout(() => {
//     sellBanner.style.transition = 'transform 0s';
//     sellBanner.style.transform = `translateX(${-324 * sellCount}px)`;
//     sellCheckArrow = false;
//   }, 300);
// });
//
//
//
//
//
// /* 두번째 배너 */
// const secondBanner = document.querySelector('.second-banner');
// const secondImageDiv = document.querySelectorAll('.second-banner .second-banner-image');
// const secondPrev = document.querySelector('.second-prev');
// const secondNext = document.querySelector('.second-next');
// let secondCheckArrow = false;
// let secondCount = 1;
//
// // secondImageDiv.forEach((div, i) => {
// //   // div.style.backgroundImage = `url(../../static/css/main/images/second-00${i + 1}.jpg)`;
// //   div.style.backgroundImage = `url(/static/images/main/recruit-00${i + 1}.jpg)`;
// // });
//
//
// const clonedsecondBanners = document.querySelectorAll('.second-banner > div').values();
//
// for (const clonedBanner of clonedsecondBanners) {
//   secondBanner.appendChild(clonedBanner.cloneNode(true));
// }
//
// const clonedsecondBanner5 = document.querySelector('.second-banner5').cloneNode(true);
// secondBanner.insertBefore(clonedsecondBanner5, document.querySelector('.second-banner .second-banner1'));
//
// secondBanner.style.transform = `translateX(-261px)`;
//
// secondPrev.addEventListener('click', function() {
//   if (secondCheckArrow) {
//     return;
//   }
//   secondCheckArrow = true;
//   secondCount--;
//   if (secondCount === 0) {
//     secondBanner.style.transition = 'transform 0s';
//     secondBanner.style.transform = `translateX(${-261 * (secondImageDiv.length + 1)}px)`;
//     setTimeout(() => {
//       secondCount = secondImageDiv.length;
//       secondBanner.style.transition = 'transform 0.3s';
//       secondBanner.style.transform = `translateX(${-261 * secondCount}px)`;
//       secondCheckArrow = false;
//     }, 10);
//   } else {
//     secondBanner.style.transition = 'transform 0.3s';
//     secondBanner.style.transform = `translateX(${-261 * secondCount}px)`;
//     setTimeout(() => {
//       secondCheckArrow = false;
//     }, 300);
//   }
// });
//
// secondNext.addEventListener('click', function() {
//   if (secondCheckArrow) {
//     return;
//   }
//   secondCheckArrow = true;
//   secondCount++;
//   secondBanner.style.transition = 'transform 0.3s';
//   secondBanner.style.transform = `translateX(${-261 * secondCount}px)`;
//   if (secondCount === secondImageDiv.length + 1) {
//     secondCount = 1;
//     setTimeout(function() {
//       secondBanner.style.transition = 'transform 0s';
//       secondBanner.style.transform = 'translateX(-261px)';
//     }, 300);
//   }
//   setTimeout(() => {
//     secondBanner.style.transition = 'transform 0s';
//     secondBanner.style.transform = `translateX(${-261 * secondCount}px)`;
//     secondCheckArrow = false;
//   }, 300);
// });
//
//
// /* 두번째 배너 액션 */
// $('.second-banners').each((i, e) => {
//   $(e).mouseover(() => {
//      $($(".recruit-box-context")[i]).css("transform", "translate(0, -180px)");
//      $($(".recruit-box-context")[i]).css("background", "linear-gradient(to top, rgba(0, 0, 0, 0.8), rgba(0, 0, 0, 0.4))");
//   });
//   $(e).mouseout(() => {
//      $($(".recruit-box-context")[i]).css("transform", "translate(0, 0px)");
//      $($(".recruit-box-context")[i]).css("background", "linear-gradient(to top, rgba(0, 0, 0, 0.8), rgba(0, 0, 0, 0))");
//   });
// });
//
// /* 세번째 배너 */
// HTMLCollection.prototype.forEach = Array.prototype.forEach;
// const thirdBanner = document.querySelector('div.third-banner');
// const thirdImageDiv = document.querySelectorAll('div.third-banner div .third-banner-image');
// const thirdFirstImageDiv = document.createElement('div');
// const thirdNext = document.querySelector('div.third-next');
// const thirdPrev = document.querySelector('div.third-prev');
// const thirdButtons = document.querySelectorAll('.third-buttons button');
//
// let thirdCheckArrow = false;
// let thirdCount = 1;
// const thirdPageNow = document.querySelector('#third-page-now');
//
//
// thirdImageDiv.forEach(
//   (div, i) => (div.style.backgroundImage = `url(/static/images/main/culture-00${i + 1}.jpg)`)
// );
//
// const clonedThirdBanner1 = $('.third-banner1').clone()[0];
// thirdBanner.appendChild(clonedThirdBanner1);
//
// const clonedThirdBanner2 = $('.third-banner2').clone()[0];
// thirdBanner.appendChild(clonedThirdBanner2);
//
// const clonedThirdBanner3 = $('.third-banner3').clone()[0];
// thirdBanner.appendChild(clonedThirdBanner3);
//
// const clonedThirdBanner4 = $('.third-banner4').clone()[0];
// thirdBanner.appendChild(clonedThirdBanner4);
//
//
//
// const clonedThirdBanner5 = $('.third-banner5').clone()[0];
// thirdBanner.insertBefore(clonedThirdBanner5, document.querySelector('div.third-banner .third-banner1'));
//
// thirdBanner.style.transform = `translate(-326px)`;
//
// thirdPrev.addEventListener('click', function () {
//   if (thirdCheckArrow) {
//     return;
//   }
//   thirdCheckArrow = true;
//   thirdCount--;
//   if (thirdCount === 0) {
//     thirdBanner.style.transition = 'transform 0s';
//     thirdBanner.style.transform = `translate(${-326 * (thirdImageDiv.length + 1)}px)`;
//     setTimeout(() => {
//       thirdCount = thirdImageDiv.length;
//       thirdBanner.style.transition = 'transform 0.3s';
//       thirdBanner.style.transform = `translate(${-326 * thirdCount}px)`;
//       thirdCheckArrow = false;
//     }, 10);
//   } else {
//     thirdBanner.style.transition = 'transform 0.3s';
//     thirdBanner.style.transform = `translate(${-326 * thirdCount}px)`;
//     setTimeout(() => {
//       thirdCheckArrow = false;
//     }, 300);
//   }
// });
//
// thirdNext.addEventListener('click', function () {
//   if (thirdCheckArrow) {
//     return;
//   }
//   thirdCheckArrow = true;
//   thirdCount++;
//   thirdBanner.style.transition = 'transform 0.3s';
//   thirdBanner.style.transform = `translate(${-326 * thirdCount}px)`;
//   if (thirdCount == thirdImageDiv.length + 1) {
//     thirdCount = 1;
//     setTimeout(function () {
//             thirdBanner.style.transition = 'transform 0s';
//             thirdBanner.style.transform = 'translate(-326px)';
//         }, 300);
//   }
//   setTimeout(() => {
//     thirdBanner.style.transition = 'transform 0s';
//     thirdBanner.style.transform = `translate(${-326 * thirdCount}px)`;
//     thirdCheckArrow = false;
//   }, 300);
// });
//
//
// /* 신청 배너 */
// HTMLCollection.prototype.forEach = Array.prototype.forEach;
// const applyBanner = document.querySelector("div.banner");
// const applyImageDiv = document.querySelectorAll("div.banner div");
// const applyLastImageDiv = document.createElement("div");
// const applyFirstImageDiv = document.createElement("div");
// const applyNext = document.querySelector("div.next");
// const applyPrev = document.querySelector("div.prev");
// const applyButtons = document.querySelectorAll(".buttons button");
// let applyCheckArrow = false;
// let applyCount = 1;
// let applyAuto = setInterval(applyAutoSlide, 2000);
// let applyTemp = applyButtons[0];
//
// HTMLCollection.prototype.forEach = Array.prototype.forEach;
// applyButtons.forEach(button => {
//     button.addEventListener("click", () => {
//         clearInterval(applyAuto);
//         applyCount = parseInt(button.innerHTML);
//         changeButtonStyle();
//         applyBanner.style.transition = "transform 0.3s";
//         applyBanner.style.transform = `translate(${-1280 * applyCount}px)`;
//         applyAuto = setInterval(applyAutoSlide, 2000);
//     });
// });
//
// applyImageDiv.forEach((div, i) => div.style.backgroundImage = `url(/static/images/main/apply-00${i+1}.jpg)`)
//
// applyBanner.appendChild(applyLastImageDiv);
// applyLastImageDiv.style.backgroundImage = `url(/static/images/main/apply-001.jpg)`;
//
// applyBanner.insertBefore(applyFirstImageDiv, document.querySelector("div.banner div"));
// applyFirstImageDiv.style.backgroundImage = `url(/static/images/main/apply-00${applyImageDiv.length}.jpg)`
//
// applyBanner.style.transform = `translate(-1280px)`;
//
// function changeButtonStyle() {
//     if(applyTemp){
//         applyTemp.style.background = "white";
//         applyTemp.style.color = "black";
//     }
//     applyButtons[applyCount - 1].style.background = "black";
//     applyButtons[applyCount - 1].style.color = "white";
//     applyTemp = applyButtons[applyCount - 1];
// }
//
// function applyAutoSlide(){
//     applyBanner.style.transition = "transform 0.3s";
//     applyBanner.style.transform = `translate(${-1280 * ++applyCount}px)`;
//     console.log(applyCount);
//     if(applyCount == 6) {
//         applyCount = 1;
//         setTimeout(function(){
//             applyBanner.style.transition = "transform 0s";
//             applyBanner.style.transform = "translate(-1280px)";
//         }, 300);
//     }
//     changeButtonStyle();
// }
//
// applyPrev.addEventListener("click", function(){
//     if(applyCheckArrow){return;}
//     applyCheckArrow = true;
//     clearInterval(applyAuto);
//     applyBanner.style.transition = "transform 0.3s";
//     applyBanner.style.transform = `translate(${-1280 * --applyCount}px)`;
//     if(applyCount == 0) {
//         applyCount = 5;
//         setTimeout(function(){
//             applyBanner.style.transition = "transform 0s";
//             applyBanner.style.transform = `translate(${-1280 * (applyImageDiv.length)}px)`;
//         }, 300);
//     }
//     changeButtonStyle();
//     applyAuto = setInterval(applyAutoSlide, 2000);
//     setTimeout(()=>{applyCheckArrow = false}, 300);
// });
//
// applyNext.addEventListener("click", function(){
//     if(applyCheckArrow){return;}
//     applyCheckArrow = true;
//     clearInterval(applyAuto);
//     applyBanner.style.transition = "transform 0.3s";
//     applyBanner.style.transform = `translate(${-1280 * ++applyCount}px)`;
//     if(applyCount == 6) {
//         applyCount = 1;
//         setTimeout(function(){
//             applyBanner.style.transition = "transform 0s";
//             applyBanner.style.transform = "translate(-1280px)";
//         }, 300);
//     }
//     changeButtonStyle();
//     applyAuto = setInterval(applyAutoSlide, 2000);
//     setTimeout(()=>{applyCheckArrow = false}, 300);
// });

HTMLCollection.prototype.forEach = Array.prototype.forEach;
const banners = document.querySelector("div.main_banner_all");
const imageDiv = document.querySelectorAll("div.main_banner_all div.main_banner");
const next = document.querySelector(".main_banner_right_button");
const prev = document.querySelector(".main_banner_left_button");
const buttons = document.querySelectorAll(".main_banner_button button");
const $bannerActive = $(".main_banner_current_bar");
const bannerPercentage = 100 / (imageDiv.length - 2);
const $mainColor = $(".banner_container");
const $currentPage = $(".current_page");

let checkArrow = false;
let count = 1;
let auto = setInterval(autoSlide, 2000);
let temp = buttons[0];

HTMLCollection.prototype.forEach = Array.prototype.forEach;
HTMLCollection.prototype.map = Array.prototype.map;

imageDiv.forEach((div, i) => (div.style.backgroundImage = `url(/images/main/00${i + 1}.png)`));

function changeButtonStyle() {
    // if (temp) {
    //     temp.style.background = "black";
    //     temp.style.color = "black";
    // }
    if (buttons[count - 1]) {
        // buttons[count - 1].style.background = "black";
        // buttons[count - 1].style.color = "black";
        temp = buttons[count - 1];
    }
    $bannerActive.css("width", `${bannerPercentage * count}%`);
    $mainColor.css("background-color", `var(--banner${count}-color)`);
    $currentPage.text(count);
}

function autoSlide() {
    banners.style.transition = "transform 0.3s";
    banners.style.transform = `translate(${-580 * ++count}px)`;

    if (count === imageDiv.length - 1) {
        count = 1;
        setTimeout(() => {
            banners.style.transition = "transform 0s";
            banners.style.transform = `translate(-580px)`;
        }, 300);
    }

    changeButtonStyle();
}

prev.addEventListener("click", function () {
    if (checkArrow) {
        return;
    }
    checkArrow = true;
    clearInterval(auto);
    banners.style.transition = "transform 0.3s";
    banners.style.transform = `translate(${-580 * --count}px)`;
    if (count === 0) {
        count = imageDiv.length - 2;
        setTimeout(() => {
            banners.style.transition = "transform 0s";
            banners.style.transform = `translate(${-580 * count}px)`;
        }, 300);
    }
    changeButtonStyle();
    auto = setInterval(autoSlide, 2000);
    setTimeout(() => {
        checkArrow = false;
    }, 300);
});

next.addEventListener("click", function () {
    if (checkArrow) {
        return;
    }
    checkArrow = true;
    clearInterval(auto);
    banners.style.transition = "transform 0.3s";
    banners.style.transform = `translate(${-580 * ++count}px)`;
    if (count === imageDiv.length - 1) {
        count = 1;
        setTimeout(() => {
            banners.style.transition = "transform 0s";
            banners.style.transform = `translate(-580px)`;
        }, 300);
    }
    changeButtonStyle();
    auto = setInterval(autoSlide, 2000);
    setTimeout(() => {
        checkArrow = false;
    }, 300);
});
