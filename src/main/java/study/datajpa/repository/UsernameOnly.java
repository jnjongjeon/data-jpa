package study.datajpa.repository;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {
    //@Value("#{target.username + ' ' + target.age}") // 원하는 결과를 조합 가능하다 오픈 프로젝션, 대신 이렇게하면 엔티티 전체를 가져옴. select 쿼리 낭비
    String getUsername();
}
