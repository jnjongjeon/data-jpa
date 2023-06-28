package study.datajpa.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        System.out.println("==================1");
        System.out.println(member.getId());
        System.out.println("================");
        Member savedMember = memberJpaRepository.save(member);
        System.out.println("==================2");
        System.out.println(member.getId());
        System.out.println("================");
        Member findMember = memberJpaRepository.find(savedMember.getId());
        System.out.println("==================3");
        System.out.println(member.getId());
        System.out.println("================");

        System.out.println(member==savedMember);
        System.out.println(member);
        System.out.println(savedMember);
        assertThat(member).isEqualTo(savedMember);
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

    }
}