package com.app.dodamdodam.repository;

import com.app.dodamdodam.domain.MemberDTO;
import com.app.dodamdodam.entity.banner.BannerApply;
import com.app.dodamdodam.entity.embeddable.Address;
import com.app.dodamdodam.entity.member.Member;
import com.app.dodamdodam.entity.point.Point;
import com.app.dodamdodam.entity.recruitment.RecruitmentBoard;
import com.app.dodamdodam.repository.banner.BannerRepository;
import com.app.dodamdodam.repository.board.recruitment.RecruitmentBoardRepository;
import com.app.dodamdodam.repository.member.MemberRepository;
import com.app.dodamdodam.repository.point.PointRepository;
import com.app.dodamdodam.type.MemberStatus;
import com.app.dodamdodam.type.MemberType;
import com.app.dodamdodam.type.PointStatus;
import com.app.dodamdodam.type.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Rollback(false)
@Transactional
@Slf4j
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RecruitmentBoardRepository recruitmentBoardRepository;

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private BannerRepository bannerRepository;

//    Enum 3개 번갈아가면서 사용하기 위한 ArrayList
    ArrayList<PointStatus> pointStatuses = new ArrayList<PointStatus>(Arrays.asList(PointStatus.CHARGE, PointStatus.SAVING, PointStatus.USE));


    /* 회원 100명 입력 */
    @Test
    public void saveTest(){
        for (int i=1; i<=100; i++) {
            Address address = new Address("서울시","강남구");
//            회원 정보 입력



//            Member member = new Member(3L,"test1234", "1234", "테스트", "test1234@gmail.com", "01012341234", address, MemberStatus.NORMAL, 0, 0, MemberType.GENERAL, Role.MEMBER);
//            memberRepository.save(member);

//            Member member = new Member("test1234", "1234", "테스트", "test1234@gmail.com", "01012341234", address, MemberStatus.NORMAL, MemberType.GENERAL, Role.MEMBER);
            Member member = Member.builder().memberName("테스트" + i).memberEmail("test" + i + "@naver.com").memberId("testId" + i)
                    .memberPassword("1234").memberPhone("01012341234").memberPoint(0).address(address)
                    .memberRole(Role.MEMBER).memberType(MemberType.GENERAL).memberStatus(MemberStatus.NORMAL)
                    .build();
            memberRepository.save(member);

//            모집 게시글 입력
//            2번 회원이 모집게시글 100개 작성
//            RecruitmentBoard recruitmentBoard = new RecruitmentBoard("모집 게시글 제목" + i, LocalDate.now(),10 + i, "www.naver.com", "1234", "경기도 성남시", "분당구 수내동");
//            memberRepository.findById(2L).ifPresent(member -> recruitmentBoard.setMember(member));
//            recruitmentBoardRepository.save(recruitmentBoard);

//            포인트 내역 입력(없어서 목록 불러오기 위해 임의로 넣어놓음)
//            Point point = new Point(10000 * i, pointStatuses.get((i % 3)));
//            memberRepository.findById(1L).ifPresent(member -> point.setMember(member));
//            pointRepository.save(point);
        }
    }

//    세션에 담긴 id 값으로 회원정보 가져오기
    @Test
    public void findByIdTest(){
        memberRepository.findById(1L).ifPresent(member -> log.info(member.toString()));
    }

//    세션에 담긴 id 값으로 회원정보 가져오기(이 유저가 참가한 회수가 이미 있는데 DTO로 참가 회수 넣어서 만들었는데 어떻게 해야할지 애매함)
    @Test
    public void findByMemberIdTest(){
        memberRepository.findByMemberId(5L).stream().map(MemberDTO::toString).forEach(log::info);
    }

//    마이페이지 내가 작성한 모집글 목록 조회
    @Test
    public void findRecruitBoardByMemberIdTest(){
        Pageable pageable = PageRequest.of(0, 5);
        recruitmentBoardRepository.findRecruitmentBoardListByMemberId_QueryDSL(pageable,2L).stream().map(recruitmentBoard -> recruitmentBoard.toString()).forEach(log::info);
    }

//    내가 작성한 모집 게시글 목록 불러오고 목록 중 하나 누르면 그 모집 게시글에 참여한 인원 목록(인원들의 정보) 가져오기
    @Test
    public void findRecruitmentBoardListByMemberIdTest(){
        Pageable pageable = PageRequest.of(0, 10);
        recruitmentBoardRepository.findRecruitmentBoardListByMemberId_QueryDSL(pageable, 2L).stream().map(recruitmentBoard -> recruitmentBoard.getRecruitments().toString()).forEach(log::info);
    }
    
//    내가 참여한 모집 게시글 목록 가져오기
    @Test
    public void findRecruitmentBoardListByMemberIdTest2(){
        Pageable pageable = PageRequest.of(0, 10);
        recruitmentBoardRepository.findRecruitmentedBoardListByMemberId_QueryDSL(pageable,5L).stream().map(RecruitmentBoard::toString).forEach(log::info);
    }
    

//    회원 정보 수정
    @Test
    public void setMemberTest(){
        memberRepository.findById(2L).ifPresent(member -> {
            member.setMemberPoint(50000);
            member.setMemberPassword("1234");
        });

    }


//    포인트 사용, 적립, 충전 내역 조회
    @Test
    public void findPointByMemberIdTest(){
        /*수정해야함*/
        pointRepository.findPointByMemberId_QueryDSL(2L).stream().map(Point::toString).forEach(log::info);
    }

//    비밀번호 변경
    @Test
    public void setMemberPasswordByIdTest(){
        memberRepository.findById(2L).ifPresent( member -> member.setMemberPassword("4321"));
    }

//    회원 탈퇴
    @Test
    public void deleteMemberByIdTest(){
        memberRepository.findById(1L).ifPresent(member -> memberRepository.delete(member));
    }

//    아이디 찾기
    @Test
    public void findMemberIdByMemberEmailTest(){
        String result = memberRepository.findMemberIdByMemberEmail_QueryDSL("test1@gmail.com");
        String msg = "";
        msg = result!=null ? result : "아이디가 없습니다.";
        log.info(msg);


    }

//    아이디 중복검사
    @Test
    public void findCheckMemberIdByEmailTest(){
        String msg = "";
        msg = memberRepository.findCheckMemberIdByMemberEmail_QueryDSL("test1@gmail.com") ? "아이디 없음" : "아이디 있음";
        log.info(msg);
    }

//    배너 신청
    @Test
    public void saveBannerTest(){
        BannerApply bannerApply = new BannerApply(LocalDate.now(),6);
        bannerApply.setMember(memberRepository.findById(2L).get());
        bannerRepository.save(bannerApply);
    }

//    배너 신청 목록
    @Test
    public void findAllBannerTest(){
        bannerRepository.findAll().stream().map(BannerApply::toString).forEach(log::info);
    }



    @Test //멤버 상세 조회
    public void findAdminMemberDetail_QueryDSL(){
        //멤버 상세 조회
        memberRepository.findById(2L).ifPresent(member -> log.info(member.toString()));
        //멤버 등급 조회        
        log.info(memberRepository.findAdminMemberDetail_QueryDSL(2L));
    }

}
