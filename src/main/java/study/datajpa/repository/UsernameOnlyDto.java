package study.datajpa.repository;

import lombok.Getter;

@Getter
public class UsernameOnlyDto {

    private final String username;
    private final int age;

    public UsernameOnlyDto(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
