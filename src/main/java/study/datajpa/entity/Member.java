package study.datajpa.entity;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "age"})
public class Member {

    @GeneratedValue
    @Id
    @Column(name = "member_id")
    private Long id;

    private int age;

    private String username;

    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;

    public Member(String username) {
        this.username = username;
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }


}
