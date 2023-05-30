package com.app.dodamdodam.controller.board.event;

import com.app.dodamdodam.domain.EventBoardDTO;
import com.app.dodamdodam.domain.EventReplyDTO;
import com.app.dodamdodam.entity.event.EventBoard;
import com.app.dodamdodam.entity.event.EventReply;
import com.app.dodamdodam.entity.free.FreeBoard;
import com.app.dodamdodam.search.EventBoardSearch;
import com.app.dodamdodam.service.board.eventBoard.EventBoardService;
import com.app.dodamdodam.service.board.eventBoard.eventReply.EventReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/event/*")
@RequiredArgsConstructor
@Slf4j
public class EventBoardController {
    private final EventBoardService eventBoardService;
    private final EventReplyService eventReplyService;

    /* 이벤트 게시판 메인 */
    @GetMapping("list")
    public String goList(Model model){
        return "event-board/event-board-list";
    }

    //    자유 게시판 무한 스크롤  // 검색으로 수정하기
    @ResponseBody
    @GetMapping("list-search")
    public List<EventBoardDTO> getEventBoardList(@RequestParam int page, @RequestParam String search){
        EventBoardSearch eventBoardSearch = new EventBoardSearch();
        eventBoardSearch.setBoardContent(search);
        eventBoardSearch.setBoardTitle(search);
        eventBoardSearch.setWriterName(search);

        log.info(eventBoardSearch.toString());

        Pageable pageable = PageRequest.of(page,12);

        List<EventBoardDTO> eventBoardDTOS = eventBoardService.getEventBoardsBySearch(pageable, eventBoardSearch);
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        log.info(eventBoardDTOS.toString());

        return eventBoardDTOS;
    }

    /* 상세페이지 */
    @GetMapping("detail/{eventBoardId}")
    public String goToDetail(Model model/*, @AuthenticationPrincipal UserDetail userDetail*/, @PathVariable("eventBoardId") Long eventBoardId) {
        log.info("상세들어옴@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        EventBoardDTO eventBoardDTO = eventBoardService.getDetail(eventBoardId);
        LocalDate currentDate = LocalDate.now();
        /* 이벤트 종료날짜와 현재날짜 비교 */
        long endDays = ChronoUnit.DAYS.between(currentDate, eventBoardDTO.getEventEndDate());
        
        /* 이벤트 종료 날짜가 이미 지났으면 못들어가게 막아주기 위해 다시 컨트롤러 태움 */
        if (endDays < 0) {
            return "redirect:/event/past?check=true";
        }

        /* D-day 구하기 현재 날짜와 event 종료 or 시작 날짜 둘중 하나 정해서 그 차이로 d-day 구해서 모델로 화면에 쏴주기 */
        long daysBetween = ChronoUnit.DAYS.between(currentDate, eventBoardDTO.getEventStartDate());

        log.info(daysBetween + " <- 남은 날짜@@@@@@@@@@@@@@@@@@@@@@@@@@");
        model.addAttribute("eventBoardDTO", eventBoardDTO);
        model.addAttribute("d_day", endDays);
        return "event-board/event-board-detail";
    }
    /*이미 지난 이벤트 들어왔을때 모달 띄우고 나가기*/
    @GetMapping("past")
    public String pastEvent(){
        log.info("이미 지난 이벤트 들어옴@@@@@@@@@@@@@@@@@@@@");
        return "event-board/event-board-detail-past";
    }

    /* 이벤트 게시판 상세보기 */
//    @GetMapping("detail/{boardId}")
//    public String eventBoardDetail(@PathVariable(value = "boardId") Long boardId, Model model, HttpSession session) {
//        session.setAttribute("boardId", 31L);
//        Long id = (Long) session.getAttribute("boardId");
//        model.addAttribute("eventBoardDetail", eventBoardService.getDetail(id));
//        /* PageRequest는 뭐 넣어도 상관없이 개수 가져와서 아무렇게나 넣음 */
//        model.addAttribute("replyCount", eventReplyService.getEventRepliesCountByBoardId(PageRequest.of(0, 5), id));
//        return "event-board/event-board-detail";
//    }

    /* 이벤트 상세페이지 이동 */
    @GetMapping("detail")
    public String goEventDetail(HttpSession session, Model model) {
        session.setAttribute("boardId", 31L);
        Long boardId = (Long)session.getAttribute("boardId");
        log.info(boardId+"boardId");
        log.info("=========================================" + eventBoardService.getDetail(boardId).toString());
        model.addAttribute("eventBoardDetail", eventBoardService.getDetail(boardId));
        model.addAttribute("replyCount", eventReplyService.getEventRepliesCountByBoardId(PageRequest.of(0, 5), boardId));
        return "event-board/event-board-detail";
    }

    /* 이벤트 작성하기 */
    @GetMapping("write")
    public String goToWriteForm(HttpSession session) {
        /* 임시로 세션에 memberId 담아둠 */
        Long memberId = (Long) session.getAttribute("memberId");
        return "event-board/event-board-write";
    }

    @ResponseBody
    @PostMapping("write")
    public void getEventWriteForm(@RequestBody EventBoardDTO eventBoardDTO, HttpSession session){
        log.info("eventBoardDTO.toString()");
        log.info(eventBoardDTO.toString());
        Long memberId = (Long) session.getAttribute("memberId");
        eventBoardService.register(eventBoardDTO, memberId);
    }

    //    자유 게시글 작성 로그인 확인
    @GetMapping("write-board-check")
    public RedirectView checkLoginForWriteBoard(HttpSession session){
        Long memberId = (Long)session.getAttribute("memberId");
//        로그인 상태가 아니라면
        if (memberId == null){
//            다시 리스트로 보내면서 로그인 해달라는 모달 띄우게 param 으로 값 보내기
            return new RedirectView("/event/list?login=false");
//            로그인 상태라면
        } else {
//            작성 페이지로
            return new RedirectView("/event/write");
        }
    }

    //    이벤트 게시글 수정 페이지
    @GetMapping("update-board/{boardId}")
    public String updateFreeBoard(@PathVariable(value = "boardId") Long boardId, Model model){
        model.addAttribute("eventBoardDetail", eventBoardService.getDetail(boardId));
        return "free-board/free-board-update";
    }


    //    이벤트 게시글 수정
    @PostMapping("update-board/{boardId}")
    public RedirectView updateFreeBoard(EventBoard updatedBoard, @PathVariable(value = "boardId") Long boardId){
        log.info("수정 들어옴");
        log.info(updatedBoard.toString());

        /* 수정 해야함 화면 쪽에서 뭐 받는지 확인하고 추가 해야함 */
        EventBoard updatedEventBoard = EventBoard.builder().boardTitle(updatedBoard.getBoardTitle()).boardContent(updatedBoard.getBoardContent())
                .build();
        eventBoardService.updateEventBoard(updatedEventBoard, boardId);
        return new RedirectView("/event/detail/{boardId}");
    }

    //    이벤트 게시글 삭제
    @GetMapping("delete-board/{boardId}")
    public RedirectView deleteFreeBoard(@PathVariable(value = "boardId") Long boardId){
        log.info("삭제 컨트롤러 들어옴");
        eventBoardService.delete(boardId);
        return new RedirectView("/event/list");
    }


    /* 이벤트 게시판 댓글 작성 */
    @PostMapping("write-reply")
    @ResponseBody
    public Long writeReply(String replyContent, HttpSession session){
        session.setAttribute("boardId", 31L);
        Long boardId = (Long)session.getAttribute("boardId");
        EventReply eventReply = new EventReply(replyContent);
        log.info("댓글작성들어옴");
        log.info("이벤트 게시판 댓글 : " + replyContent);
        log.info("게시판 ID : " + boardId);
        Long memberId = (Long)session.getAttribute("memberId");
        eventReplyService.saveEventBoardReply(eventReply, boardId, memberId);
        Long replyCount = eventReplyService.getEventRepliesCountByBoardId(PageRequest.of(0, 5), boardId);
        return replyCount;
    }

    /* 이벤트 게시판 댓글 수정 */
    @PostMapping("update-reply/{replyId}")
    @ResponseBody
    public String updateReply(String updatedEventReply, @PathVariable(value = "replyId") Long replyId, HttpSession session) {
        log.info("댓글 수정 들어옴");
        log.info("댓글 : " + updatedEventReply);
        log.info("댓글 : " + replyId);
//        session.setAttribute("boardId", 31L);
//        Long boardId = (Long)session.getAttribute("boardId");

        EventReply updatedReply = new EventReply(updatedEventReply);
        eventReplyService.setEventReplyContent(updatedReply, replyId);
        return "success##";
    }

    /* 이벤트 게시판 댓글 삭제 */
    @PostMapping("delete-reply/{replyId}")
    @ResponseBody
    public Integer deleteReply(@PathVariable(value = "replyId") Long replyId, HttpSession session){
        log.info("댓글 삭제 들어옴");
        session.setAttribute("boardId", 31L);
        Long boardId = (Long)session.getAttribute("boardId");
        Integer replyCount = eventReplyService.getEventRepliesCountByReplyId(replyId);
        eventReplyService.removeEventReply(replyId);
        return replyCount - 1;
    }

    /* 이벤트 게시판 댓글 리스트 */
    @GetMapping("replies/{page}")
    @ResponseBody
    public List<EventReplyDTO> getReplies(@PathVariable(value = "page") int page, HttpSession session){
        session.setAttribute("boardId", 31L);
        Long boardId = (Long)session.getAttribute("boardId");
        log.info("==========================댓글 리스트 들어옴================================");
        log.info(boardId + " || " + page);
        Pageable pageable = PageRequest.of(page, 5);
        return eventReplyService.getEventRepliesByBoardId(pageable, boardId);
    }
}
