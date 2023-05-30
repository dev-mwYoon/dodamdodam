package com.app.dodamdodam.entity.event;

import com.app.dodamdodam.entity.board.Board;
import com.app.dodamdodam.entity.embeddable.Address;
import com.app.dodamdodam.entity.member.Member;
import com.app.dodamdodam.type.EventType;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @ToString(exclude = "member", callSuper = true)
@Table(name = "TBL_EVENT_BOARD")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
public class EventBoard extends Board {

//    이벤트 게시글 비지니스 작성
//    @NotNull private String eventAddress;
//    @NotNull private String eventAddressDetail;
    @Embedded
    @NotNull private Address address;
    @NotNull private LocalDate eventStartDate;
    @NotNull private LocalDate eventEndDate;
//    좋아요 수
    @Column(columnDefinition = "integer default 0")
    private Integer eventLikeCount;

    /*리뷰 수*/
    @ColumnDefault(value="0")
    private Integer eventReplyCount;

//   진행전, 진행중, 진행마감
    @ColumnDefault("'HOLD'")
    @Enumerated(EnumType.STRING)
     private EventType eventStatus;

     private String eventBusinessNumber;
     private String eventBusinessName;
     private String eventBusinessTel;
     private String eventBusinessEmail;

    /* 댓글 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "eventBoard", cascade = CascadeType.REMOVE)
    private List<EventReply> eventReplies = new ArrayList<>();;

    /* 파일 */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "eventBoard")
    private List<EventFile> eventFiles = new ArrayList<>();

// 작성한 유저
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Builder
    public EventBoard(Long id, String boardTitle, String boardContent, Address address, LocalDate eventStartDate, LocalDate eventEndDate, Integer eventLikeCount, Integer eventReplyCount, EventType eventStatus, String eventBusinessNumber, String eventBusinessName, String eventBusinessTel, String eventBusinessEmail, List<EventFile> eventFiles, Member member) {
        super(id, boardTitle, boardContent);
        this.address = address;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.eventLikeCount = eventLikeCount;
        this.eventReplyCount = eventReplyCount;
        this.eventStatus = eventStatus;
        this.eventBusinessNumber = eventBusinessNumber;
        this.eventBusinessName = eventBusinessName;
        this.eventBusinessTel = eventBusinessTel;
        this.eventBusinessEmail = eventBusinessEmail;
        this.eventFiles = eventFiles;
        this.member = member;
    }

    public void setEventLikeCount(Integer eventLikeCount){
        this.eventLikeCount = eventLikeCount;
    }

    public void setEventBoardReplyCount(Integer eventReplyCount) {
        this.eventReplyCount = eventReplyCount;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}