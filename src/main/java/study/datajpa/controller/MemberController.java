package study.datajpa.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();

    }

    /**
     * 위에 코드와 동일하다. 다만 권장하진 않는다. 스프링이 해주는거임
     */
    @GetMapping("/members2/{id}")
    public String findMember(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    /**
     * ?page=0&size=10&sort=id,desc&sort=username,desc
     */
    @GetMapping("/members")
    public Result list(@PageableDefault(size = 5) Pageable pageable){

        Page<Member> page = memberRepository.findAll(pageable);
        List<MemberDto> members = page.stream().map(m -> new MemberDto(m.getId(), m.getUsername(), null))
                .collect(Collectors.toList());

        return new Result(members, page.getPageable());
    }

    @GetMapping("/members2")
    public Page<MemberDto> list2(@PageableDefault(size = 5) Pageable pageable){
        return memberRepository.findAll(pageable).map(MemberDto::new);
    }



   //@PostConstruct
    public void init() {
        for (int i = 0; i < 100; i++) {
            memberRepository.save(new Member("user" + i, i));
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
        private Pageable pageable;

    }

}
