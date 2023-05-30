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
        log.info("==================================================");

        if (session.getAttribute("member") != null) {
            MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
            log.info(memberDTO + "");
            if (memberDTO.getMemberId() == null) {
                redirectAttributes.addFlashAttribute("member", memberDTO);
                return new RedirectView("/member/join");
            }
        }

        if (session.getAttribute("memberId") != null){
            Long memberId = (Long) session.getAttribute("memberId");
            log.info(memberId + "");

            MemberDTO memberInfo = memberService.getMemberInfo(memberId);

            if (memberInfo.getMemberRole() == Role.ADMIN){
                return new RedirectView("/admins/home");
            } else {
                return new RedirectView("/home");
            }
        }

        return new RedirectView("/home");

    }

}
