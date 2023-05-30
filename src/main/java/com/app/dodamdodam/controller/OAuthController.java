package com.app.dodamdodam.controller;

import com.app.dodamdodam.domain.MemberDTO;
import com.app.dodamdodam.entity.member.Member;
import com.app.dodamdodam.provider.UserDetail;
import com.app.dodamdodam.service.member.MemberService;
import com.app.dodamdodam.type.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OAuthController {

    private final MemberService memberService;

    @GetMapping("/")
    public RedirectView oAuthLogin(HttpSession session, RedirectAttributes redirectAttributes){
        log.info(" ------------------- 로그인 처리 후 맨 마지막 컨트롤러 ------------------------------------- ");
        MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
        Long memberId = (Long) session.getAttribute("memberId");
        log.info("==================================================");
        log.info(memberDTO + "");
        log.info(memberId + "");


        if (memberDTO != null && memberDTO.getMemberId() == null) {
            redirectAttributes.addFlashAttribute("member", memberDTO);
            return new RedirectView("/member/join");
        }

        MemberDTO memberInfo = memberService.getMemberInfo(memberId);

        if (memberId != null && memberInfo.getMemberRole() == Role.ADMIN){
            return new RedirectView("/admins/home");
        } else if (memberId != null && memberInfo.getMemberRole() == Role.MEMBER){
            return new RedirectView("/home");
        } else {
            return new RedirectView("/member/logout");
        }
    }


//    @GetMapping("")
//    public void main(@AuthenticationPrincipal UserDetail userDetail, Model model) {
//        Long memberId = null;
//        if (session.getAttribute("member") == null) {
//            if(userDetail != null){
//                Member member = memberService.getOptionalMember(userDetail.getId()).orElseGet(null);
//                MemberDTO memberDTO = memberService.toMemberDTO(member);
//                session.setAttribute("member", memberDTO);
//            }
//        } else {
//            memberId = ((MemberDTO)session.getAttribute("member")).getId();
//            MemberDTO memberDTO = memberService.getMember(memberId);
//            session.setAttribute("member", memberDTO);
//        }
//
//        List<GroupChallengeDTO> groupChallengeDTOS = groupChallengeService.getGroupChallengeList(PageRequest.of(0, 6)).getContent();
//        List<GroupCalendarDTO> calendarDTOS = groupChallengeService.findAllCalendar();
//        MemberDTO memberInfo = null;
//        if (userDetail != null) {
//            memberInfo = myPageService.getMemberDTO(userDetail.getId());
//        } else if (memberId != null){
//            memberInfo = myPageService.getMemberDTO(memberId);
//        }
//        model.addAttribute("memberDTO", memberInfo);
//        model.addAttribute("groupChallengeDTOS", groupChallengeDTOS);
//        model.addAttribute("calendarDTOS", calendarDTOS);
//    }

}
