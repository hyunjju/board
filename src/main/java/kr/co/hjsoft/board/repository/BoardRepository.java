package kr.co.hjsoft.board.repository;

import kr.co.hjsoft.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    //Member 와 Board Entity 의 Join 을 수행하는 메소드를 생성
    //Board Entity 에는 Member Entity 와 연관 관계를 갖는 writer 가 존재

    //bno에 해당하는 Board 를 가져올 때 Member 에 대한 정보도 가져오기
    @Query("select b, w from Board b left join b.writer w where b.bno = :bno")
    Object getBoardWithWriter(@Param("bno")Long bno);

    //Reply 와 Board Entity 의 Join 을 수행하는 메소드를 생성
    //Board Entity 에서는 Reply Entity 와 연관 관계를 가지고 있지 않음
    //양쪽의 공통 된 속성을 찾아야 한다
    //Reply가 Board 정보를 BOARd 라는 속성으로 가지고 있음

    //bno에 해당하는 Board 를 가져올 때 Member 에 대한 정보도 가져오기
    @Query("select b, r from Board b left join Reply r on r.board= b where b.bno = :bno")
    //하나의 게시글에 댓글이 여러개 일 수 있어서 리턴 타입은 List
    List<Object []> getBoardWithReply(@Param("bno")Long bno);

    //목록 보기를 위한 메소드
    //JPQL 에서는 Page 단위로 리턴할 때 countQuery 가 필수
    @Query(value = "select b, w, count(r) from Board b left join b.writer w left join Reply r On r.board =b group by b",
    countQuery = "select count(b) from Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);
}
