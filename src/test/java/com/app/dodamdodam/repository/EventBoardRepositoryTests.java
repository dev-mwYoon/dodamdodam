package com.app.dodamdodam.repository;

import com.app.dodamdodam.entity.embeddable.Address;
import com.app.dodamdodam.entity.event.EventBoard;
import com.app.dodamdodam.entity.event.EventFile;
import com.app.dodamdodam.entity.event.EventReply;
import com.app.dodamdodam.entity.member.Member;
import com.app.dodamdodam.repository.board.event.board.EventBoardRepository;
import com.app.dodamdodam.repository.board.event.file.EventFileRepository;
import com.app.dodamdodam.repository.board.event.reply.EventReplyRepository;
import com.app.dodamdodam.repository.member.MemberRepository;
import com.app.dodamdodam.type.MemberStatus;
import com.app.dodamdodam.type.MemberType;
import com.app.dodamdodam.type.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
@Slf4j
@Transactional
@Rollback(false)
public class EventBoardRepositoryTests {
    @Autowired
    private EventBoardRepository eventBoardRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EventFileRepository eventFileRepository;
    @Autowired
    private EventReplyRepository eventReplyRepository;

    /* 이벤트 게시글 추가 */
    @Test
    public void saveTest() {
        /* 회원 추가 */
//        for (int i=1; i<=10; i++) {
//            Address address = new Address("서울시", "강남구");
//
//            Member member = Member.builder().memberName("테스트" + i).memberEmail("test" + i + "@naver.com").memberId("testId" + i)
//                    .memberPassword("1234").memberPhone("01012341234").memberPoint(0).address(address)
//                    .memberRole(Role.MEMBER).memberType(MemberType.GENERAL).memberStatus(MemberStatus.NORMAL)
//                    .build();
//            memberRepository.save(member);
//        }
//
//        /* 이벤트 게시글 추가 */
//        for (int i = 1; i <= 10; i++) {
//            EventBoard eventBoard1 = EventBoard.builder()
//                    .boardTitle("이벤트 게시글 제목" + i)
//                    .boardContent("이벤트 게시글 내용" + i)
//                    .eventAddress("서울시")
//                    .eventAddressDetail("강남구")
//                    .eventBusinessEmail("test1@naver.com")
//                    .eventBusinessName("기업이름" + i)
//                    .eventBusinessNumber("10000" + i)
//                    .eventBusinessTel("01012341234")
//                    .eventStartDate((LocalDate.now()))
//                    .eventEndDate(LocalDate.of(2023, 6, 20))
//                    .eventLikeCount(0)
//                    .eventReplyCount(0)
//                    .build();
//            memberRepository.findById(2L).ifPresent(member1 -> eventBoard1.setMember(member1));
////            eventBoard.setMember(member);
////            for(int j = 0; j < 5; j ++){
////                EventFile eventFile = new EventFile(UUID.randomUUID().toString(), "test" + i+1, 10L, eventBoard, 500, "");
////                eventFileRepository.save(eventFile);
////            }
//
//            eventBoardRepository.save(eventBoard1);
//        }
        for (int i=1; i<=10; i++) {
            Address address = new Address("서울시", "강남구");

            Member member = Member.builder().memberName("테스트" + i).memberEmail("test" + i + "@naver.com").memberId("testId" + i)
                    .memberPassword("1234").memberPhone("01012341234").memberPoint(0).address(address)
                    .memberRole(Role.MEMBER).memberType(MemberType.GENERAL).memberStatus(MemberStatus.NORMAL)
                    .build();
            memberRepository.save(member);
        }

        /* 이벤트 게시글 추가 */
        for (int i = 1; i <= 10; i++) {
            EventBoard eventBoard1 = EventBoard.builder()
                    .boardTitle("이벤트 게시글 제목" + i)
                    .boardContent("이벤트 게시글 내용" + i)
                    .eventBusinessEmail("test1@naver.com")
                    .eventBusinessName("기업이름" + i)
                    .eventBusinessNumber("10000" + i)
                    .eventBusinessTel("01012341234")
                    .eventStartDate((LocalDate.now()))
                    .eventEndDate(LocalDate.of(2023, 6, 20))
                    .eventLikeCount(0)
                    .eventReplyCount(0)
                    .build();
            memberRepository.findById(1L).ifPresent(member1 -> eventBoard1.setMember(member1));

//            for(int j = 0; j < 5; j ++){
//                EventFile eventFile = new EventFile(UUID.randomUUID().toString(), "test" + i+1, 10L, eventBoard, 500, "");
//                eventFileRepository.save(eventFile);
//            }

            eventBoardRepository.save(eventBoard1);
        }
    }

    /* 이벤트 게시글 상세글 */
    @Test
    public void findByIdTest() {
        eventBoardRepository.findById(1L).map(EventBoard::toString).ifPresent(log::info);
    }

    /* 이벤트 게시글 삭제 */
    @Test
    public void deleteTest() {
        eventBoardRepository.findById(101L).ifPresent(eventBoardRepository::delete);
    }

    @Test
    public void findEventBoardById_QueryDSLTest() {
        eventBoardRepository.findEventBoardById_QueryDSL(1L)
                .ifPresent(eventBoard -> log.info(eventBoard.toString()));
    }

    /* 이벤트 게시글 댓글 추가 */
    @Test
    public void eventReplySaveTest() {
        for (int i = 1; i <= 10; i++) {
            EventReply eventReply = new EventReply("댓글1" + i);
            eventReply.setEventBoard(eventBoardRepository.findById(31L).get());
            eventReplyRepository.save(eventReply);
        }
    }

    @Test
    public void updateTest(){
        eventBoardRepository.findById(31L).ifPresent(eventBoard -> {
            eventBoard.setBoardTitle("수정제목1");
            eventBoard.setBoardContent("수정내용1");
        });
    }

    /* 자유 게시판 댓글 수정 */
    @Test
    public void updateEventReplyTest(){
        eventBoardRepository.findById(45L).ifPresent(eventBoard -> eventBoard.getEventReplies().get(0).setReplyContent("수정된 댓글"));
    }
}
