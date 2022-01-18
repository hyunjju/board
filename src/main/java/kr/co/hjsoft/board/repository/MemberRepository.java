package kr.co.hjsoft.board.repository;

import kr.co.hjsoft.board.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
