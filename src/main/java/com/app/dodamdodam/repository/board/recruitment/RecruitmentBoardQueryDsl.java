package com.app.dodamdodam.repository.board.recruitment;

import com.app.dodamdodam.entity.purchase.PurchaseBoard;
import com.app.dodamdodam.entity.recruitment.RecruitmentBoard;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecruitmentBoardQueryDsl {
//    세션에 담긴 id 값 받아와서 내가 작성한 자유 게시글 리스트 가져오기
    public List<RecruitmentBoard> findRecruitmentBoardListByMemberId(Pageable pageable, Long memberId);
}
