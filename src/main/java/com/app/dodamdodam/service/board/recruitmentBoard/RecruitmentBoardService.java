package com.app.dodamdodam.service.board.recruitmentBoard;

import com.app.dodamdodam.domain.*;
import com.app.dodamdodam.entity.member.Member;
import com.app.dodamdodam.entity.recruitment.Recruitment;
import com.app.dodamdodam.entity.recruitment.RecruitmentBoard;
import com.app.dodamdodam.entity.recruitment.RecruitmentFile;
import com.app.dodamdodam.search.board.AdminRecruitmentSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

public interface RecruitmentBoardService {
    //    작성하기
    public void register(RecruitmentBoardDTO recruitmentBoardDTO, Long memberId);

//    내가 작성한 모집 게시글 목록
    public List<RecruitmentBoardFileDTO> getRecruimentBoardListByMemberId(Pageable pageable, Long memberId);

//    내가 작성한 모집 게시글에 참가한 인원들 목록
    public RecruitmentMemberDTO getRecruitmentedMembersByBoardId(Long boardId);

//    내가 참가한 모집 게시글 목록
    public List<RecruitmentBoardFileDTO> getRecruimentedBoardListByMemberId(Pageable pageable, Long memberId);

//    모집 게시글 상세 보기
    public RecruitmentBoardFileDTO getRecruitmentBoardDetailByBoardId(Long boardId);

//    모집 게시글 삭제
    public void deleteRecruitmentBoardByBoardId(Long boardId);

//    모집 게시글 수정
    public void updateRecruitmentBoard(RecruitmentBoardFileDTO updatedBoard, Long boardId);

//    관리자 목록 게시판
    public Page<RecruitmentBoardFileDTO> showList(Pageable pageable);

//    관리자 검색
    public Page<RecruitmentBoardFileDTO> showAdminRecruitmentWithSearch_QueryDSL(Pageable pageable, AdminRecruitmentSearch adminRecruitmentSearch);

//    관리자 삭제
    public void deleteRecruitmentBoard(List<Long> recruitmentBoardIds);

//    관리자 상세보기
    public RecruitmentBoardFileDTO getAdminRecruitmentBoardDetail(Long id);

//    모집 게시글 목록
    public List<RecruitmentBoardFileDTO> getRecruitmentBoardListByPaging(Pageable pageable);

    /* 최근 작성된 자유 게시글 리스트 */
    public List<RecruitmentBoardFileDTO> getRecentRecruitmentBoardList();

//    모집 신청
    public void getRecruitment(Long boardId, Long memberId);

    default RecruitmentBoardFileDTO toRecruitmentBoardFileDto(RecruitmentBoard recruitmentBoard){
        return RecruitmentBoardFileDTO.builder()
                .id(recruitmentBoard.getId())
                .boardContent(recruitmentBoard.getBoardContent())
                .boardTitle(recruitmentBoard.getBoardTitle())
                .memberDTO(toMemberDTO(recruitmentBoard.getMember()))
                .recruitmentAddress(recruitmentBoard.getRecruitmentAddress())
                .recruitmentAddressDetail(recruitmentBoard.getRecruitmentAddressDetail())
                .recruitmentDate(recruitmentBoard.getRecruitmentDate())
                .recruitmentDTOS(recruitmentBoard.getRecruitments().stream().map(recruitment -> toRecruitmentDto(recruitment)).collect(Collectors.toList()))
                .recruitmentFileDTOS(recruitmentBoard.getRecruitmentFiles().stream().map(recruitmentFile -> toRecruitmentFileDto(recruitmentFile)).collect(Collectors.toList()))
                .recruitmentOpenChatting(recruitmentBoard.getRecruitmentOpenChatting())
                .recruitmentPassword(recruitmentBoard.getRecruitmentPassword())
                .recruitmentPeopleCount(recruitmentBoard.getRecruitmentPeopleCount())
                .recruitmentStatus(recruitmentBoard.getRecruitmentStatus())
                .recruitmentSubtitle(recruitmentBoard.getRecruitmentSubtitle())
                .build();
    }

    default MemberDTO toMemberDTO(Member member){
        return MemberDTO.builder().id(member.getId())
                .memberEmail(member.getMemberEmail())
                .memberName(member.getMemberName())
                .memberId(member.getMemberId())
                .memberPassword(member.getMemberPassword())
                .memberPhone(member.getMemberPhone())
                .memberStatus(member.getMemberStatus())
                .memberPoint(member.getMemberPoint())
                .participationCount(member.getParticipationCount())
                .address(member.getAddress())
                .memberType(member.getMemberType())
                .build();
    }

    default RecruitmentDTO toRecruitmentDto(Recruitment recruitment){
        return RecruitmentDTO.builder().id(recruitment.getId())
                .member(toMemberDTO(recruitment.getMember()))
                .build();
    }

    default RecruitmentFileDTO toRecruitmentFileDto(RecruitmentFile recruitmentFile){
        return RecruitmentFileDTO.builder()
                .fileOriginalName(recruitmentFile.getFileOriginalName())
                .filePath(recruitmentFile.getFilePath())
                .fileSize(recruitmentFile.getFileSize())
                .fileUuid(recruitmentFile.getFileUuid())
                .id(recruitmentFile.getId())
                .build();
    }

    default RecruitmentMemberDTO toRecruitmentMemberDTO(RecruitmentBoard recruitmentBoard){
        return RecruitmentMemberDTO.builder()
                .recruitmentBoardId(recruitmentBoard.getId())
                .memberDTOS(recruitmentBoard.getRecruitments().stream().map(recruitment -> toMemberDTO(recruitment.getMember())).collect(Collectors.toList()))
                .build();
    }

    default Member toMemberEntity(MemberDTO memberDTO){
        return Member.builder()
                .memberId(memberDTO.getMemberId())
                .memberPassword(memberDTO.getMemberPassword())
                .memberEmail(memberDTO.getMemberEmail())
                .memberName(memberDTO.getMemberName())
                .memberPhone(memberDTO.getMemberPhone())
                .memberStatus(memberDTO.getMemberStatus())
                .memberRole(memberDTO.getMemberRole())
                .build();
    }

    default RecruitmentFile toRecruitmentFileEntity(RecruitmentFileDTO recruitmentFileDTO){
        return RecruitmentFile.builder()
                .id(recruitmentFileDTO.getId())
                .fileOriginalName(recruitmentFileDTO.getFileOriginalName())
                .fileUuid(recruitmentFileDTO.getFileUuid())
                .filePath(recruitmentFileDTO.getFilePath())
                .build();
    }

    default RecruitmentBoard toRecruitmentBoardEntity(RecruitmentBoardDTO recruitmentBoardDTO){
        return RecruitmentBoard.builder()
                .id(recruitmentBoardDTO.getId())
                .boardTitle(recruitmentBoardDTO.getBoardTitle())
                .boardContent(recruitmentBoardDTO.getBoardContent())
                .member(toMemberEntity(recruitmentBoardDTO.getMemberDTO()))
                .recruitmentSubtitle(recruitmentBoardDTO.getRecruitmentSubtitle())
                .recruitmentAddress(recruitmentBoardDTO.getRecruitmentAddress())
                .recruitmentAddressDetail(recruitmentBoardDTO.getRecruitmentAddressDetail())
                .recruitmentDate(recruitmentBoardDTO.getRecruitmentDate())
                .recruitmentPeopleCount(recruitmentBoardDTO.getRecruitmentPeopleCount())
                .recruitmentOpenChatting(recruitmentBoardDTO.getRecruitmentOpenChatting())
                .build();
    }
}
