package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void testMember(){
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId()).orElseGet(() -> new Member("NotFound"));

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(member).isEqualTo(findMember).isEqualTo(savedMember);

    }

    @Test
    public void basicCRUD() {
        // 초기 세팅
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        // 단건 조회 검증
        Member findMember1 = memberRepository.findById(member1.getId()).orElseGet(() -> new Member("NotFound", -1, null));
        Member findMember2 = memberRepository.findById(member2.getId()).orElseGet(() -> new Member("NotFound", -1, null));
        assertThat(member1).isEqualTo(findMember1);
        assertThat(member2).isEqualTo(findMember2);

        // 리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        // 카운트 검증
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2L);

        // 삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);
        assertThat(memberRepository.count()).isEqualTo(memberRepository.findAll().size()).isEqualTo(0L);
    }
}
