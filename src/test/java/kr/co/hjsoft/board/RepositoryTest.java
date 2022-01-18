package kr.co.hjsoft.board;

import kr.co.hjsoft.board.entity.Board;
import kr.co.hjsoft.board.entity.Member;
import kr.co.hjsoft.board.entity.Reply;
import kr.co.hjsoft.board.repository.BoardRepository;
import kr.co.hjsoft.board.repository.MemberRepository;
import kr.co.hjsoft.board.repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@SpringBootTest
public class RepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;


   // @Test
    public void insertMembers() {
        for (int i = 1; i <= 100; i = i + 1) {
            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .password("1234")
                    .name("USER" + i)
                    .build();
            memberRepository.save(member);
        }
    }

   // @Test
    public void insertBoards() {
        for (int i = 1; i <= 100; i = i + 1) {
            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .password("1234")
                    .name("USER" + i)
                    .build();
            Board board = Board.builder()
                    .title("제목..."+i)
                    .content("내용..."+i)
                    .writer(member)
                    .build();
            boardRepository.save(board);
        }
    }


    //@Test
    public void insertReplys() {
        Random r = new Random();
        for (long i = 1; i <= 300; i = i + 1) {
            Board board = Board.builder()
                    .bno((long)(r.nextInt(100)+1))
                    .build();
            Reply reply = Reply.builder()
                    .rno(i)
                    .text("댓글..."+i)
                    .replyer("빌런")
                    .board(board)
                    .build();
            replyRepository.save(reply);
        }
    }

    //게시물 1개를 가져오는 메소드 테스트
    //@Test
    public void eagerLoading(){
        Optional<Board> board = boardRepository.findById(100L);
        if (board.isPresent()){
            System.out.println(board.get());
            System.out.println(board.get().getWriter());
        }
    }

    //게시물 1개를 가져오는 메소드 테스트
    //@Test
    @Transactional
    public void lazyLoading(){
        Optional<Board> board = boardRepository.findById(100L);
        if (board.isPresent()){
            System.out.println(board.get());
            System.out.println(board.get().getWriter());
        }
    }

    //Board 와 Member 를 join 하는 메소드
    //@Test
    public void testJoin1(){
        Object result = boardRepository.getBoardWithWriter(100L);
        Object [] arr = (Object[]) result;
        System.out.println(arr);
    }

    //@Test
    public void testJoin2(){
        List<Object[]> result = boardRepository.getBoardWithReply(36L);
        for(Object [] arr : result){
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void testBoardList(){
        //페이징 조건 생성 - 0 페이지에 10개의 데이터를 bno 의 내림차순으로 가져오기
        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
        result.get().forEach(row->{
            Object[]ar=(Object[]) row;
            System.out.println(Arrays.toString(ar));
        });
    }
}



