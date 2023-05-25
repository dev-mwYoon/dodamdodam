package com.app.dodamdodam.entity.recruitment;

import com.app.dodamdodam.entity.board.Board;
import com.app.dodamdodam.entity.member.Member;
import com.app.dodamdodam.type.RecruitmentType;
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
@Getter
@Table(name = "TBL_RECRUITMENT_BOARD")
@ToString(exclude = "member", callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
public class RecruitmentBoard extends Board {
    @NotNull
    private String recruitmentSubtitle;
    @NotNull
    private LocalDate recruitmentDate;
    @NotNull
    private int recruitmentPeopleCount;
    @NotNull
    private String recruitmentOpenChatting;
    @NotNull
    private String recruitmentPassword;
    @ColumnDefault("'APPLYING'")
    @Enumerated(EnumType.STRING)
    @NotNull private RecruitmentType recruitmentStatus;
    @NotNull
    private String recruitmentAddress;
    @NotNull
    private String recruitmentAddressDetail;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recruitmentBoard", cascade = CascadeType.REMOVE)
    private List<RecruitmentFile> recruitmentFiles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recruitmentBoard", cascade = CascadeType.REMOVE)
    private List<RecruitmentReply> recruitmentReplies;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

//    @OneToMany(fetch = FetchType.LAZY)
//    private List<Member> recruitmentedMembers = new ArrayList<Member>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recruitmentBoard")
    private List<Recruitment> recruitments = new ArrayList<Recruitment>();

    public void addRecruitment(Recruitment recruitment){
        this.recruitments.add(recruitment);
    }

    public RecruitmentBoard(String recruitmentSubtitle, LocalDate recruitmentDate, int recruitmentPeopleCount, String recruitmentOpenChatting, String recruitmentPassword, String recruitmentAddress, String recruitmentAddressDetail) {
        this.recruitmentSubtitle = recruitmentSubtitle;
        this.recruitmentDate = recruitmentDate;
        this.recruitmentPeopleCount = recruitmentPeopleCount;
        this.recruitmentOpenChatting = recruitmentOpenChatting;
        this.recruitmentPassword = recruitmentPassword;
        this.recruitmentAddress = recruitmentAddress;
        this.recruitmentAddressDetail = recruitmentAddressDetail;
    }

    public void setRecruitmentSubtitle(String recruitmentSubtitle) {
        this.recruitmentSubtitle = recruitmentSubtitle;
    }

    public void setRecruitmentDate(LocalDate recruitmentDate) {
        this.recruitmentDate = recruitmentDate;
    }

    public void setRecruitmentPeopleCount(int recruitmentPeopleCount) {
        this.recruitmentPeopleCount = recruitmentPeopleCount;
    }

    public void setRecruitmentOpenChatting(String recruitmentOpenChatting) {
        this.recruitmentOpenChatting = recruitmentOpenChatting;
    }

    public void setRecruitmentPassword(String recruitmentPassword) {
        this.recruitmentPassword = recruitmentPassword;
    }

    public void setRecruitmentStatus(RecruitmentType recruitmentStatus) {
        this.recruitmentStatus = recruitmentStatus;
    }

    public void setRecruitmentAddress(String recruitmentAddress) {
        this.recruitmentAddress = recruitmentAddress;
    }

    public void setRecruitmentAddressDetail(String recruitmentAddressDetail) {
        this.recruitmentAddressDetail = recruitmentAddressDetail;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Builder
    public RecruitmentBoard(Long id, String boardTitle, String boardContent, String recruitmentSubtitle, LocalDate recruitmentDate, int recruitmentPeopleCount, String recruitmentOpenChatting, String recruitmentPassword, RecruitmentType recruitmentStatus, String recruitmentAddress, String recruitmentAddressDetail, List<RecruitmentFile> recruitmentFiles, List<RecruitmentReply> recruitmentReplies, Member member, List<Recruitment> recruitments) {
        super(id, boardTitle, boardContent);
        this.recruitmentSubtitle = recruitmentSubtitle;
        this.recruitmentDate = recruitmentDate;
        this.recruitmentPeopleCount = recruitmentPeopleCount;
        this.recruitmentOpenChatting = recruitmentOpenChatting;
        this.recruitmentPassword = recruitmentPassword;
        this.recruitmentStatus = recruitmentStatus;
        this.recruitmentAddress = recruitmentAddress;
        this.recruitmentAddressDetail = recruitmentAddressDetail;
        this.recruitmentFiles = recruitmentFiles;
        this.recruitmentReplies = recruitmentReplies;
        this.member = member;
        this.recruitments = recruitments;
    }
}
