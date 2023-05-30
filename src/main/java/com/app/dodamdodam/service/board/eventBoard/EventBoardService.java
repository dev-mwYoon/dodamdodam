package com.app.dodamdodam.service.board.eventBoard;

import com.app.dodamdodam.domain.EventBoardDTO;
import com.app.dodamdodam.domain.EventFileDTO;
import com.app.dodamdodam.domain.MemberDTO;
import com.app.dodamdodam.entity.event.EventBoard;
import com.app.dodamdodam.entity.event.EventFile;
import com.app.dodamdodam.entity.free.FreeBoard;
import com.app.dodamdodam.entity.member.Member;
import com.app.dodamdodam.search.EventBoardSearch;
import com.app.dodamdodam.search.board.AdminEventBoardSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface EventBoardService {

//    상세보기
    public EventBoardDTO getDetail(Long eventBoardId);
    // 저장
    public void register(EventBoardDTO eventBoardDTO, Long memberId);

    /* 이벤트 게시글 검색 */
    public List<EventBoardDTO> getEventBoardsBySearch(Pageable pageable, EventBoardSearch eventBoardSearch);

    //    현재 시퀀스 가져오기
    public EventBoard getCurrentSequence();

//    //    목록 페이징(최신순)
//    public Slice<EventBoardDTO> getEventBoards(Pageable pageable);

    // 수정
    public void update(EventBoardDTO eventBoardDTO);

    /* 이벤트 게시글 수정 */
    public void updateEventBoard(EventBoard updateBoard, Long boardId);

    // 삭제
    public void delete(Long eventBoardId);

    //     관리자 : 게시글 검색
    public Page<EventBoardDTO> showAdminEventWithSearch_QueryDSL(Pageable pageable, AdminEventBoardSearch adminEventBoardSearch);

    //    관리자 상세보기 : 게시글 상세
    public EventBoardDTO getAdminEventBoardDetail(Long id);

    /* 최근 작성된 이벤트 게시글 리스트 */
    public List<EventBoardDTO> getRecentEventBoardList();

    default EventBoardDTO eventBoardToDTO(EventBoard eventBoard){
        return EventBoardDTO.builder()
                .id(eventBoard.getId())
                .boardTitle(eventBoard.getBoardTitle())
                .boardContent(eventBoard.getBoardContent())
                .createdDate(eventBoard.getCreatedDate())
                .address(eventBoard.getAddress())
                .eventStatus(eventBoard.getEventStatus())
                .eventBusinessNumber(eventBoard.getEventBusinessNumber())
                .eventBusinessEmail(eventBoard.getEventBusinessEmail())
                .eventBusinessName(eventBoard.getEventBusinessName())
                .eventBusinessTel(eventBoard.getEventBusinessTel())
                .eventStartDate(eventBoard.getEventStartDate())
                .eventEndDate(eventBoard.getEventEndDate())
                .memberDTO(toMemberDTO(eventBoard.getMember()))
                .eventFileDTOS(eventBoard.getEventFiles().stream().map(e -> toEventFileDTO(e)).collect(Collectors.toList()))
                .build();
    }

    default EventBoardDTO toEventSearchBoardDTO(EventBoard eventBoard){
        return EventBoardDTO.builder()
                .id(eventBoard.getId())
                .boardTitle(eventBoard.getBoardTitle())
                .boardContent(eventBoard.getBoardContent())
                .createdDate(eventBoard.getCreatedDate())
                .address(eventBoard.getAddress())
                .eventBusinessNumber(eventBoard.getEventBusinessNumber())
                .eventBusinessEmail(eventBoard.getEventBusinessEmail())
                .eventBusinessName(eventBoard.getEventBusinessName())
                .eventBusinessTel(eventBoard.getEventBusinessTel())
                .eventStartDate(eventBoard.getEventStartDate())
                .eventEndDate(eventBoard.getEventEndDate())
                .memberDTO(toMemberDTO(eventBoard.getMember()))
                .build();
    }

    default MemberDTO toMemberDTO(Member member){
        return MemberDTO.builder()
                .id(member.getId())
                .memberId(member.getMemberId())
                .memberPassword(member.getMemberPassword())
                .memberName(member.getMemberName())
                .memberEmail(member.getMemberEmail())
                .memberPhone(member.getMemberPhone())
                .memberStatus(member.getMemberStatus())
                .memberPoint(member.getMemberPoint())
                .participationCount(member.getParticipationCount())
                .createdDate(member.getCreatedDate())
                .build();
    }

    default EventBoard toEventBoardEntity(EventBoardDTO eventBoardDTO){
        return EventBoard.builder()
                .boardTitle(eventBoardDTO.getBoardTitle())
                .boardContent(eventBoardDTO.getBoardContent())
                .eventBusinessName(eventBoardDTO.getEventBusinessName())
                .eventBusinessNumber(eventBoardDTO.getEventBusinessNumber())
                .eventBusinessEmail(eventBoardDTO.getEventBusinessEmail())
                .eventBusinessTel(eventBoardDTO.getEventBusinessTel())
                .eventStartDate(eventBoardDTO.getEventStartDate())
                .eventEndDate(eventBoardDTO.getEventEndDate())
                .address(eventBoardDTO.getAddress())
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

    default List<EventFileDTO> eventFileToDTO(List<EventFile> eventFiles){
        List<EventFileDTO> eventFileDTOS = new ArrayList<>();
        eventFiles.forEach(
                eventFile ->{
                    EventFileDTO eventFileDTO = EventFileDTO.builder()
                            .id(eventFile.getId())
                            .fileOriginalName(eventFile.getFileOriginalName())
                            .filePath(eventFile.getFilePath())
                            .fileUuid(eventFile.getFileUuid())
                            .fileSize(eventFile.getFileSize())
                            .fileType(eventFile.getFileType())
                            .build();
                    eventFileDTOS.add(eventFileDTO);
                }
        );
        return eventFileDTOS;
    }

    default EventFile toEventFileEntity(EventFileDTO eventFileDTO){
        return EventFile.builder()
                .id(eventFileDTO.getId())
                .fileOriginalName(eventFileDTO.getFileOriginalName())
                .fileUuid(eventFileDTO.getFileUuid())
                .filePath(eventFileDTO.getFilePath())
                .eventBoard(eventFileDTO.getEventBoard())
                .build();
    }

    default EventFileDTO toEventFileDTO(EventFile eventFile){
        return EventFileDTO.builder().id(eventFile.getId())
                .fileOriginalName(eventFile.getFileOriginalName())
                .filePath(eventFile.getFilePath())
                .fileUuid(eventFile.getFileUuid())
                .fileSize(eventFile.getFileSize())
                .build();
    }

}