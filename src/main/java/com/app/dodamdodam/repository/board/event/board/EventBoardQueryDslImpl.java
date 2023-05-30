package com.app.dodamdodam.repository.board.event.board;

import com.app.dodamdodam.entity.event.EventBoard;
import com.app.dodamdodam.entity.event.QEventBoard;
import com.app.dodamdodam.search.EventBoardSearch;
import com.app.dodamdodam.search.board.AdminEventBoardSearch;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static com.app.dodamdodam.entity.event.QEventBoard.eventBoard;
import static com.app.dodamdodam.entity.event.QEventFile.eventFile;

public class EventBoardQueryDslImpl implements EventBoardQueryDsl {
    @Autowired
    private JPAQueryFactory query;

    /*검색 + 목록*/
    @Override
    public Page<EventBoard> findEventBoardBySearchWithPaging_QueryDSL(EventBoardSearch eventBoardSearch, Pageable pageable) {
        BooleanExpression writerNameEq = eventBoardSearch.getWriterName() == null ? null : eventBoard.member.memberName.contains(eventBoardSearch.getWriterName());
        BooleanExpression eventTitleEq = eventBoardSearch.getBoardTitle() == null ? null : eventBoard.boardTitle.contains(eventBoardSearch.getBoardTitle());
        BooleanExpression eventContentEq = eventBoardSearch.getBoardContent() == null ? null : eventBoard.boardContent.contains(eventBoardSearch.getBoardContent());

        List<EventBoard> eventBoards = query.select(eventBoard)
                .from(eventBoard)
                .join(eventBoard.member).fetchJoin()
                .leftJoin(eventBoard.eventFiles).fetchJoin()
                .where(writerNameEq.or(eventTitleEq).or(eventContentEq))
                .orderBy(eventBoard.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long count = query.select(eventBoard.count()).where(writerNameEq.or(eventTitleEq).or(eventContentEq)).from(eventBoard).fetchOne();

        return new PageImpl<>(eventBoards, pageable, count);
    }


    // 리스트
    @Override
    public Slice<EventBoard> findByMemberIdEventBoard_QueryDsl(String sort, Pageable pageable) {
        List<EventBoard> foundEventList = query.select(eventBoard)
                .from(eventBoard)
                .leftJoin(eventBoard.eventFiles, eventFile)
                .fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (foundEventList.size() > pageable.getPageSize()) {
            foundEventList.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(foundEventList, pageable, hasNext);
    }

    @Override
    public EventBoard getCurrentSequence_QueryDsl() {
        return query.select(eventBoard)
                .from(eventBoard)
                .orderBy(eventBoard.id.desc())
                .limit(1)
                .fetchOne();
    }

    // 상세보기
    @Override
    public Optional<EventBoard> findEventBoardById_QueryDSL(Long eventBoardId) {
        EventBoard eventBoard = query.select(QEventBoard.eventBoard)
                .from(QEventBoard.eventBoard)
                .join(QEventBoard.eventBoard.member).fetchJoin()
                .leftJoin(QEventBoard.eventBoard.eventFiles).fetchJoin()
                .where(QEventBoard.eventBoard.id.eq(eventBoardId))
                .fetchOne();
        return Optional.of(eventBoard);
    }

    /* 관리자 이벤트 게시글 검색 */
    @Override
    public Page<EventBoard> findAdmindEventBoardWithPaging_QueryDSL(AdminEventBoardSearch adminEventBoardSearch, Pageable pageable) {
        BooleanExpression searchAll = null;
        if (adminEventBoardSearch.getBoardTitle() != null || adminEventBoardSearch.getMemberName() != null || adminEventBoardSearch.getEventAddress() != null || adminEventBoardSearch.getEventAddressDetail() != null) {
            BooleanExpression searchTitle = adminEventBoardSearch.getBoardTitle() != null ? eventBoard.boardTitle.contains(adminEventBoardSearch.getBoardTitle()) : null;
            BooleanExpression searchName = adminEventBoardSearch.getMemberName() != null ? eventBoard.member.memberName.contains(adminEventBoardSearch.getMemberName()) : null;
            BooleanExpression searchAddress = adminEventBoardSearch.getMemberName() != null ? eventBoard.address.address.contains(adminEventBoardSearch.getEventAddress()) : null;
            BooleanExpression searchAddressDetail = adminEventBoardSearch.getMemberName() != null ? eventBoard.address.addressDetail.contains(adminEventBoardSearch.getEventAddressDetail()) : null;

            searchAll = searchTitle.or(searchName).or(searchAddress).or(searchAddressDetail);
        }

        List<EventBoard> adminEventBoards = query.select(eventBoard)
                .from(eventBoard)
                .where(searchAll)
                .orderBy(eventBoard.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long count = query.select(eventBoard.count()).from(eventBoard).where(searchAll).fetchOne();

        return new PageImpl<>(adminEventBoards, pageable, count);
    }

    // 게시글 검색
    @Override
    public List<EventBoard> findEventBoardWithSearch_QueryDSL(String search) {
        BooleanExpression eventTitleEq = search == null ? null : eventBoard.boardTitle.like("%" + search + "%");
        BooleanExpression eventContentEq = search == null ? null : eventBoard.boardContent.like("%" + search + "%");
        List<EventBoard> eventSearchs = query.select(eventBoard)
                .from(eventBoard)
                .where(eventTitleEq)
                .where(eventContentEq)
                .leftJoin(eventBoard.eventFiles, eventFile)
                .orderBy(eventBoard.id.desc())
                .fetch();

        return eventSearchs;
    }

    /* 댓글 id로 자유게시글 접근해서 그 안에 달린 댓글 개수 가져오기 */
    @Override
    public Integer findReplyCountByReplyId_QueryDsl(Long replyId) {
        return query.select(eventBoard.eventReplies.size()).from(eventBoard).where(eventBoard.eventReplies.any().id.eq(replyId)).fetchOne();
    }

    @Override
    public List<EventBoard> findRecentEventBoardList_QueryDSL() {
        return query.selectFrom(eventBoard)
                .leftJoin(eventBoard.eventFiles).fetchJoin()
                .orderBy(eventBoard.id.desc())
                .limit(5)
                .fetch();
    }

    /* 게시글 상세페이지 board 정보, reply정보 */
    @Override
    public Optional<EventBoard> findEventBoardAndEventRepliesById_QueryDSL(Long boardId) {
        return Optional.ofNullable(query.select(eventBoard).from(eventBoard)
                .leftJoin(eventBoard.eventReplies).fetchJoin()
                .where(eventBoard.id.eq(boardId))
                .fetchOne());
    }


}
