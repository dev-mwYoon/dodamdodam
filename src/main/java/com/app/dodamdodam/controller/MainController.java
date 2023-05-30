package com.app.dodamdodam.controller;

import com.app.dodamdodam.domain.PurchaseBoardFileDTO;
import com.app.dodamdodam.entity.purchase.Purchase;
import com.app.dodamdodam.provider.UserDetail;
import com.app.dodamdodam.service.board.eventBoard.EventBoardService;
import com.app.dodamdodam.service.board.freeBoard.FreeBoardService;
import com.app.dodamdodam.service.board.purchase.PurchaseBoardService;
import com.app.dodamdodam.service.board.recruitmentBoard.RecruitmentBoardService;
import com.app.dodamdodam.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final MemberService memberService;
    private final FreeBoardService freeBoardService;
    private final EventBoardService eventBoardService;
    private final PurchaseBoardService purchaseBoardService;
    private final RecruitmentBoardService recruitmentBoardService;

    @GetMapping("")
    public RedirectView goToMain(){
        return new RedirectView("/home");
    }

    /*=========================== 발표 버전 메인 ========================================*/

    @GetMapping("/home")
    public String main(HttpSession session, Model model){
        log.info("==================== main controller =====================");
//        log.info(session.getAttribute("member") + "");
        log.info("=========================================");
        model.addAttribute("id", session.getId());
        purchaseBoardService.getRecentPurchaseBoardList().stream().map(PurchaseBoardFileDTO::getPurchaseFileDTOS);

        model.addAttribute("freeBoards", freeBoardService.getRecentFreeBoardList());
        model.addAttribute("eventBoards", eventBoardService.getRecentEventBoardList());
        model.addAttribute("purchaseBoards", purchaseBoardService.getRecentPurchaseBoardList());
        model.addAttribute("purchaseBoardsFile", purchaseBoardService.getRecentPurchaseBoardList().stream().map(PurchaseBoardFileDTO::getBoardContent));
        model.addAttribute("recruitmentBoards", recruitmentBoardService.getRecentRecruitmentBoardList());
//        model.addAttribute("id", session.getId());
        return "main/main-first";
    }


/*
    @GetMapping("home")
    public String main(HttpSession session, Model model){
        log.info("==================== main controller =====================");
//        log.info(session.getAttribute("member") + "");
        log.info("=========================================");
        model.addAttribute("id", session.getId());
        purchaseBoardService.getRecentPurchaseBoardList().stream().map(PurchaseBoardFileDTO::getPurchaseFileDTOS);

        model.addAttribute("freeBoards", freeBoardService.getRecentFreeBoardList());
        model.addAttribute("eventBoards", eventBoardService.getRecentEventBoardList());
        model.addAttribute("purchaseBoards", purchaseBoardService.getRecentPurchaseBoardList());
        model.addAttribute("purchaseBoardsFile", purchaseBoardService.getRecentPurchaseBoardList().stream().map(PurchaseBoardFileDTO::getBoardContent));
        model.addAttribute("recruitmentBoards", recruitmentBoardService.getRecentRecruitmentBoardList());
//        model.addAttribute("id", session.getId());
        return  "main/main";
    }*/




    @GetMapping("/introduce")
    public String introduce(){
        return "introduce/introduce";
    }

    @GetMapping("/helpcenter")
    public String helpcenter(){
        return "helpcenter/inquiry-home";
    }
}
